package org.example.Classes;

import org.example.Enums.Position;
import org.example.Enums.Status;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import java.util.Date;

public final class Worker implements  Comparable<Worker>, Serializable {
    private static final long serialVersionUID = 1L;
    
    private transient String whoModificates;
    private int id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDateTime creationDate;
    private int salary;
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

    public String getSalaryStr() {
        return String.valueOf(this.salary);
    }

    public void setEndDate(Date endDateStr) {
        this.endDate = endDateStr;
    }

    public Date getEndDate() {
        return this.endDate;
    }

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


    public String getOrganizationStr() {
        return this.organization==null ? "" : "Organization: "+this.organization.toString();
    }

    public String getPositionStr() {
        return this.position==null ? "" : "Position: "+this.position.toString();
    }

    public String getStatusStr() {
        return this.position==null ? "" : "Status: "+this.status.toString();
    }

    public String  getInfo() {
        return String.format(
                        "🔹 ID: %s%n" +
                        "🔹 Nombre: %s%n" +
                        "🔹 Coordenadas: %s%n" +
                        "🔹 Salario: $%s%n" +
                        "🔹 Posición: %s%n" +
                        "🔹 Estado: %s%n" +
                        "🔹 Organización: %s%n" +
                        "🔹 Fecha de creación: %s%n" +
                        "🔹 Fecha de fin: %s%n"
                , this.getIdStr(), this.getName(), this.getCoordinates(), this.getSalaryStr(), this.getPositionStr(), this.getStatusStr(),this.getOrganizationStr(), this.getCreationDate().toLocalDate(),this.getEndDateStr());
    }
    public  void setWhoModificates(String whoModificates){
        this.whoModificates = whoModificates;
    }
    public  String getWhoModificates(){
        return this.whoModificates;
    }

    @Override
    public int compareTo(Worker worker) {
        return this.getName().compareTo(worker.getName());
    }
}