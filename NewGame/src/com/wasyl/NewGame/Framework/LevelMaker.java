package com.wasyl.NewGame.Framework;

import com.wasyl.NewGame.Blocks.BackgroundBlock;
import com.wasyl.NewGame.Blocks.BlocksId;
import com.wasyl.NewGame.Blocks.EnemyBlock;
import com.wasyl.NewGame.Blocks.WallBlock;

public class LevelMaker {

    //64x36
    public static void makeDefaultLevel(Handler handler) {
        for (int i = 0; i < 64; i++)
            for (int j = 0; j < 36; j++)
                handler.addBlock(new BackgroundBlock(i, j, BlocksId.BackgroundBlock, handler));


           for(int i = 0; i<10; i++)
          for (int j = 0 ; j<3;j++)
           handler.addBlock(new WallBlock(i+4,j*4+3, BlocksId.WallBlock, handler));

        EnemyBlock enemy = new EnemyBlock(20, 10, BlocksId.EnemyBlock, handler);
        enemy.setPlayer();
        handler.addBlock(enemy);
    }
}
