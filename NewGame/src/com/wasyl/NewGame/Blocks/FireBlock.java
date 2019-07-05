package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

//blok FireBlock to blok ataku - "ruchomy obiekt" jeżeli będzie znajdował się na jakimś
// innym ruchomym obiekcie lub na ścianie to zabierze mu / jej kilka punktów zdrowia
public class FireBlock extends AbstractBlock {

    //jedna zmienna do ustalenia ile punktów ataku ma mieć ten blok
    private static final int ATTACK_POWER = 1;

    //tablice definiujące kształt trójkąta
    private double[] xPoints;
    private double[] yPoints;
    private int nPoints;

    //konstruktor bloku ataku
    FireBlock(int positionX, int positionY, int red, int green, int blue, BlocksId blocksId, String direction, ArrayList<AbstractBlock> objects,ArrayList<AbstractBlock> additionalObjects, AbstractBlock[][] blocksMatrix) {
        super(positionX, positionY, red, green, blue, 10, blocksId, objects,additionalObjects, blocksMatrix);
        //10 punktów zdrowia - żeby po tym czasie zniknął
        makeTrianglePolygon(direction);
    }

    //draw() - tylko wyświetla odpowiedni kształt tego bloku
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.rgb(getRed(), getGreen(), getBlue(), getAlpha()));
        gc.fillPolygon(xPoints, yPoints, nPoints);
    }

    //update() w tym przypadku zabiera HP temu blokowi oraz blokowi / obiektowi na którym stoi
    @Override
    public void update() {
        //odjęcie HP samemu temu blokowi - żeby zniknął
        addHealthPoints(-ATTACK_POWER);

        //sprawdzenie innych ruchomych obiektów
        for (AbstractBlock ab : getObjects())
            if (ab.getPositionX() == getPositionX() && ab.getPositionY() == getPositionY())
                if (ab != this)
                    ab.addHealthPoints(-ATTACK_POWER);

        //sprawdzenie ścian
        for (int x = 0; x < Game.HORIZONTAL_NUMBER_OF_BLOCKS; x++)
            for (int y = 0; y < Game.VERTICAL_NUMBER_OF_BLOCKS; y++) {
                AbstractBlock currentBlock = getBlocksMatrix()[x][y];
                if (x == getNode().getX() && y == getNode().getY())
                    currentBlock.addHealthPoints(-ATTACK_POWER);
            }
    }

    //ta metoda ustawia punkty do skierowania trójkąta
    private void makeTrianglePolygon(String direction) {
        xPoints = new double[3];
        yPoints = new double[3];
        nPoints = 3;
        switch (direction) {
            case "UP": {
                xPoints[0] = getPositionX() * DEFAULT_X;
                yPoints[0] = getPositionY() * DEFAULT_Y + Y_SIDE_OF_BLOCK;
                xPoints[1] = getPositionX() * DEFAULT_X + X_SIDE_OF_BLOCK;
                yPoints[1] = getPositionY() * DEFAULT_Y + Y_SIDE_OF_BLOCK;
                xPoints[2] = getPositionX() * DEFAULT_X + X_SIDE_OF_BLOCK / 2;
                yPoints[2] = getPositionY() * DEFAULT_Y;
                break;
            }
            case "DOWN": {
                xPoints[0] = getPositionX() * DEFAULT_X;
                yPoints[0] = getPositionY() * DEFAULT_Y;
                xPoints[1] = getPositionX() * DEFAULT_X + X_SIDE_OF_BLOCK;
                yPoints[1] = getPositionY() * DEFAULT_Y;
                xPoints[2] = getPositionX() * DEFAULT_X + X_SIDE_OF_BLOCK / 2;
                yPoints[2] = getPositionY() * DEFAULT_Y + Y_SIDE_OF_BLOCK;
                break;
            }
            case "LEFT": {
                xPoints[0] = getPositionX() * DEFAULT_X + X_SIDE_OF_BLOCK;
                yPoints[0] = getPositionY() * DEFAULT_Y;
                xPoints[1] = getPositionX() * DEFAULT_X + X_SIDE_OF_BLOCK;
                yPoints[1] = getPositionY() * DEFAULT_Y + Y_SIDE_OF_BLOCK;
                xPoints[2] = getPositionX() * DEFAULT_X;
                yPoints[2] = getPositionY() * DEFAULT_Y + Y_SIDE_OF_BLOCK / 2;
                break;
            }
            case "RIGHT": {
                xPoints[0] = getPositionX() * DEFAULT_X;
                yPoints[0] = getPositionY() * DEFAULT_Y;
                xPoints[1] = getPositionX() * DEFAULT_X;
                yPoints[1] = getPositionY() * DEFAULT_Y + Y_SIDE_OF_BLOCK;
                xPoints[2] = getPositionX() * DEFAULT_X + X_SIDE_OF_BLOCK;
                yPoints[2] = getPositionY() * DEFAULT_Y + Y_SIDE_OF_BLOCK / 2;
                break;
            }
        }
    }
}