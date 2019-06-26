package com.wasyl.NewGame.Blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class AbstractBlock {

    //zmienne dla każdego bloku
    private int positionX;
    private int positionY;
    private BlocksId blocksId;
    private Color color;
    private final int widthHeight = 25;

    //konstruktor domyślny
    public AbstractBlock(int positionX, int positionY, BlocksId blocksId, Color color){
        this.blocksId = blocksId;
        this.positionX = positionX;
        this.positionY = positionY;
        this.color = color;
    }

    //metody do nadpisanie
    public abstract void update();
    public abstract void draw(GraphicsContext gc);

    //gettery i settery takie same zawsze
    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
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

    public int getWidthHeight() {
        return widthHeight;
    }
}
