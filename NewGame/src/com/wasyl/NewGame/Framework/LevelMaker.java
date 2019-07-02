package com.wasyl.NewGame.Framework;

import com.wasyl.NewGame.Blocks.*;
import com.wasyl.NewGame.aStar.aStarGraph;

//LevelMaker służy do utworzenia całej planszy oraz wroga (wrogów)
public class LevelMaker {

    //metoda do utworzenia domyślengo poziomu z jednym wrogiem i trzema ścianami
    public static void makeDefaultLevel(Handler handler) {
        for (int i = 0; i < 64; i++)
            for (int j = 0; j < 36; j++)
                handler.addElement(new BackgroundBlock(i, j, BlocksId.BackgroundBlock, handler));


        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 3; j++) {
                handler.addElement(new WallBlock(i + 4, j * 4 + 3, BlocksId.WallBlock, handler));
            }

        EnemyBlock enemy = new EnemyBlock(10, 14, BlocksId.EnemyBlock, handler);
        enemy.setPlayer();
        handler.addElement(enemy);

        //utworzenie aStarGraph'u na podstawie tej planszy
        aStarGraph graph = new aStarGraph(handler);
        handler.setStarGraph(graph);
    }
}

