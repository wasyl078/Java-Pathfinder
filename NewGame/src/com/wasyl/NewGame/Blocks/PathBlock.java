package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Handler;
import javafx.scene.paint.Color;

import java.util.ArrayList;

//blok ścieżki - niebieska kropka na bloku tła
public class PathBlock extends AbstractBlock {

    private int liveTime = 100;

    public PathBlock(int positionX, int positionY, BlocksId blocksId, Handler handler) {
        super(positionX, positionY, blocksId, handler);
        setColor(Color.BLUE);
    }

    //aktualizowanie czasu życia tego bloku
    @Override
    public void update(ArrayList<AbstractBlock> objects) {
        liveTime--;
    }

    //getter
    public int getLiveTime() {
        return liveTime;
    }
}
