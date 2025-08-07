package org.example.GUI.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import org.example.Classes.*;
import org.example.Enums.FormMode;
import org.example.Enums.MessageType;
import org.example.Enums.Position;
import org.example.Enums.Status;
import org.example.GUI.I18.I18NManager;
import org.example.GUI.RefreshableUI;
import org.example.GUI.ViewController;
import org.example.connection.NotificationManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Date;

public class FormView extends VBox implements RefreshableUI {

    private final TextField nameField = new TextField();
    private final TextField salaryField = new TextField();
    private final ComboBox<Position> positionBox = new ComboBox<>();
    private final ComboBox<Status> statusBox = new ComboBox<>();
    private final DatePicker endDatePicker = new DatePicker();

    private final TextField coordXField = new TextField();
    private final TextField coordYField = new TextField();

    private final TextField orgNameField = new TextField();
    private final TextField orgTurnoverField = new TextField();
    private final TextField orgEmployeesField = new TextField();
    private final TextField zipCodeField = new TextField();

    private final Button submitButton = new Button("Submit");

    private FormMode mode;
    private Worker workerToModify;

    public FormView(FormMode mode, Worker workerToModify) {
        this.mode = mode;
        this.workerToModify = workerToModify;


        I18NManager.getInstance().registerComponent(this);
        initializeUI();

        if (mode == FormMode.MODIFY && workerToModify != null) {
            fillFields(workerToModify);
        }
    }

    private void handleSubmit() {
        try {


            String name = nameField.getText().trim();
            double salary = Double.parseDouble(salaryField.getText().trim());
            Position position = positionBox.getValue();
            Status status = statusBox.getValue();
            LocalDate endLocalDate = endDatePicker.getValue();
            if (endLocalDate == null) throw new IllegalArgumentException("End Date must be set");
            Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            int x = Integer.parseInt(coordXField.getText().trim());
            float y = Float.parseFloat(coordYField.getText().trim());

            String orgName = orgNameField.getText().trim();
            int turnover = Integer.parseInt(orgTurnoverField.getText().trim());
            int employees = Integer.parseInt(orgEmployeesField.getText().trim());
            String zip = zipCodeField.getText().trim();

            Coordinates coordinates = new Coordinates(x, y);
            Address address = new Address(zip);
            Organization organization = new Organization(orgName, turnover, employees, address);

            Worker worker = new Worker(name, (workerToModify != null ? workerToModify.getId() : 0), LocalDateTime.now(), endDate, (int) salary, coordinates, position, status, organization);

            switch (mode) {
                case DELETE_GREATER_THAN -> ViewController.getInstance().deleteGreaterThan(worker);
                case ADD -> ViewController.getInstance().addData(worker);
                case ADD_IF_MAX -> ViewController.getInstance().addIfMax(worker);
                case MODIFY -> {
                    NotificationManager.getInstance().pushMessage("NO ESTA LLEGANDO"+mode.toString(), MessageType.INFO);
                    ViewController.getInstance().modifyWorker(worker);
                }
            }

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Input Error");
            alert.setContentText("Error while creating worker: " + ex.getMessage());
            alert.showAndWait();
        }
    }



