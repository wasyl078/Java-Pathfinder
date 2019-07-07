package com.wasyl.NewGame.Framework;

import com.wasyl.NewGame.Blocks.*;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

//LevelMaker służy do utworzenia całej planszy oraz wroga (wrogów)
class LevelMaker {

    //metoda do utworzenia domyślengo poziomu z jednym wrogiem i trzema ścianami
    static void makeDefaultLevel(Handler handler) {
        for (int i = 0; i < 64; i++)
            for (int j = 0; j < 36; j++)
                handler.addElement(new BackgroundBlock(i, j, 0, 0, 0, BlocksId.BackgroundBlock));


        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 3; j++) {
                handler.addElement(new WallBlock(i + 4, j * 4 + 3, 255, 255, 255, BlocksId.WallBlock, handler));
            }

        EnemyBlock enemy = new EnemyBlock(10, 14, 255, 0, 0, BlocksId.EnemyBlock, handler);
        enemy.setPlayer();
        handler.addElement(enemy);
        handler.makeaAstarGraph();
        enemy.setaAstarGraph(handler.getStarGraph());

    }

    //metoda do stworzenia losowego poziomu przy wykorzystaniu algorytmu Prima
    static void makePrimsMaze(Handler handler,PlayerBlock player) {

        for (int x = 0; x < Game.HORIZONTAL_NUMBER_OF_BLOCKS; x++)
            for (int y = 0; y < Game.VERTICAL_NUMBER_OF_BLOCKS; y++)
                handler.addElement(new WallBlock(x, y, 100, 100, 100, BlocksId.WallBlock, handler));

        handler.makeaAstarGraph();

        ArrayList<BackgroundBlock> maze = handler.getStarGraph().generatePrimsMaze();

        for (BackgroundBlock bb : maze)
            handler.addElement(bb);

        EnemyBlock enemy = null;
        for (int x = Game.HORIZONTAL_NUMBER_OF_BLOCKS/2; x < Game.HORIZONTAL_NUMBER_OF_BLOCKS; x++)
            for (int y = Game.VERTICAL_NUMBER_OF_BLOCKS/2; y < Game.VERTICAL_NUMBER_OF_BLOCKS; y++)
                if (handler.getBlocksMatrix()[x][y].getBlocksId() == BlocksId.BackgroundBlock) {
                    enemy = new EnemyBlock(x, y, 255, 0, 0, BlocksId.EnemyBlock, handler);
                    break;
                }

        handler.addElement(player);
        enemy.setPlayer();
        handler.addElement(enemy);
        handler.makeaAstarGraph();
        handler.removeElement(player);
        handler.addElement(player);
        player.setRed(0);
        player.setBlue(0);
        enemy.setaAstarGraph(handler.getStarGraph());

    }

    //metoda do tworzenia w fancy sposób labiryntu
    static void makeFancyMaze(Handler handler, GraphicsContext gc,PlayerBlock player){
        for (int x = 0; x < Game.HORIZONTAL_NUMBER_OF_BLOCKS; x++)
            for (int y = 0; y < Game.VERTICAL_NUMBER_OF_BLOCKS; y++)
                handler.addElement(new WallBlock(x, y, 255, 255, 255, BlocksId.WallBlock, handler));
        handler.makeaAstarGraph();

        ArrayList<BackgroundBlock> maze = handler.getStarGraph().generatePrimsMaze();
        FancyMazeMakingBlock fancy = new FancyMazeMakingBlock(maze,handler, player);
        handler.addElement(fancy);
        handler.addElement(player);
    }
}

