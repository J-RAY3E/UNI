package org.example.GUI.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.Classes.Worker;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DescriptionView extends VBox {

    private final Map<String, Label> valueLabels = new HashMap<>();
    private final Button deleteButton;
    private final Button modifyButton;

    private Consumer<Worker> onDelete;
    private Consumer<Worker> onModify;

    // Para guardar el worker actualmente mostrado
    private Worker currentWorker;

    public DescriptionView() {
        setSpacing(5);
        setPadding(new Insets(20, 25, 20, 25));
        setBackground(new Background(new BackgroundFill(Color.rgb(60, 60, 60), CornerRadii.EMPTY, Insets.EMPTY)));

        Font labelFont = Font.font("Segoe UI", 13);
        Font valueFont = Font.font("Segoe UI", javafx.scene.text.FontWeight.BOLD, 13);
        Color labelColor = Color.rgb(180, 180, 180);
        Color valueColor = Color.WHITE;

        addField("Nombre", labelFont, labelColor, valueFont, valueColor);
        addField("Salario", labelFont, labelColor, valueFont, valueColor);
        addField("Cargo", labelFont, labelColor, valueFont, valueColor);
        addField("Estado", labelFont, labelColor, valueFont, valueColor);
        addField("Organización", labelFont, labelColor, valueFont, valueColor);
        addField("Fecha fin", labelFont, labelColor, valueFont, valueColor);

        // Botones al final
        HBox buttonsBox = new HBox(10);
        buttonsBox.setPadding(new Insets(15, 0, 0, 0));
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        deleteButton = new Button("Eliminar");
        deleteButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> {
            if (onDelete != null && currentWorker != null) {
                onDelete.accept(currentWorker);
            }
        });

        modifyButton = new Button("Modificar");
        modifyButton.setStyle("-fx-background-color: #5bc0de; -fx-text-fill: white;");
        modifyButton.setOnAction(e -> {
            if (onModify != null && currentWorker != null) {
                onModify.accept(currentWorker);
            }
        });

        buttonsBox.getChildren().addAll(modifyButton, deleteButton);
        getChildren().add(buttonsBox);
    }

    private void addField(String labelText, Font labelFont, Color labelColor, Font valueFont, Color valueColor) {
        HBox row = new HBox();
        row.setSpacing(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setMaxWidth(Double.MAX_VALUE);

        Label label = new Label(labelText + ": ");
        label.setFont(labelFont);
        label.setTextFill(labelColor);

        Label valueLabel = new Label("-");
        valueLabel.setFont(valueFont);
        valueLabel.setTextFill(valueColor);
        HBox.setHgrow(valueLabel, Priority.ALWAYS);
        valueLabel.setMaxWidth(Double.MAX_VALUE);
        valueLabel.setAlignment(Pos.CENTER_RIGHT);

        valueLabels.put(labelText, valueLabel);

        row.getChildren().addAll(label, valueLabel);
        getChildren().add(row);
    }

    public void setWorker(Worker worker) {
        currentWorker = worker;

        if (worker != null) {
            valueLabels.get("Nombre").setText(worker.getName());
            valueLabels.get("Salario").setText("$" + worker.getSalaryStr());
            valueLabels.get("Cargo").setText(worker.getPositionStr());
            valueLabels.get("Estado").setText(worker.getStatusStr());

            if (worker.getOrganization() == null) {
                valueLabels.get("Organización").setText("N/A");
            } else {
                String orgText = String.format("%s | Turnover: %d | Employees: %d",
                        worker.getOrganization().getFullName(),
                        worker.getOrganization().getAnnualTurnover(),
                        worker.getOrganization().getEmployeesCount() != null ? worker.getOrganization().getEmployeesCount() : 0
                );
                valueLabels.get("Organización").setText(orgText);
            }

            valueLabels.get("Fecha fin").setText(worker.getEndDateStr());
        } else {
            valueLabels.values().forEach(lbl -> lbl.setText("-"));
        }
    }

    // Setters para los consumidores de botones
    public void setOnDelete(Consumer<Worker> onDelete) {
        this.onDelete = onDelete;
    }

    public void setOnModify(Consumer<Worker> onModify) {
        this.onModify = onModify;
    }
}
