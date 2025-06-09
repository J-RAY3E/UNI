package org.example.GUI;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.Classes.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ObjectListPanel extends BorderPane {

    private List<Worker> allObjects = new ArrayList<>();
    private final List<ObjectCard> cardsList = new ArrayList<>();
    private final Consumer<Worker> onObjectSelected = worker -> {};
    private Consumer<Worker> onObjectSelectedListener = onObjectSelected;

    private final VBox listPanel = new VBox(5);

    private final Button addButton = new Button("+");
    private final Button filterButton = new Button("🔍");
    private final TextField filterField = new TextField();

    public ObjectListPanel() {
        // Fondo base
        setStyle("-fx-background-color: #2D2D2D;");
        setPadding(new Insets(10));

        // Filtro: textfield estilo
        filterField.setPromptText("Filter...");
        filterField.setFont(Font.font("Segoe UI", 14));
        filterField.setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: white;");
        filterField.setMaxHeight(36);
        filterField.setFocusTraversable(false);

        // Listener para filtrar en cada cambio de texto
        filterField.textProperty().addListener((obs, oldVal, newVal) -> updateFilter(newVal));

        // Botones estilo
        stylizeButton(addButton);
        stylizeButton(filterButton);

        // Barra superior horizontal con botones y filtro
        HBox navConf = new HBox(5, addButton, filterButton);
        navConf.setPadding(new Insets(5, 0, 10, 0));
        navConf.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topBar = new HBox(navConf, spacer, filterField);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 0, 10, 0));

        // ListPanel: VBox con spacing y fondo transparente
        listPanel.setPadding(new Insets(0));
        listPanel.setStyle("-fx-background-color: transparent;");

        // ScrollPane para la lista
        ScrollPane scrollPane = new ScrollPane(listPanel);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Agregamos al BorderPane
        setTop(topBar);
        setCenter(scrollPane);

        // Accion para addButton: definida afuera con setter
    }

    private void stylizeButton(Button button) {
        button.setStyle(
                "-fx-background-color: #505050;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;" +
                        "-fx-border-color: #646464;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-padding: 5 14 5 14;"
        );
        button.setCursor(Cursor.HAND);
    }

    private void updateFilter(String text) {
        String lowerText = text.toLowerCase().trim();
        for (var node : listPanel.getChildren()) {
            if (node instanceof ObjectCard card) {
                String whoMod = card.getObject().getWhoModificates().toLowerCase();
                boolean visible = lowerText.isEmpty() || whoMod.contains(lowerText);
                card.setVisible(visible);
                card.setManaged(visible);
            }
        }
    }

    public void setObjects(List<Worker> objects,String currentUser) {
        this.allObjects = new ArrayList<>(objects);
        cardsList.clear();
        listPanel.getChildren().clear();

        for (Worker obj : objects) {
            ObjectCard card = new ObjectCard(obj, onObjectSelectedListener, cardsList);
            card.setColor(currentUser);
            cardsList.add(card);
            listPanel.getChildren().add(card);
        }
    }

    public void setOnObjectSelected(Consumer<Worker> listener) {
        this.onObjectSelectedListener = listener;
    }

    public void setOnButtonAction(Runnable callback) {
        addButton.setOnAction(e -> {
            if (callback != null) callback.run();
        });
    }

}
