package org.example.ReaderManager;

import org.example.Classes.Address;
import org.example.Classes.Coordinates;
import org.example.Classes.Organization;
import org.example.Classes.Worker;
import org.example.Enums.Position;
import org.example.Enums.Status;
import org.example.ReaderManager.Inputs.InputValidator;
import org.example.ReaderManager.Parse.BooleanCondition;
import org.example.ReaderManager.TypeValidation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;


/**
 * Factory class responsible for creating Worker objects with validated input.
 */
public final class CreationFactory {

    private final InputValidator inputValidator;

    /**
     * Constructs a CreationFactory instance.
     * @param inputValidator the input validator used for input validation.
     */
    public CreationFactory(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    /**
     * Creates a Worker instance with a given ID and user-provided input.
     * @param id the worker ID.
     * @return a new Worker instance.
     */
    public Worker createWorker(int id) {
        return createWorker(id, "", false);
    }

    /**
     * Creates a Worker instance with a given ID and name.
     * @param id the worker ID.
     * @param name the worker's name.
     * @return a new Worker instance.
     */
    public Worker createWorker(int id, String name) {
        return createWorker(id, name, true);
    }
    /**
     * Creates a Worker instance without ID.
     * @param name the worker's name.
     * @return a new Worker instance.
     */
    public Worker createWorker(String name) {
        return createWorker(0, name, true);
    }

    /**
     * Creates a Worker instance with full attribute input.
     * @param id the worker ID.
     * @param name the worker's name.
     * @param updateMode flag to determine if update mode is active.
     * @return a new Worker instance.
     */
    public Worker createWorker(int id, String name, boolean updateMode) {
        if (!updateMode) {
            name = inputValidator.execute(
                    "Insert the name for the new element id " + id + ": ",
                    new ValidationString(255, false),
                    String::trim
            );
        } else {
            System.out.printf("Insert the data for the element %s%n", name);
        }

        Position position = selectEnum("position", Position.class);
        Status status = selectEnum("status", Status.class);
        Integer salary = inputValidator.execute(
                ">> Insert salary (must be between 0 and " + Integer.MAX_VALUE + "): ",
                new ValidationNumber(0d, (double) Integer.MAX_VALUE, false),
                Integer::parseInt
        ).intValue();

        Coordinates coordinates = createCoordinates();
        LocalDate endDate = inputValidator.execute(
                ">> Insert end date (yyyy-MM-dd): ",
                new ValidationDate(true),
                LocalDate::parse
        );

        Organization organization = createOrganizationIfRequired();

        return new Worker(
                name, id, LocalDateTime.now(),
                (endDate != null) ? Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null,
                salary, coordinates, position, status, organization
        );
    }

    /**
     * Generic method for selecting an enum value from user input.
     * @param fieldName the field name.
     * @param enumClass the enum class type.
     * @return the selected enum value.
     * @param <T> the enum type.
     */
    private <T extends Enum<T>> T selectEnum(String fieldName, Class<T> enumClass) {
        return inputValidator.execute(
                String.format(">> Insert %s (%s): ", fieldName, Arrays.toString(enumClass.getEnumConstants())),
                new ValidationEnum<>(enumClass, true),
                value -> Enum.valueOf(enumClass, value.toUpperCase())
        );
    }

    /**
     * Creates and validates coordinate values.
     * @return a Coordinates instance.
     */
    private Coordinates createCoordinates() {
        Integer x = inputValidator.execute(
                ">> Insert coordinate X (0 ≤ X ≤ 395): ",
                new ValidationNumber(0d, 395d, false),
                Integer::parseInt
        ).intValue();

        Float y = inputValidator.execute(
                ">> Insert coordinate Y (-Float.MAX_VALUE ≤ Y ≤ Float.MAX_VALUE): ",
                new ValidationNumber((double) -Float.MAX_VALUE, (double) Float.MAX_VALUE, false),
                Float::parseFloat
        ).floatValue();

        return new Coordinates(x, y);
    }

    /**
     * Creates an Organization if the user opts to add one.
     * @return an Organization instance or null if skipped.
     */
    private Organization createOrganizationIfRequired() {
        Boolean createOrg = inputValidator.execute(
                ">> Add organization (y/n): ",
                new ValidationBoolean(false),
                BooleanCondition::parse
        );

        if (createOrg == null || !createOrg) {
            return null;
        }

        String fullName = inputValidator.execute(
                ">> Insert organization's full name: ",
                new ValidationString(255, false),
                String::trim
        );

        Integer annualTurnover = Objects.requireNonNull(inputValidator.execute(
                ">> Insert annual turnover (≥0): ",
                new ValidationNumber(0d, (double) Integer.MAX_VALUE, false),
                Integer::parseInt
        )).intValue();

        Integer employeesCount = inputValidator.execute(
                ">> Insert employees count (≥0): ",
                new ValidationNumber(0d, (double) Integer.MAX_VALUE, false),
                Integer::parseInt
        ).intValue();

        Address address = createAddress();

        return new Organization(fullName, annualTurnover, employeesCount, address);
    }

    /**
     * Creates and validates an Address object.
     * @return an Address instance.
     */
    private Address createAddress() {
        String zipCode = inputValidator.execute(
                ">> Insert ZIP code (9 digits): ",
                new ValidationString(9, false),
                String::trim
        );
        return new Address(zipCode);
    }
}
