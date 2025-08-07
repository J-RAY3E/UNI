package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.CommandsManager.ConsoleCommandsManager;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.FileInputManager;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.ReaderManager.Inputs.Request;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;
import org.example.UserUtils.UserManager;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Arrays;


/**
 * Command to execute a script from a file.
 */
public final class ExecuteFile extends Command {
    private static final long serialVersionUID = 1L;
    private String parameter1;
    /**
     * Constructs an ExecuteFile command.

     * @param numArguments the expected number of arguments.
     */
    public ExecuteFile(Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "execute_file {file} - execute instructions from the specified file";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        String fileName = Paths.get(this.parameter1).getFileName().toString();
            String fullPath = Paths.get(System.getProperty("user.home"), "scripts", fileName).toString();
        if (!FileInputManager.isAvailablePath(fullPath)) {
            return new Response("Error: Not available path" + fullPath, RequestState.ERROR);
        }
        try {
            System.out.println("paso pero no se por que no empieza a leer s necesito ayuda mrd ");
            FileInputManager fileInputManager = new FileInputManager(fullPath);
            InputManagerRegistry.getInstance().addScriptInput(fileInputManager);
            String output = "";
            ConsoleCommandsManager consoleCommandsManager = new ConsoleCommandsManager(collectionManager);
            consoleCommandsManager.load();
            while (InputManagerRegistry.getInstance().getCurrentInput().hasNextLine()){
                String query = InputManagerRegistry.getInstance().getCurrentInput().nextLine();
                String[] parameters = query.split(" ");
                Response response = consoleCommandsManager.eject(new Request(parameters[0], Arrays.copyOfRange(parameters, 1, parameters.length), parameters.length - 1,new UserManager(this.getUsername(),"",true,"script")));
                output = output.concat(response.getMessage());
            }
            if(output.isEmpty()){
                return new Response(this.getClass().getSimpleName(), RequestState.DONE);
            }
            return new Response(output, RequestState.UPDATE);
        } catch (FileNotFoundException e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
        finally {
            InputManagerRegistry.getInstance().removeCurrentInput();
        }
    }

    @Override
    public void setParameters(String... args){
        this.parameter1 = args[0];
    }
}
