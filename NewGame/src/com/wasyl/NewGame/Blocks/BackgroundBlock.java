package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Handler;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class BackgroundBlock extends AbstractBlock {

    public BackgroundBlock(int positionX, int positionY, BlocksId blocksId, Handler handler) {
        super(positionX, positionY, blocksId, handler);
        setColor(Color.BLACK);
    }

    @Override
    public void update(ArrayList<AbstractBlock>objects) {

    }
}
