package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Game;
import com.wasyl.NewGame.Framework.Handler;
import com.wasyl.NewGame.aStar.aStarNode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

//klasa abstrakcyjnego bloku, po której dziedziczy każdy element (blok) w grze
public abstract class AbstractBlock {

    //stałe globalne
    static final int DEFAULT_X = 25 * Game.SCREEN_WIDTH / 1600;
    static final int DEFAULT_Y = 25 * Game.SCREEN_HEIGHT / 900;
    static final int X_SIDE_OF_BLOCK = 25 * Game.SCREEN_WIDTH / 1600;
    static final int Y_SIDE_OF_BLOCK = 25 * Game.SCREEN_HEIGHT / 900;

    //zmienne prywatbe dla każdego bloku definiującego jego egzystencje
    private int positionX;
    private int positionY;
    private BlocksId blocksId;
    private Color color;

    //zmienne prywatne dla każdego bloku zawierające elementy niezbędne do pracy nad nim
    private ArrayList<AbstractBlock> objects;
    private AbstractBlock[][] blocks;
    private aStarNode node;

    //konstruktor domyślny, definiuje stan bloku
    public AbstractBlock(int positionX, int positionY, BlocksId blocksId, Handler handler) {
        this.blocksId = blocksId;
        this.positionX = positionX;
        this.positionY = positionY;
        this.blocks = handler.getBlocksMatrix();
        this.objects = handler.getObjectsList();
        this.node = new aStarNode(positionX, 35 - positionY, blocksId == BlocksId.WallBlock);
    }

    //update() - należy ją nadpisać - służy do aktualizowanai stanu bloku przy każdej klatce
    public abstract void update(ArrayList<AbstractBlock> objects);

    //updateNode() - pomocnicza metoda do aktualizowania stanu odpowiadającego blokowi node'a
    public void updateNode() {
        this.node = new aStarNode(positionX, Game.VERTICAL_NUMBER_OF_BLOCKS - 1 - positionY, blocksId == BlocksId.WallBlock);
    }

    //draw() - tylko wyświetla każdy blok w odpowiednim miejscu w oknie
    public void draw(GraphicsContext gc) {
        gc.setFill(getColor());
        gc.fillRect(getPositionX() * DEFAULT_X, getPositionY() * DEFAULT_Y, X_SIDE_OF_BLOCK, Y_SIDE_OF_BLOCK);
    }

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
        if (blocks[positionX][Game.VERTICAL_NUMBER_OF_BLOCKS - 1 - positionY].getBlocksId() != BlocksId.WallBlock) {
            for (AbstractBlock ab : objects)
                if (ab.getPositionX() == positionX && ab.getPositionY() == positionY)
                    if (ab != this)
                        return;
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
        if (blocks[positionX][Game.VERTICAL_NUMBER_OF_BLOCKS - 1 - positionY].getBlocksId() != BlocksId.WallBlock) {
            for (AbstractBlock ab : objects)
                if (ab.getPositionX() == positionX && ab.getPositionY() == positionY)
                    if (ab != this)
                        return;
            this.positionY = positionY;
        }
    }

    public BlocksId getBlocksId() {
        return blocksId;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public AbstractBlock[][] getBlocks() {
        return this.blocks;
    }

    public aStarNode getNode() {
        return node;
    }

    public void setNode(aStarNode node) {
        this.node = node;
    }
}
