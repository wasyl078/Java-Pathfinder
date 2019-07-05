package com.wasyl.NewGame.Blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

//blok ścieżki - niebieska kropka na bloku tła
public class PathBlock extends AbstractBlock {

    //konstruktor
    PathBlock(int positionX, int positionY, int red, int green, int blue, BlocksId blocksId, ArrayList<AbstractBlock> objects, ArrayList<AbstractBlock> additionalObjects, AbstractBlock[][] blocksMatrix) {
        super(positionX, positionY, red, green, blue, 100, blocksId, objects, additionalObjects, blocksMatrix);
        //blok ścieżki ma zwykle 100 punktów zdrowia
    }

    //draw() - wyświetla niebieskie kółko - kształt tego bloku
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.rgb(getRed(), getGreen(), getBlue(), getAlpha()));
        gc.fillOval(getPositionX() * DEFAULT_X + 0.4*X_SIDE_OF_BLOCK , getPositionY() * DEFAULT_Y +0.4*Y_SIDE_OF_BLOCK, X_SIDE_OF_BLOCK / 5, Y_SIDE_OF_BLOCK / 5);
    }

    //aktualizowanie czasu życia tego bloku
    @Override
    public void update() {
        addHealthPoints(-1);
    }
}
