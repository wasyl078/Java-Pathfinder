package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Handler;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class WallBlock extends AbstractBlock{

    public WallBlock(int positionX, int positionY, BlocksId blocksId, Handler handler) {
        super(positionX, positionY, blocksId, handler);
        setColor(Color.WHITE);
    }

    @Override
    public void update(ArrayList<AbstractBlock> blocks) {

    }
}
