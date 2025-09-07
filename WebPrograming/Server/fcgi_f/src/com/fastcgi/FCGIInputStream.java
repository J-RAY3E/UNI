/*
 * Decompiled with CFR 0.152.
 */
package com.fastcgi;

import com.fastcgi.FCGIMessage;
import com.fastcgi.FCGIRequest;
import java.io.IOException;
import java.io.InputStream;

public class FCGIInputStream
extends InputStream {
    private static final String RCSID = "$Id: FCGIInputStream.java,v 1.4 2000/03/21 12:12:25 robs Exp $";
    public int rdNext;
    public int stop;
    public boolean isClosed;
    private int errno;
    private Exception errex;
    public byte[] buff;
    public int buffLen;
    public int buffStop;
    public int type;
    public int contentLen;
    public int paddingLen;
    public boolean skip;
    public boolean eorStop;
    public FCGIRequest request;
    public InputStream in;

    public FCGIInputStream(InputStream inStream, int bufLen, int streamType, FCGIRequest inReq) {
        this.in = inStream;
        this.buffLen = Math.min(bufLen, 65535);
        this.buff = new byte[this.buffLen];
        this.type = streamType;
        this.buffStop = 0;
        this.rdNext = 0;
        this.stop = 0;
        this.isClosed = false;
        this.contentLen = 0;
        this.paddingLen = 0;
        this.skip = false;
        this.eorStop = false;
        this.request = inReq;
    }

    @Override
    public int read() throws IOException {
        if (this.rdNext != this.stop) {
            return this.buff[this.rdNext++] & 0xFF;
        }
        if (this.isClosed) {
            return -1;
        }
        this.fill();
        if (this.rdNext != this.stop) {
            return this.buff[this.rdNext++] & 0xFF;
        }
        return -1;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (len <= 0) {
            return 0;
        }
        if (len <= this.stop - this.rdNext) {
            System.arraycopy(this.buff, this.rdNext, b, off, len);
            this.rdNext += len;
            return len;
        }
        int bytesMoved = 0;
        while (true) {
            if (this.rdNext != this.stop) {
                int m = Math.min(len - bytesMoved, this.stop - this.rdNext);
                System.arraycopy(this.buff, this.rdNext, b, off, m);
                this.rdNext += m;
                if ((bytesMoved += m) == len) {
                    return bytesMoved;
                }
                off += m;
            }
            if (this.isClosed) {
                return bytesMoved;
            }
            this.fill();
        }
    }

    public void fill() throws IOException {
        byte[] headerBuf = new byte[8];
        int headerLen = 0;
        int status = 0;
        int count = 0;
        block8: while (true) {
            if (this.rdNext == this.buffStop) {
                try {
                    count = this.in.read(this.buff, 0, this.buffLen);
                }
                catch (IOException e) {
                    this.setException(e);
                    return;
                }
                if (count == 0) {
                    this.setFCGIError(-3);
                    return;
                }
                this.rdNext = 0;
                this.buffStop = count;
            }
            if (this.contentLen > 0) {
                count = Math.min(this.contentLen, this.buffStop - this.rdNext);
                this.contentLen -= count;
                if (!this.skip) {
                    this.stop = this.rdNext + count;
                    return;
                }
                this.rdNext += count;
                if (this.contentLen > 0) continue;
                this.skip = false;
            }
            if (this.paddingLen > 0) {
                count = Math.min(this.paddingLen, this.buffStop - this.rdNext);
                this.paddingLen -= count;
                this.rdNext += count;
                if (this.paddingLen > 0) continue;
            }
            if (this.eorStop) {
                this.stop = this.rdNext;
                this.isClosed = true;
                return;
            }
            count = Math.min(headerBuf.length - headerLen, this.buffStop - this.rdNext);
            System.arraycopy(this.buff, this.rdNext, headerBuf, headerLen, count);
            this.rdNext += count;
            if ((headerLen += count) < headerBuf.length) continue;
            headerLen = 0;
            this.eorStop = true;
            this.stop = this.rdNext;
            status = 0;
            status = new FCGIMessage(this).processHeader(headerBuf);
            this.eorStop = false;
            this.isClosed = false;
            switch (status) {
                case 0: {
                    if (this.contentLen != 0) continue block8;
                    this.stop = this.rdNext;
                    this.isClosed = true;
                    return;
                }
                case 1: {
                    this.skip = true;
                    continue block8;
                }
                case 2: {
                    return;
                }
                case 3: {
                    continue block8;
                }
            }
            break;
        }
        this.setFCGIError(status);
    }

    @Override
    public long skip(long n) throws IOException {
        byte[] data = new byte[(int)n];
        return this.in.read(data);
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

    public int getFCGIError() {
        return this.errno;
    }

    public Exception getException() {
        return this.errex;
    }

    public void setReaderType(int streamType) {
        this.type = streamType;
        this.eorStop = false;
        this.skip = false;
        this.contentLen = 0;
        this.paddingLen = 0;
        this.stop = this.rdNext;
        this.isClosed = false;
    }

    @Override
    public void close() throws IOException {
        this.isClosed = true;
        this.stop = this.rdNext;
    }

    @Override
    public int available() throws IOException {
        return this.stop - this.rdNext + this.in.available();
    }
}

