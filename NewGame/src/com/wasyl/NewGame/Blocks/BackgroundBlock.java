package com.wasyl.NewGame.Blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

//blok tła - można po nim się poruszać
public class BackgroundBlock extends AbstractBlock {

    public BackgroundBlock(int positionX, int positionY,int red, int green, int blue,  BlocksId blocksId, ArrayList<AbstractBlock> objects, ArrayList<AbstractBlock> additionalObjects,AbstractBlock[][] blocksMatrix) {
        super(positionX, positionY, red,  green, blue, Integer.MAX_VALUE, blocksId,objects, additionalObjects,blocksMatrix);
        //blok tła ma maksymalną liczbę punktów zdrowia
    }

    //draw() - tylko wyświetla czarny blok
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.rgb(getRed(),getGreen(),getBlue(), getAlpha()));
        gc.fillRect(getPositionX() * DEFAULT_X, getPositionY() * DEFAULT_Y, X_SIDE_OF_BLOCK, Y_SIDE_OF_BLOCK);
    }

    @Override
    public void update() {
        //tu nic nie musi się dziać
    }
}
