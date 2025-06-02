package org.example.DataBaseManager;


import org.example.Enums.MessageType;
import org.example.connection.NotificationManager;
import java.sql.*;

public class DBManager {
    public static DBManager dbManager;
    private final String database;
    private final String username;
    private final String password;
    private Connection connection;

    private DBManager(String database,String username,String password){
        this.database = database;
        this.username =username;
        this.password = password;
    }


    public static DBManager getInstance(String database,String host,String password){
        dbManager = new DBManager(database,host,password);
        return dbManager;
    }
    public static DBManager getInstance(){
        return dbManager;
    }
    public void establishConnection(){

        // connection = DriverManager.getConnection("jdbc:postgresql://pg:5432/studs", "", ""); //
        //connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres", "postgres", ""); //
        try{

            connection = DriverManager.getConnection(String.format("jdbc:postgresql://localhost:5432/%s", this.database),this.username,this.password);
        }catch (SQLException e){
            NotificationManager.getInstance().pushMessage(String.format("Couldn't connect to database %s, Check the username o database's name"+ e ,this.database), MessageType.ERROR);
        }

    }

    public boolean registration(String name,String password,String privileges){
        try(PreparedStatement statement = connection.prepareStatement(QueryFabric.getQuery("registration"));){
            statement.setString(1,name);
            statement.setString(2, password);
            statement.setString(3, privileges);
            int result  = statement.executeUpdate();

            if(result != 0){
                NotificationManager.getInstance().pushMessage("The registration went right, try log in",MessageType.INFO);
                return true;
            }
            else{
                NotificationManager.getInstance().pushMessage("Couldn't try the registration",MessageType.ERROR);
            }
        }catch (SQLException e){
            NotificationManager.getInstance().pushMessage("Something went wrong at moment creation query",MessageType.ERROR);
        }
        return false;

    }

    public boolean authorization(String name,String password ){

        try(PreparedStatement statement = connection.prepareStatement(QueryFabric.getQuery("authorization"));){
            statement.setString(1,name);
            statement.setString(2, password);
            ResultSet result  = statement.executeQuery();
            if(result.next()){
                return true;
            }
            else{
                NotificationManager.getInstance().pushMessage("Couldn't try the LOGIN",MessageType.ERROR);
            }
        }catch (SQLException e){
            NotificationManager.getInstance().pushMessage("Something went wrong at moment creation query",MessageType.ERROR);
        }
        return false;
    }


    public PreparedStatement getPreparedStatement(String typeStatement) throws SQLException {
        return this.connection.prepareStatement(typeStatement);
    }
    public String getPrivileges(String name, String password) {
        try(PreparedStatement statement = connection.prepareStatement(QueryFabric.getQuery("getPrivileges"));){
            statement.setString(1,name);
            statement.setString(2, password);
            ResultSet result  = statement.executeQuery();

            if(result.next()){
                return result.getString("privilegios");
            }
            else{
                NotificationManager.getInstance().pushMessage("Couldn't try the LOGIN",MessageType.ERROR);
            }
        }catch (SQLException e){
            NotificationManager.getInstance().pushMessage("Something went wrong at moment creation query",MessageType.ERROR);
        }
        return null;

    }

    public Connection getConnection() {
        return this.connection;
    }

}
