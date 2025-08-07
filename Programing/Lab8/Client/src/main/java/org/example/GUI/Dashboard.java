package org.example.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import org.example.Enums.FormMode;
import org.example.GUI.I18.I18NManager;
import org.example.GUI.Views.FormView;

import java.io.IOException;
import java.util.Locale;
import java.util.function.Consumer;

public class Dashboard implements  RefreshableUI {

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
    @FXML private Button languageBtn;

    private final ViewController viewController;

    private ObjectListPanel objectListPanel;
    private OperationViewer operationViewer;
    private CanvasPanel canvasPanel;
    private Runnable onLogout = () -> {};



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
        I18NManager.getInstance().registerComponent(this);
        refreshUI();

        // Configura el botón de idioma
        languageBtn.setOnAction(e -> showLanguageMenu());
        objectListPanel = new ObjectListPanel();
        operationViewer = new OperationViewer();
        canvasPanel = new CanvasPanel();

        objectListPanel.setStyle("-fx-background-color: #323232;");
        operationViewer.setStyle("-fx-background-color: #373737;");
        canvasPanel.setStyle("-fx-background-color: #1a2a2a;");


        wrapper.getChildren().add(objectListPanel);

        canvasPanel.setOnWorkerSelected(operationViewer::showDetails);

        VBox viewerAndCanvas = new VBox();
        viewerAndCanvas.setPrefHeight(1000);


        operationViewer.setPrefHeight(400); // 50% de 600
        canvasPanel.setPrefHeight(600);     // 50% de 600


        VBox.setVgrow(operationViewer, Priority.ALWAYS);
        VBox.setVgrow(canvasPanel, Priority.ALWAYS);

        viewerAndCanvas.getChildren().addAll(operationViewer, canvasPanel);
        viewerAndCanvas.setSpacing(5);


        operationViewerContainer.getChildren().add(viewerAndCanvas);


        objectListPanel.setOnButtonAction(() -> {
            operationViewer.setFormView(new FormView(FormMode.ADD,null));
            operationViewer.showForm();
        });
        objectListPanel.setOnObjectSelected(operationViewer::showDetails);
        objectListPanel.initializeButtonActions(this.viewController::clearAll,
                this.viewController::minByPosition,
                this.viewController::filterByName,
                this.viewController::getData,
                this.viewController::deleteLast,
                () -> {
                    operationViewer.setFormView(new FormView(FormMode.DELETE_GREATER_THAN,null));
                    operationViewer.showForm();
                },
                () -> {
                    operationViewer.setFormView(new FormView(FormMode.ADD_IF_MAX,null));
                    operationViewer.showForm();
                });

        operationViewer.setOnDelete(this.viewController::removeById);

        addBtn.setOnAction(e -> {

        });

        logoutBtn.setOnAction(e -> onLogout.run());
    }


    private void showLanguageMenu() {
        ContextMenu languageMenu = new ContextMenu();

        MenuItem russianItem = new MenuItem("Русский");
        russianItem.setOnAction(e -> changeLanguage(new Locale("ru", "RU")));

        MenuItem norwegianItem = new MenuItem("Norsk");
        norwegianItem.setOnAction(e -> changeLanguage(new Locale("no", "NO")));

        MenuItem albanianItem = new MenuItem("Shqip");
        albanianItem.setOnAction(e -> changeLanguage(new Locale("sq", "AL")));

        MenuItem englishItem = new MenuItem("English (NZ)");
        englishItem.setOnAction(e -> changeLanguage(new Locale("en", "NZ")));

        languageMenu.getItems().addAll(russianItem, norwegianItem, albanianItem, englishItem);
        languageMenu.show(languageBtn, Side.BOTTOM, 0, 0);
    }

    private void changeLanguage(Locale locale) {
        I18NManager.getInstance().setLocale(locale);
    }

    @Override
    public void refreshUI() {
        I18NManager i18n = I18NManager.getInstance();
        titleLabel.setText(i18n.getMessage("dashboard.title"));
        logoutBtn.setText(i18n.getMessage("logout.button"));
        languageBtn.setText(i18n.getMessage("language.button"));

        // Actualiza los paneles que implementan RefreshableUI
        if (objectListPanel != null) objectListPanel.refreshUI();
        if(operationViewer != null){
            operationViewer.getFormView().refreshUI();
        }

    }
    public void setOnLogout(Runnable logoutAction) {
        if (logoutAction != null) {
            this.onLogout = logoutAction;
        }
    }

    public BorderPane getRoot() {
        return rootPane;
    }

    public ObjectListPanel getObjectListPanel() {
        return objectListPanel;
    }
    public CanvasPanel getCanvasPanel() {
        return canvasPanel;
    }
}
