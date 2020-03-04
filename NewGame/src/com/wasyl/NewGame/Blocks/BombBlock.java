package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Game;
import com.wasyl.NewGame.Framework.Handler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class BombBlock extends AbstractBlock {

    private static final double EXPLOSION_TIME = 180;
    private static final int ATTACK_POWER = 250;
    private double explosionCounter;
    private int explosionPower;
    private PlayerBlock player;

    BombBlock(int positionX, int positionY, BlocksId blocksId, int explosionPower, PlayerBlock player, Handler handler) {
        super(positionX, positionY, 255, 255, 0, 69, blocksId, handler);
        this.explosionPower = explosionPower;
        this.player = player;
        this.explosionCounter = EXPLOSION_TIME;
    }

    @Override
    public void update() {
        explosionCounter--;
        if (getGreen() > 0)
            setGreen(getGreen() - 1);

        if (explosionCounter <= 0 || getHealthPoints() != getMaxHP()) {
            calculateExplosion();
            player.setActualNumberOfBombs(player.getActualNumberOfBombs() - 1);
            addHealthPoints(-100);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        //malowanie przeciwnika
        double part = explosionCounter / EXPLOSION_TIME;
        gc.setFill(Color.rgb(getRed(), getGreen(), getBlue(), getAlpha()));
        gc.fillRect(getPositionX() * DEFAULT_X + 0.5 * X_SIDE_OF_BLOCK * part, getPositionY() * DEFAULT_Y + 0.5 * Y_SIDE_OF_BLOCK * part, X_SIDE_OF_BLOCK * (1 - part), Y_SIDE_OF_BLOCK * (1 - part));
    }

    private void calculateExplosion() {
        //na srodku
        dealDamage(getPositionX(), getPositionY());

        //do gory
        boolean destroyed = false;
        for (int i = 1; i <= explosionPower && !destroyed; i++)
            destroyed = dealDamage(getPositionX(), getPositionY() - i);

        //do dolu
        destroyed = false;
        for (int i = 1; i <= explosionPower && !destroyed; i++)
            destroyed = dealDamage(getPositionX(), getPositionY() + i);

        //w lewo
        destroyed = false;
        for (int i = 1; i <= explosionPower && !destroyed; i++)
            destroyed = dealDamage(getPositionX() - i, getPositionY());

        //w prawo
        destroyed = false;
        for (int i = 1; i <= explosionPower && !destroyed; i++)
            destroyed = dealDamage(getPositionX() + i, getPositionY());
    }

    private boolean dealDamage(int positionX, int positionY) {

        boolean destroyed = false;

        //sprawdzenie innych ruchomych obiektów
        for (AbstractBlock ab : getObjects())
            if (ab.getPositionX() == positionX && ab.getPositionY() == positionY)
                ab.addHealthPoints(-ATTACK_POWER);

        //sprawdzenie ścian
        for (int x = 0; x < Game.HORIZONTAL_NUMBER_OF_BLOCKS; x++)
            for (int y = 0; y < Game.VERTICAL_NUMBER_OF_BLOCKS; y++) {
                AbstractBlock currentBlock = getBlocksMatrix()[x][y];
                if (x == positionX && y == Game.VERTICAL_NUMBER_OF_BLOCKS - 1 - positionY) {
                    currentBlock.addHealthPoints(-ATTACK_POWER);
                    if (currentBlock.getBlocksId() == BlocksId.WallBlock)
                        destroyed = true;
                }
            }

        //utworzenie oldBlocka w tym miejscu
        makeOldBLock(positionX, positionY);
        return destroyed;
    }
}
