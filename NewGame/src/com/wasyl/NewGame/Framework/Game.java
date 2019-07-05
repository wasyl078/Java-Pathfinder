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

import java.awt.*;

//klasa gry - tutaj tworzone są wszystkie instancje obiektów oraz obsługiwana jest klawiatura
public class Game extends Application {

    //związane z okienkiem i rozmiarem gry
    private static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public final static int SCREEN_WIDTH = (int)SCREEN_SIZE.getWidth();
    public final static int SCREEN_HEIGHT = (int)SCREEN_SIZE.getHeight();
    //public final static int SCREEN_WIDTH = 1600;
    //public final static int SCREEN_HEIGHT = 900;
    public final static int HORIZONTAL_NUMBER_OF_BLOCKS = 64;
    public final static int VERTICAL_NUMBER_OF_BLOCKS = 36;
    private Canvas canvas;
    private GraphicsContext gc;

    //związane z obiektami w grze
    private Handler handler;
    private PlayerBlock player;
    private boolean ableToMove = true;
    private final int step = 1;

    //start() == launch()
    @Override
    public void start(Stage stage) {
        //stworzenie "frame'a", grupy oraz odpalenie gry
        stage.setTitle("Gra");
        Group root = new Group();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, true, SceneAntialiasing.BALANCED);
        stage.setScene(scene);

        //zainicjalizowanie obiektów
        initializeImportantObjects();

        //obsługa zdarzeń z klawiatury
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            KeyCode e = key.getCode();
            //wyjście z gry
            if (e == KeyCode.ESCAPE) System.exit(0);

            //zaatakowanie
            if(e == KeyCode.Z) player.attack();

            //poruszanie się
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
            }
            ableToMove = false;
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            KeyCode e = key.getCode();
            if (e == KeyCode.UP || e == KeyCode.DOWN || e == KeyCode.RIGHT || e == KeyCode.LEFT || e == KeyCode.Z) {
                ableToMove = true;
            }
        });

        //stworzenie płótna
        root.getChildren().add(canvas);

        //wywołanie aktualizowania wszystkiego oraz wyświetlania wszystkiego
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        //możliwość ustawienia ilości klatek na seundę
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.0166666),                // 60 FPS
                //Duration.seconds(0.033333),                // 30 FPS
                ae -> update());

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();

        //ustawienie full screen'a
        stage.setMaximized(true);
        stage.setFullScreen(true);

        stage.show();
    }

    //inicjalizowanie najważniejszych obiektów
    private void initializeImportantObjects() {
        handler = new Handler();
        player = new PlayerBlock(10, 2, 0,255,0,BlocksId.PlayerBlock, handler.getObjectsList(),handler.getAdditionalObjects(), handler.getBlocksMatrix());
        handler.addElement(player);
        LevelMaker.makeDefaultLevel(handler);
        handler.removeElement(player);
        handler.addElement(player);
        canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
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
}