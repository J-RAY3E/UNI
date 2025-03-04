package org.example.ReaderManager.Inputs;

import java.io.*;

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

    public static boolean isAvailablePath(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            System.out.println("Error: Empty file path.");
            return false;
        }
        File file = new File(filePath);
        System.out.println(file.getAbsolutePath());
        if (!file.exists()) {
            System.out.println("Error: File no exists: " + filePath);
            return false;
        }

        if (!file.canRead()) {
            System.out.println("Error: No lecture permissions: " + filePath);
            return false;
        }

        return true;

    }
}
