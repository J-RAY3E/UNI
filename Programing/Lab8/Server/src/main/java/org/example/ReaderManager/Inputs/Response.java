package org.example.ReaderManager.Inputs;


import org.example.Classes.Worker;
import org.example.Enums.RequestState;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the response of a command execution.
 */
public final class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String response;
    private RequestState requestState;
    private  List<Worker> returned;

    /**
     * Constructs a Response object.
     * @param response The message of the response.
     * @param requestState The state of the response.
     */
    public Response(final String response, RequestState requestState) {
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

    public List<Worker> getReturned() {
        return this.returned;
    }

    public void setReturned(List<Worker> returned){
        this.returned = returned;
    }

    public void setRequestState(RequestState requestState) {
        this.requestState = requestState;
    }
}
