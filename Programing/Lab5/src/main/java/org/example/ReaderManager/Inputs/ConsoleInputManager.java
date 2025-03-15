package org.example.ReaderManager.Inputs;


import java.util.Scanner;

public class ConsoleInputManager implements InputManager{
    public final Scanner reader;
    public final String path= "Console";
    public ConsoleInputManager()  {
        this.reader = new Scanner(System.in);
    }

    public String getPath() {return path;}
    public String nextLine(){
        return  this.reader.nextLine();
    }
    public Boolean hasNextLine(){
        return this.reader.hasNextLine();
    }

}
