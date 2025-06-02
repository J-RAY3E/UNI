package org.example.CommandsManager.Commands;




import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.CreationFactory;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.ReaderManager.Inputs.InputValidator;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;


/**
 * Command to add a new element to the collection.
 */
public final class Add extends Command {
    private static final long serialVersionUID = 1L;
    public Worker parameter1;
    /**
     * Constructs an Add command.
     * @param numArguments the expected number of arguments.
     */
    public Add( Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "add {element} - add new element to the collection";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        this.parameter1.setWhoModificates(this.getUsername());
        Boolean added = collectionManager.add(this.parameter1);
        if (added) {
            return new Response(String.format("The element %s was added successfully",this.parameter1.getName()), RequestState.DONE);
        }
        return new Response("The element was not added to the data base", RequestState.DONE);
    }
    @Override
    public  void setParameters(String ...args){
        this.parameter1 = new CreationFactory(
                new InputValidator(InputManagerRegistry.getInstance().getCurrentInput())
        ).createWorker(args[0]);

    }
}

