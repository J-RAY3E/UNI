package org.example.GUI;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.Node;

import org.example.Classes.Worker;
import org.example.Enums.MessageType;
import org.example.connection.NotificationManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javafx.animation.*;
import javafx.scene.shape.*;
import java.util.*;
import java.util.stream.Collectors;
import javafx.scene.layout.*;

public class CanvasPanel extends Pane {
    private final Map<Integer, Node> visualObjects = new HashMap<>();
    private final Map<Node, Worker> nodeWorkerMap = new HashMap<>();
    private Consumer<Worker> onWorkerSelected;
    private Color clientColor = Color.web("#4CAF50");
    private Color otherUsersColor = Color.web("#2196F3");

    // Dimensiones fijas y límites
    private static final double WIDTH = 600;
    private static final double HEIGHT = 400;
    private final Rectangle boundaryClip;

    public CanvasPanel() {
        // Configuración del tamaño fijo
        setPrefSize(WIDTH, HEIGHT);
        setMinSize(WIDTH, HEIGHT);
        setMaxSize(WIDTH, HEIGHT);

        // Clip para contener todo dentro de los límites
        boundaryClip = new Rectangle(WIDTH, HEIGHT);
        setClip(boundaryClip);

        setStyle("-fx-background-color: #1a2a2a; -fx-border-color: #2a3a3a; -fx-border-width: 2;");

        drawOfficeLayout();
    }

    private void drawOfficeLayout() {
        // Limpiar primero
        getChildren().clear();

        // Cuadrícula de fondo
        for (int x = 0; x < WIDTH; x += 40) {
            Line line = new Line(x, 0, x, HEIGHT);
            line.setStroke(Color.web("#2a3a3a"));
            getChildren().add(line);
        }
        for (int y = 0; y < HEIGHT; y += 40) {
            Line line = new Line(0, y, WIDTH, y);
            line.setStroke(Color.web("#2a3a3a"));
            getChildren().add(line);
        }

        // Áreas de trabajo (3 cubículos)
        for (int i = 0; i < 3; i++) {
            double x = 50 + i * 180;
            double y = 50;

            // Cubículo
            Rectangle cubicle = new Rectangle(x, y, 160, 120);
            cubicle.setFill(Color.web("#2C3E50", 0.7));
            cubicle.setStroke(Color.web("#3C4E60"));
            cubicle.setArcWidth(15);
            cubicle.setArcHeight(15);
            getChildren().add(cubicle);

            // Escritorio
            Rectangle desk = new Rectangle(x + 30, y + 60, 100, 10);
            desk.setFill(Color.web("#5D4037"));
            desk.setArcWidth(5);
            desk.setArcHeight(5);
            getChildren().add(desk);

            // Silla
            Circle chair = new Circle(x + 80, y + 90, 8);
            chair.setFill(Color.web("#795548"));
            getChildren().add(chair);
        }
    }

    public void addWorkerWithAnimation(Worker worker) {
        Point2D pos = normalizeCoordinates(worker.getCoordinates().getX(), worker.getCoordinates().getY());
        Node workerNode = createWorkerNode(worker);
        workerNode.setLayoutX(pos.getX());
        workerNode.setLayoutY(0); // Comienza en el borde superior

        // Animación controlada dentro de límites
        TranslateTransition fall = new TranslateTransition(Duration.millis(600), workerNode);
        fall.setToY(pos.getY());
        fall.setInterpolator(Interpolator.EASE_BOTH);

        RotateTransition rotate = new RotateTransition(Duration.millis(300), workerNode);
        rotate.setFromAngle(-15);
        rotate.setToAngle(15);
        rotate.setAutoReverse(true);
        rotate.setCycleCount(2);

        ParallelTransition anim = new ParallelTransition(fall, rotate);
        anim.setOnFinished(e -> {
            // Asegurar posición final exacta
            workerNode.setLayoutY(pos.getY());
            workerNode.setTranslateY(0);
        });

        getChildren().add(workerNode);
        visualObjects.put(worker.getId(), workerNode);
        nodeWorkerMap.put(workerNode, worker);

        anim.play();
    }

