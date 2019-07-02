package com.wasyl.NewGame.aStar;

import com.wasyl.NewGame.Blocks.AbstractBlock;
import com.wasyl.NewGame.Blocks.BlocksId;
import com.wasyl.NewGame.Framework.Game;
import com.wasyl.NewGame.Framework.Handler;

import java.util.ArrayList;
import java.util.HashSet;

public class aStarGraph {

    //hashSet'y oraz macierz nodów do obsługi a*
    //closedSet - node'y, który już zostały odwiedzone
    //openSet - node'u, które nie zostały odwiedzone, ale były widziane
    //nodes - macierz wszystkich dostępnych node'ów - niebędących ścianą
    private HashSet<aStarNode> closedSet;
    private HashSet<aStarNode> openSet;
    private aStarNode[][] nodes;

    //konstruktor wywołyjący metodę tworzącą macierz node'ów
    public aStarGraph(Handler handler) {
        nodes = new aStarNode[Game.HORIZONTAL_NUMBER_OF_BLOCKS][Game.VERTICAL_NUMBER_OF_BLOCKS];
        createNodesMatrix(handler);
        this.closedSet = new HashSet<>();
        this.openSet = new HashSet<>();
    }

    //stworzenie macierzy nodów o założonej w Game wielkości
    // jeżeli jest on ścianą, to nie tworzymy z niego node'a w tablicy
    private void createNodesMatrix(Handler handler) {
        for (AbstractBlock ab : handler.getBlocksList())
            if (ab.getBlocksId() == BlocksId.BackgroundBlock || ab.getBlocksId() == BlocksId.WallBlock) {
                int x = ab.getNode().getX();
                int y = ab.getNode().getY();
                if (!ab.getNode().isObstraction())
                    nodes[x][y] = ab.getNode();
            }
    }

    //metoda do wyszukiwania najkrótszej ścieżki a*
    public ArrayList<aStarNode> generateAStarPath(aStarNode start, aStarNode goal) {

        //blok gracza nie jest odpowiednim blokiem w tablicy
        // dlatego trzeba wziąć odpowiadający mu bloczek z tablicy
        goal = nodes[goal.getX()][goal.getY()];

        //wrzucamy do openSet'a sąsiadów startowego bloku
        // zawsze będzie się tylko bloki z openSet'a badać
        openSet.addAll(calculateAndAddNeighbourNodes(start));

        //ustawienie na 0 g - score'a
        start.setgScore(0);

        //dla node'a, który aktualnie znajduje się w openSet'cie, i który ma najmniejszy fScore
        while (!openSet.isEmpty()) {
            //wyszukanie tego node'a z najmniejszym fScore'm
            aStarNode x = getLowestFCostNodeFromOpenSet();

            //jeżeli okaże się, że jest to nasz cel to zwracamy wywołanie funkcji odczytania ścieżki
            if (x == goal)
                return reconstruct_path(goal);

            //usuwamy tego nide'a z nieodwiedzonych i awansujmey go na odwiedzonego node'a
            openSet.remove(x);
            closedSet.add(x);

            //lista aktualnie sąsiednich node'ów
            ArrayList<aStarNode> neighbourNodes = calculateAndAddNeighbourNodes(x);

            //dla każdego sąsiedniego node'a
            for (aStarNode y : neighbourNodes) {
                //jeżeli był już odwiedzony to nic się nie dzieje
                if (closedSet.contains(y)) continue;

                //w przeciwnym przypadku obliczamy dla niego gScore
                double tentative_g_score = x.getgScore() + distance(x, y);
                boolean tentative_is_better = false;

                //w zależności od jego stanu odwiedzenia
                if (!openSet.contains(y)) {
                    openSet.add(y);
                    y.sethScore(distance(y, goal));
                    tentative_is_better = true;
                } else if (tentative_g_score < y.getgScore()) {
                    tentative_is_better = true;
                }

                //jeżeli okaże się lepszym
                if (tentative_is_better) {
                    y.setParentNode(x);
                    y.setgScore(tentative_g_score);
                    y.setfScore(y.getgScore() + y.gethScore());
                }
            }
        }

        //w przypadku gdy ścieżka nie istnieje to rzucany jest błąd
        throw new NoPathFoundException(start, goal);
    }


    //metoda do obliczania dystansu między dwoma node'ami - wielokrotnego zastosowania
    private double distance(aStarNode node, aStarNode goal) {
        int x = node.getX() - goal.getX();
        int y = node.getY() - goal.getY();
        int zz = x * x + y * y;
        return Math.sqrt(zz);
    }

    //metoda do wyszukania node'a o najmniejszym fScore'ze w openSet'cie
    private aStarNode getLowestFCostNodeFromOpenSet() {
        aStarNode buf = null;
        double minFCost = Integer.MAX_VALUE;
        for (aStarNode node : openSet)
            if (node.getfScore() < minFCost) {
                minFCost = node.getfScore();
                buf = node;
            }
        return buf;
    }

    //metoda do rekonstruowania ścieżki od końca do początku
    private ArrayList<aStarNode> reconstruct_path(aStarNode node) {
        ArrayList<aStarNode> path = new ArrayList<>();
        //póki dany node ma swojego rodzica, póty tworzona jest z nich ścieżka
        while (node.getParentNode() != null) {
            path.add(node);
            node = node.getParentNode();
        }

        //zresetowanie setów
        openSet.clear();
        closedSet.clear();

        //zresetowanie wszystkich nodów
        for (aStarNode[] nArr : nodes)
            for (aStarNode n : nArr)
                if (n != null)
                    n.setParentNode(null);

        //zwróćenie ścieżki w postaci arrayListy node'ów
        return path;
    }

    //metoda do wyszukiwania sąsiadujących node'ów w tablicy node'ów
    // oraz do zwrócenia ich w postaci arrayListy
    private ArrayList<aStarNode> calculateAndAddNeighbourNodes(aStarNode node) {
        ArrayList<aStarNode> neighbours = new ArrayList<>();
        int x = node.getX();
        int y = node.getY();
        //góra lewo
        if (x > 0 && y < 35 && (nodes[x - 1][y + 1] != null))
            neighbours.add(nodes[x - 1][y + 1]);
        //góra
        if (y < 35 && (nodes[x][y + 1] != null))
            neighbours.add(nodes[x][y + 1]);
        //góra prawo
        if (x < 63 && y < 35 && (nodes[x + 1][y + 1] != null))
            neighbours.add(nodes[x + 1][y + 1]);
        //dól lewo
        if (x > 0 && y > 0 && (nodes[x - 1][y - 1] != null))
            neighbours.add(nodes[x - 1][y - 1]);
        //dól
        if (y > 0 && (nodes[x][y - 1] != null))
            neighbours.add(nodes[x][y - 1]);
        //dól prawo
        if (x < 63 && y > 0 && (nodes[x + 1][y - 1] != null))
            neighbours.add(nodes[x + 1][y - 1]);
        //lewo
        if (x > 0 && (nodes[x - 1][y] != null))
            neighbours.add(nodes[x - 1][y]);
        //prawo
        if (x < 63 && (nodes[x + 1][y] != null)) {
            neighbours.add(nodes[x + 1][y]);
        }
        return neighbours;
    }
}