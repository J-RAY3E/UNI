package org.example;
// DataStructures


//Tools IO 


import org.example.ReaderManager.Handler;
import org.example.ReaderManager.RuntimeManager;


public class Main{
    public static void main(String[] args) {


        if (args.length > 0) {

           // new ReadJSON("default_ path").loadData(collectionManager);
        }

        System.out.println("Welcome to StorageManager");
        new RuntimeManager(new Handler()).Reader();



    }

}