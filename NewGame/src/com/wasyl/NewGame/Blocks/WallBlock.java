package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Handler;
import javafx.scene.paint.Color;

import java.util.ArrayList;

//blok ściany - nie można się na nim poruszać
public class WallBlock extends AbstractBlock {

    //konstruktor
    public WallBlock(int positionX, int positionY, BlocksId blocksId, Handler handler) {
        super(positionX, positionY, blocksId, handler);
        setColor(Color.WHITE);
    }
    @Override
    public void update(ArrayList<AbstractBlock> blocks) {
        //żadna informacja nie musi być atualizowana dla ściany
    }
}
