/*
 * Decompiled with CFR 0.152.
 */
package com.fastcgi;

public abstract class FCGIGlobalDefs {
    private static final String RCSID = "$Id: FCGIGlobalDefs.java,v 1.3 2000/03/21 12:12:25 robs Exp $";
    public static final int def_FCGIMaxLen = 65535;
    public static final int def_FCGIHeaderLen = 8;
    public static final int def_FCGIEndReqBodyLen = 8;
    public static final int def_FCGIBeginReqBodyLen = 8;
    public static final int def_FCGIUnknownBodyTypeBodyLen = 8;
    public static int def_FCGIVersion1 = 1;
    public static final int def_FCGIBeginRequest = 1;
    public static final int def_FCGIAbortRequest = 2;
    public static final int def_FCGIEndRequest = 3;
    public static final int def_FCGIParams = 4;
    public static final int def_FCGIStdin = 5;
    public static final int def_FCGIStdout = 6;
    public static final int def_FCGIStderr = 7;
    public static final int def_FCGIData = 8;
    public static final int def_FCGIGetValues = 9;
    public static final int def_FCGIGetValuesResult = 10;
    public static final int def_FCGIUnknownType = 11;
    public static final int def_FCGIMaxType = 11;
    public static final int def_FCGINullRequestID = 0;
    public static int def_FCGIKeepConn = 1;
    public static final int def_FCGIResponder = 1;
    public static final int def_FCGIAuthorizer = 2;
    public static final int def_FCGIFilter = 3;
    public static final int def_FCGIRequestComplete = 0;
    public static final int def_FCGICantMpxConn = 1;
    public static final int def_FCGIOverload = 2;
    public static final int def_FCGIUnknownRole = 3;
    public static final String def_FCGIMaxConns = "FCGI_MAX_CONNS";
    public static final String def_FCGIMaxReqs = "FCGI_MAX_REQS";
    public static final String def_FCGIMpxsConns = "FCGI_MPXS_CONNS";
    public static final int def_FCGIStreamRecord = 0;
    public static final int def_FCGISkip = 1;
    public static final int def_FCGIBeginRecord = 2;
    public static final int def_FCGIMgmtRecord = 3;
    public static final int def_FCGIUnsupportedVersion = -2;
    public static final int def_FCGIProtocolError = -3;
    public static final int def_FCGIParamsError = -4;
    public static final int def_FCGICallSeqError = -5;
}

