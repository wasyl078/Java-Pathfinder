package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

//blok gracza - możliwy do sterowania strzałkami
public class PlayerBlock extends AbstractBlock {

    //countDown na atak - raz na dwie sekundy można zaatakować (przy 60fps'ach)
    private final double DEFAULT_ATTACK_COUNTDOWN = 120;
    private double actualAttackCountdown;

    //konstruktor
    public PlayerBlock(int positionX, int positionY, int red, int green, int blue, BlocksId blocksId, ArrayList<AbstractBlock> objects, ArrayList<AbstractBlock> additionalObjects, AbstractBlock[][] blocksMatrix) {
        super(positionX, positionY, red, green, blue, 100, blocksId, objects, additionalObjects, blocksMatrix);

        //gracz ma 100 punktów zdrowia
    }

    //pozycja gracza aktualizowana jest w klasie Game

    //draw() - wyświetla zielony blok gracza
    public void draw(GraphicsContext gc) {
        double part = actualAttackCountdown / DEFAULT_ATTACK_COUNTDOWN;

        gc.setFill(Color.rgb(51, 102, 0));
        gc.fillRect(getPositionX() * DEFAULT_X, getPositionY() * DEFAULT_Y, X_SIDE_OF_BLOCK, Y_SIDE_OF_BLOCK * part);

        gc.setFill(Color.rgb(getRed(), getGreen(), getBlue(), getAlpha()));
        gc.fillRect(getPositionX() * DEFAULT_X, getPositionY() * DEFAULT_Y + Y_SIDE_OF_BLOCK * part, X_SIDE_OF_BLOCK, Y_SIDE_OF_BLOCK * (1 - part));
    }
//TODO nowy kształt na countdown
    //tutaj można ewentualnie wypisywać aktualną pozycję gracza
    @Override
    public void update() {
        actualAttackCountdown--;
        if (actualAttackCountdown < 0)
            actualAttackCountdown = 0;
    }

    //metoda do obsługi żadania ataku
    public void attack() {

        //jeżeli jest countdown
        if (actualAttackCountdown > 0)
            return;

        //atak w czterech kieunkach - sprawdza czy można w danym miejscu postawić nowy blok ataku
        //w góre
        if (getPositionY() > 0)
            getObjects().add(new FireBlock(getPositionX(), getPositionY() - 1, 255, 255, 0, BlocksId.FireBlock, "UP", getObjects(), getAdditionalObjects(), getBlocksMatrix()));

        //w dół
        if (getPositionY() < Game.VERTICAL_NUMBER_OF_BLOCKS - 1)
            getObjects().add(new FireBlock(getPositionX(), getPositionY() + 1, 255, 255, 0, BlocksId.FireBlock, "DOWN", getObjects(), getAdditionalObjects(), getBlocksMatrix()));

        //w lewo
        if (getPositionX() > 0)
            getObjects().add(new FireBlock(getPositionX() - 1, getPositionY(), 255, 255, 0, BlocksId.FireBlock, "LEFT", getObjects(), getAdditionalObjects(), getBlocksMatrix()));

        //w prawo
        if (getPositionX() < Game.HORIZONTAL_NUMBER_OF_BLOCKS - 1)
            getObjects().add(new FireBlock(getPositionX() + 1, getPositionY(), 255, 255, 0, BlocksId.FireBlock, "RIGHT", getObjects(), getAdditionalObjects(), getBlocksMatrix()));

        actualAttackCountdown = DEFAULT_ATTACK_COUNTDOWN;
    }
}
