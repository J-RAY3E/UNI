/*
 * Decompiled with CFR 0.152.
 */
package com.fastcgi;

import com.fastcgi.FCGIInputStream;
import com.fastcgi.FCGIOutputStream;
import java.net.Socket;
import java.util.Properties;

public class FCGIRequest {
    private static final String RCSID = "$Id: FCGIRequest.java,v 1.3 2000/03/21 12:12:26 robs Exp $";
    public Socket socket;
    public boolean isBeginProcessed;
    public int requestID;
    public boolean keepConnection;
    public int role;
    public int appStatus;
    public int numWriters;
    public FCGIInputStream inStream;
    public FCGIOutputStream outStream;
    public FCGIOutputStream errStream;
    public Properties params;
}

