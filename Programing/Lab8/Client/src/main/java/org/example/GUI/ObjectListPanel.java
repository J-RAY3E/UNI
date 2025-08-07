package org.example.GUI;




import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.example.Classes.Worker;
import org.example.GUI.I18.I18NManager;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ObjectListPanel extends BorderPane implements RefreshableUI {

    private List<Worker> allObjects = new ArrayList<>();
    private final List<ObjectCard> cardsList = new ArrayList<>();
    private final Consumer<Worker> onObjectSelected = worker -> {};
    private Consumer<Worker> onObjectSelectedListener = onObjectSelected;
    private String currentUser;

    private final VBox listPanel = new VBox(5);

    // Botones existentes
    private final Button addButton = new Button("+");
    private final Button filterButton = new Button("🔍");
    private final Button addIfMaxButton = new Button("+ Max");
    private final Button clearButton = new Button("Clear");
    private final Button countByEndDateButton = new Button("Count Date");
    private final Button minByPositionButton = new Button("Min");
    private final Button removeLastButton = new Button("Delete Last");
    private final Button removeGreaterButton = new Button("Delete Greater");
    private final TextField StarWithNamefilterField = new TextField();

    // Nuevos componentes para filtrado
    private final ComboBox<String> filterColumnCombo = new ComboBox<>();
    private final TextField filterValueField = new TextField();
    private final Button applyFilterButton = new Button("Filtrar");
    private final Button resetFilterButton = new Button("Limpiar");


    // Botones de ordenamiento
    private final Button sortIdButton = new Button("ID");
    private final Button sortNameButton = new Button("Name");
    private final Button sortXButton = new Button("X");
    private final Button sortYButton = new Button("Y");
    private final Button sortSalaryButton = new Button("Salary");
    private final Button sortPositionButton = new Button("Position");
    private final Button sortStatusButton = new Button("Status");
    private final Button sortOrgButton = new Button("Organization");
    private final Button sortCreatedButton = new Button("Created");
    private final Button sortEndDateButton = new Button("End Date");
    private final Button loadTextFileButton = new Button("Cargar TXT");

    private final List<ToggleActionButton> toggleButtons = new ArrayList<>();

    public enum SortDirection {
        ASCENDING, DESCENDING
    }

    public ObjectListPanel() {
        setStyle("-fx-background-color: #2D2D2D;");
        setPadding(new Insets(10));

        // Configuración existente del campo de filtro
        StarWithNamefilterField.setPromptText("Filter...");
        StarWithNamefilterField.setFont(Font.font("Segoe UI", 14));
        StarWithNamefilterField.setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: white;");
        StarWithNamefilterField.setMaxHeight(36);
        StarWithNamefilterField.setFocusTraversable(false);
        StarWithNamefilterField.textProperty().addListener((obs, oldVal, newVal) -> updateFilter(newVal));



        // Configurar botones de ordenamiento (se añadirán al final)
        configureSortButton(sortIdButton, "id");
        configureSortButton(sortNameButton, "name");
        configureSortButton(sortXButton, "x");
        configureSortButton(sortYButton, "y");
        configureSortButton(sortSalaryButton, "salary");
        configureSortButton(sortPositionButton, "position");
        configureSortButton(sortStatusButton, "status");
        configureSortButton(sortOrgButton, "org");
        configureSortButton(sortCreatedButton, "created");
        configureSortButton(sortEndDateButton, "enddate");

        // Configuración de botones existentes
        List<Button> buttons = Arrays.asList(addButton, filterButton, addIfMaxButton,
                clearButton, countByEndDateButton, minByPositionButton, removeLastButton, removeGreaterButton, loadTextFileButton);
        buttons.forEach(this::stylizeButton);

        loadTextFileButton.setOnAction(e -> {
            String filePath = showTextFileChooser(this.getScene().getWindow());
            if (filePath != null) {
                System.out.println("Archivo .txt seleccionado: " + filePath);
                ViewController.getInstance().setScript(filePath);
            }
        });

        // 1. Barra de navegación principal (botones de funcionalidad)
        HBox navConf = new HBox(5, clearButton, addButton, addIfMaxButton,
                minByPositionButton, removeLastButton, removeGreaterButton, countByEndDateButton, loadTextFileButton);
        navConf.setPadding(new Insets(5, 0, 5, 0));
        navConf.setAlignment(Pos.CENTER_LEFT);

        // 2. Controles de filtrado (nuevos)
        HBox filterControls = createFilterControls();
        filterControls.setPadding(new Insets(5, 0, 5, 0));

        // 3. Barra de botones de ordenamiento
        HBox sortButtons = new HBox(3);
        sortButtons.getChildren().addAll(
                sortIdButton, sortNameButton, sortXButton, sortYButton,
                sortSalaryButton, sortPositionButton, sortStatusButton,
                sortOrgButton, sortCreatedButton, sortEndDateButton
        );
        sortButtons.setAlignment(Pos.CENTER_LEFT);
        sortButtons.setPadding(new Insets(5, 0, 5, 0));

        // Crear el contenedor principal vertical
        VBox mainTopContainer = new VBox();
        mainTopContainer.setSpacing(5);
        mainTopContainer.setPadding(new Insets(0, 0, 10, 0));

        // Añadir los componentes en el orden deseado
        mainTopContainer.getChildren().addAll(
                navConf,        // 1. Botones de funcionalidad
                filterControls, // 2. Controles de filtrado
                sortButtons     // 3. Botones de ordenamiento
        );

        // Configuración del área de lista
        listPanel.setPadding(new Insets(0));
        listPanel.setStyle("-fx-background-color: transparent;");

        ScrollPane scrollPane = new ScrollPane(listPanel);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        I18NManager.getInstance().registerComponent(this);
        refreshUI();
        filterColumnCombo.setStyle(
                "-fx-background-color: #3C3C3C; " +
                        "-fx-text-fill: white; " +  // Color del texto seleccionado
                        "-fx-prompt-text-fill: #AAAAAA; " +  // Color del texto de sugerencia (más suave)
                        "-fx-font-family: 'Segoe UI'; " +
                        "-fx-font-size: 14px;"
        );


        setTop(mainTopContainer);
        setCenter(scrollPane);
    }

    private void configureSortButton(Button button, String columnName) {
        button.setUserData(SortDirection.ASCENDING);
        stylizeButton(button);

        button.setOnAction(e -> {
            SortDirection currentDirection = (SortDirection) button.getUserData();
            sortByColumn(columnName, currentDirection);

            // Resetear todos los botones
            resetSortButtons();

            // Configurar el botón actual
            SortDirection newDirection = currentDirection == SortDirection.ASCENDING ?
                    SortDirection.DESCENDING : SortDirection.ASCENDING;
            button.setUserData(newDirection);

            // Actualizar texto del botón
            String arrow = newDirection == SortDirection.ASCENDING ? " ↑" : " ↓";
            button.setText(button.getText().replaceAll(" ↑| ↓", "") + arrow);
        });
    }

    private void resetSortButtons() {
        for (Button btn : Arrays.asList(sortIdButton, sortNameButton, sortXButton, sortYButton,
                sortSalaryButton, sortPositionButton, sortStatusButton,
                sortOrgButton, sortCreatedButton, sortEndDateButton)) {
            btn.setText(btn.getText().replaceAll(" ↑| ↓", ""));
            btn.setUserData(SortDirection.ASCENDING);
        }
    }

    public void sortByColumn(String columnName, SortDirection direction) {
        Comparator<Worker> comparator = getComparatorForColumn(columnName);

        if (direction == SortDirection.DESCENDING) {
            comparator = comparator.reversed();
        }

        List<Worker> sortedWorkers = allObjects.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        setObjects(sortedWorkers, currentUser);
    }

    private Comparator<Worker> getComparatorForColumn(String columnName) {
        switch (columnName.toLowerCase()) {
            case "id":
                return Comparator.comparingLong(Worker::getId);
            case "name":
                return Comparator.comparing(Worker::getName);
            case "x":
                return Comparator.comparingDouble(w -> w.getCoordinates().getX());
            case "y":
                return Comparator.comparingDouble(w -> w.getCoordinates().getY());
            case "salary":
                return Comparator.comparingInt(Worker::getSalary);
            case "position":
                return Comparator.comparing(Worker::getPositionStr);
            case "status":
                return Comparator.comparing(Worker::getStatusStr);
            case "org":
                return  Comparator.comparing(
                        w -> w.getOrganization() == null ?
                                "-" : w.getOrganization().getFullName(),
                        Comparator.nullsFirst(Comparator.naturalOrder())
                );
            case "created":
                return Comparator.comparing(Worker::getCreationDate);

            case "enddate":
                return Comparator.comparing(
                        Worker::getEndDate,
                        Comparator.nullsFirst(
                                Comparator.naturalOrder()
                        )
                );
            default:
                return Comparator.comparingLong(Worker::getId);
        }
    }

    private void stylizeButton(Button button) {
        button.setStyle(
                "-fx-background-color: #505050;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12;" +
                        "-fx-border-color: #646464;" +
                        "-fx-border-radius: 3;" +
                        "-fx-background-radius: 3;" +
                        "-fx-padding: 3 8 3 8;"
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

    public void setObjects(List<Worker> objects, String currentUser) {
        this.allObjects = new ArrayList<>(objects);
        this.currentUser = currentUser;
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

    public void initializeButtonActions(
            Runnable onClear,
            Runnable onMinByPosition,
            Consumer<String> onFilterByName,
            Runnable onRestoreAll,
            Runnable onDeleteLast,
            Runnable onDeleteGreaterThan,
            Runnable onAddIfMax
    ) {
        clearButton.setOnAction(e -> {
            if (onClear != null) {
                onClear.run();
                allObjects.clear();
            }
        });

        toggleButtons.add(new ToggleActionButton(
                filterButton,
                "🔍",
                () -> {
                    onFilterByName.accept("string");
                },
                onRestoreAll
        ));

        toggleButtons.add(new ToggleActionButton(
                minByPositionButton,
                "Min",
                onMinByPosition,
                onRestoreAll
        ));

        toggleButtons.add(new ToggleActionButton(
                removeLastButton,
                "Delete Last",
                onDeleteLast,
                onRestoreAll
        ));

        toggleButtons.add(new ToggleActionButton(
                addIfMaxButton,
                "+ Max",
                onAddIfMax,
                onRestoreAll
        ));

        toggleButtons.add(new ToggleActionButton(
                removeGreaterButton,
                "Delete Greater",
                onDeleteGreaterThan,
                onRestoreAll
        ));
    }

    public String getStarWithNameText() {
        return StarWithNamefilterField.getText();
    }

    private static class ToggleActionButton {
        private final Button button;
        private final String normalText;
        private final Runnable onActivate;
        private final Runnable onDeactivate;
        private boolean active = false;

        public ToggleActionButton(Button button, String normalText, Runnable onActivate, Runnable onDeactivate) {
            this.button = button;
            this.normalText = normalText;
            this.onActivate = onActivate;
            this.onDeactivate = onDeactivate;

            setup();
        }

        private void setup() {
            button.setText(normalText);
            button.setOnAction(e -> {
                if (active) {
                    deactivate();
                } else {
                    activate();
                }
            });
        }

        private void activate() {
            active = true;
            button.setText("❌");
            onActivate.run();
        }

        private void deactivate() {
            active = false;
            button.setText(normalText);
            onDeactivate.run();
        }
    }

    @Override
    public void refreshUI() {

        addButton.setText(I18NManager.getInstance().getMessage("add"));
        filterButton.setText(I18NManager.getInstance().getMessage("filter"));
        addIfMaxButton.setText(I18NManager.getInstance().getMessage("add_max"));
        clearButton.setText(I18NManager.getInstance().getMessage("clear"));
        countByEndDateButton.setText(I18NManager.getInstance().getMessage("count_date"));
        minByPositionButton.setText(I18NManager.getInstance().getMessage("min"));
        removeLastButton.setText(I18NManager.getInstance().getMessage("delete_last"));
        removeGreaterButton.setText(I18NManager.getInstance().getMessage("delete_greater"));
        StarWithNamefilterField.setPromptText(I18NManager.getInstance().getMessage("filter_prompt"));
        loadTextFileButton.setText(I18NManager.getInstance().getMessage("script"));


        sortIdButton.setText(I18NManager.getInstance().getMessage("sort_id"));
        sortNameButton.setText(I18NManager.getInstance().getMessage("sort_name"));
        sortXButton.setText(I18NManager.getInstance().getMessage("sort_x"));
        sortYButton.setText(I18NManager.getInstance().getMessage("sort_y"));
        sortSalaryButton.setText(I18NManager.getInstance().getMessage("sort_salary"));
        sortPositionButton.setText(I18NManager.getInstance().getMessage("sort_position"));
        sortStatusButton.setText(I18NManager.getInstance().getMessage("sort_status"));
        sortOrgButton.setText(I18NManager.getInstance().getMessage("sort_org"));
        sortCreatedButton.setText(I18NManager.getInstance().getMessage("sort_created"));
        sortEndDateButton.setText(I18NManager.getInstance().getMessage("sort_end_date"));

        filterColumnCombo.setPromptText(I18NManager.getInstance().getMessage("select_column"));
        filterValueField.setPromptText(I18NManager.getInstance().getMessage("search_value"));
        applyFilterButton.setText(I18NManager.getInstance().getMessage("filter"));
        resetFilterButton.setText(I18NManager.getInstance().getMessage("reset"));

        // Actualizar items del ComboBox con traducciones
        filterColumnCombo.getItems().setAll(
                I18NManager.getInstance().getMessage("id"),         // Índice 0
                I18NManager.getInstance().getMessage("name"),       // Índice 1
                I18NManager.getInstance().getMessage("position"),   // Índice 2
                I18NManager.getInstance().getMessage("salary"),     // Índice 3
                I18NManager.getInstance().getMessage("status"),     // Índice 4
                I18NManager.getInstance().getMessage("organization"), // Índice 5
                I18NManager.getInstance().getMessage("created_date"), // Índice 6
                I18NManager.getInstance().getMessage("end_date")    // Índice 7
        );
    }

    public String showTextFileChooser(Window ownerWindow) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo de texto");

        // Filtro para solo permitir archivos .txt
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Archivos de texto (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setSelectedExtensionFilter(extFilter);

        // Configuración adicional recomendada
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File selectedFile = fileChooser.showOpenDialog(ownerWindow);
        return selectedFile != null ? selectedFile.getAbsolutePath() : null;
    }

    private HBox createFilterControls() {
        // Configurar ComboBox
        filterColumnCombo.setPromptText(I18NManager.getInstance().getMessage("select_column"));
        filterColumnCombo.setPrefWidth(150);

        // Configurar campo de texto
        filterValueField.setPromptText(I18NManager.getInstance().getMessage("search_value"));
        filterValueField.setPrefWidth(200);

        // Configurar botones
        applyFilterButton.setText(I18NManager.getInstance().getMessage("filter"));
        resetFilterButton.setText(I18NManager.getInstance().getMessage("reset"));
        stylizeButton(applyFilterButton);
        stylizeButton(resetFilterButton);

        // Estilos (manteniendo tu esquema de colores)
        filterColumnCombo.setStyle(
                "-fx-background-color: #3C3C3C; " +
                        "-fx-text-fill: white; " +  // Color del texto seleccionado
                        "-fx-prompt-text-fill: #AAAAAA; " +  // Color del texto de sugerencia (más suave)
                        "-fx-font-family: 'Segoe UI'; " +
                        "-fx-font-size: 14px;"
        );
        filterValueField.setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: white;");
        applyFilterButton.setOnAction(e -> applyColumnFilter());
        resetFilterButton.setOnAction(e -> resetColumnFilter());

        // Crear y devolver el panel de filtrado
        HBox filterBox = new HBox(5, filterColumnCombo, filterValueField, applyFilterButton, resetFilterButton);
        filterBox.setAlignment(Pos.CENTER_LEFT);

        return filterBox;
    }
    private void applyColumnFilter() {
        // Obtener el índice seleccionado en el ComboBox
        int selectedIndex = filterColumnCombo.getSelectionModel().getSelectedIndex();
        String value = filterValueField.getText().trim().toLowerCase();

        if (selectedIndex < 0 || value.isEmpty()) return;


        Predicate<Worker> filter = switch (selectedIndex) {
            case 0 -> w -> String.valueOf(w.getId()).contains(value);          // ID
            case 1 -> w -> w.getName().toLowerCase().contains(value);         // Nombre
            case 2 -> w -> w.getPositionStr().toLowerCase().contains(value);  // Posición
            case 3 -> w -> String.valueOf(w.getSalary()).contains(value);     // Salario
            case 4 -> w -> w.getStatusStr().toLowerCase().contains(value);    // Estado
            case 5 -> w -> w.getOrganization().getFullName().toLowerCase().contains(value); // Organización
            case 6 -> w -> w.getCreationDate().toString().contains(value);    // Fecha Creación
            case 7 -> w -> w.getEndDate() != null && w.getEndDate().toString().contains(value); // Fecha Fin
            default -> w -> true;
        };

        // Aplicar el filtro
        listPanel.getChildren().forEach(node -> {
            if (node instanceof ObjectCard card) {
                boolean matches = filter.test(card.getObject());
                card.setVisible(matches);
                card.setManaged(matches);
            }
        });
    }
    private void resetColumnFilter() {
        filterColumnCombo.getSelectionModel().clearSelection();
        filterValueField.clear();
        listPanel.getChildren().forEach(node -> {
            node.setVisible(true);
            node.setManaged(true);
        });
    }
}
