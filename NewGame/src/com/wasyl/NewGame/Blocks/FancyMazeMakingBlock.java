package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Game;
import com.wasyl.NewGame.Framework.Handler;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FancyMazeMakingBlock extends AbstractBlock {

    private ArrayList<BackgroundBlock> mazeBlocks;
    private int counter;
    private Handler handler;
    private PlayerBlock player;

    public FancyMazeMakingBlock(ArrayList<BackgroundBlock> mazeBlocks, Handler handler, PlayerBlock player) {
        super(-100, -100, 0, 0, 0, 69, BlocksId.FancyMazeMakingBlock, handler);
        this.mazeBlocks = mazeBlocks;
        counter = -60;
        this.handler = handler;
        this.player = player;

        Collections.sort(mazeBlocks, Comparator.comparingInt(BackgroundBlock::getTime));
    }

    @Override
    public void update() {
        counter ++;

        if (counter >= 1 && !mazeBlocks.isEmpty()) {
            counter = 0;
            handler.addElement(mazeBlocks.get(0));
            mazeBlocks.remove(0);
            handler.addElement(mazeBlocks.get(0));
            mazeBlocks.remove(0);
            handler.addElement(mazeBlocks.get(0));
            mazeBlocks.remove(0);
        }


        if (mazeBlocks.isEmpty()) {
            addHealthPoints(-100);

            for (int x = 10; x < Game.HORIZONTAL_NUMBER_OF_BLOCKS; x++)
                for (int y = 10; y < Game.VERTICAL_NUMBER_OF_BLOCKS; y++)
                    if (handler.getBlocksMatrix()[x][y].getBlocksId() == BlocksId.BackgroundBlock) {
                        player.setPositionX(x);
                        player.setPositionY(Game.VERTICAL_NUMBER_OF_BLOCKS - 1 - y);
                        player.setBlue(0);
                        player.setRed(0);
                        player.setGreen(255);
                        return;
                    }
        }


    }

    @Override
    public void draw(GraphicsContext gc) {

    }
}