    private void removeWorkerWithAnimation(int id) {
        Node workerNode = visualObjects.get(id);
        if (workerNode == null) return;

        // Animación controlada dentro de límites
        TranslateTransition exit = new TranslateTransition(Duration.millis(800), workerNode);
        exit.setByX(WIDTH - workerNode.getLayoutX() + 50); // Sale por la derecha
        exit.setByY(HEIGHT/2 - workerNode.getLayoutY());

        FadeTransition fade = new FadeTransition(Duration.millis(500), workerNode);
        fade.setToValue(0);

        ParallelTransition anim = new ParallelTransition(exit, fade);
        anim.setOnFinished(e -> {
            getChildren().remove(workerNode);
            visualObjects.remove(id);
            nodeWorkerMap.remove(workerNode);
        });

        anim.play();
    }

    private void updateWorkerPosition(Worker worker) {
        Node workerNode = visualObjects.get(worker.getId());
        if (workerNode == null) return;

        Point2D newPos = normalizeCoordinates(worker.getCoordinates().getX(), worker.getCoordinates().getY());

        TranslateTransition move = new TranslateTransition(Duration.millis(400), workerNode);
        move.setToX(newPos.getX() - workerNode.getLayoutX());
        move.setToY(newPos.getY() - workerNode.getLayoutY());
        move.setOnFinished(e -> {
            // Asegurar posición final exacta
            workerNode.setLayoutX(newPos.getX());
            workerNode.setLayoutY(newPos.getY());
            workerNode.setTranslateX(0);
            workerNode.setTranslateY(0);
        });
        move.play();
    }

    private Point2D normalizeCoordinates(float x, float y) {
        // Garantiza que las coordenadas estén dentro de los límites visibles
        double normX = Math.max(20, Math.min(WIDTH - 20, (x / 100.0) * WIDTH));
        double normY = Math.max(20, Math.min(HEIGHT - 20, (y / 100.0) * HEIGHT));
        return new Point2D(normX, normY);
    }

    private Node createWorkerNode(Worker worker) {
        Circle head = new Circle(0, -15, 8);
        head.setFill(Color.web("#FFDBAC"));

        Path body = new Path();
        body.getElements().addAll(
                new MoveTo(-8, -5),
                new LineTo(0, -15),
                new LineTo(8, -5),
                new LineTo(5, 15),
                new LineTo(0, 20),
                new LineTo(-5, 15),
                new ClosePath()
        );
        body.setFill(getWorkerColor(worker));

        Pane workerNode = new Pane(body, head);
        workerNode.setUserData("worker");

        workerNode.setOnMouseClicked(e -> {
            if (onWorkerSelected != null) onWorkerSelected.accept(worker);
        });

        return workerNode;
    }


    private Color getWorkerColor(Worker worker) {
        return worker.getWhoModificates().equals(ViewController.getInstance().getHandler().getUserManager().getUsername())
                ? clientColor : otherUsersColor;
    }


    public void syncWorkers(List<Worker> workers) {
        if (workers == null) return;

        Set<Integer> currentIds = workers.stream()
                .map(Worker::getId)
                .collect(Collectors.toSet());

        // Eliminar workers que ya no están
        new ArrayList<>(visualObjects.keySet()).stream()
                .filter(id -> !currentIds.contains(id))
                .forEach(this::removeWorkerWithAnimation);

        // Añadir o actualizar
        workers.forEach(worker -> {
            if (visualObjects.containsKey(worker.getId())) {
                updateWorkerPosition(worker);
            } else {
                addWorkerWithAnimation(worker);
            }
        });
    }


    public void setOnWorkerSelected(Consumer<Worker> handler) {
        this.onWorkerSelected = handler;
    }

    public void clear() {
        visualObjects.clear();
        nodeWorkerMap.clear();

    }

    public void loadData(List<Worker> workers) {
        if (workers == null || workers.isEmpty()) {
            NotificationManager.getInstance().pushMessage("No workers to display", MessageType.INFO);
            return;
        }
        syncWorkers(workers);
    }
}