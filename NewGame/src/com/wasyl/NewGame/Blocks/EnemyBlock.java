package com.wasyl.NewGame.Blocks;

import com.wasyl.NewGame.Framework.Game;
import com.wasyl.NewGame.Framework.Handler;
import com.wasyl.NewGame.aStar.Graph;
import com.wasyl.NewGame.aStar.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;

//blok przeciwnika - wyszukuję najkrótszą ścieżkę do gracza
public class EnemyBlock extends AbstractBlock {

    //posiada instancję samego gracza oraz kilka liczników
    private PlayerBlock player;
    private int counter = 0;
    private int refresh = 0;
    private Graph starGraph;
    private ArrayList<AbstractBlock> path;

    //konstruktor
    public EnemyBlock(int positionX, int positionY, int red, int green, int blue, BlocksId blocksId, Handler handler) {
        super(positionX, positionY, red, green, blue, 100, blocksId, handler);
        path = new ArrayList<>();
        //przeciwnicy mają zwykle 100 punktów zdrowia
    }

    //nadpisanie update() - aktualizacja najkrótszej ścieżki do gracza - path
    @Override
    public void update() {
        counter++;
        refresh++;

        //aktualizowanie najkrótszej ścieżki
        if (refresh >= 20) {
            calculateSSTF();
            refresh = 0;
        }

        //aktualizowanie pozycji przeciwnika na podstawie najkrótszej ścieżki
        if (counter >= 20 && path.size() > 0) {
            setPositionX(path.get(path.size() - 1).getPositionX());
            setPositionY(path.get(path.size() - 1).getPositionY());
            path.remove(path.size() - 1);
            counter = 0;
        }
    }

    //malowanie bloku przeciwnika oraz bloków ścieżki
    @Override
    public void draw(GraphicsContext gc) {

        //malowanie i obsługa stanu ścieżki
        Iterator iter = path.iterator();
        while (iter.hasNext()) {
            AbstractBlock ab = (AbstractBlock) iter.next();
            ab.draw(gc);
            if (ab.getHealthPoints() < 0)
                iter.remove();
        }

        //malowanie przeciwnika
        gc.setFill(Color.rgb(getRed(), getGreen(), getBlue(), getAlpha()));
        gc.fillRect(getPositionX() * DEFAULT_X, getPositionY() * DEFAULT_Y, X_SIDE_OF_BLOCK, Y_SIDE_OF_BLOCK);
    }

    //wywołanie obliczenia najkrótszej ścieżki od bloku do gracza
    private void calculateSSTF() {
        ArrayList<Node> nodesPath = starGraph.generateAStarPath(this.getNode(), player.getNode());
        this.path.clear();
        if (nodesPath != null)
            for (Node node : nodesPath)
                path.add(new PathBlock(node.getX(), Game.VERTICAL_NUMBER_OF_BLOCKS -1 - node.getY(), 0, 0, 255, BlocksId.PathBlock));
    }

    //setter gracza
    public void setPlayer() {
        for (AbstractBlock ab : getObjects())
            if (ab.getBlocksId() == BlocksId.PlayerBlock)
                this.player = (PlayerBlock) ab;
    }

    //setter Graph'u
    public void setaAstarGraph(Graph graph) {
        this.starGraph = graph;
    }
}
