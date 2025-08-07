package org.example.GUI.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.Classes.Worker;
import org.example.GUI.I18.I18NManager;
import org.example.GUI.RefreshableUI;
import org.example.GUI.ViewController;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

public class DescriptionView extends VBox implements RefreshableUI {
    private Worker currentWorker;
    private final Map<String, Label> valueLabels = new HashMap<>();
    private Button deleteButton, modifyButton;
    private Consumer<Worker> onDelete, onModify;

    public DescriptionView() {
        I18NManager.getInstance().registerComponent(this);
        initializeUI();
    }

    private void initializeUI() {
        setSpacing(8);
        setPadding(new Insets(20));
        setBackground(new Background(new BackgroundFill(Color.rgb(60, 60, 60), new CornerRadii(6), Insets.EMPTY)));

        Font labelFont = Font.font("Segoe UI", FontWeight.BOLD, 13);
        Font valueFont = Font.font("Segoe UI", 13);
        Font sectionFont = Font.font("Segoe UI", FontWeight.BOLD, 14);
        Color labelColor = Color.rgb(200, 200, 200);
        Color valueColor = Color.WHITE;
        Color sectionColor = Color.rgb(100, 180, 255);

        // Contenedor de dos columnas
        HBox columnsContainer = new HBox(20);
        VBox leftColumn = new VBox(8);
        VBox rightColumn = new VBox(8);
        columnsContainer.getChildren().addAll(leftColumn, rightColumn);

        // Sección de información básica (columna izquierda)
        addSectionLabel(leftColumn, "basic_info", sectionFont, sectionColor);
        addField(leftColumn, "name", labelFont, labelColor, valueFont, valueColor);
        addField(leftColumn, "salary", labelFont, labelColor, valueFont, valueColor);
        addField(leftColumn, "position", labelFont, labelColor, valueFont, valueColor);
        addField(leftColumn, "status", labelFont, labelColor, valueFont, valueColor);

        // Sección de coordenadas (columna izquierda)
        addSectionLabel(leftColumn, "coordinates", sectionFont, sectionColor);
        addField(leftColumn, "x_coordinate", labelFont, labelColor, valueFont, valueColor);
        addField(leftColumn, "y_coordinate", labelFont, labelColor, valueFont, valueColor);

        // Sección de organización (columna derecha)
        addSectionLabel(rightColumn, "organization", sectionFont, sectionColor);
        addField(rightColumn, "full_name", labelFont, labelColor, valueFont, valueColor);
        addField(rightColumn, "annual_turnover", labelFont, labelColor, valueFont, valueColor);
        addField(rightColumn, "employees", labelFont, labelColor, valueFont, valueColor);

        // Sección de fechas (columna derecha)
        addSectionLabel(rightColumn, "dates", sectionFont, sectionColor);
        addField(rightColumn, "creation_date", labelFont, labelColor, valueFont, valueColor);
        addField(rightColumn, "end_date", labelFont, labelColor, valueFont, valueColor);

        getChildren().add(columnsContainer);

        // Botones
        HBox buttonsBox = new HBox(15);
        buttonsBox.setPadding(new Insets(20, 0, 0, 0));
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        modifyButton = createActionButton("modify", "#5bc0de");
        deleteButton = createActionButton("delete", "#d9534f");
        buttonsBox.getChildren().addAll(modifyButton, deleteButton);
        getChildren().add(buttonsBox);
        refreshUI();
    }

    private void addSectionLabel(VBox container, String resourceKey, Font font, Color color) {
        Label sectionLabel = new Label();
        sectionLabel.setFont(font);
        sectionLabel.setTextFill(color);
        sectionLabel.setPadding(new Insets(10, 0, 5, 0));
        sectionLabel.setUserData(resourceKey); // Guardamos la clave para internacionalización
        container.getChildren().add(sectionLabel);
    }

    private void addField(VBox container, String resourceKey, Font labelFont, Color labelColor, Font valueFont, Color valueColor) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        Label label = new Label();
        label.setFont(labelFont);
        label.setTextFill(labelColor);
        label.setPrefWidth(120);
        label.setUserData(resourceKey); // Guardamos la clave para internacionalización

        Label valueLabel = new Label("-");
        valueLabel.setFont(valueFont);
        valueLabel.setTextFill(valueColor);
        valueLabel.setWrapText(true);

