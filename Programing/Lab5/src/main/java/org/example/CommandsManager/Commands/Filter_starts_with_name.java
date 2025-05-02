package CommandsManager.Commands;

import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

import java.util.stream.Collectors;

public class Filter_starts_with_name extends Command {


    public Filter_starts_with_name (CollectionManager storageManager,Integer numArguments){
        super(storageManager,numArguments);
    }
    
    @Override
    public String description(){
        return "filter_starts_with_name name - print all the elements that contains the substring name";
    }

    @Override
    public Response execute(String... args){
        try {
            String output = this.collectionManager.getCollection().stream().filter(Worker -> Worker.getName().contains(args[0])).map(Worker::getInfo).collect(Collectors.joining("\n"));
            if(!output.isEmpty()){
                return new Response(output, RequestState.RETURNED);
            }
            return new Response(this.getClass().toString(), RequestState.DONE);
        } catch (Exception e){
            return new Response(e.getMessage() + " in command"  + this.getClass(), RequestState.ERROR);
        }
    }
}
