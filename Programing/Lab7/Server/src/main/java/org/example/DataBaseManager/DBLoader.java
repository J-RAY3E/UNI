package org.example.DataBaseManager;

import org.example.Classes.Address;
import org.example.Classes.Coordinates;
import org.example.Classes.Organization;
import org.example.Classes.Worker;
import org.example.UserUtils.PasswordEncryptation;
import org.example.UserUtils.UserManager;
import org.example.Enums.MessageType;
import org.example.Enums.Position;
import org.example.Enums.Status;
import org.example.Storage.CollectionManager;
import org.example.connection.NotificationManager;

import java.net.PasswordAuthentication;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;


public class DBLoader{
    private static DBLoader dbLoader;
    private final DBManager dbManager;
    private DBLoader(DBManager dbManager){
        this.dbManager = dbManager;
    }

    public static void initiate(DBManager dbManager){
        dbLoader = new DBLoader(dbManager);
    }

    public static DBLoader getInstance(){
        return dbLoader;
    }

    private Integer addAddress(Organization org) throws SQLException {
        if (org == null || org.getPostalAddress() == null) return null;

        Address address = org.getPostalAddress();
        PreparedStatement stmt = dbManager.getPreparedStatement(QueryFabric.getQuery("addAddress"));
        stmt.setString(1, address.getZipCode());
        ResultSet rs = stmt.executeQuery();
        return rs.next() ? rs.getInt(1) : null;
    }

    private Integer addOrganization(Organization org, Integer addressId) throws SQLException {
        if (org == null) return null;

        PreparedStatement stmt = dbManager.getPreparedStatement(QueryFabric.getQuery("addOrganization"));
        stmt.setString(1, org.getFullName());
        stmt.setDouble(2, org.getAnnualTurnover());
        stmt.setInt(3, org.getEmployeesCount());
        if (addressId != null) {
            stmt.setInt(4, addressId);
        } else {
            stmt.setNull(4, Types.INTEGER);
        }

        ResultSet rs = stmt.executeQuery();
        return rs.next() ? rs.getInt(1) : null;
    }

    private Integer addCoordinates(Coordinates coords) throws SQLException {
        PreparedStatement stmt = dbManager.getPreparedStatement(QueryFabric.getQuery("addCoordinates"));
        stmt.setInt(1, coords.getX());
        stmt.setFloat(2, coords.getY());
        ResultSet rs = stmt.executeQuery();
        return rs.next() ? rs.getInt(1) : null;
    }

    private Integer addWorker(Worker worker, Integer coordinatesId, Integer organizationId, String username) throws SQLException {
        PreparedStatement stmt = dbManager.getPreparedStatement(QueryFabric.getQuery("addWorker"));
        stmt.setString(1, worker.getName());
        stmt.setInt(2, coordinatesId);
        stmt.setString(3, worker.getCreationDate().toString());
        stmt.setDouble(4, worker.getSalary());

        if (worker.getEndDate() != null)
            stmt.setString(5, worker.getEndDate().toString());
        else
            stmt.setNull(5, Types.VARCHAR);

        stmt.setString(6, worker.getPosition() != null ? worker.getPosition().name() : null);
        stmt.setString(7, worker.getStatus() != null ? worker.getStatus().name() : null);

        if (organizationId != null)
            stmt.setInt(8, organizationId);
        else
            stmt.setNull(8, Types.INTEGER);

        stmt.setString(9, username);

        ResultSet rs = stmt.executeQuery();
        return rs.next() ? rs.getInt(1) : null;
    }
    public Integer addData(Worker worker, String username) {
        try {
            Integer addressId = addAddress(worker.getOrganization());
            Integer organizationId = addOrganization(worker.getOrganization(), addressId);
            Integer coordinatesId = addCoordinates(worker.getCoordinates());
            return addWorker(worker, coordinatesId, organizationId, username);
        } catch (SQLException e) {
            NotificationManager.getInstance().pushMessage("The worker was not inserted in the database\n" + e.getMessage(), MessageType.ERROR);
            return null;
        }
    }

