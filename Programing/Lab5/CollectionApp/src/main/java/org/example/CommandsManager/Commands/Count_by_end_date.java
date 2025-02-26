package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.CommandIn;
import org.example.ReaderManager.InputValidator;
import org.example.ReaderManager.Inputs.InputManager;
import org.example.ReaderManager.TypeValidation.ValidationDate;
import org.example.Storage.CollectionManager;


import java.time.LocalDate;


public class Count_by_end_date extends CommandIn {

    public Count_by_end_date (CollectionManager storageManager, InputManager inputManager) {
        super(storageManager, inputManager);
    }
    
    @Override
    public String description() {
        return "count_by_end_date - count all the elements of the list with the same end_date field";
    };

    @Override
    public void execute(String... args) {
        LocalDate date = new InputValidator(this.inputManager).execute(">> Insert Date",new ValidationDate(false), LocalDate::parse, args[0]);
        this.collectionManager.count_by_end_date(date);
    };
}
