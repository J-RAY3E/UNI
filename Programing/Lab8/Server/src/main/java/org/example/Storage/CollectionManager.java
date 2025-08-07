package org.example.Storage;



import org.example.Classes.Worker;
import org.example.DataBaseManager.DBLoader;
import org.example.Enums.MessageType;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.connection.NotificationManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * Manages the collection of Worker objects, providing functionality
 * for data loading, retrieval, and storage operations.
 */
public final class CollectionManager {

    private final LocalDateTime dataCreation;
    private final DBLoader dbLoader;
    private final String classList;
    private final List<Worker> collection;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * Constructs a CollectionManager and initializes storage-related fields.
     */
    public CollectionManager() {
        this.collection = new LinkedList<>();
        this.dataCreation = LocalDateTime.now();
        this.classList = collection.getClass().toString();
        this.dbLoader = DBLoader.getInstance();
    }

    /**
     * Loads data from a JSON file into the collection.
     */
    public void load() {
        readWriteLock.writeLock().lock();
        try {
            this.dbLoader.loadData(this);
            NotificationManager.getInstance().pushMessage("THE DATA SUCCESSFULLY GOT LOADED", MessageType.INFO);
        } catch (Exception e) {
            NotificationManager.getInstance().pushMessage("THE DATA COULDN'T BE LOADED", MessageType.INFO);
        }
        finally {
            readWriteLock.writeLock().unlock();
}

    }

public boolean add(Worker worker){
        readWriteLock.writeLock().lock();
        try {
            Integer id = this.dbLoader.addData(worker,worker.getWhoModificates());
            if(id != null){
                worker.setId(id);
                this.collection.add(worker);
                return true;
            }
            else{
                return false;

            }
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
    }
    public boolean addMax(Worker worker){
        readWriteLock.writeLock().lock();
        try {
            if (this.collection.stream()
                    .noneMatch(existingWorker -> existingWorker.getSalary() > worker.getSalary())) {
                    return this.add(worker);
                } else {
                    return false;
                }
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public boolean clear(String user){
        readWriteLock.writeLock().lock();
        try {
            ArrayList<Integer> deletedIds = dbLoader.removeAll(user);
            this.collection.removeIf(worker -> deletedIds.contains(worker.getId()));
            return !deletedIds.isEmpty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public Boolean removebyId(String username,Integer id) throws SQLException{
        readWriteLock.writeLock().lock();
        try{
            if(this.collection.stream().anyMatch(worker ->  worker.getWhoModificates().equals(username) && worker.getId() == id)){
                Integer idn = DBLoader.getInstance().remove(username,id);
                if(idn != null){
                    this.collection.removeIf(worker -> worker.getId() == id);
                    return true;
                }
                else {
                    return false;
                }
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public String removeGreaterThan(Worker worker) throws  SQLException{
        readWriteLock.writeLock().lock();
        try{
            List<Worker> toDelete = this.collection.stream()
                    .filter(worker1 -> worker1.getWhoModificates().equals(worker.getWhoModificates())
                            && worker1.getSalary() >= worker.getSalary())
                    .toList();
            if(toDelete.isEmpty()){
                return "No element deleted";
            }
            List<Integer> successfullyDeletedIds = new ArrayList<>();
            for (Worker workerc : toDelete) {
                Integer idn = DBLoader.getInstance().remove(worker.getWhoModificates(), workerc.getId());
                if (idn != null) {
                    successfullyDeletedIds.add(workerc.getId());
                }
            }
            this.collection.removeIf(workert -> successfullyDeletedIds.contains(workert.getId()));
            return "The next elements were deleted: ".concat(toDelete.stream().map(Worker::getId).map(String::valueOf).collect(Collectors.joining(", ")));
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public String removeLast(String username){
        readWriteLock.writeLock().lock();
        try {
            if (this.collection.stream().anyMatch(worker -> worker.getWhoModificates() != null && worker.getWhoModificates().equals(username))) {
                Integer id = this.collection.stream().filter(worker -> worker.getWhoModificates() != null && worker.getWhoModificates().equals(username)).max(Comparator.comparing(Worker::getId)).map(Worker::getId).get();
                Integer idn = DBLoader.getInstance().remove(username, id);
                if (idn != null) {
                    this.collection.removeIf(worker -> worker.getId() == idn);
                    return String.format("The element %s has been deleted %n",idn);
                } else {
                    return "No element has been deleted \n";
                }
            }else {
                return String.format("The user %s does has no workers inserted in the data base %n",username);
            }
        }catch (SQLException e) {
        throw new RuntimeException(e);
        } finally {
        readWriteLock.writeLock().unlock();
        }
    }

    public Boolean update(Worker worker){
        readWriteLock.writeLock().lock();
        try {
            if (this.collection.stream().anyMatch(worker1 -> worker1.getWhoModificates().equals(worker.getWhoModificates()) && worker1.getId() == worker.getId())) {
                Boolean changeId = DBLoader.getInstance().update(worker, worker.getWhoModificates());
                if (changeId != null && changeId) {
                    this.collection.removeIf(worker1 -> worker1.getWhoModificates().equals(worker.getWhoModificates()) && worker1.getId() == worker.getId());
                    this.collection.add(worker);
                    return true;
                } else {
                    return false;
                }
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
                readWriteLock.writeLock().unlock();
        }
    }

    public long countByEndDate(LocalDate date){
        readWriteLock.readLock().lock();
        try{
            long count = 0;
            if (date != null) {
                count = this.collection.stream().filter(Worker -> Worker.getEndDate().toString().equals(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()).toString())).count();
            }else {
                count = this.collection.stream().filter(Worker -> Worker.getEndDate() == null).count();
            }
            return count;
        }
        finally {
            readWriteLock.readLock().unlock();
        }
    }

    public String filterStartsWithName(String starts_with){
        readWriteLock.readLock().lock();
        try{
            String output = this.collection.stream()
                    .filter(worker -> worker.getName().toLowerCase().startsWith(starts_with.toLowerCase())).sorted(Comparator.comparingDouble(Worker::getSalary).reversed())
                    .map(Worker::getInfo)
                    .collect(Collectors.joining("\n"));

            if (output.isEmpty()) {
                return null;
            }
            return output;
        }
        finally {
            readWriteLock.readLock().unlock();
        }
    }

    public List<Worker> minByPosition(){
        readWriteLock.readLock().lock();
        try{
            if (this.collection.isEmpty()){
                return new ArrayList<Worker>();
            }
            Optional<Worker> maxWorker = this.collection.stream()
                    .filter(worker -> worker.getPosition() != null)
                    .max(Comparator.comparing(Worker::getPosition));

            List<Worker> result = maxWorker.map(List::of).orElseGet(List::of);
            return result;
        }
        finally {
            readWriteLock.readLock().unlock();
        }
    }

    public List<Worker> showAll(){
        readWriteLock.readLock().lock();
        try{
            List<Worker> output = this.collection.stream().sorted(Comparator.comparingDouble(Worker::getSalary).reversed())
                    .toList();

            if (!output.isEmpty()) {
                return output;
            }
            return new ArrayList<Worker>();
        }
        finally {
            readWriteLock.readLock().unlock();
        }
    }

    public String getClassList(){
        return this.classList;
    }

    /**
     * Provides information about the collection.
     * @return a formatted string with collection details.
     */
    public String getInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format(
                "Data creation: %s%nNumber of elements: %d%nStorage: %s%n",
                this.dataCreation.format(formatter), this.collection.size(), this.getClassList()
        );
    }

    public DBLoader getDBLoader() {
        return this.dbLoader;
    }

    public Collection<Worker> getCollection() {
        return this.collection;
    }
}
