package org.example.GUI.Views;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.GUI.I18.I18NManager;
import org.example.GUI.RefreshableUI;

public class EmptyView extends VBox implements RefreshableUI {

    private final Label welcomeLabel;
    private final Label descriptionLabel;

    public EmptyView() {
        setSpacing(15);
        setAlignment(Pos.CENTER);

        welcomeLabel = new Label();
        welcomeLabel.setTextFill(Color.web("#1877F2"));
        welcomeLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));

        descriptionLabel = new Label();
        descriptionLabel.setFont(Font.font("Segoe UI", 16));
        descriptionLabel.setTextFill(Color.DARKGRAY);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setAlignment(Pos.CENTER);

        getChildren().addAll(welcomeLabel, descriptionLabel);

        I18NManager.getInstance().registerComponent(this);
        refreshUI();
    }

    @Override
    public void refreshUI() {
        welcomeLabel.setText(I18NManager.getInstance().getMessage("emptyview.welcome"));
        descriptionLabel.setText(I18NManager.getInstance().getMessage("emptyview.description"));
    }
}