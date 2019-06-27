package com.wasyl.NewGame.Framework;

import com.wasyl.NewGame.Blocks.*;
import com.wasyl.NewGame.graph.MyGraph;

import java.util.ArrayList;

public class LevelMaker {

    //64x36
    public static void makeDefaultLevel(Handler handler) {
        for (int i = 0; i < 64; i++)
            for (int j = 0; j < 36; j++)
                handler.addBlock(new BackgroundBlock(i, j, BlocksId.BackgroundBlock, handler));


        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 3; j++) {
                handler.removeBlock(i + 4, j * 4 + 3);
                handler.addBlock(new WallBlock(i + 4, j * 4 + 3, BlocksId.WallBlock, handler));
            }

        EnemyBlock enemy = new EnemyBlock(20, 10, BlocksId.EnemyBlock, handler);
        enemy.setPlayer();
        handler.addBlock(enemy);

        makeGraph(handler);
        enemy.setMapGraph(handler.getMapGraph());
    }

    private static void makeGraph(Handler handler) {
        //utworzenie instancji grafu i listy background bloków
        MyGraph graph = new MyGraph(6537);
        ArrayList<BackgroundBlock> bblocks = new ArrayList<>();

        //wpisanie tylko background bloków do tej listy
        for (AbstractBlock ab : handler.getBlocksList())
            if (ab.getBlocksId() == BlocksId.BackgroundBlock)
                bblocks.add((BackgroundBlock) ab);

        //utworzenie odpowiednich wierzchołków w tym grafie - wierzchołki mają numery utworzone
        // zgodnie z równaniem: (posY+1)*100+(posX+1)
        for (BackgroundBlock bb : bblocks) {
            graph.addVertex((bb.getPositionY() + 1) * 100 + (bb.getPositionX() + 1));
        }

        //utworzenie odpowiednich krawędzi w tym grafie
        for (BackgroundBlock bb : bblocks) {

            int numToLeft, numToRight, numToUp, numToDown, actNum;
            actNum = (bb.getPositionY() + 1) * 100 + (bb.getPositionX() + 1);
            numToLeft = (bb.getPositionY() + 1) * 100 + (bb.getPositionX() + 1) - 1;
            numToRight = (bb.getPositionY() + 1) * 100 + (bb.getPositionX() + 1) + 1;
            numToUp = bb.getPositionY() * 100 + (bb.getPositionX() + 1);
            numToDown = (bb.getPositionY()+2) * 100 + (bb.getPositionX() + 1);

            //krawędż w lewo
            if(graph.containsVertes(numToLeft))
                graph.addEdge(actNum, numToLeft);
            //krawędż w prawo
            if(graph.containsVertes(numToRight))
                graph.addEdge(actNum,numToRight);
            //krawędż w górę
            if(graph.containsVertes(numToUp))
                graph.addEdge(actNum,numToUp);
            //krawędż w dół
            if(graph.containsVertes(numToDown))
                graph.addEdge(actNum,numToDown);
        }

        handler.setMapGraph(graph);
    }
}
