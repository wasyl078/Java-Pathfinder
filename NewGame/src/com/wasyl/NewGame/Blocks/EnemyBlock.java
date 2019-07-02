package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Handler;
import com.wasyl.NewGame.aStar.aStarNode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

//blok przeciwnika - wyszukuję najkrótszą ścieżkę do gracza
public class EnemyBlock extends AbstractBlock {

    //posiada instancję samego gracza oraz kilka liczników
    private PlayerBlock player;
    private int counter = 0;
    private int refresh = 0;
    private Handler handler;
    private ArrayList<AbstractBlock> path;

    //konstruktor
    public EnemyBlock(int positionX, int positionY, BlocksId blocksId, Handler handler) {
        super(positionX, positionY, blocksId, handler);
        setColor(Color.RED);
        this.handler = handler;
        path = new ArrayList<>();
    }

    //nadpisanie update() - aktualizacja najkrótszej ścieżki do gracza - path
    @Override
    public void update(ArrayList<AbstractBlock> objects) {
        counter++;
        refresh++;
        if (counter >= 10 && path.size() > 0) {
            setPositionX(path.get(path.size() - 1).getPositionX());
            setPositionY(path.get(path.size() - 1).getPositionY());
            path.remove(path.size() - 1);
            counter = 0;
        }
        if (refresh >= 10) {
            calculateSSTF();
            refresh = 0;
        }
    }

    //malowanie bloku przeciwnika oraz bloków ścieżki
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLUE);

        ArrayList<AbstractBlock> blocksToDelete = new ArrayList<>();
        for (AbstractBlock ab : path) {
            gc.fillOval(ab.getPositionX() * DEFAULT_X + 5, ab.getPositionY() * DEFAULT_Y + 5, AbstractBlock.SIDE_OF_BLOCK / 5, AbstractBlock.SIDE_OF_BLOCK / 5);
            if (ab.isToDelete())
                blocksToDelete.add(ab);
        }

        path.removeAll(blocksToDelete);

        gc.setFill(getColor());
        gc.fillRect(getPositionX() * DEFAULT_X, getPositionY() * DEFAULT_Y, AbstractBlock.SIDE_OF_BLOCK, AbstractBlock.SIDE_OF_BLOCK);
    }

    //wywołanie obliczenia najkrótszej ścieżki od bloku do gracza
    public void calculateSSTF() {
        ArrayList<aStarNode> nodesPath = handler.getStarGraph().generateAStarPath(this.getNode(), player.getNode());
        this.path.clear();
        for (aStarNode node : nodesPath)
            path.add(new PathBlock(node.getX(), 35 - node.getY(), BlocksId.PathBlock, handler));
    }

    //setter gracza
    public void setPlayer() {
        for (AbstractBlock ab : getBlocks())
            if (ab.getBlocksId() == BlocksId.PlayerBlock)
                this.player = (PlayerBlock) ab;
    }
}
