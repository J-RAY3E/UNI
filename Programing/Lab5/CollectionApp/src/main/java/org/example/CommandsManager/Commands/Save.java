package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.CommandIn;
import org.example.ReaderManager.Parse.WriteJSON;
import org.example.Storage.CollectionManager;

public class Save extends CommandIn {

    public Save(CollectionManager collectionManager){
        super(collectionManager, null);
    }

    @Override
    public String  description(){
        return "save - save the elements of the collection ";
    };

    @Override
    public void execute(String... args){
        new WriteJSON(args[0], this.collectionManager).saveCollectionToJson();
        ;
    };

}
