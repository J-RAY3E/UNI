package org.example.GUI.Views;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EmptyView extends VBox {

    public EmptyView() {
        setSpacing(15);
        setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome to Data!");
        welcomeLabel.setTextFill(Color.web("#1877F2")); // Azul Facebook (#1877F2)
        welcomeLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));

        Label descriptionLabel = new Label("Your personal data service at your fingertips.\nSelect a user to begin.");
        descriptionLabel.setFont(Font.font("Segoe UI", 16));
        descriptionLabel.setTextFill(Color.DARKGRAY);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setAlignment(Pos.CENTER);

        getChildren().addAll(welcomeLabel, descriptionLabel);
    }
}
