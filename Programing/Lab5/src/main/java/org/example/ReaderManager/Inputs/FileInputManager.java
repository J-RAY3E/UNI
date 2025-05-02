package ReaderManager.Inputs;

import java.io.*;

/**
 * Handles input from files, providing line-by-line reading functionality.
 */
public final class FileInputManager implements InputManager {

    private final BufferedInputStream reader;
    private final String path;

    /**
     * Constructs a FileInputManager instance.
     * @param path the file path.
     * @throws FileNotFoundException if the file is not found.
     */
    public FileInputManager(final String path) throws FileNotFoundException {
        this.path = path;
        this.reader = new BufferedInputStream(new FileInputStream(path));
    }

    /**
     * Retrieves the file path.
     * @return the file path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Reads the next line from the file.
     * @return the next line as a string.
     */
    public String nextLine() {
        StringBuilder buffer = new StringBuilder();
        int data;
        try {
            while ((data = reader.read()) != -1) {
                char c = (char) data;
                if (c == '\n') {
                    break;
                }
                if (c != '\r') {
                    buffer.append(c);
                }
            }
            return buffer.toString();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Checks if the file has more lines.
     * @return true if more lines exist, false otherwise.
     */
    @Override
    public Boolean hasNextLine() {
        try {
            reader.mark(1);
            if (reader.read() != -1) {
                reader.reset();
                return true;
            }
        } catch (IOException e) {
            System.err.println("Error checking file: " + e.getMessage());
        }
        return false;
    }

    /**
     * Reads the entire file content as a single string.
     * @return the file content.
     */
    public String readAll() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(reader))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString().trim();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }
    }

    /**
     * Checks if a given file path is valid and readable.
     * @param filePath the file path.
     * @return true if the file exists and is readable.
     */
    public static boolean isAvailablePath(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            System.err.println("Error: Empty file path.");
            return false;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("Error: File does not exist - " + filePath);
            return false;
        }
        if (!file.canRead()) {
            System.err.println("Error: No read permission - " + filePath);
            return false;
        }
        return true;
    }
}
