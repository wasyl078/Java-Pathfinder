package com.wasyl.NewGame.Framework;

import com.wasyl.NewGame.Blocks.AbstractBlock;
import com.wasyl.NewGame.Blocks.BackgroundBlock;
import com.wasyl.NewGame.Blocks.BlocksId;
import com.wasyl.NewGame.aStar.Graph;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Iterator;

//klasa Handler służy do wywoływania aktualizowania wszystkich bloków
public class Handler {

    //lista wszystkich obiektów,dodatkowych obiektów, które trzeba aktualizować oraz macierz planszy
    private ArrayList<AbstractBlock> objects;
    private ArrayList<AbstractBlock> additionalObjects;
    private AbstractBlock[][] blocksMatrix;

    //graf mapy
    private Graph starGraph;

    //konstruktor, który tworzy pustą listę bloków
    Handler() {
        blocksMatrix = new AbstractBlock[Game.HORIZONTAL_NUMBER_OF_BLOCKS][Game.VERTICAL_NUMBER_OF_BLOCKS];
        objects = new ArrayList<>();
        additionalObjects = new ArrayList<>();
    }

    //aktualizowanie wszystkich obiektów
    void update() {

        //zmienna removed służy do sygnalizowania czy została jakaś zmiana przeprowadzona w rozkładzie planszy
        // jeśli tak to będzie aktualizowany starGraph
        boolean removed = false;

        Iterator iter = objects.iterator();
        while (iter.hasNext()) {
            AbstractBlock ab = (AbstractBlock) iter.next();
            ab.update();
            ab.updateNode();
            //sprawdza za każdym razem czy dany objekt ma wystarczającą liczbę punktów życia
            if (ab.getHealthPoints() < 0) {
                removed = true;
                iter.remove();
            }
        }
        //w przypadku gdy zaszła jakaś zmiana
        if (removed)
            starGraph.createNodesMatrix(this);

        //wystarczy aktualizować tylko ruchome elementy
        // plansza nie musi być aktualizowana

        //w przypadku gdyby były jakieś dodatkowe obiekty (np. smuga)
        Iterator addIter = additionalObjects.iterator();
        while (addIter.hasNext()) {
            AbstractBlock ab = (AbstractBlock) addIter.next();
            ab.update();
            //sprawdza za każdym razem czy dany objekt ma wystarczającą liczbę punktów życia
            if (ab.getHealthPoints() < 0)
                addIter.remove();
        }
    }

    //rysowanie wszystkich obiektów i całej planszy
    public void draw(GraphicsContext gc) {
        //plansza
        for (int x = 0; x < Game.HORIZONTAL_NUMBER_OF_BLOCKS; x++)
            for (int y = 0; y < Game.VERTICAL_NUMBER_OF_BLOCKS; y++) {
                AbstractBlock currentBlock = blocksMatrix[x][y];
                //sprawdza za każdym razem czy dany blok planszy ma wystarczjąco dużo hp żeby dalej istnieć
                // jeżeli ściana będzie miała 0 lub mniej HP to zamieni się w BackgrounBlock'a
                if (currentBlock.getHealthPoints() < 0)
                    blocksMatrix[x][y] = new BackgroundBlock(currentBlock.getPositionX(), currentBlock.getPositionY(), 0, 0, 0, BlocksId.BackgroundBlock);
                currentBlock.draw(gc);
            }

        //obiekty ruchome
        for (AbstractBlock ab : objects)
            ab.draw(gc);

        //dodatkowe obiekty
        for (AbstractBlock ab : additionalObjects)
            ab.draw(gc);
    }

    //metoda służąca wywołuniu tworzenia aStarGraphu
    void makeaAstarGraph() {
        //utworzenie Graph'u na podstawie tej planszy
        this.starGraph = new Graph(this);
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
    void removeElement(AbstractBlock ab) {
        //jeżeli usuwany element jest część planszy
        if (ab.getBlocksId() == BlocksId.BackgroundBlock || ab.getBlocksId() == BlocksId.WallBlock)
            blocksMatrix[ab.getNode().getX()][ab.getNode().getY()] = null;
            //jeżeli jest jakimś ruchomym elementem
        else objects.remove(ab);
    }

    //gettery
    public ArrayList<AbstractBlock> getObjectsList() {
        return this.objects;
    }

    public AbstractBlock[][] getBlocksMatrix() {
        return this.blocksMatrix;
    }

    Graph getStarGraph() {
        return starGraph;
    }

    public ArrayList<AbstractBlock> getAdditionalObjects() {
        return additionalObjects;
    }
}
