package org.example.DataBaseManager;


import org.example.Classes.*;
import org.example.Enums.Position;
import org.example.Enums.Status;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Locale;

public class BuilderWorkerSql {

    public static Worker buildWorker(ResultSet rs) throws SQLException, ParseException {
        Coordinates coordinates = buildCoordinates(rs);
        Organization organization = buildOrganization(rs);

        if (coordinates == null || organization == null) return null;

        Worker worker = new Worker();
        worker.setId(rs.getInt("workerId"));
        worker.setName(rs.getString("name"));

        String posStr = rs.getString("position");
        worker.setPosition(posStr != null ? Position.valueOf(posStr) : null);

        String statusStr = rs.getString("status");
        worker.setStatus(statusStr != null ? Status.valueOf(statusStr) : null);

        worker.setSalary(rs.getInt("salary"));
        worker.setCreationDate(LocalDateTime.parse(rs.getString("creationDate")));

        String endDateStr = rs.getString("endDate");
        if (endDateStr != null) {
            worker.setEndDate(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(endDateStr));
        }

        worker.setCoordinates(coordinates);
        worker.setOrganization(organization);
        worker.setWhoModificates(rs.getString("whoCreate"));

        return worker;
    }

    public static Coordinates buildCoordinates(ResultSet rs) throws SQLException {
        Coordinates coords = new Coordinates();
        coords.setX(rs.getInt("x"));
        coords.setY(rs.getFloat("y"));
        return coords;
    }

    public static Organization buildOrganization(ResultSet rs) throws SQLException {
        Address address = buildAddress(rs);
        if (address == null) return null;

        Organization org = new Organization();
        org.setFullName(rs.getString("fullName"));
        org.setAnnualTurnover(rs.getInt("annualTurnover"));
        org.setEmployeesCount(rs.getInt("employeesCount"));
        org.setPostalAddress(address);
        return org;
    }

    public static Address buildAddress(ResultSet rs) throws SQLException {
        String zip = rs.getString("zipCode");
        if (zip == null || zip.isEmpty()) return null;

        Address addr = new Address();
        addr.setZipCode(zip);
        return addr;
    }
}
