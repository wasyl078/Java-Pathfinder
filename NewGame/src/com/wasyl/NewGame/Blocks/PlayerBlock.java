package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Handler;
import javafx.scene.paint.Color;

import java.util.ArrayList;

//blok gracza - możliwy do sterowania strzałkami
public class PlayerBlock extends AbstractBlock {

    //konstruktor
    public PlayerBlock(int positionX, int positionY, BlocksId blocksId, Handler handler) {
        super(positionX, positionY, blocksId, handler);
        setColor(Color.GREEN);
    }

    //pozycja gracza aktualizowana jest w klasie Game

    //tutaj można ewentualnie wypisywać aktualną pozycję gracza
    @Override
    public void update(ArrayList<AbstractBlock> objects) {
     //   System.out.println("  ||  now: " + getPositionX() + "x" + getPositionY());
    }
}
