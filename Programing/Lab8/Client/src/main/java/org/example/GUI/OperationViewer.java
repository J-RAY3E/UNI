package org.example.GUI;

import org.example.Classes.Worker;
import org.example.GUI.Views.DescriptionView;
import org.example.GUI.Views.EmptyView;
import org.example.GUI.Views.FormView;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;

public class OperationViewer extends StackPane {

    private final DescriptionView detailsView = new DescriptionView();
    private final FormView formView = new FormView();
    private final EmptyView emptyView = new EmptyView();

    public static final String EMPTY = "EMPTY";
    public static final String DETAILS = "DETAILS";
    public static final String FORM = "FORM";

    public OperationViewer() {
        // Añadimos las vistas a este StackPane
        getChildren().addAll(emptyView, detailsView, formView);

        // Mostrar vacío inicialmente
        showEmpty();

        // Aplica el estilo de fondo gris oscuro
        setStyle("-fx-background-color: #373737;");
    }

    public void showEmpty() {
        emptyView.setVisible(true);
        detailsView.setVisible(false);
        formView.setVisible(false);
        emptyView.setManaged(true);
        detailsView.setManaged(false);
        formView.setManaged(false);
    }

    public void showDetails(Worker worker) {
        detailsView.setWorker(worker);
        emptyView.setVisible(false);
        detailsView.setVisible(true);
        formView.setVisible(false);

        emptyView.setManaged(false);
        detailsView.setManaged(true);
        formView.setManaged(false);
    }

    public void showForm() {
        emptyView.setVisible(false);
        detailsView.setVisible(false);
        formView.setVisible(true);

        emptyView.setManaged(false);
        detailsView.setManaged(false);
        formView.setManaged(true);
    }


    public void setOnSave(Runnable onSave) {

        formView.setOnSave(onSave);
    }

    public interface ProjectionHandler {
        void onItemSelected(Worker worker);
        void onItemEdited(Worker worker);
        void onNewItem();
    }
}
