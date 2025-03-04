package org.example.ReaderManager;


public class RuntimeManager {

    Handler handler;

    public RuntimeManager(Handler handler){
        this.handler = handler;
    }

    public void Reader(){
        while (this.handler.getState()) {
            this.handler.createRequest();
            this.handler.pullRequest();
        }
    }
}
