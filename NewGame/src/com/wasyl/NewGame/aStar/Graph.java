package com.wasyl.NewGame.aStar;

import com.wasyl.NewGame.Blocks.AbstractBlock;
import com.wasyl.NewGame.Framework.Game;
import com.wasyl.NewGame.Framework.Handler;

import java.util.ArrayList;
import java.util.HashSet;

public class Graph {

    //hashSet'y oraz macierz nodów do obsługi a*
    //closedSet - node'y, który już zostały odwiedzone
    //openSet - node'u, które nie zostały odwiedzone, ale były widziane
    //nodes - macierz wszystkich dostępnych node'ów - niebędących ścianą
    private HashSet<Node> closedSet;
    private HashSet<Node> openSet;
    private Node[][] nodes;

    //konstruktor wywołyjący metodę tworzącą macierz node'ów
    public Graph(Handler handler) {
        nodes = new Node[Game.HORIZONTAL_NUMBER_OF_BLOCKS][Game.VERTICAL_NUMBER_OF_BLOCKS];
        createNodesMatrix(handler);
        this.closedSet = new HashSet<>();
        this.openSet = new HashSet<>();
    }

    //stworzenie macierzy nodów o założonej w Game wielkości
    // jeżeli jest on ścianą, to nie tworzymy z niego node'a w tablicy
    // metoda ta jest również używana do aktualizowania tej macierzy
    public void createNodesMatrix(Handler handler) {
        for (int x = 0; x < Game.HORIZONTAL_NUMBER_OF_BLOCKS; x++)
            for (int y = 0; y < Game.VERTICAL_NUMBER_OF_BLOCKS; y++) {
                AbstractBlock ab = handler.getBlocksMatrix()[x][y];
                if (!ab.getNode().isObstraction())
                    nodes[x][y] = ab.getNode();
                else nodes[x][y] = null;
            }
    }

    //metoda do wyszukiwania najkrótszej ścieżki a*
    public ArrayList<Node> generateAStarPath(Node start, Node goal) {

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
            Node x = getLowestFCostNodeFromOpenSet();

            //jeżeli okaże się, że jest to nasz cel to zwracamy wywołanie funkcji odczytania ścieżki
            if (x == goal)
                return reconstruct_path(goal);

            //usuwamy tego nide'a z nieodwiedzonych i awansujmey go na odwiedzonego node'a
            openSet.remove(x);
            closedSet.add(x);

            //lista aktualnie sąsiednich node'ów
            ArrayList<Node> neighbourNodes = calculateAndAddNeighbourNodes(x);

            //dla każdego sąsiedniego node'a
            for (Node y : neighbourNodes) {
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
    private double distance(Node node, Node goal) {
        int x = node.getX() - goal.getX();
        int y = node.getY() - goal.getY();
        int zz = x * x + y * y;
        return Math.sqrt(zz);
    }

    //metoda do wyszukania node'a o najmniejszym fScore'ze w openSet'cie
    private Node getLowestFCostNodeFromOpenSet() {
        Node buf = null;
        double minFCost = Integer.MAX_VALUE;
        for (Node node : openSet)
            if (node.getfScore() < minFCost) {
                minFCost = node.getfScore();
                buf = node;
            }
        return buf;
    }

    //metoda do rekonstruowania ścieżki od końca do początku
    private ArrayList<Node> reconstruct_path(Node node) {
        ArrayList<Node> path = new ArrayList<>();
        //póki dany node ma swojego rodzica, póty tworzona jest z nich ścieżka
        while (node != null) {
            path.add(node);
            node = node.getParentNode();
        }

        //zresetowanie setów
        openSet.clear();
        closedSet.clear();

        //zresetowanie wszystkich nodów
        for (Node[] nArr : nodes)
            for (Node n : nArr)
                if (n != null)
                    n.setParentNode(null);

        //zwróćenie ścieżki w postaci arrayListy node'ów
        return path;
    }

    //metoda do wyszukiwania sąsiadujących node'ów w tablicy node'ów
    // oraz do zwrócenia ich w postaci arrayListy
    private ArrayList<Node> calculateAndAddNeighbourNodes(Node node) {
        ArrayList<Node> neighbours = new ArrayList<>();
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

    //kklasa wewnętrzna, który służy tylko jako wyjątek w przypadku gdy a* nie znajdzie ścieżki
    class NoPathFoundException extends RuntimeException {
        NoPathFoundException(Node start, Node end) {
            super("Impossible to find path from " + start.getX() + "x" + start.getY() + " to " + end.getX() + "x" + end.getY());
        }
    }

    //metoda do stworzenia losoweggo labiruntu przy pomocy algorytmu Prima
    public void generatePrimsMaze() {

        ArrayList<Node> maze = new ArrayList<>();
        ArrayList<Node> walls = new ArrayList<>();

        //stworzenie wierzchołków
        HashSet<Node> nodesSet = new HashSet<>();
        Node[][] nodes = new Node[Game.HORIZONTAL_NUMBER_OF_BLOCKS][Game.VERTICAL_NUMBER_OF_BLOCKS];
        for (int x = 0; x < Game.HORIZONTAL_NUMBER_OF_BLOCKS; x++)
            for (int y = 0; y < Game.VERTICAL_NUMBER_OF_BLOCKS; y++) {
                nodes[x][y] = new Node(x, y, false);
                nodesSet.add(nodes[x][y]);
            }

        //stworzenie krawędzi
        HashSet<Edge> edgesSet = new HashSet<>();
        for (int x = 0; x < Game.HORIZONTAL_NUMBER_OF_BLOCKS; x++) {
            for (int y = 0; y < Game.VERTICAL_NUMBER_OF_BLOCKS; y++) {

                ArrayList<Edge> edges = new ArrayList<>();
                //góra
                if (y < Game.VERTICAL_NUMBER_OF_BLOCKS - 1)
                    edges.add(new Edge(nodes[x][y], nodes[x][y + 1]));
                //dół
                if (y > 0)
                    edges.add(new Edge(nodes[x][y], nodes[x][y - 1]));
                //lewo
                if (x > 0)
                    edges.add(new Edge(nodes[x][y], nodes[x - 1][y]));
                //prawo
                if (x < Game.HORIZONTAL_NUMBER_OF_BLOCKS - 1)
                    edges.add(new Edge(nodes[x][y], nodes[x + 1][y]));
                edgesSet.addAll(edges);
            }
        }//TODO trochę trzeba pozmieniać sam graf i krawędzie

        //wybranie początkowego miejsca
        Node start = nodes[0][0];

        //zrobienie z niej krawędzi labiryntu
        maze.add(start);

        //dodanie jej ścianek do listy ścianek
        walls.addAll(neighboursOf(start, edgesSet));

        //dopóki są ścianki w liście ścianek
    }

    //metoda zwracająca sąsiadów node'a
    private ArrayList<Node> neighboursOf(Node node, HashSet<Edge> edges) {
        ArrayList<Node> neighbours = new ArrayList<>();
        for (Edge e : edges)
            if (e.getNode1().equals(node) || e.getNode2().equals(node))
                neighbours.add(node);
        return neighbours;
    }
}
