package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Game;
import com.wasyl.NewGame.Framework.Handler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//blok gracza - możliwy do sterowania strzałkami
public class PlayerBlock extends AbstractBlock {

    //countDown na atak - raz na dwie sekundy można zaatakować (przy 60fps'ach)
    private final double DEFAULT_ATTACK_COUNTDOWN = 20;
    private double actualAttackCountdown;

    //zmienne dotyczace dzilainia bomb
    private int maxNumberOfBombs;
    private int actualNumberOfBombs;
    private int explosionBombPower;

    //potrzebuje handler'a żeby go przekazać do fireBlock'ów
    private Handler handler;

    //konstruktor
    public PlayerBlock(int positionX, int positionY, int red, int green, int blue, BlocksId blocksId, Handler handler) {
        super(positionX, positionY, red, green, blue, 100, blocksId, handler);
        this.handler = handler;
        //gracz ma 100 punktów zdrowia
        maxNumberOfBombs = 10;
        explosionBombPower = 3;
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
            getObjects().add(new FireBlock(getPositionX(), getPositionY() - 1, 255, 255, 0, BlocksId.FireBlock, "UP", handler));

        //w dół
        if (getPositionY() < Game.VERTICAL_NUMBER_OF_BLOCKS - 1)
            getObjects().add(new FireBlock(getPositionX(), getPositionY() + 1, 255, 255, 0, BlocksId.FireBlock, "DOWN", handler));

        //w lewo
        if (getPositionX() > 0)
            getObjects().add(new FireBlock(getPositionX() - 1, getPositionY(), 255, 255, 0, BlocksId.FireBlock, "LEFT", handler));

        //w prawo
        if (getPositionX() < Game.HORIZONTAL_NUMBER_OF_BLOCKS - 1)
            getObjects().add(new FireBlock(getPositionX() + 1, getPositionY(), 255, 255, 0, BlocksId.FireBlock, "RIGHT", handler));

        actualAttackCountdown = DEFAULT_ATTACK_COUNTDOWN;
    }

    //metoda do plantowania bomby
    public void plantBomb(){
        if(actualNumberOfBombs<maxNumberOfBombs){
            actualNumberOfBombs++;
            handler.addElement(new BombBlock(getPositionX(),getPositionY(), BlocksId.BombBlock, explosionBombPower, this, handler));
        }
    }
    //setter i getter
    public void setActualNumberOfBombs(int actualNumberOfBombs) {
        this.actualNumberOfBombs = actualNumberOfBombs;
    }
    public int getActualNumberOfBombs(){
        return this.actualNumberOfBombs;
    }

}
