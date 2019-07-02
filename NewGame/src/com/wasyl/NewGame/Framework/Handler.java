package com.wasyl.NewGame.Framework;

import com.wasyl.NewGame.Blocks.AbstractBlock;
import com.wasyl.NewGame.Blocks.BlocksId;
import com.wasyl.NewGame.aStar.aStarGraph;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

//klasa Handler służy do wywoływania aktualizowania wszystkich bloków
public class Handler {

    //lista wszystkich obiektów, które trzeba aktualizować
    private ArrayList<AbstractBlock> blocks;
    private AbstractBlock[][] nodes;

    //graf mapy
    private aStarGraph starGraph;

    //konstruktor, który tworzy pustą listę bloków
    public Handler() {
        nodes = new AbstractBlock[64][36];
        blocks = new ArrayList<>();
    }

    //aktualizowanie wszystkich obiektów
    public void update() {
        ArrayList<AbstractBlock>blocksToDelete = new ArrayList<>();
        for (AbstractBlock block : blocks) {
            block.update(blocks);
            block.updateNode();
        }
    }

    //rysowanie wszystkich obiektów
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        for (AbstractBlock block : blocks)
            block.draw(gc);
    }

    //dodawanie bloków
    public void addBlock(AbstractBlock ab) {
        blocks.add(ab);

        if(ab.getBlocksId() == BlocksId.WallBlock || ab.getBlocksId() == BlocksId.BackgroundBlock)
            nodes[ab.getNode().getX()][ab.getNode().getY()] = ab;
    }

    //usuwanie bloków
    public void removeBlock(AbstractBlock ab) {
        blocks.remove(ab);
    }

    public void removeBlock(int x, int y) {
        AbstractBlock abToRemove = null;
        for (AbstractBlock ab : blocks)
            if (ab.getPositionY() == y && ab.getPositionX() == x)
                abToRemove = ab;
        if (abToRemove != null)
            blocks.remove(abToRemove);

        if(abToRemove.getBlocksId() == BlocksId.WallBlock || abToRemove.getBlocksId() == BlocksId.BackgroundBlock)
            nodes[abToRemove.getNode().getX()][abToRemove.getNode().getY()] = null;
    }

    //getter do listy bloków
    public ArrayList<AbstractBlock> getBlocksList() {
        return this.blocks;
    }

    public aStarGraph getStarGraph() {
        return starGraph;
    }

    public void setStarGraph(aStarGraph starGraph) {
        this.starGraph = starGraph;
    }

    public AbstractBlock[][] getNodes() {
        return nodes;
    }
}
