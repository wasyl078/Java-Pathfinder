package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Handler;
import javafx.scene.canvas.GraphicsContext;


public class BombBlock extends AbstractBlock {

    private static final int EXPLOSION_TIME= 240;
    private int explosionCounter;
    private int explosionPower;

    BombBlock(int positionX, int positionY, int red, int green, int blue, BlocksId blocksId, Handler handler) {
        super(positionX, positionY, red, green, blue, 240, blocksId, handler);
    }

    @Override
    public void update() {
        addHealthPoints(-1);
        explosionCounter--;

        if(explosionCounter==0){

        }
    }

    @Override
    public void draw(GraphicsContext gc) {

    }
}
