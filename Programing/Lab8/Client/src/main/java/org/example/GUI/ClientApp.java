package org.example.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.GUI.Views.LoginView;

public class ClientApp extends Application {

    private static ViewController staticController;

    private ViewController viewController;

    private BorderPane rootPane;
    private StackPane container;

    private LoginView loginView;
    private Pane mainPanel;

    public ClientApp() {
        // Constructor vacío para JavaFX Application
    }

    public static void setController(ViewController controller) {
        staticController = controller;
    }

    @Override
    public void start(Stage primaryStage) {
        // Inicializar el viewController con la referencia estática
        this.viewController = staticController;

        rootPane = new BorderPane();
        container = new StackPane();

        loginView = createLoginView();
        mainPanel = createMainPanel();

        container.getChildren().addAll(loginViewRoot(), mainPanel);
        showLogin();

        rootPane.setCenter(container);

        Scene scene = new Scene(rootPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Client");
        primaryStage.show();
    }

    private Pane loginViewRoot() {
        return loginView.getRoot();
    }

    private LoginView createLoginView() {
        return new LoginView(credentials -> {
            boolean authorized = viewController.authorize(credentials[0], credentials[1]);
            if (authorized) {
                showMain();
                viewController.getData();
            } else {
                showAlert("Error", "The username or password is incorrect. Try again.");
            }
        }, credentials -> {
            viewController.register(credentials[0], credentials[1]);
            showAlert("Info", "Registration successful, please try to sign in.");
        });
    }

    private Pane createMainPanel() {
        BorderPane mainPane = new BorderPane();

        Label welcomeLabel = new Label("Welcome to Dashboard");
        welcomeLabel.setStyle("-fx-font-size: 18px;");
        welcomeLabel.setMaxWidth(Double.MAX_VALUE);
        welcomeLabel.setAlignment(javafx.geometry.Pos.CENTER);

        Button signOutButton = new Button("Sign out");
        signOutButton.setOnAction(e -> showLogin());

        Dashboard dashboard = new Dashboard(viewController);
        viewController.setDashboard(dashboard);

        mainPane.setTop(welcomeLabel);
        mainPane.setCenter(dashboard.getRoot());
        mainPane.setBottom(signOutButton);
        BorderPane.setAlignment(signOutButton, javafx.geometry.Pos.CENTER);

        return mainPane;
    }

    private void showLogin() {
        loginViewRoot().setVisible(true);
        mainPanel.setVisible(false);
    }

    private void showMain() {
        loginViewRoot().setVisible(false);
        mainPanel.setVisible(true);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
