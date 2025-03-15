package org.example.ReaderManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManagerID {
    private  final int idSize;
    private final ArrayList<Integer> ids = new ArrayList<>();
    public ManagerID(final int idSize) {
        this.idSize = idSize;
    }

    public Integer generateID(){
        int id;
        do{
            id = new Random().nextInt(10,1000*idSize);

        }
        while (isValidID(id));
        addID(id);
        return id;
    }

    public boolean isValidID(Integer id ){
        return this.ids.contains(id);
    }

     private void  addID(Integer id){
            this.ids.add(id);
    }

}
