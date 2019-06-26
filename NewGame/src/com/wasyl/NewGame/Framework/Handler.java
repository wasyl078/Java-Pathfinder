package com.wasyl.NewGame.Framework;

import com.wasyl.NewGame.Blocks.AbstractBlock;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Handler {

    //lista wszystkich obiektów, które trzeba aktualizować
    private ArrayList<AbstractBlock> blocks = new ArrayList<>();

    //aktualizowanie wszystkich obiektów
    public void update(){
        for(AbstractBlock block:blocks)
            block.update();
    }

    //rysowanie wszystkich obiektów
    public void draw(GraphicsContext gc){
        gc.setFill(Color.BLACK);
        for(AbstractBlock block:blocks)
            block.draw(gc);
    }

    //dodawanie bloków
    public void addBlock(AbstractBlock ab){
        blocks.add(ab);
    }

    //usuwanie bloków
    public void removeBlock(AbstractBlock ab){
        blocks.remove(ab);
    }
}
