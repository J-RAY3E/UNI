package org.example.readerManager.inputs;

import org.example.enums.RequestState;

/**
 * Represents the response of a command execution.
 */
public final class Response {

    private final String response;
    private final RequestState requestState;

    /**
     * Constructs a Response object.
     * @param response The message of the response.
     * @param requestState The state of the response.
     */
    public Response(final String response, final RequestState requestState) {
        this.response = response;
        this.requestState = requestState;
    }

    /**
     * Retrieves the response message.
     * @return The response message.
     */
    public String getMessage() {
        return response;
    }

    /**
     * Retrieves the request state.
     * @return The request state.
     */
    public RequestState getRequestState() {
        return requestState;
    }
}
