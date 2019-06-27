package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Handler;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class EnemyBlock extends AbstractBlock {

    private PlayerBlock player;
    private int counter = 0;

    public EnemyBlock(int positionX, int positionY, BlocksId blocksId, Handler handler) {
        super(positionX, positionY, blocksId, handler);
        setColor(Color.RED);
    }

    @Override
    public void update(ArrayList<AbstractBlock> objects) {
        counter++;
        if (counter == 30) {
            double dy, dx, z, sin, cos, urgeToMoveY, urgeToMoveX;
            dy = getPositionY() - player.getPositionY();
            dx = player.getPositionX() - getPositionX();
            z = Math.sqrt(Math.pow(dy, 2) + Math.pow(dx, 2));

            sin = -dy / z;
            cos = dx / z;

            urgeToMoveY = sin * getStep();
            urgeToMoveX = cos * getStep();

            if (Math.abs(urgeToMoveX) > Math.abs(urgeToMoveY)) {
                if (urgeToMoveX > 0)
                    setPositionX(getPositionX() + getStep());
                else
                    setPositionX(getPositionX() - getStep());
            } else {
                if (urgeToMoveY > 0)
                    setPositionY(getPositionY() + getStep());
                else
                    setPositionY(getPositionY() - getStep());
            }
            counter = 0;
        }


        // System.out.println("x: " + urgeToMoveX + " | y: " + urgeToMoveY);
    }

    public void setPlayer() {
        for(AbstractBlock ab: getBlocks())
            if(ab.getBlocksId() == BlocksId.PlayerBlock)
                this.player = (PlayerBlock)ab;

    }
}
