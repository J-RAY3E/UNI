package ReaderManager;

import org.example.ReaderManager.Inputs.InputManager;

import java.util.Stack;

public class StackInputs {
    private static Stack<InputManager> readers;


    private StackInputs() {
    }

    public static void initialize(){
        if (readers == null) {
            readers = new Stack<>();
        }
    }

    public  static InputManager getCurrentInput(){
        if (readers.isEmpty()) {
            throw new IllegalStateException("Error: No readers available in the stack");
        }
        return readers.peek();
    }

    public static void addReader(InputManager reader){
        if(readers.stream().noneMatch(InputManager-> InputManager.getPath().equals(reader.getPath()))){readers.add(reader);}
        else{System.out.println("Error: Reader is already added");};
    }

    public static void removeReader(){
        if (!readers.isEmpty()) {
            readers.pop();
        } else {
            System.out.println("Error: No readers to remove.");
        }
    }

}
