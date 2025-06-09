package org.example.GUI.Views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.function.Consumer;

public class LoginView {
    @FXML private VBox card;  // Este será el nodo raíz del FXML
    @FXML private Label title;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button actionButton;
    @FXML private Label switchLabel;
    @FXML private Button switchButton;

    private Pane root;
    private boolean isLoginMode = true;

    private Consumer<String[]> onLogin;
    private Consumer<String[]> onRegister;

    public LoginView(Consumer<String[]> onLogin, Consumer<String[]> onRegister) {
        this.onLogin = onLogin;
        this.onRegister = onRegister;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/LoginView.fxml"));
            loader.setController(this);
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LoginView() {}

    public Pane getRoot() {
        return card;
    }

    @FXML
    private void handleAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isLoginMode) {
            System.out.println("Login: " + username + " / " + password);
            if (onLogin != null) onLogin.accept(new String[]{username, password});
        } else {
            System.out.println("Register: " + username + " / " + password);
            if (onRegister != null) onRegister.accept(new String[]{username, password});
        }
    }

    @FXML
    private void toggleMode() {
        isLoginMode = !isLoginMode;
        actionButton.setText(isLoginMode ? "Login" : "Register");
        switchLabel.setText(isLoginMode ? "Don't have an account?" : "Already have an account?");
        switchButton.setText(isLoginMode ? "Register" : "Login");
    }
}
