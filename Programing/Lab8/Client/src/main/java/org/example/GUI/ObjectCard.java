package org.example.GUI;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.Classes.Worker;

import java.util.List;
import java.util.function.Consumer;

public class ObjectCard extends StackPane {

    private final Worker object;
    private final List<ObjectCard> objectCardList;
    private Color baseColor;
    private final HBox row;

    public ObjectCard(Worker object, Consumer<Worker> onClick, List<ObjectCard> allCards) {
        this.object = object;
        this.objectCardList = allCards;

        setMaxWidth(Double.MAX_VALUE);
        setPrefHeight(40);  // Tamaño fijo menor para vista tabla
        setCursor(Cursor.HAND);

        // Contenedor horizontal para columnas
        row = new HBox();
        row.setSpacing(10);
        row.setPadding(new Insets(5, 15, 5, 15));
        row.setBackground(new Background(new BackgroundFill(Color.rgb(70, 70, 70), new CornerRadii(6), Insets.EMPTY)));

        Font font = Font.font("Segoe UI", 13);

        Label idLabel = createColumnLabel(String.valueOf(object.getId()), 40, font);
        Label nameLabel = createColumnLabel(object.getName(), 120, font);
        Label salaryLabel = createColumnLabel(String.format("$%d", object.getSalary()), 80, font);
        Label positionLabel = createColumnLabel(object.getPositionStr(), 100, font);
        Label statusLabel = createColumnLabel(object.getStatusStr(), 80, font);
        Label coordXLabel = createColumnLabel(String.valueOf(object.getCoordinates().getX()), 60, font);
        Label coordYLabel = createColumnLabel(String.valueOf(object.getCoordinates().getY()), 60, font);
        Label orgLabel = createColumnLabel(object.getOrganization().getFullName(), 120, font);
        Label creationDateLabel = createColumnLabel(object.getCreationDate().toString(), 110, font);
        Label endDateLabel = createColumnLabel(object.getEndDate() != null ? object.getEndDate().toString() : "-", 110, font);

        row.getChildren().addAll(idLabel, nameLabel, salaryLabel, positionLabel, statusLabel,
                coordXLabel, coordYLabel, orgLabel, creationDateLabel, endDateLabel);

        getChildren().add(row);

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            select();
            onClick.accept(object);
        });
    }

    private Label createColumnLabel(String text, double width, Font font) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setFont(font);
        label.setTextFill(Color.WHITE);
        label.setEllipsisString("...");
        label.setStyle("-fx-alignment: center-left;");
        return label;
    }

    private void select() {
        for (ObjectCard card : objectCardList) {
            card.setSelected(card == this);
        }
    }

    public void setSelected(boolean selected) {
        if (selected) {
            row.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 140), new CornerRadii(6), Insets.EMPTY)));
        } else {
            row.setBackground(new Background(new BackgroundFill(baseColor, new CornerRadii(6), Insets.EMPTY)));
        }
    }

    public void setColor(String currentUser) {
        baseColor = object.getWhoModificates().equals(currentUser) ? Color.rgb(83, 112, 135) : Color.rgb(70, 70, 70);
        row.setBackground(new Background(new BackgroundFill(baseColor, new CornerRadii(6), Insets.EMPTY)));
    }

    public Worker getObject() {
        return object;
    }

}
