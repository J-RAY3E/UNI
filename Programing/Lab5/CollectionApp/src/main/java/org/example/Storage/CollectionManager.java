package org.example.Storage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.example.Classes.Worker;



public class CollectionManager {

    LinkedList<Worker> collection;
    int idx;
    java.time.LocalDateTime dataCreation;
    String data_init;
    public CollectionManager(){
        idx = 0;
        collection = new LinkedList<>();
        dataCreation = LocalDateTime.now();
        data_init = collection.getClass().toString();
    }

    public int getIndex(){
        return this.idx;
    }

    public void addElement(Worker record){
        this.collection.addLast(record);
        this.idx ++;
    }

    public void deleteById(int id){
        this.collection.removeIf(Worker ->  Worker.getId() ==  id);
    }

    public void updateElement(int idx,Worker element){
        IntStream.range(0, this.collection.size()).filter(i ->  this.collection.get(i).getId() == idx).findFirst().ifPresent(i -> this.collection.set(i, element));
    }

    public void  deleteLast(){
        collection.removeLast();
    }

    public void show(){
        this.collection.forEach(Worker::getInfo);
    }

    public void getInfo(){
        DateTimeFormatter dataCasted = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.printf("Data creation: %s  %nNumber of element: %d  %nCurrent idx: %d %nStorage: %s %n",this.dataCreation.format(dataCasted),this.collection.size(),this.idx,this.collection.getClass());
    }

    public void clear(){
        this.collection.clear();
        this.idx = 0;
    }
    
    public void add_if_max (Worker record){
        boolean add = false;
        for(Worker worker: this.collection){
            if (worker.getSalary() > record.getSalary()){
                add = true;
                break;
            }
        }
        if(!add){this.addElement(record);}
    }


    public void filter_starts_with_name(String name){

        collection.forEach(Worker ->{
            if (Worker.getName().contains(name)){
                Worker.getInfo();
            }
        });
    }

    public void count_by_end_date(LocalDate date){
        long count = this.collection.stream().filter(Worker-> Worker.getEndDateStr().equals(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()).toString())).count();
        System.out.println("Total elements in storage: " + String.valueOf(count));
    }

    public void removeGreater(Worker element){
        this.collection.removeIf(Worker ->  Worker.getSalary() >=  element.getSalary());
        this.collection.add(element);
    }

    public  void updateId(int idxAdded){
        if(idxAdded > this.idx){
            this.idx = idxAdded + 1;
        }
    }

    public  void min_by_position(){

        this.collection.stream().min(Comparator.comparing(Worker -> Worker.getPosition().ordinal())).ifPresent(Worker::getInfo);

    }

    public LinkedList<Worker> getData(){return this.collection;}
}
