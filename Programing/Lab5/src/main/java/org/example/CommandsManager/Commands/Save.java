package org.example.CommandsManager.Commands;

import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.FileInputManager;
import org.example.ReaderManager.Inputs.Response;
import org.example.ReaderManager.Parse.WriteJSON;
import org.example.Storage.CollectionManager;

import java.util.stream.Collectors;


public class Save extends Command {

    public Save(CollectionManager collectionManager, Integer numArguments){
        super(collectionManager,numArguments);
    }

    @Override
    public String  description(){
        return "save - save the elements of the collection ";
    };

    @Override
    public Response execute(String... args){
        try {
            new WriteJSON(this.collectionManager).saveCollectionToJson();
            return new Response(this.getClass().toString(), RequestState.DONE);
        } catch (Exception e){
            return new Response(e.getMessage() + " in command"  + this.getClass(), RequestState.ERROR);
        }
    }

}
