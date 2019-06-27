package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Handler;
import com.wasyl.NewGame.graph.MyGraph;
import com.wasyl.NewGame.graph.Vertex;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class EnemyBlock extends AbstractBlock {

    private PlayerBlock player;
    private int counter = 0;
    private MyGraph mapGraph;
    private Handler handler;

    public EnemyBlock(int positionX, int positionY, BlocksId blocksId, Handler handler) {
        super(positionX, positionY, blocksId, handler);
        setColor(Color.RED);
        this.handler = handler;
    }

    @Override
    public void update(ArrayList<AbstractBlock> objects) {
        counter++;
        if (counter == 10) {
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

    public void calculateSSTF() {
        ArrayList<Vertex> path = mapGraph.dijkstrasAlgorithm(getV(), player.getV());

        for (Vertex v : path) {
            int x, y;
            y = v.getNumber() / 100 - 1;
            x = v.getNumber() - (y+1)*100 - 1;
            handler.changeBlockColor(x,y);
        }
    }

    public void setPlayer() {
        for (AbstractBlock ab : getBlocks())
            if (ab.getBlocksId() == BlocksId.PlayerBlock)
                this.player = (PlayerBlock) ab;
    }

    public void setMapGraph(MyGraph graph) {
        this.mapGraph = graph;
    }
}
