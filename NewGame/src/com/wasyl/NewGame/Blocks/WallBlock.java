package com.wasyl.NewGame.Blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class WallBlock extends AbstractBlock{

    public WallBlock(int positionX, int positionY, BlocksId blocksId, Color color) {
        super(positionX, positionY, blocksId, color);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(GraphicsContext gc) {

    }
}
