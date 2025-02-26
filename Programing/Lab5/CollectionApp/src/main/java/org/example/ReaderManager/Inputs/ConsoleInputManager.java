package org.example.ReaderManager.Inputs;


import java.util.Scanner;

public class ConsoleInputManager implements InputManager{
    Scanner reader;
    public ConsoleInputManager()  {
        this.reader = new Scanner(System.in);
    }
    public String nextLine(){
        return  this.reader.nextLine();
    }
}
