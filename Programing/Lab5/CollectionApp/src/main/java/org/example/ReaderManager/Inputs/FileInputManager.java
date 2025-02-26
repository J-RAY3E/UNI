package org.example.ReaderManager.Inputs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileInputManager implements InputManager{

    private final BufferedInputStream reader;
    private final StringBuilder buffer = new StringBuilder();

    public FileInputManager(String path) throws FileNotFoundException{
        this.reader = new BufferedInputStream(new FileInputStream(path));
    }
    public String nextLine(){
        this.buffer.setLength(0);
        int data;
        try {
            while ((data = reader.read()) != -1) {
                char c = (char) data;
                if (c == '\n') break;
                if (c != '\r') buffer.append(c);
            }

            if (buffer.isEmpty() && data == -1) return null;
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String readAll() {
        StringBuilder content = new StringBuilder();
        int data;
        try {
            while ((data = reader.read()) != -1) {
                content.append((char) data);
            }
            return content.toString().trim();
        } catch (IOException e) {
            throw new RuntimeException("It was not possible  read the actual file", e);
        }
    }

}
