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
import java.util.Optional;

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
                    dashboard.getCanvasPanel().loadData(response.getReturned());
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
            this.dashboard.getCanvasPanel().loadData(returned);
        });

    }

    public Handler getHandler() {
        return this.handler;
    }

    public void clearAll() {
        try {
            handler.setCurrentRequest( "clear");
            Response response = handler.pullRequest(connection);
            handler.ejectRequestPrint(response);
        }catch (IOException e){
            NotificationManager.getInstance().pushMessage("Couldn't process registration",MessageType.ERROR);
        }
    }

    public void minByPosition() {
        try {
            handler.setCurrentRequest( "min_by_position");
            Response response = handler.pullRequest(connection);

            if (response != null && RequestState.RETURNED == response.getRequestState()) {
                Platform.runLater(() -> {
                    dashboard.getObjectListPanel().setObjects(response.getReturned(),this.handler.getUserManager().getUsername());
                    dashboard.getCanvasPanel().loadData(response.getReturned());
                });
            }
        }catch (IOException e){
            NotificationManager.getInstance().pushMessage("Couldn't process registration",MessageType.ERROR);
        }

    }

    public List<Worker> restore() {
        return null;
    }

    public Optional<Worker> filterByName(String parametr) {

        try {
            if ( parametr.isEmpty()) return Optional.empty();
            handler.setCurrentRequest(String.format("filter_starts_with_name %s",parametr));
            Response response = handler.pullRequest(connection);
            if (response != null && RequestState.RETURNED == response.getRequestState()) {
                Platform.runLater(() -> {
                    dashboard.getObjectListPanel().setObjects(response.getReturned(),this.handler.getUserManager().getUsername());
                    dashboard.getCanvasPanel().loadData(response.getReturned());
                });
            }
        }catch (IOException e){
            NotificationManager.getInstance().pushMessage("Couldn't load data", MessageType.ERROR);
        }
        return null;
    }

    public void deleteLast() {
        try {

            handler.setCurrentRequest("remove_last");
            Response response = handler.pullRequest(connection);
            if (response != null && RequestState.RETURNED == response.getRequestState()) {
                Platform.runLater(() -> {
                    dashboard.getObjectListPanel().setObjects(response.getReturned(),this.handler.getUserManager().getUsername());
                    dashboard.getCanvasPanel().loadData(response.getReturned());
                });
            }
        }catch (IOException e){
            NotificationManager.getInstance().pushMessage("Couldn't load data", MessageType.ERROR);
        }

    }

    public void deleteGreaterThan(Worker worker) {
        try {
            handler.setCurrentRequest(String.format("add_if_max %s", worker));
            Response response = handler.pullRequest(connection);
            if (response != null && RequestState.RETURNED == response.getRequestState()) {
                NotificationManager.getInstance().pushMessage("LA DATA NO ESTA VACIA", MessageType.ERROR);

            }
        }catch (IOException e){
            NotificationManager.getInstance().pushMessage("Couldn't load data", MessageType.ERROR);
        }


    }

    public boolean addIfMax(Worker worker) {
        try {
            NotificationManager.getInstance().pushMessage("the message is being sending",MessageType.INFO);
            handler.setCurrentRequest(String.format("add_if_max %s", worker.toCommandString()));
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

    public void modifyWorker(Worker worker) {

        try {
            NotificationManager.getInstance().pushMessage(String.valueOf("IM SURRE THAT IS HERE"+ worker == null), MessageType.INFO);
            handler.setCurrentRequest(String.format("update_by_id %s",worker.toCommandString()));
            handler.pullRequest(connection);
        }catch (IOException e){
            NotificationManager.getInstance().pushMessage("Couldn't load data", MessageType.ERROR);
        }

    }

    public void removeById(Worker worker) {
        try {
            handler.setCurrentRequest(String.format("remove_by_id %d",worker.getId()));
            handler.pullRequest(connection);
        }catch (IOException e){
            NotificationManager.getInstance().pushMessage("Couldn't load data", MessageType.ERROR);
        }
    }

    public void setScript(String filePath) {
        try {
            handler.setCurrentRequest(String.format("execute_file %s",filePath));
            System.out.println("PASO POR AQUI ESEPEREMOSA VER QUE PASA");
            handler.pullRequest(connection);
        }catch (IOException e){
            NotificationManager.getInstance().pushMessage("Couldn't load data", MessageType.ERROR);
        }
    }
}
