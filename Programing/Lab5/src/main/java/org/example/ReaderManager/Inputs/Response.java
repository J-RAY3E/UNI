package org.example.ReaderManager.Inputs;

import org.example.Enums.RequestState;

public class Response {
    private final String response;
    private final RequestState requestState;
    public Response (final String response, final RequestState requestState) {
        this.response = response;
        this.requestState = requestState;
    }


    public String getMessage() {
        return response;
    }
    public RequestState getRequestState() {
        return requestState;
    };
}
