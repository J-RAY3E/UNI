package org.example.GUI;

import javafx.application.Platform;
import org.example.Classes.Worker;
import org.example.Enums.MessageType;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Handler;
import org.example.ReaderManager.Inputs.Response;
import org.example.connection.Connection;
import org.example.connection.NotificationManager;

import java.io.IOException;
import java.util.List;

public class ViewController {
    public static ViewController viewController;
    private final Connection connection;
    private final Handler handler;
    private Dashboard dashboard;
    private ViewController(Handler handler, Connection connection){
        this.handler = handler;
        this.connection = connection;
    }

    public static void initiate(Handler handler, Connection connection){
        viewController = new ViewController(handler,connection);
    }
    public static ViewController getInstance(){
        return viewController;
    }



    public void getData()  {

        try {
            handler.setCurrentRequest("show");
            Response response = handler.pullRequest(connection);
            if (response != null && RequestState.RETURNED == response.getRequestState()) {
                Platform.runLater(() -> {
                    dashboard.getObjectListPanel().setObjects(response.getReturned(),this.handler.getUserManager().getUsername());
                });
            }
        }catch (IOException e){
            NotificationManager.getInstance().pushMessage("Couldn't load data", MessageType.ERROR);
        }
    }

    public boolean addData(Worker worker){
        try {
            handler.setCurrentRequest(String.format("add %s", worker.toCommandString()));
            Response response = handler.pullRequest(connection);
            if (response != null && RequestState.RETURNED == response.getRequestState()) {
                NotificationManager.getInstance().pushMessage("LA DATA NO ESTA VACIA", MessageType.ERROR);
                return true;
            }
        }catch (IOException e){
            NotificationManager.getInstance().pushMessage("Couldn't load data", MessageType.ERROR);
        }
        return false;
    }

    public void register(String user,String password)  {
        try {
            handler.setCurrentRequest(String.format( "registration %s %s",user,password));
            Response response = handler.pullRequest(connection);
            handler.ejectRequestPrint(response);
        }catch (IOException e){
            NotificationManager.getInstance().pushMessage("Couldn't process registration",MessageType.ERROR);
        }
    }

    public Boolean authorize(String user, String password) {
        try {
            handler.setCurrentRequest(String.format( "authorization %s %s",user,password));
            Response response = handler.pullRequest(connection);
            handler.ejectRequestPrint(response);
            if(response != null && response.getRequestState() == RequestState.CONNECTED){
                return true;
            }
        }catch (IOException e){
            NotificationManager.getInstance().pushMessage("Couldn't process registration",MessageType.ERROR);
        }
        return false;
    }

    public void setDashboard(Dashboard dashboard){
        this.dashboard =dashboard;
    }
    public void loadData(List<Worker> returned) {
        Platform.runLater(() -> {
            this.dashboard.getObjectListPanel().setObjects(returned,this.handler.getUserManager().getUsername());
        });

    }
}
