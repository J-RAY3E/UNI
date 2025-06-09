package org.example.ReaderManager;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

import org.example.Classes.*;


import org.example.Enums.Position;
import org.example.Enums.Status;
import org.example.ReaderManager.Inputs.InputValidator;
import org.example.ReaderManager.Parse.BooleanCondition;
import org.example.ReaderManager.TypeValidation.*;

public class CreationManager {

    InputValidator inputValidator;

    public CreationManager(InputValidator inputValidator){
        this.inputValidator = inputValidator;
    }
    public Worker creationelement(int id,String element){
        return creationWorker(id, element, true);
    }
    public Worker creationelement(int id){
        return creationWorker(id, "", false);
    }
    public  Worker creationWorker(int id,String element,boolean hasElement){
        if (!hasElement){

            element = this.inputValidator.execute(String.format("Insert the name for the new element id %d : ",id), new ValidationString(), String::trim);
        }
        else{
            System.out.printf("Insert the data new data for the element %s %n",element);
        }
        Position position = this.inputValidator.execute(String.format(">> Insert position %s: ", Arrays.toString(Position.values())), new ValidationEnum<>(Position.class,true), Position::valueOf);
        Status status = this.inputValidator.execute(String.format(">> Insert status %s: ", Arrays.toString(Status.values())), new ValidationEnum<>(Status.class,true), Status::valueOf);
        Number  salary = this.inputValidator.execute(">> Insert salary: ", new ValidationNumber(0.d,(double) Integer.MAX_VALUE,false), Integer::parseInt);

        Coordinates coordinates = this.creationCoordinates(element);
        LocalDate endDate  = this.inputValidator.execute(">> Insert end date {yy-MM-dd}: ", new ValidationDate(true), LocalDate::parse);
        Boolean OrganizationOp = this.inputValidator.execute(">> Add organization (y/n):", new ValidationBoolean(false), BooleanCondition::parse);
        Organization organization =  (OrganizationOp) ?  this.creationOrganization() : null;
        return new Worker(element,id, LocalDateTime.now(),(endDate != null) ? Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()):null,(Integer) salary,coordinates,position,status,organization);
    }

    private Coordinates creationCoordinates(String element){
        System.out.printf("Create coordinate's data of the object  %s %n",element);
        Number x =  this.inputValidator.execute(">> Insert coordinate x: ", new ValidationNumber(0.d,395.d,false), Integer::parseInt);
        Number y = this.inputValidator.execute(">> Insert coordinate y: ", new ValidationNumber((double)-Float.MAX_VALUE,(double)Float.MAX_VALUE,false), Float::parseFloat);
        return new Coordinates((Integer)x,(Float)y);
        
    }
    private Organization creationOrganization(){
        String fullName = this.inputValidator.execute(">> Insert the fullName: ", new ValidationString(), String::trim);
        Number annualTurnover =  this.inputValidator.execute(">> Insert the annualTurnover: ", new ValidationNumber(0.d,(double)Integer.MAX_VALUE,false), Integer::parseInt);
        Number employeesCount =  this.inputValidator.execute(">> Insert the employeesCount: ", new ValidationNumber(0.d,(double)Integer.MAX_VALUE,false), Integer::parseInt);
        Address address  = this.createAddress();
        return new Organization(fullName,(Integer) annualTurnover,(Integer) employeesCount,address);
    }

    private Address createAddress(){
        String zipCode = this.inputValidator.execute(">> Insert the Address: ", new ValidationString(9,false), String::trim);

        return new Address(zipCode);
    }
} 
