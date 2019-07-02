package com.wasyl.NewGame.Framework;

import com.wasyl.NewGame.Blocks.AbstractBlock;
import com.wasyl.NewGame.Blocks.BlocksId;
import com.wasyl.NewGame.aStar.aStarGraph;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

//klasa Handler służy do wywoływania aktualizowania wszystkich bloków
public class Handler {

    //lista wszystkich obiektów, które trzeba aktualizować oraz macierz planszy
    private ArrayList<AbstractBlock> objects;
    private AbstractBlock[][] blocksMatrix;

    //graf mapy
    private aStarGraph starGraph;

    //konstruktor, który tworzy pustą listę bloków
    public Handler() {
        blocksMatrix = new AbstractBlock[64][36];
        objects = new ArrayList<>();
    }

    //aktualizowanie wszystkich obiektów
    void update() {
        for (AbstractBlock ab : objects) {
            ab.update(objects);
            ab.updateNode();
        }
        //wystarczy aktualizować tylko ruchome elementy
        // plansza nie musi być aktualizowana
    }

    //rysowanie wszystkich obiektów i całej planszy
    public void draw(GraphicsContext gc) {
        //plansza
        for (int x = 0; x < Game.HORIZONTAL_NUMBER_OF_BLOCKS; x++)
            for (int y = 0; y < Game.VERTICAL_NUMBER_OF_BLOCKS; y++)
                blocksMatrix[x][y].draw(gc);

        //obiekty ruchome
        for (AbstractBlock ab : objects)
            ab.draw(gc);
    }

    //dodawanie bloków lub ruchomych obiektów
    public void addElement(AbstractBlock ab) {
        //jeżeli dodawanym elementem jest część planszy
        if (ab.getBlocksId() == BlocksId.BackgroundBlock || ab.getBlocksId() == BlocksId.WallBlock)
            blocksMatrix[ab.getNode().getX()][ab.getNode().getY()] = ab;
            //jeżeli jest jakimś ruchomym elementem
        else objects.add(ab);
    }

    //usuwanie bloków lub ruchomych obiektów
    public void removeElement(AbstractBlock ab) {
        //jeżeli usuwany element jest część planszy
        if (ab.getBlocksId() == BlocksId.BackgroundBlock || ab.getBlocksId() == BlocksId.WallBlock)
            blocksMatrix[ab.getNode().getX()][ab.getNode().getY()] = null;
            //jeżeli jest jakimś ruchomym elementem
        else objects.remove(ab);
    }

    //getter do listy bloków
    public ArrayList<AbstractBlock> getObjectsList() {
        return this.objects;
    }

    public AbstractBlock[][] getBlocksMatrix(){
        return this.blocksMatrix;
    }

    public aStarGraph getStarGraph() {
        return starGraph;
    }

    public void setStarGraph(aStarGraph starGraph) {
        this.starGraph = starGraph;
    }
}
