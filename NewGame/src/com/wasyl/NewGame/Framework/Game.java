package com.wasyl.NewGame.Framework;

import com.wasyl.NewGame.Blocks.BlocksId;
import com.wasyl.NewGame.Blocks.PlayerBlock;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class Game extends Application {

    //związane z okienkiem
    private final static int screenWidth = 1600;
    private final static int screenHeight = 900;
    private Canvas canvas;
    private GraphicsContext gc;

    //związane z obiektami w grze
    private ArrayList<String> input;
    private Handler handler;
    private int playerLastAction;
    private PlayerBlock player;

    //start() == launch()
    @Override
    public void start(Stage stage) {
        //stworzenie "frame'a", grupy oraz odpalenie gry
        stage.setTitle("Gra");
        Group root = new Group();
        Scene scene = new Scene(root, screenWidth, screenHeight, true, SceneAntialiasing.BALANCED);
        stage.setScene(scene);

        //zainicjalizowanie obiektów
        initializeImportantObjcects();

        //obsługa zdarzeń z klawiatury
        scene.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();
                    if (!input.contains(code)) input.add(code);
                    if (code.equals("UP")) setPlayerLastAction(1);
                    else if (code.equals("RIGHT")) setPlayerLastAction(2);
                    else if (code.equals("DOWN")) setPlayerLastAction(3);
                    else if (code.equals("LEFT")) setPlayerLastAction(4);
                    if (code.equals("ESCAPE")) System.exit(0);
                });

        scene.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    input.remove(code);
                    if (input.isEmpty() || code.equals("LEFT") || code.equals("RIGHT")) setPlayerLastAction(0);
                });

        //stworzenie płótna
        root.getChildren().add(canvas);

        //wywołanie aktualizowania wszystkiego oraz wyświetlania wszystkiego
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.0166666),                // 60 FPS
                ae -> update());

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
        stage.show();
    }

    //inicjalizowanie obiektów
    private void initializeImportantObjcects(){
        input= new ArrayList<>();
        handler = new Handler();
        player = new PlayerBlock(0,0, BlocksId.PlayerBlock, Color.RED, this);
        playerLastAction = 0;
        handler.addBlock(player);
        canvas = new Canvas(screenWidth, screenHeight);
        gc = canvas.getGraphicsContext2D();
    }

    //aktualizowanie i wyświetlanie obiektów
    private void update(){
        handler.update();
        handler.draw(gc);
    }

    //main() wywołujący launch(), czyli start()
    public static void main(String[] args) {
        launch(args);
    }
    //settery i gettery
    public int getPlayerLastAction() {
        return playerLastAction;
    }

    public void setPlayerLastAction(int playerLastAction) {
        this.playerLastAction = playerLastAction;
    }

}