package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Handler;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PathBlock extends AbstractBlock {

    private int liveTime = 100;

    public PathBlock(int positionX, int positionY, BlocksId blocksId, Handler handler) {
        super(positionX, positionY, blocksId, handler);
        setColor(Color.BLUE);
    }

    @Override
    public void update(ArrayList<AbstractBlock> blocks) {
        liveTime--;
        if (liveTime < 0)
            setToDelete(true);
    }
}
