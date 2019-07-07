package com.wasyl.NewGame.Blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//to tylko blok dawnej ścieżki - ma różne kolory, zmienia swoją alphe z każdym update'm i umiera szybko
public class OldBlock extends AbstractBlock{

    private final double SELF_HARM_POINTS = 0.333;

    public OldBlock(int positionX, int positionY, int red, int green, int blue, BlocksId blocksId) {
        super(positionX, positionY, red, green, blue, 10, blocksId, null);
    }

    @Override
    public void update() {
        addHealthPoints(-SELF_HARM_POINTS);
        setAlpha(getHealthPoints()/getMaxHP());
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.rgb(getRed(),getGreen(),getBlue(),getAlpha()));
        gc.fillRect(getPositionX() * DEFAULT_X, getPositionY() * DEFAULT_Y, X_SIDE_OF_BLOCK, Y_SIDE_OF_BLOCK);
    }
}
