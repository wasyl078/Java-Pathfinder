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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Game extends Application {

    //związane z okienkiem
    private final static int screenWidth = 1600;
    private final static int screenHeight = 900;
    private Canvas canvas;
    private GraphicsContext gc;

    //związane z obiektami w grze
    private Handler handler;
    private int playerLastAction;
    private PlayerBlock player;
    private boolean ableToMove = true;
    private final int step = 1;
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
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            KeyCode e = key.getCode();
            if(e == KeyCode.ESCAPE) System.exit(0);

            if (ableToMove) {
                switch (e) {
                    case UP: {
                        player.setPositionY(player.getPositionY() - step);
                        break;
                    }
                    case DOWN: {
                        player.setPositionY(player.getPositionY() + step);
                        break;
                    }
                    case LEFT: {
                        player.setPositionX(player.getPositionX() - step);
                        break;
                    }
                    case RIGHT: {
                        player.setPositionX(player.getPositionX() + step);
                        break;
                    }
                }
            } else setPlayerLastAction(0);
            ableToMove = false;
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            KeyCode e = key.getCode();

            if (e == KeyCode.UP || e == KeyCode.DOWN || e == KeyCode.RIGHT || e == KeyCode.LEFT) {
                setPlayerLastAction(0);
                ableToMove = true;
            }
        });
        //stworzenie płótna
        root.getChildren().add(canvas);

        //wywołanie aktualizowania wszystkiego oraz wyświetlania wszystkiego
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                //Duration.seconds(0.0166666),                // 60 FPS
                Duration.seconds(0.033333),                // 30 FPS
                ae -> update());

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
        stage.show();
    }

    //inicjalizowanie obiektów
    private void initializeImportantObjcects() {
        handler = new Handler();
        player = new PlayerBlock(10, 5, BlocksId.PlayerBlock, handler);
        handler.addBlock(player);
        LevelMaker.makeDefaultLevel(handler);
        handler.removeBlock(player);
        handler.addBlock(player);
        canvas = new Canvas(screenWidth, screenHeight);
        gc = canvas.getGraphicsContext2D();
    }

    //aktualizowanie i wyświetlanie obiektów
    private void update() {
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