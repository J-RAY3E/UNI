package org.example.Classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.Enums.Position;
import org.example.Enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import java.util.Date;

public final class Worker implements  Comparable<Worker> {

    private int id;
    private String name;
    private Coordinates coordinates;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private java.time.LocalDateTime creationDate;
    private int salary;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private java.util.Date endDate;
    private Position position;
    private Status status;
    private Organization organization;

    public Worker(String name, int id, java.time.LocalDateTime creationDateTime, java.util.Date endDate, int salary, Coordinates coordinates, Position position, Status status, Organization organization) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDateTime;
        this.salary = salary;
        this.coordinates = coordinates;
        this.endDate = endDate;
        this.position = position;
        this.status = status;
        this.organization = organization;
    }

    public Worker() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @JsonIgnore
    public String getIdStr() {
        return String.valueOf(this.id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public java.time.LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDateStr) {
        this.creationDate = creationDateStr ;
    }
    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
    @JsonIgnore
    public String getSalaryStr() {
        return String.valueOf(this.salary);
    }

    public void setEndDate(Date endDateStr) {
        this.endDate = endDateStr;
    }

    public Date getEndDate() {
        return this.endDate;
    }
    @JsonIgnore
    public String getEndDateStr() {
        return this.endDate == null ? "End-Date: null"  : "End-Date: " + new SimpleDateFormat("yyyy-MM-dd").format(this.endDate);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @JsonIgnore
    public String getOrganizationStr() {
        return this.organization==null ? "" : "Organization: "+this.organization.toString();
    }
    @JsonIgnore
    public String getPositionStr() {
        return this.position==null ? "" : "Position: "+this.position.toString();
    }
    @JsonIgnore
    public String getStatusStr() {
        return this.position==null ? "" : "Status: "+this.status.toString();
    }
    @JsonIgnore
    public String  getInfo() {
        return String.format("ID: %s Name: %s %s Salary: %s %s %s %s Date-Creation %s %s %n",
                this.getIdStr(), this.getName(), this.getCoordinates(), this.getSalaryStr(), this.getPositionStr(), this.getStatusStr(),this.getOrganizationStr(), this.getCreationDate().toLocalDate(),this.getEndDateStr());
    }

    @Override
    public int compareTo(Worker worker) {
        return this.getName().compareTo(worker.getName());
    }
}