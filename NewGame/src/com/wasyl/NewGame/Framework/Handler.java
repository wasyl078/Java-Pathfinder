package com.wasyl.NewGame.Framework;

import com.wasyl.NewGame.Blocks.AbstractBlock;
import com.wasyl.NewGame.graph.MyGraph;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Handler {

    //lista wszystkich obiektów, które trzeba aktualizować
    private ArrayList<AbstractBlock> blocks;

    //graf mapy
    private MyGraph mapGraph;

    //konstruktor, który tworzy pustą listę bloków
    public Handler() {
        blocks = new ArrayList<>();
    }

    //aktualizowanie wszystkich obiektów
    public void update() {
        for (AbstractBlock block : blocks) {
            block.update(blocks);
            block.updateVertexNumber();
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
    }

    //getter do listy bloków
    public ArrayList<AbstractBlock> getBlocksList() {
        return this.blocks;
    }

    //getter i setter grafu mapy
    public MyGraph getMapGraph() {
        return mapGraph;
    }

    public void setMapGraph(MyGraph mapGraph) {
        this.mapGraph = mapGraph;
    }

    public void changeBlockColor(int x, int y) {
        AbstractBlock abToRecolor = null;
        for (AbstractBlock ab : blocks)
            if (ab.getPositionY() == y && ab.getPositionX() == x)
                abToRecolor = ab;
        if (abToRecolor != null)
            abToRecolor.setColor(Color.BLUE);
    }
}
