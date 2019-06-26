package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlayerBlock extends AbstractBlock {

    //referencja na klasę gry - żeby mieć wgląd w ostatnią akcję gracza
    private Game game;
    private int lastAction;

    public PlayerBlock(int positionX, int positionY, BlocksId blocksId, Color color, Game game) {
        super(positionX, positionY, blocksId, color);
        this.game = game;
    }

    //tutaj - aktualizowanie pozycji gracza
    @Override
    public void update() {
        lastAction = game.getPlayerLastAction();
        System.out.println(lastAction);
        switch (lastAction){
            case 1:{
                setPositionY(getPositionY() - 5);
                break;
            }
            case 2:{
                setPositionX(getPositionX() + 5);
                break;
            }
            case 3:{
                setPositionY(getPositionY() + 5);
                break;
            }
            case 4:{
                setPositionX(getPositionX() - 5);
                break;
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(getColor());
        gc.fillRect(getPositionX(),getPositionY(),25,25);
    }
}
