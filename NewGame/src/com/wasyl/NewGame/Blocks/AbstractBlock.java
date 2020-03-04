package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Game;
import com.wasyl.NewGame.Framework.Handler;
import com.wasyl.NewGame.aStar.Node;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

//klasa abstrakcyjnego bloku, po której dziedziczy każdy element (blok) w grze
public abstract class AbstractBlock {

    //stałe globalne
    static final int X_SIDE_OF_BLOCK = Game.SCREEN_WIDTH / Game.HORIZONTAL_NUMBER_OF_BLOCKS;
    static final int Y_SIDE_OF_BLOCK = Game.SCREEN_HEIGHT / Game.VERTICAL_NUMBER_OF_BLOCKS;
    static final int DEFAULT_X = X_SIDE_OF_BLOCK;
    static final int DEFAULT_Y = Y_SIDE_OF_BLOCK;

    //zmienne prywatne dla każdego bloku definiującego pozycje i stan
    private int positionX;
    private int positionY;
    private BlocksId blocksId;
    private double healthPoints;
    private double maxHP;

    //zmienne prywatne definiujące jego kolor
    private int red;
    private int green;
    private int blue;
    private double alpha;

    //zmienne prywatne dla każdego bloku zawierające elementy niezbędne do pracy nad nim
    private ArrayList<AbstractBlock> objects;
    private AbstractBlock[][] blocksMatrix;
    private Node node;
    private ArrayList<AbstractBlock> additionalObjects;

    //konstruktor domyślny, definiuje stan bloku
    AbstractBlock(int positionX, int positionY, int red, int green, int blue, int maxHP, BlocksId blocksId, Handler handler) {
        this.blocksId = blocksId;
        this.positionX = positionX;
        this.positionY = positionY;
        this.maxHP = maxHP;
        this.healthPoints = maxHP;
        this.node = new Node(positionX, Game.VERTICAL_NUMBER_OF_BLOCKS - 1 - positionY, blocksId == BlocksId.WallBlock);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = 1f;
        if (handler != null) {
            this.blocksMatrix = handler.getBlocksMatrix();
            this.objects = handler.getObjectsList();
            this.additionalObjects = handler.getAdditionalObjects();
        }
    }

    //update() - należy ją nadpisać - służy do aktualizowanai stanu bloku przy każdej klatce
    public abstract void update();

    //updateNode() - pomocnicza metoda do aktualizowania stanu odpowiadającego blokowi node'a
    public void updateNode() {
        if (node != null)
            this.node = new Node(positionX, Game.VERTICAL_NUMBER_OF_BLOCKS - 1 - positionY, blocksId == BlocksId.WallBlock);
    }

    //draw() - tylko wyświetla każdy blok / kształt w odpowiednim miejscu w oknie
    public abstract void draw(GraphicsContext gc);

    //gettery i settery
    public int getPositionX() {
        return positionX;
    }

    //ustawianie X sprawdza czy na tym miejscu nie znajduje się przypadkiem ściana
    public void setPositionX(int positionX) {

        //sprawdzenie podstawowych przypadków
        if (positionX < 0 || positionX >= Game.HORIZONTAL_NUMBER_OF_BLOCKS)
            return;

        //sprawdzenie przypadków gdy docelowy blok jest ścianą lub innym ruchomym obiektem
        if (blocksMatrix[positionX][Game.VERTICAL_NUMBER_OF_BLOCKS - 1 - positionY].getBlocksId() != BlocksId.WallBlock) {
            for (AbstractBlock ab : objects)
                if (ab.getPositionX() == positionX && ab.getPositionY() == positionY)
                    if (ab != this)
                        return;
            makeOldBLock(positionX, positionY);
            this.positionX = positionX;
        }
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {

        //sprawdzenie podstawowych przypadków
        if (positionY < 0 || positionY >= Game.VERTICAL_NUMBER_OF_BLOCKS)
            return;

        //sprawdzenie przypadków gdy docelowy blok jest ścianą lub innym ruchomym obiektem
        if (blocksMatrix[positionX][Game.VERTICAL_NUMBER_OF_BLOCKS - 1 - positionY].getBlocksId() != BlocksId.WallBlock) {
            for (AbstractBlock ab : objects)
                if (ab.getPositionX() == positionX && ab.getPositionY() == positionY)
                    if (ab != this)
                        return;

            makeOldBLock(positionX, positionY);
            this.positionY = positionY;

        }
    }

    //metoda do tworzenia "smugi za poruszającymi się blokami"
    public void makeOldBLock(int positionX, int positionY) {
        OldBlock newBlock = new OldBlock(positionX, positionY, red, green, blue, BlocksId.OldBlock);
        additionalObjects.add(newBlock);
    }

    //gettery  settery
    public BlocksId getBlocksId() {
        return blocksId;
    }

    AbstractBlock[][] getBlocksMatrix() {
        return this.blocksMatrix;
    }

    ArrayList<AbstractBlock> getObjects() {
        return objects;
    }

    public Node getNode() {
        return node;
    }

    public double getHealthPoints() {
        return healthPoints;
    }

    void addHealthPoints(double howMany) {
        this.healthPoints += howMany;
    }

    double getAlpha() {
        return alpha;
    }

    void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    double getMaxHP() {
        return maxHP;
    }

    int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public ArrayList<AbstractBlock> getAdditionalObjects() {
        return additionalObjects;
    }
}
