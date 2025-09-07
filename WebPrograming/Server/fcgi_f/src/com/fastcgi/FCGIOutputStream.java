/*
 * Decompiled with CFR 0.152.
 */
package com.fastcgi;

import com.fastcgi.FCGIMessage;
import com.fastcgi.FCGIRequest;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;

public class FCGIOutputStream
extends OutputStream {
    private static final String RCSID = "$Id: FCGIOutputStream.java,v 1.3 2000/03/21 12:12:26 robs Exp $";
    public int wrNext;
    public int stop;
    public boolean isClosed;
    private int errno;
    private Exception errex;
    public byte[] buff;
    public int buffLen;
    public int buffStop;
    public int type;
    public boolean isAnythingWritten;
    public boolean rawWrite;
    public FCGIRequest request;
    public OutputStream out;

    public FCGIOutputStream(OutputStream outStream, int bufLen, int streamType, FCGIRequest inreq) {
        this.out = outStream;
        this.buffLen = Math.min(bufLen, 65535);
        this.buff = new byte[this.buffLen];
        this.type = streamType;
        this.stop = this.buffStop = this.buffLen;
        this.isAnythingWritten = false;
        this.rawWrite = false;
        this.wrNext = 8;
        this.isClosed = false;
        this.request = inreq;
    }

    @Override
    public void write(int c) throws IOException {
        if (this.wrNext != this.stop) {
            this.buff[this.wrNext++] = (byte)c;
            return;
        }
        if (this.isClosed) {
            throw new EOFException();
        }
        this.empty(false);
        if (this.wrNext != this.stop) {
            this.buff[this.wrNext++] = (byte)c;
            return;
        }
        throw new EOFException();
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (len <= this.stop - this.wrNext) {
            System.arraycopy(b, off, this.buff, this.wrNext, len);
            this.wrNext += len;
            return;
        }
        int bytesMoved = 0;
        while (true) {
            if (this.wrNext != this.stop) {
                int m = Math.min(len - bytesMoved, this.stop - this.wrNext);
                System.arraycopy(b, off, this.buff, this.wrNext, m);
                this.wrNext += m;
                if ((bytesMoved += m) == len) {
                    return;
                }
                off += m;
            }
            if (this.isClosed) {
                throw new EOFException();
            }
            this.empty(false);
        }
    }

    public void empty(boolean doClose) throws IOException {
        if (!this.rawWrite) {
            int cLen = this.wrNext - 8;
            if (cLen > 0) {
                System.arraycopy(new FCGIMessage().makeHeader(this.type, this.request.requestID, cLen, 0), 0, this.buff, 0, 8);
            } else {
                this.wrNext = 0;
            }
        }
        if (doClose) {
            this.writeCloseRecords();
        }
        if (this.wrNext != 0) {
            this.isAnythingWritten = true;
            try {
                this.out.write(this.buff, 0, this.wrNext);
            }
            catch (IOException e) {
                this.setException(e);
                return;
            }
            this.wrNext = 0;
        }
        if (!this.rawWrite) {
            this.wrNext += 8;
        }
    }

    @Override
    public void close() throws IOException {
        if (this.isClosed) {
            return;
        }
        this.empty(true);
        this.isClosed = true;
        this.stop = this.wrNext;
    }

    @Override
    public void flush() throws IOException {
        if (this.isClosed) {
            return;
        }
        this.empty(false);
    }

    public void setFCGIError(int errnum) {
        if (this.errno == 0) {
            this.errno = errnum;
        }
        this.isClosed = true;
    }

    public void setException(Exception errexpt) {
        if (this.errex == null) {
            this.errex = errexpt;
        }
        this.isClosed = true;
    }

    public void clearFCGIError() {
        this.errno = 0;
    }

    public void clearException() {
        this.errex = null;
    }

    public int etFCGIError() {
        return this.errno;
    }

    public Exception getException() {
        return this.errex;
    }

    public void writeCloseRecords() throws IOException {
        FCGIMessage msg = new FCGIMessage();
        this.rawWrite = true;
        if (this.type != 7 || this.wrNext != 0 || this.isAnythingWritten) {
            byte[] hdr = new byte[8];
            System.arraycopy(msg.makeHeader(this.type, this.request.requestID, 0, 0), 0, hdr, 0, 8);
            this.write(hdr, 0, hdr.length);
        }
        if (this.request.numWriters == 1) {
            byte[] endReq = new byte[16];
            System.arraycopy(msg.makeHeader(3, this.request.requestID, 8, 0), 0, endReq, 0, 8);
            System.arraycopy(msg.makeEndrequestBody(this.request.appStatus, 0), 0, endReq, 8, 8);
            this.write(endReq, 0, 16);
        }
        --this.request.numWriters;
    }
}

