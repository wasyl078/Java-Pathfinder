package com.wasyl.NewGame.Framework;

import com.wasyl.NewGame.Blocks.*;
import com.wasyl.NewGame.aStar.aStarGraph;

public class LevelMaker {

    //64x36
    public static void makeDefaultLevel(Handler handler) {
        for (int i = 0; i < 64; i++)
            for (int j = 0; j < 36; j++)
                handler.addBlock(new BackgroundBlock(i, j, BlocksId.BackgroundBlock, handler));


        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 3; j++) {
                handler.removeBlock(i + 4, j * 4 + 3);
                handler.addBlock(new WallBlock(i + 4, j * 4 + 3, BlocksId.WallBlock, handler));
            }

        EnemyBlock enemy = new EnemyBlock(5, 14, BlocksId.EnemyBlock, handler);
        enemy.setPlayer();
        handler.addBlock(enemy);
        aStarGraph graph = new aStarGraph(handler);
        handler.setStarGraph(graph);
    }
}

