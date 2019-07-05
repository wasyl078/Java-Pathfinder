package com.wasyl.NewGame.Blocks;

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
    public EnemyBlock(int positionX, int positionY,int red, int green, int blue,  BlocksId blocksId, ArrayList<AbstractBlock> objects,ArrayList<AbstractBlock> additionalObjects,AbstractBlock[][] blocksMatrix) {
        super(positionX, positionY, red, green, blue, 100, blocksId, objects, additionalObjects,blocksMatrix);
        path = new ArrayList<>();
        //przeciwnicy mają zwykle 100 punktów zdrowia
    }

    //nadpisanie update() - aktualizacja najkrótszej ścieżki do gracza - path
    @Override
    public void update() {
        counter++;
        refresh++;

        //aktualizowanie najkrótszej ścieżki
        if (refresh >= 10) {
            calculateSSTF();
            refresh = 0;
        }

        //aktualizowanie pozycji przeciwnika na podstawie najkrótszej ścieżki
        if (counter >= 10 && path.size() > 0) {
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
        gc.setFill(Color.rgb(getRed(),getGreen(),getBlue(),getAlpha()));
        gc.fillRect(getPositionX() * DEFAULT_X, getPositionY() * DEFAULT_Y, X_SIDE_OF_BLOCK, Y_SIDE_OF_BLOCK);
    }

    //wywołanie obliczenia najkrótszej ścieżki od bloku do gracza
    private void calculateSSTF() {
        ArrayList<Node> nodesPath = starGraph.generateAStarPath(this.getNode(), player.getNode());
        this.path.clear();
        for (Node node : nodesPath)
            path.add(new PathBlock(node.getX(), 35 - node.getY(), 0,0,255,BlocksId.PathBlock, getObjects(),getAdditionalObjects(),getBlocksMatrix()));
    }

    //setter gracza
    public void setPlayer() {
        for (AbstractBlock ab : getObjects())
            if (ab.getBlocksId() == BlocksId.PlayerBlock)
                this.player = (PlayerBlock) ab;
    }

    //setter Graph'u
    public void setaAstarGraph(Graph graph){
        this.starGraph = graph;
    }
}
