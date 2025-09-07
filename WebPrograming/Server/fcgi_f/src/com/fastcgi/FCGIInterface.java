/*
 * Decompiled with CFR 0.152.
 */
package com.fastcgi;

import com.fastcgi.FCGIInputStream;
import com.fastcgi.FCGIMessage;
import com.fastcgi.FCGIOutputStream;
import com.fastcgi.FCGIRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.util.Properties;

public class FCGIInterface {
    private static final String RCSID = "$Id: FCGIInterface.java,v 1.4 2000/03/27 15:37:25 robs Exp $";
    public static FCGIRequest request = null;
    public static boolean acceptCalled = false;
    public static boolean isFCGI = true;
    public static Properties startupProps;
    public static ServerSocket srvSocket;

    public int FCGIaccept() {
        int acceptResult = 0;
        if (!acceptCalled) {
            isFCGI = System.getProperties().containsKey("FCGI_PORT");
            acceptCalled = true;
            if (isFCGI) {
                startupProps = new Properties(System.getProperties());
                String str = new String(System.getProperty("FCGI_PORT"));
                if (str.length() <= 0) {
                    return -1;
                }
                int portNum = Integer.parseInt(str);
                try {
                    srvSocket = new ServerSocket(portNum);
                }
                catch (IOException e) {
                    if (request != null) {
                        FCGIInterface.request.socket = null;
                    }
                    srvSocket = null;
                    request = null;
                    return -1;
                }
            }
        } else if (!isFCGI) {
            return -1;
        }
        if (isFCGI) {
            try {
                acceptResult = this.FCGIAccept();
            }
            catch (IOException e) {
                return -1;
            }
            if (acceptResult < 0) {
                return -1;
            }
            System.setIn(new BufferedInputStream(FCGIInterface.request.inStream, 8192));
            System.setOut(new PrintStream(new BufferedOutputStream(FCGIInterface.request.outStream, 8192)));
            System.setErr(new PrintStream(new BufferedOutputStream(FCGIInterface.request.errStream, 512)));
            System.setProperties(FCGIInterface.request.params);
        }
        return 0;
    }

    int FCGIAccept() throws IOException {
        block14: {
            boolean errCloseEx = false;
            boolean outCloseEx = false;
            if (request != null) {
                boolean prevRequestfailed;
                System.err.close();
                System.out.close();
                boolean bl = prevRequestfailed = errCloseEx || outCloseEx || FCGIInterface.request.inStream.getFCGIError() != 0 || FCGIInterface.request.inStream.getException() != null;
                if (prevRequestfailed || !FCGIInterface.request.keepConnection) {
                    FCGIInterface.request.socket.close();
                    FCGIInterface.request.socket = null;
                }
                if (prevRequestfailed) {
                    request = null;
                    return -1;
                }
            } else {
                request = new FCGIRequest();
                FCGIInterface.request.socket = null;
                FCGIInterface.request.inStream = null;
            }
            boolean isNewConnection = false;
            do {
                if (FCGIInterface.request.socket == null) {
                    try {
                        FCGIInterface.request.socket = srvSocket.accept();
                    }
                    catch (IOException e) {
                        FCGIInterface.request.socket = null;
                        request = null;
                        return -1;
                    }
                    isNewConnection = true;
                }
                FCGIInterface.request.isBeginProcessed = false;
                FCGIInterface.request.inStream = new FCGIInputStream(FCGIInterface.request.socket.getInputStream(), 8192, 0, request);
                FCGIInterface.request.inStream.fill();
                if (FCGIInterface.request.isBeginProcessed) break block14;
                FCGIInterface.request.socket.close();
                FCGIInterface.request.socket = null;
            } while (!isNewConnection);
            return -1;
        }
        FCGIInterface.request.params = new Properties(startupProps);
        switch (FCGIInterface.request.role) {
            case 1: {
                FCGIInterface.request.params.put("ROLE", "RESPONDER");
                break;
            }
            case 2: {
                FCGIInterface.request.params.put("ROLE", "AUTHORIZER");
                break;
            }
            case 3: {
                FCGIInterface.request.params.put("ROLE", "FILTER");
                break;
            }
            default: {
                return -1;
            }
        }
        FCGIInterface.request.inStream.setReaderType(4);
        if (new FCGIMessage(FCGIInterface.request.inStream).readParams(FCGIInterface.request.params) < 0) {
            return -1;
        }
        FCGIInterface.request.inStream.setReaderType(5);
        FCGIInterface.request.outStream = new FCGIOutputStream(FCGIInterface.request.socket.getOutputStream(), 8192, 6, request);
        FCGIInterface.request.errStream = new FCGIOutputStream(FCGIInterface.request.socket.getOutputStream(), 512, 7, request);
        FCGIInterface.request.numWriters = 2;
        return 0;
    }
}

