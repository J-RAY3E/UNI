package org.example.CommandsManager.Commands;


import org.example.Classes.Address;
import org.example.Classes.Coordinates;
import org.example.Classes.Organization;
import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;

import org.example.Enums.Position;
import org.example.Enums.Status;
import org.example.ReaderManager.CreationFactory;
import org.example.ReaderManager.Inputs.InputValidator;
import org.example.ReaderManager.Inputs.InputManagerRegistry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;


/**
 * Command to remove elements greater than a given one.
 */
public final class RemoveGreater extends Command {
    private static final long serialVersionUID = 1L;
    private Worker parameter1;
    public RemoveGreater( Integer numArguments) {
        super( numArguments);
    }

    @Override
    public String description() {
        return "remove_greater {element} - remove all elements greater than the given one";
    }


    @Override
    public void setParameters(String... args) {
        if (args.length == 0) throw new IllegalArgumentException("No command string provided");
        this.parameter1 = parseWorkerFromCommand(String.join(" ", args));
    }

    private Worker parseWorkerFromCommand(String input) {
        String[] parts = input.trim().split(" ");

        Worker worker = null;
        Organization organization = null;
        Address address = null;
        Coordinates coordinates = null;

        for (String part : parts) {
            if (part.startsWith(":worker:")) {
                String[] fields = part.replace(":worker:", "").split("\\$");
                worker = new Worker();
                worker.setId(Integer.parseInt(fields[0]));
                worker.setName(fields[1]);
                worker.setSalary(Integer.parseInt(fields[2]));
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                    worker.setEndDate(formatter.parse(fields[3]));
                }catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                try {
                    worker.setCreationDate(LocalDateTime.parse(fields[4]));
                } catch (DateTimeParseException e) {
                    worker.setCreationDate(LocalDateTime.now());
                }
                worker.setPosition(Position.valueOf(fields[5]));
                worker.setStatus(Status.valueOf(fields[6]));

            } else if (part.startsWith(":organization:")) {
                String[] orgParts = part.replace(":organization:", "").split("\\$");
                organization = new Organization();
                organization.setFullName(orgParts[0]);
                organization.setAnnualTurnover(Integer.parseInt(orgParts[1]));
                organization.setEmployeesCount(Integer.parseInt(orgParts[2]));

            } else if (part.startsWith(":address:")) {
                address = new Address();
                address.setZipCode(part.replace(":address:", ""));

            } else if (part.startsWith(":coords:")) {
                String[] coords = part.replace(":coords:", "").split("\\$");
                coordinates = new Coordinates();
                coordinates.setX(Integer.parseInt(coords[0]));
                coordinates.setY(Float.parseFloat(coords[1]));
            }
        }

        if (organization != null) {
            if (address != null) {
                organization.setPostalAddress(address);
            }
            if (worker != null) {
                worker.setOrganization(organization);
            }
        }
        if (coordinates != null && worker != null) {
            worker.setCoordinates(coordinates);
        }

        return worker;
    }
}