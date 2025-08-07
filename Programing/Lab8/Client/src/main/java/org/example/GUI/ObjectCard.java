package org.example.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.Classes.Worker;
import org.example.GUI.I18.I18NManager;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class ObjectCard extends StackPane implements  RefreshableUI {

    private final Worker object;
    private final List<ObjectCard> objectCardList;
    private Color baseColor;
    private final GridPane grid;

    public ObjectCard(Worker object, Consumer<Worker> onClick, List<ObjectCard> allCards) {
        this.object = object;
        this.objectCardList = allCards;

        setMaxWidth(Double.MAX_VALUE);
        setPrefHeight(40);
        setCursor(Cursor.HAND);

        // Contenedor principal con scroll horizontal si es necesario
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(5); // Espacio mínimo entre columnas
        grid.setPadding(new Insets(0, 5, 0, 5));
        grid.setBackground(new Background(new BackgroundFill(Color.rgb(70, 70, 70), new CornerRadii(6), Insets.EMPTY)));

        Font font = Font.font("Segoe UI", 12);

        // Crear etiquetas
        Label idLabel = createCompactLabel(String.valueOf(object.getId()), 0);
        Label nameLabel = createCompactLabel(object.getName(), 1);
        Label coordXLabel = createCompactLabel(String.valueOf(object.getCoordinates().getX()), 2);
        Label coordYLabel = createCompactLabel(String.valueOf(object.getCoordinates().getY()), 3);
        Label salaryLabel = createCompactLabel("$" + object.getSalary(), 4);
        Label positionLabel = createCompactLabel(object.getPositionStr(), 5);
        Label statusLabel = createCompactLabel(object.getStatusStr(), 6);
        Label orgLabel = createCompactLabel(object.getOrganization() == null  ? "-": object.getOrganization().getFullName(), 7);
        Label creationDateLabel = createCompactLabel(formatDateTime(object.getCreationDate()), 8);
        Label endDateLabel = createCompactLabel(object.getEndDate() != null ? formatDate(object.getEndDate()) : "-", 9);

        // Agregar etiquetas al GridPane
        grid.add(idLabel, 0, 0);
        grid.add(nameLabel, 1, 0);
        grid.add(coordXLabel, 2, 0);
        grid.add(coordYLabel, 3, 0);
        grid.add(salaryLabel, 4, 0);
        grid.add(positionLabel, 5, 0);
        grid.add(statusLabel, 6, 0);
        grid.add(orgLabel, 7, 0);
        grid.add(creationDateLabel, 8, 0);
        grid.add(endDateLabel, 9, 0);

        getChildren().add(grid);

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            select();
            onClick.accept(object);
        });
    }

    private Label createCompactLabel(String text, int columnIndex) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", 12));
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-alignment: center-left; -fx-padding: 0 3 0 3;");
        label.setEllipsisString("...");

        GridPane.setColumnSpan(label, 1);
        GridPane.setHgrow(label, Priority.ALWAYS);

        return label;
    }


    private void select() {
        for (ObjectCard card : objectCardList) {
            card.setSelected(card == this);
        }
    }

    public void setSelected(boolean selected) {
        if (selected) {
            grid.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 140), new CornerRadii(6), Insets.EMPTY)));
        } else {
            grid.setBackground(new Background(new BackgroundFill(baseColor, new CornerRadii(6), Insets.EMPTY)));
        }
    }

    @Override
    public void refreshUI() {
        I18NManager i18n = I18NManager.getInstance();


        Label salaryLabel = (Label) grid.getChildren().get(4);
        salaryLabel.setText(i18n.getMessage("currency_symbol") + object.getSalary());


        Label creationDateLabel = (Label) grid.getChildren().get(8);
        creationDateLabel.setText(formatDateTime(object.getCreationDate()));

        Label endDateLabel = (Label) grid.getChildren().get(9);
        endDateLabel.setText(object.getEndDate() != null ?
                formatDate(object.getEndDate()) :
                i18n.getMessage("not_available"));
    }

    private String formatDateTime(LocalDateTime dateTime) {
        I18NManager i18n = I18NManager.getInstance();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    i18n.getMessage("datetime_format"),
                    i18n.getCurrentLocale()
            );
            return dateTime.format(formatter);
        } catch (Exception e) {
            return i18n.getMessage("datetime_invalid_format");
        }
    }

    private String formatDate(Date date) {
        I18NManager i18n = I18NManager.getInstance();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(
                    i18n.getMessage("date_format"),
                    i18n.getCurrentLocale()
            );
            return formatter.format(date);
        } catch (Exception e) {
            return i18n.getMessage("date_invalid_format");
        }
    }

    public void setColor(String currentUser) {
        baseColor = object.getWhoModificates().equals(currentUser) ? Color.rgb(39, 38, 32) : Color.rgb(70, 70, 70);
        grid.setBackground(new Background(new BackgroundFill(baseColor, new CornerRadii(6), Insets.EMPTY)));
    }

    public Worker getObject() {
        return object;
    }
}