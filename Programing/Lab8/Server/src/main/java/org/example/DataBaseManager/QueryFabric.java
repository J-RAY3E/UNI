package org.example.DataBaseManager;

import org.example.Enums.MessageType;
import org.example.connection.NotificationManager;

public class QueryFabric {

    public static String getQuery(String query){
        return switch (query) {
            case "registration" -> "INSERT INTO users (username,password,privilegios) VALUES(?,?,?);";
            case "authorization" -> "SELECT * FROM users WHERE username = ? AND password = ?;";
            case "getPrivileges" ->
                    "SELECT privilegios FROM users WHERE username = ? AND password = ?";
            case "addAddress" ->
                    "INSERT INTO address(zipCode) VALUES(?)  RETURNING addressId";
            case "addOrganization" ->
                    "INSERT INTO organization(fullName, annualTurnover, employeesCount, addressId) VALUES(?,?,?,?)  RETURNING organizationId";
            case "addCoordinates" ->
                    "INSERT INTO coordinates(x, y) VALUES(?,?)  RETURNING coordinatesId";
            case "addWorker" ->
                    "INSERT INTO worker(name, coordinatesId,creationDate, salary, endDate, position, status, organizationId, whoCreate) VALUES(?,?,?,?,?,?,?,?,?) RETURNING workerId";
            case "loadAll" ->
                    "SELECT * FROM worker w LEFT JOIN organization o ON w.organizationId = o.organizationId LEFT JOIN address a ON o.addressId = a.addressId LEFT JOIN coordinates c ON w.coordinatesId = c.coordinatesId;";
            case "deleteAll" ->
                    "DELETE FROM worker WHERE whoCreate = ? RETURNING workerId";
            case "delete" ->
                    "DELETE FROM worker WHERE whoCreate = ? AND workerId = ? RETURNING workerId";
            case "updateWorker" ->
                    "UPDATE worker SET name = ?, creationDate = ?, salary = ?, endDate = ?, position = ?, status = ?, whoCreate = ? " +
                            "WHERE workerId = ? AND whoCreate = ? RETURNING coordinatesId, organizationId;";
            case "updateCoordinates" ->
                    "UPDATE coordinates SET x = ?, y = ? WHERE coordinatesId = ?;";

            case "updateOrganization" ->
                    "UPDATE organization SET fullName = ?, annualTurnover = ?, employeesCount = ? " +
                            "WHERE organizationId = ? RETURNING addressId;";

            case "updateAddress" ->
                    "UPDATE address SET zipCode = ? WHERE addressId = ?;";
            default -> {
                NotificationManager.getInstance().pushMessage("Query no exist", MessageType.ERROR);
                yield null;
            }
        };
    }
}
