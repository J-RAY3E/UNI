package org.example.GUI.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.Classes.*;
import org.example.Enums.Position;
import org.example.Enums.Status;
import org.example.GUI.ViewController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Consumer;

public class FormView extends VBox {

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

    private Consumer<Worker> addWorker;
    private final Button addButton = new Button("Submit");

    public FormView() {
        setSpacing(10);
        setPadding(new Insets(15));
        setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));

        // Set ComboBox items
        positionBox.getItems().addAll(Position.values());
        statusBox.getItems().addAll(Status.values());

        // Build layout with GridPane (2 columns)
        GridPane grid = new GridPane();
        grid.setVgap(6);
        grid.setHgap(15);
        grid.setMaxWidth(Double.MAX_VALUE);

        int row = 0;

        // Worker section label
        Label workerLabel = createSectionLabel("Worker");
        grid.add(workerLabel, 0, row, 2, 1);
        row++;

        addStyled(grid, "Name:", nameField, row++);
        addStyled(grid, "Salary:", salaryField, row++);
        addStyled(grid, "Position:", positionBox, row++);
        addStyled(grid, "Status:", statusBox, row++);
        addStyled(grid, "End Date:", endDatePicker, row++);

        // Coordinates section
        Label coordLabel = createSectionLabel("Coordinates");
        grid.add(coordLabel, 0, row, 2, 1);
        row++;

        addStyled(grid, "X Coordinate:", coordXField, row++);
        addStyled(grid, "Y Coordinate:", coordYField, row++);

        // Organization section
        Label orgLabel = createSectionLabel("Organization");
        grid.add(orgLabel, 0, row, 2, 1);
        row++;

        addStyled(grid, "Full Name:", orgNameField, row++);
        addStyled(grid, "Annual Turnover:", orgTurnoverField, row++);
        addStyled(grid, "Employees Count:", orgEmployeesField, row++);
        addStyled(grid, "ZIP Code:", zipCodeField, row++);

        // Button
        addButton.setMaxWidth(Double.MAX_VALUE);
        styleButton(addButton);
        // Align button to the right column
        grid.add(new Label(""), 0, row); // filler
        grid.add(addButton, 1, row);

        VBox.setVgrow(grid, Priority.ALWAYS);
        getChildren().add(grid);

        setAddWorkerConsumer();

        addButton.setOnAction(e -> {
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

                Worker worker = new Worker(name, 2, LocalDateTime.now(), endDate, (int) salary, coordinates, position, status, organization);

                if (addWorker != null) addWorker.accept(worker);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Input Error");
                alert.setContentText("Error while creating worker: " + ex.getMessage());
                alert.showAndWait();
            }
        });
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

    private void addStyled(GridPane grid, String labelText, Control field, int row) {
        Label label = new Label(labelText);
        label.setTextFill(Color.LIGHTGRAY);
        grid.add(label, 0, row);

        styleField(field);
        grid.add(field, 1, row);
        GridPane.setHgrow(field, Priority.ALWAYS);
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

    public void setAddWorkerConsumer() {
        this.addWorker = worker -> ViewController.getInstance().addData(worker);
    }

    public void setOnSave(Runnable onSave) {

    }
}
