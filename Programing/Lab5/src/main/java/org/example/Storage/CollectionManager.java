package org.example.Storage;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.stream.IntStream;

import org.example.Classes.Worker;
import org.example.ReaderManager.ManagerID;
import org.example.ReaderManager.Parse.ReadJSON;
import org.example.ReaderManager.StackInputs;


public class CollectionManager {

    private final java.time.LocalDateTime dataCreation;
    private final String classList;

    private final LinkedList<Worker> collection;
    private final ManagerID managerID;


    public CollectionManager() {
        this.collection = new LinkedList<>();
        this.dataCreation = LocalDateTime.now();
        this.classList = collection.getClass().toString();
        this.managerID = new ManagerID(5);
    }
    public void load() {
        try{
            new ReadJSON(System.getenv("data")).loadData(this) ;
        } catch (Exception e) {
            System.out.println("No data loaded in the collection");
        }
    }
    public LinkedList<Worker> getCollection(){
        return collection;
    }

    public Integer getID(){
        return managerID.generateID();
    }

    public String getClassList(){
        return this.classList;
    }

    public void addElement(Worker record){
        this.collection.addLast(record);
    }

    public String getInfo(){
        DateTimeFormatter dataCasted = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("Data creation: %s  %nNumber of element: %d %nStorage: %s %n",this.dataCreation.format(dataCasted),this.collection.size(),this.getClassList());
    }



}
