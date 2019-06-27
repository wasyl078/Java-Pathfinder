package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Handler;
import com.wasyl.NewGame.graph.Vertex;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class AbstractBlock {

    //zmienne dla każdego bloku
    private ArrayList<AbstractBlock> blocks;
    private Vertex v;
    private int positionX;
    private int positionY;
    private int defaultX = 25;
    private int defaultY = 25;
    private BlocksId blocksId;
    private Color color;
    private final int widthHeight = 25;
    private final int step = 1;

    //konstruktor domyślny
    public AbstractBlock(int positionX, int positionY, BlocksId blocksId, Handler handler) {
        this.blocksId = blocksId;
        this.positionX = positionX;
        this.positionY = positionY;
        this.blocks = handler.getBlocksList();
        v = new Vertex((getPositionY() + 1)*100 + (getPositionX() + 1));
    }

    //metody do nadpisania lub nie
    public abstract void update(ArrayList<AbstractBlock> blocks);

    public void updateVertexNumber(){
        this.v = new Vertex((getPositionY() + 1)*100 + (getPositionX() + 1));
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(getColor());
        gc.fillRect(getPositionX() * defaultX, getPositionY() * defaultY, getWidthHeight(), getWidthHeight());
    }

    //kolizje
    public Rectangle2D getBounds() {
        return new Rectangle2D(getPositionX() * defaultX, getPositionY() * defaultY, getWidthHeight(), getWidthHeight());
    }

    //gettery i settery takie same zawsze
    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        for (AbstractBlock ab : getBlocks())
            if (ab.getBlocksId() != BlocksId.BackgroundBlock)
                if (ab.getPositionX() == positionX && ab.getPositionY() == getPositionY())
                    return;
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        for (AbstractBlock ab : getBlocks())
            if (ab.getBlocksId() != BlocksId.BackgroundBlock)
                if (ab.getPositionY() == positionY && ab.getPositionX() == getPositionX())
                    return;
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

    public int getStep() {
        return step;
    }

    public ArrayList<AbstractBlock> getBlocks() {
        return blocks;
    }

    public Vertex getV() {
        return v;
    }
}
