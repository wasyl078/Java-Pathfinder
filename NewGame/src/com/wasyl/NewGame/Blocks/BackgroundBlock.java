package com.wasyl.NewGame.Blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//blok tła - można po nim się poruszać
public class BackgroundBlock extends AbstractBlock {

    private int time;

    public BackgroundBlock(int positionX, int positionY,int red, int green, int blue,  BlocksId blocksId) {
        super(positionX, positionY, red,  green, blue, Integer.MAX_VALUE, blocksId, null);
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
