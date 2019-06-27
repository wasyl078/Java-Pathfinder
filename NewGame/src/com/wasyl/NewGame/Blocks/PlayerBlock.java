package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Handler;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PlayerBlock extends AbstractBlock {

    //referencja na klasę gry - żeby mieć wgląd w ostatnią akcję gracza

    public PlayerBlock(int positionX, int positionY, BlocksId blocksId, Handler handler) {
        super(positionX, positionY, blocksId, handler);
        setColor(Color.GREEN);
    }

    //tutaj - aktualizowanie pozycji gracza
    @Override
    public void update(ArrayList<AbstractBlock> blocks) {
        System.out.println("  ||  now: " + getPositionX() + "x" + getPositionY());
    }
}
