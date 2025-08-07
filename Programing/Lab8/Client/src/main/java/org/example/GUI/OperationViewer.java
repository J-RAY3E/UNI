package org.example.GUI;

import org.example.Classes.Worker;
import org.example.Enums.FormMode;
import org.example.GUI.Views.DescriptionView;
import org.example.GUI.Views.EmptyView;
import org.example.GUI.Views.FormView;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;

import java.util.function.Consumer;

public class OperationViewer extends StackPane {

    private final DescriptionView detailsView = new DescriptionView();
    private FormView formView = new FormView(FormMode.ADD,null);
    private final EmptyView emptyView = new EmptyView();

    public static final String EMPTY = "EMPTY";
    public static final String DETAILS = "DETAILS";
    public static final String FORM = "FORM";

    public OperationViewer() {
        getChildren().addAll(emptyView, detailsView, formView);

        showEmpty();

        setStyle("-fx-background-color: #373737;");

        this.detailsView.setOnModify(worker -> {
            setFormView(new FormView(FormMode.MODIFY,worker));
            showForm();
        });
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

    public void setFormView(FormView newFormView) {
        if (this.formView != null) getChildren().remove(this.formView);
        this.formView = newFormView;
        getChildren().add(this.formView);
    }

    public void setOnDelete(Consumer<Worker> onDelete) {
        this.detailsView.setOnDelete(onDelete);
    }

    public interface ProjectionHandler {
        void onItemSelected(Worker worker);
        void onItemEdited(Worker worker);
        void onNewItem();
    }

    public FormView getFormView(){
        return  formView;
    }
}