    private void fillFields(Worker worker) {
        nameField.setText(worker.getName());
        salaryField.setText(String.valueOf(worker.getSalary()));
        positionBox.setValue(worker.getPosition());
        statusBox.setValue(worker.getStatus());

        coordXField.setText(String.valueOf(worker.getCoordinates().getX()));
        coordYField.setText(String.valueOf(worker.getCoordinates().getY()));

        orgNameField.setText(worker.getOrganization() == null  ? null: worker.getOrganization().getFullName());
        orgTurnoverField.setText(worker.getOrganization() == null  ? null:  String.valueOf(worker.getOrganization().getAnnualTurnover()));
        orgEmployeesField.setText(worker.getOrganization() == null  ? null:  String.valueOf(worker.getOrganization().getEmployeesCount()));
        zipCodeField.setText(worker.getOrganization() == null  ? null:  worker.getOrganization().getPostalAddress().getZipCode());

        endDatePicker.setValue(worker.getEndDate() == null ? null: worker.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    private Label createSectionLabel(String text) {
        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        label.setFont(javafx.scene.text.Font.font("SansSerif", javafx.scene.text.FontWeight.BOLD, 14));
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1, 0, 1, 0))));
        return label;
    }

    private void addStyled(GridPane grid, String labelText, Node field, int col, int row, int colSpan) {
        Label label = new Label(labelText);
        label.setStyle("-fx-text-fill: white;");
        grid.add(label, col, row);
        grid.add(field, col + 1, row, colSpan, 1);
    }

    private void styleField(Control field) {
        field.setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: white;");

        if (field instanceof TextField tf) {
            tf.setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: white; -fx-caret-color: white;");
        } else if (field instanceof ComboBox<?> cb) {
            cb.setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: white;");
        } else if (field instanceof DatePicker dp) {
            dp.setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: white;");
        }
    }

    private void styleButton(Button button) {
        button.setStyle(
                "-fx-background-color: #5A5A5A; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-color: lightgray; " +
                        "-fx-border-radius: 4; " +
                        "-fx-background-radius: 4;");
    }

    @Override
    public void refreshUI() {
        // Guarda los valores actuales
        Worker currentData = getCurrentFormData();

        // Reconstruye la UI
        getChildren().clear();
        initializeUI();

        // Restaura los valores
        if (currentData != null) {
            fillFields(currentData);
        } else if (workerToModify != null) {
            fillFields(workerToModify);
        }
    }

    private void initializeUI() {
        setSpacing(10);
        setPadding(new Insets(15));
        setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));

        positionBox.getItems().clear();
        positionBox.getItems().addAll(Position.values());
        statusBox.getItems().clear();
        statusBox.getItems().addAll(Status.values());

        configureComboBoxes();
        configureDatePicker();

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(20);
        grid.setMaxWidth(Double.MAX_VALUE);

        int row = 0;

        // Worker Section
        grid.add(createSectionLabel(I18NManager.getInstance().getMessage("section_worker")), 0, row++, 4, 1);
        addStyled(grid, I18NManager.getInstance().getMessage("label_name") + ":", nameField, 0, row, 1);
        addStyled(grid, I18NManager.getInstance().getMessage("label_salary") + ":", salaryField, 2, row++, 1);
        addStyled(grid, I18NManager.getInstance().getMessage("label_position") + ":", positionBox, 0, row, 1);
        addStyled(grid, I18NManager.getInstance().getMessage("label_status") + ":", statusBox, 2, row++, 1);
        addStyled(grid, I18NManager.getInstance().getMessage("label_end_date") + ":", endDatePicker, 0, row++, 2);

        // Coordinates Section
        grid.add(createSectionLabel(I18NManager.getInstance().getMessage("section_coordinates")), 0, row++, 4, 1);
        addStyled(grid, I18NManager.getInstance().getMessage("label_x_coord") + ":", coordXField, 0, row, 1);
        addStyled(grid, I18NManager.getInstance().getMessage("label_y_coord") + ":", coordYField, 2, row++, 1);

        // Organization Section
        grid.add(createSectionLabel(I18NManager.getInstance().getMessage("section_organization")), 0, row++, 4, 1);
        addStyled(grid, I18NManager.getInstance().getMessage("label_full_name") + ":", orgNameField, 0, row, 1);
        addStyled(grid, I18NManager.getInstance().getMessage("label_turnover") + ":", orgTurnoverField, 2, row++, 1);
        addStyled(grid, I18NManager.getInstance().getMessage("label_employees") + ":", orgEmployeesField, 0, row, 1);
        addStyled(grid, I18NManager.getInstance().getMessage("label_zip") + ":", zipCodeField, 2, row++, 1);

        submitButton.setText(I18NManager.getInstance().getMessage("submit"));
        submitButton.setMaxWidth(Double.MAX_VALUE);
        styleButton(submitButton);
        grid.add(submitButton, 0, row, 4, 1);

        submitButton.setOnAction(e -> handleSubmit());

        VBox.setVgrow(grid, Priority.ALWAYS);
        getChildren().add(grid);
    }

    private Worker getCurrentFormData() {
        try {
            String name = nameField.getText().trim();
            double salary = salaryField.getText().isEmpty() ? 0 : Double.parseDouble(salaryField.getText().trim());
            Position position = positionBox.getValue();
            Status status = statusBox.getValue();
            LocalDate endLocalDate = endDatePicker.getValue();
            Date endDate = endLocalDate != null ?
                    Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;

            int x = coordXField.getText().isEmpty() ? 0 : Integer.parseInt(coordXField.getText().trim());
            float y = coordYField.getText().isEmpty() ? 0 : Float.parseFloat(coordYField.getText().trim());

            String orgName = orgNameField.getText().trim();
            int turnover = orgTurnoverField.getText().isEmpty() ? 0 : Integer.parseInt(orgTurnoverField.getText().trim());
            int employees = orgEmployeesField.getText().isEmpty() ? 0 : Integer.parseInt(orgEmployeesField.getText().trim());
            String zip = zipCodeField.getText().trim();

            Coordinates coordinates = new Coordinates(x, y);
            Address address = new Address(zip);
            Organization organization = new Organization(orgName, turnover, employees, address);

            return new Worker(
                    name,
                    (workerToModify != null ? workerToModify.getId() : 0),
                    LocalDateTime.now(),
                    endDate,
                    (int) salary,
                    coordinates,
                    position,
                    status,
                    organization
            );
        } catch (Exception e) {
            return null;
        }
    }

    private void configureComboBoxes() {
        positionBox.setConverter(new StringConverter<Position>() {
            @Override
            public String toString(Position position) {
                return position != null ?
                        I18NManager.getInstance().getMessage("position_" + position.name().toLowerCase()) : "";
            }

            @Override
            public Position fromString(String string) {
                return positionBox.getItems().stream()
                        .filter(p -> toString(p).equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        statusBox.setConverter(new StringConverter<Status>() {
            @Override
            public String toString(Status status) {
                return status != null ?
                        I18NManager.getInstance().getMessage("status_" + status.name().toLowerCase()) : "";
            }

            @Override
            public Status fromString(String string) {
                return statusBox.getItems().stream()
                        .filter(s -> toString(s).equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    private void configureDatePicker() {
        String pattern = DateTimeFormatterBuilder.getLocalizedDateTimePattern(
                FormatStyle.SHORT,
                null,
                IsoChronology.INSTANCE,
                I18NManager.getInstance().getCurrentLocale()
        );
        endDatePicker.setConverter(new LocalDateStringConverter(
                DateTimeFormatter.ofPattern(pattern),
                DateTimeFormatter.ofPattern(pattern)
        ));
    }


}
