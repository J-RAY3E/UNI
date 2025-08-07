package org.example.GUI.Views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.GUI.I18.I18NManager;
import org.example.GUI.RefreshableUI;

import java.io.IOException;
import java.net.URL;
import java.sql.Ref;
import java.util.Locale;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.function.Consumer;

public class LoginView implements RefreshableUI {
    @FXML private AnchorPane rootPane;
    @FXML private VBox card;
    @FXML private Button languageBtn;
    @FXML private Label title;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button actionButton;
    @FXML private Label switchLabel;
    @FXML private Button switchButton;

    private boolean isLoginMode = true;
    private final Consumer<String[]> onLogin;
    private final Consumer<String[]> onRegister;

    public LoginView(Consumer<String[]> onLogin, Consumer<String[]> onRegister) {
        this.onLogin = onLogin;
        this.onRegister = onRegister;

        try {
            // Verificar que el recurso existe
            URL fxmlUrl = getClass().getResource("/GUI/LoginView.fxml");
            if (fxmlUrl == null) {
                throw new RuntimeException("No se encontró el archivo FXML: /GUI/LoginView.fxml");
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            loader.setController(this);
            rootPane = loader.load();

            // Verificar inyección de componentes
            if (title == null) throw new RuntimeException("title no inyectado");
            if (usernameField == null) throw new RuntimeException("usernameField no inyectado");
            if (passwordField == null) throw new RuntimeException("passwordField no inyectado");
            if (actionButton == null) throw new RuntimeException("actionButton no inyectado");
            if (switchLabel == null) throw new RuntimeException("switchLabel no inyectado");
            if (switchButton == null) throw new RuntimeException("switchButton no inyectado");
            if (languageBtn == null) throw new RuntimeException("languageBtn no inyectado");

            // Configurar eventos
            actionButton.setOnAction(e -> handleAction());
            switchButton.setOnAction(e -> toggleMode());
            languageBtn.setOnAction(e -> showLanguageMenu());

            // Registrar para internacionalización
            I18NManager.getInstance().registerComponent(this);
            refreshUI();

        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading LoginView: " + e.getMessage());
        }
    }

    public Pane getRoot() {
        return rootPane;
    }

    private void handleAction() {
        String[] credentials = new String[]{
                usernameField.getText(),
                passwordField.getText()
        };

        if (isLoginMode) {
            if (onLogin != null) onLogin.accept(credentials);
        } else {
            if (onRegister != null) onRegister.accept(credentials);
        }
    }

    private void toggleMode() {
        isLoginMode = !isLoginMode;
        refreshUI();
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
        // Asegurar que los componentes están inicializados
        if (title == null) return;

        title.setText(I18NManager.getInstance().getMessage("app_title"));
        usernameField.setPromptText(I18NManager.getInstance().getMessage("username"));
        passwordField.setPromptText(I18NManager.getInstance().getMessage("password"));
        languageBtn.setText(I18NManager.getInstance().getMessage("language"));

        if (isLoginMode) {
            actionButton.setText(I18NManager.getInstance().getMessage("login"));
            switchLabel.setText(I18NManager.getInstance().getMessage("dont_have_account"));
            switchButton.setText(I18NManager.getInstance().getMessage("register"));
        } else {
            actionButton.setText(I18NManager.getInstance().getMessage("register"));
            switchLabel.setText(I18NManager.getInstance().getMessage("already_have_account"));
            switchButton.setText(I18NManager.getInstance().getMessage("login"));
        }
    }
}