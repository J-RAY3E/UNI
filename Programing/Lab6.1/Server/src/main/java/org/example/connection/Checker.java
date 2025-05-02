package org.example.connection;


import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Checker {
    private final Logger logger;
    private static Checker checker;
    private Checker (String name){
        this.logger = Logger.getLogger(name);
        this.logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler() {
            {
                setOutputStream(System.out);
            }
        };
        handler.setLevel(Level.ALL);
        handler.setFormatter(new SimpleFormatter());
        this.logger.addHandler(handler);
    }
    public static  Checker getInstance(String name){

        return checker = new Checker(name);
    }

    public static  Checker getInstance() throws IllegalStateException{
        if(checker == null) throw  new IllegalStateException("There is not instance of Checker yet ");
        return checker;
    }

    public Logger getLogger(){
        return  this.logger;
    }
}