        valueLabels.put(resourceKey, valueLabel);
        row.getChildren().addAll(label, valueLabel);
        container.getChildren().add(row);
    }

    private Button createActionButton(String resourceKey, String color) {
        Button button = new Button();
        button.setStyle(String.format(
                "-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; " +
                        "-fx-padding: 8 20; -fx-background-radius: 4;", color));
        button.setCursor(Cursor.HAND);
        button.setUserData(resourceKey); // Guardamos la clave para internacionalización
        return button;
    }

    @Override
    public void refreshUI() {
        // Actualizar todos los labels de sección y campo
        for (Node node : getChildren()) {
            if (node instanceof HBox container) {
                for (Node child : container.getChildren()) {
                    if (child instanceof VBox column) {
                        updateLabelsInColumn(column);
                    }
                }
            }
        }

        // Actualizar botones
        modifyButton.setText(I18NManager.getInstance().getMessage("modify"));
        deleteButton.setText(I18NManager.getInstance().getMessage("delete"));

        // Actualizar datos del worker si existe
        if (currentWorker != null) {
            updateWorkerData();
        }
    }

    private void updateLabelsInColumn(VBox column) {
        for (Node node : column.getChildren()) {
            if (node instanceof HBox row) {
                for (Node child : row.getChildren()) {
                    if (child instanceof Label label && label.getUserData() != null) {
                        String text = I18NManager.getInstance().getMessage(label.getUserData().toString());
                        // Solo actualizar si es el label del campo, no del valor
                        if (!valueLabels.containsValue(label)) {
                            label.setText(text + ":");
                        }
                    }
                }
            } else if (node instanceof Label sectionLabel && sectionLabel.getUserData() != null) {
                sectionLabel.setText(I18NManager.getInstance().getMessage(sectionLabel.getUserData().toString()));
            }
        }
    }

    private void updateWorkerData() {
        // Formatear fechas correctamente
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateOnlyFormatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy", Locale.US);

        // Basic Information
        valueLabels.get("name").setText(currentWorker.getName());
        valueLabels.get("salary").setText("$" + currentWorker.getSalary());
        valueLabels.get("position").setText(currentWorker.getPositionStr());
        valueLabels.get("status").setText(currentWorker.getStatusStr());

        // Coordinates
        valueLabels.get("x_coordinate").setText(String.format("%.2f", currentWorker.getCoordinates().getX()));
        valueLabels.get("y_coordinate").setText(String.format("%.2f", currentWorker.getCoordinates().getY()));

        // Organization
        if (currentWorker.getOrganization() != null) {
            valueLabels.get("full_name").setText(currentWorker.getOrganization().getFullName());
            valueLabels.get("annual_turnover").setText(String.valueOf(currentWorker.getOrganization().getAnnualTurnover()));
            valueLabels.get("employees").setText(currentWorker.getOrganization().getEmployeesCount() != null ?
                    String.valueOf(currentWorker.getOrganization().getEmployeesCount()) : "N/A");
        }

        // Dates - Formateadas correctamente
        valueLabels.get("creation_date").setText(currentWorker.getCreationDate().format(dateFormatter));
        valueLabels.get("end_date").setText(currentWorker.getEndDate() != null ?
                dateOnlyFormatter.parse(currentWorker.getEndDate().toString()).toString() : "N/A");
    }

    public void setWorker(Worker worker) {
        currentWorker = worker;

        if (worker != null) {
            // Basic Information
            valueLabels.get("name").setText(worker.getName());
            valueLabels.get("salary").setText("$" + worker.getSalary());
            valueLabels.get("position").setText(worker.getPositionStr());
            valueLabels.get("status").setText(worker.getStatusStr());

            // Coordinates
            valueLabels.get("x_coordinate").setText(String.valueOf(worker.getCoordinates().getX()));
            valueLabels.get("y_coordinate").setText(String.valueOf(worker.getCoordinates().getY()));

            // Organization
            if (worker.getOrganization() != null) {
                valueLabels.get("full_name").setText(worker.getOrganization().getFullName());
                valueLabels.get("annual_turnover").setText(String.valueOf(worker.getOrganization().getAnnualTurnover()));
                valueLabels.get("employees").setText(worker.getOrganization().getEmployeesCount() != null ?
                        String.valueOf(worker.getOrganization().getEmployeesCount()) : "N/A");
            } else {
                valueLabels.get("full_name").setText("N/A");
                valueLabels.get("annual_turnover").setText("N/A");
                valueLabels.get("employees").setText("N/A");
            }

            // Dates
            valueLabels.get("creation_date").setText(worker.getCreationDate().toString());
            valueLabels.get("end_date").setText(worker.getEndDate() != null ? worker.getEndDate().toString() : "N/A");

            // Mostrar/ocultar botones según permisos
            boolean isOwner = ViewController.getInstance().getHandler().getUserManager()
                    .getUsername().equals(worker.getWhoModificates());
            deleteButton.setVisible(isOwner);
            modifyButton.setVisible(isOwner);
        } else {
            valueLabels.values().forEach(lbl -> lbl.setText("-"));
            deleteButton.setVisible(false);
            modifyButton.setVisible(false);
        }
    }

    public void setOnDelete(Consumer<Worker> onDelete) {
        this.onDelete = onDelete;
        deleteButton.setOnAction(e -> {
            if (onDelete != null && currentWorker != null) {
                onDelete.accept(currentWorker);
            }
        });
    }

    public void setOnModify(Consumer<Worker> onModify) {
        this.onModify = onModify;
        modifyButton.setOnAction(e -> {
            if (onModify != null && currentWorker != null) {
                onModify.accept(currentWorker);
            }
        });
    }
}