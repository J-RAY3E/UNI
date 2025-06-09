package org.example.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Dashboard {

    @FXML
    private BorderPane rootPane;

    @FXML
    private VBox wrapper;

    @FXML
    private VBox operationViewerContainer;

    @FXML
    private Button addBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Label titleLabel;

    private final ViewController viewController;

    private ObjectListPanel objectListPanel;
    private OperationViewer operationViewer;

    public Dashboard(ViewController viewController) {
        this.viewController = viewController;
        loadFXML();
        initUI();
    }

    private void loadFXML() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Dashboard.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load Dashboard.fxml");
        }
    }

    private void initUI() {
        // Crear instancias de ObjectListPanel y OperationViewer (que deben ser nodos JavaFX)
        objectListPanel = new ObjectListPanel();
        operationViewer = new OperationViewer();

        // Estilos para los paneles (si no están en CSS)
        objectListPanel.setStyle("-fx-background-color: #323232;");
        operationViewer.setStyle("-fx-background-color: #373737;");

        // Insertar en el layout
        wrapper.getChildren().add(objectListPanel);
        operationViewerContainer.getChildren().add(operationViewer);

        // Configurar callbacks
        objectListPanel.setOnButtonAction(operationViewer::showForm);
        objectListPanel.setOnObjectSelected(operationViewer::showDetails);


        // Botones
        addBtn.setOnAction(e -> {
            // Tu lógica para añadir
        });

        logoutBtn.setOnAction(e -> {
            // Tu lógica para logout, quizás:

        });
    }

    public BorderPane getRoot() {
        return rootPane;
    }

    public ObjectListPanel getObjectListPanel() {
        return objectListPanel;
    }
}
