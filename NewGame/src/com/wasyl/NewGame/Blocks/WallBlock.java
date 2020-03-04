package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Handler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;


//blok ściany - nie można się na nim poruszać
public class WallBlock extends AbstractBlock {

    private static Random r = new Random();

    //konstruktor
    public WallBlock(int positionX, int positionY, int red, int green, int blue, BlocksId blocksId, Handler handler) {
        super(positionX, positionY,red,green,blue,50, blocksId, handler);
        //ściana ma tylko 50 punktów zdrowia
    }

    //draw() - wyświetla biały blok - ścianę
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.rgb(getRed(),getGreen(),getBlue(),getAlpha()));
        //gc.setFill(Color.rgb(r.nextInt(50) + 200,r.nextInt(50)+ 200, r.nextInt(50)+ 200,getAlpha()));
        gc.fillRect(getPositionX() * DEFAULT_X, getPositionY() * DEFAULT_Y, X_SIDE_OF_BLOCK, Y_SIDE_OF_BLOCK);
    }

    @Override
    public void update() {
        //nic nie musi się tu dziać
    }
}
