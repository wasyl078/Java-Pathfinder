package com.wasyl.NewGame.Framework;

import com.wasyl.NewGame.Blocks.BackgroundBlock;
import com.wasyl.NewGame.Blocks.BlocksId;
import com.wasyl.NewGame.Blocks.EnemyBlock;
import com.wasyl.NewGame.Blocks.WallBlock;

//LevelMaker służy do utworzenia całej planszy oraz wroga (wrogów)
class LevelMaker {

    //metoda do utworzenia domyślengo poziomu z jednym wrogiem i trzema ścianami
    static void makeDefaultLevel(Handler handler) {
        for (int i = 0; i < 64; i++)
            for (int j = 0; j < 36; j++)
                handler.addElement(new BackgroundBlock(i, j, 0, 0, 0, BlocksId.BackgroundBlock, handler.getObjectsList(),handler.getAdditionalObjects(), handler.getBlocksMatrix()));


        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 3; j++) {
                handler.addElement(new WallBlock(i + 4, j * 4 + 3, 255, 255, 255, BlocksId.WallBlock, handler.getObjectsList(),handler.getAdditionalObjects(), handler.getBlocksMatrix()));
            }

        EnemyBlock enemy = new EnemyBlock(10, 14, 255, 0, 0, BlocksId.EnemyBlock, handler.getObjectsList(),handler.getAdditionalObjects(), handler.getBlocksMatrix());
        enemy.setPlayer();
        handler.addElement(enemy);
        handler.makeaAstarGraph();
        enemy.setaAstarGraph(handler.getStarGraph());

        handler.getStarGraph().generatePrimsMaze();
    }
}