    public  void loadData(CollectionManager collectionManager) throws SQLException, ParseException {
        PreparedStatement loadALL = this.dbManager.getPreparedStatement(QueryFabric.getQuery("loadAll"));
        ResultSet results = loadALL.executeQuery();
        LinkedList<Worker> currentListWorkers = new LinkedList<>();
        while(results.next()){
            Worker currentWorker = new Worker();
            currentWorker.setId(results.getInt("workerId"));
            currentWorker.setName(results.getString("name"));
            currentWorker.setPosition((results.getString("position") != null) ?Position.valueOf(results.getString("position")):null);
            currentWorker.setStatus((results.getString("status") != null) ? Status.valueOf(results.getString("status")):null);
            currentWorker.setSalary(results.getInt("salary"));
            currentWorker.setCreationDate(LocalDateTime.parse(results.getString("creationDate")));
            currentWorker.setEndDate((results.getString("endDate") != null) ? new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(results.getString("endDate")): null);
            Address currentAddress = new Address();
            currentAddress.setZipCode(results.getString("zipCode"));
            Organization currentOrganization = new Organization();
            currentOrganization.setAnnualTurnover(results.getInt("annualTurnover"));
            currentOrganization.setEmployeesCount(results.getInt("employeesCount"));
            currentOrganization.setFullName(results.getString("fullName"));
            currentOrganization.setPostalAddress(currentAddress);
            currentWorker.setOrganization(currentOrganization);
            Coordinates currentCoordinates = new Coordinates();
            currentCoordinates.setX(results.getInt("x"));
            currentCoordinates.setY(results.getFloat("y"));
            currentWorker.setCoordinates(currentCoordinates);
            currentWorker.setWhoModificates(results.getString("whoCreate"));
            currentListWorkers.add(currentWorker);
        }

        collectionManager.getCollection().addAll(currentListWorkers);
    }

    public ArrayList<Integer> removeAll(String username) throws SQLException {
        PreparedStatement removeAll = dbManager.getPreparedStatement(QueryFabric.getQuery("deleteAll"));
        removeAll.setString(1,username);
        ResultSet rs = removeAll.executeQuery();
        ArrayList<Integer> deletedIds = new ArrayList<>();
        while (rs.next()) {
            deletedIds.add(rs.getInt("workerId"));
        }
        return deletedIds;
    }

    public Integer remove(String username,Integer id) throws SQLException {
        PreparedStatement removeAll = dbManager.getPreparedStatement(QueryFabric.getQuery("delete"));
        removeAll.setString(1,username);
        removeAll.setInt(2,id);
        ResultSet rs = removeAll.executeQuery();
        ArrayList<Integer> deletedIds = new ArrayList<>();
        Integer idn = null;
        if(rs.next()) {
            idn = rs.getInt(1);
        }
        return idn;
    }

    public boolean authorization(UserManager userManager){
        boolean state =  this.dbManager.authorization(userManager.getUsername(), userManager.getPassword());
        if(userManager.getPrivileges().equals("admin") && !state){
            return this.dbManager.registration(userManager.getUsername(), userManager.getPassword(),userManager.getPrivileges());
        }
        return state;
    }

    public Boolean update(Worker worker, String username) throws SQLException {
        PreparedStatement stmt = dbManager.getPreparedStatement(QueryFabric.getQuery("updateWorker"));
        stmt.setString(1, worker.getName());
        stmt.setString(2, worker.getCreationDate().toString());
        stmt.setInt(3, worker.getSalary());
        stmt.setString(4, (worker.getEndDate() != null) ? worker.getEndDate().toString() : null);
        stmt.setString(5, (worker.getPosition() != null) ? worker.getPosition().toString() : null);
        stmt.setString(6, (worker.getStatus() != null) ? worker.getStatus().toString() : null);
        stmt.setString(7, worker.getWhoModificates());
        stmt.setInt(8, worker.getId());
        stmt.setString(9, username);

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) return false;

        int coordinatesId = rs.getInt("coordinatesId");
        int organizationId = rs.getInt("organizationId");

        updateCoordinates(worker.getCoordinates(), coordinatesId);

        int addressId = updateOrganization(worker.getOrganization(), organizationId);

        if (worker.getOrganization().getPostalAddress() != null) {
            updateAddress(worker.getOrganization().getPostalAddress(), addressId);
        }

        return true;
    }

    private void updateCoordinates(Coordinates coords, int coordinatesId) throws SQLException {
        PreparedStatement stmt = dbManager.getPreparedStatement(QueryFabric.getQuery("updateCoordinates"));
        stmt.setInt(1, coords.getX());
        stmt.setFloat(2, coords.getY());
        stmt.setInt(3, coordinatesId);
        stmt.executeUpdate();
    }

    private int updateOrganization(Organization org, int organizationId) throws SQLException {
        PreparedStatement stmt = dbManager.getPreparedStatement(QueryFabric.getQuery("updateOrganization"));
        stmt.setString(1, org.getFullName());
        stmt.setDouble(2, org.getAnnualTurnover());
        stmt.setInt(3, org.getEmployeesCount());
        stmt.setInt(4, organizationId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) return rs.getInt("addressId");
        else return -1;
    }

    private void updateAddress(Address addr, int addressId) throws SQLException {
        PreparedStatement stmt = dbManager.getPreparedStatement(QueryFabric.getQuery("updateAddress"));
        stmt.setString(1, addr.getZipCode());
        stmt.setInt(2, addressId);
        stmt.executeUpdate();
    }

}
