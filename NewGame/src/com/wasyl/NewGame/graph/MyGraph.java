package com.wasyl.NewGame.graph;

import java.util.*;
import java.util.function.Supplier;

public class MyGraph extends AbstractGraph {

    //zmienne - sety elementó grafu oraz pare stałych
    private Set<Edge> setOfEdges;
    private Set<Vertex> setOfVertexs;
    private ArrayList<Vertex>[] arrOfNeighbours;
    private int maximumCapacity;
    private final static String GRAPH_TYPE = "Graph with list of neighbours";

    //konstruktor grafu - rzyjmuje maksymalną ilość wierchołków grafie
    public MyGraph(int maximumCapacity) {
        setOfEdges = new HashSet<>();
        setOfVertexs = new HashSet<>();
        this.maximumCapacity = maximumCapacity;
        arrOfNeighbours = new ArrayList[maximumCapacity];
        for (int i = 0; i < maximumCapacity; i++)
            arrOfNeighbours[i] = new ArrayList<>();
    }

    //szybkie dodawanie krawędzi bez wagi
    public Edge addEdge(int i1, int i2) {
        return addEdge(new Vertex(i1), new Vertex(i2));
    }

    //szybkie dodawanie krawędzi z wagą
    public boolean addEdge(int i1, int i2, int wage) {
        return addEdge(new Vertex(i1), new Vertex(i2), wage);
    }

    //dodanie krawędzi z defaultową wagą
    @Override
    public Edge addEdge(Vertex v1, Vertex v2) {
        //nowa krawędź będzie miała wagę 0
        Edge newEdge = new Edge(v1, v2);
        if (setOfEdges.contains(newEdge)) {
            for (Edge e : setOfEdges)
                if (e.equals(newEdge)) return e;
        }

        //mogę sobie tak o dodawać beztrosko bo set za mnie zajmnie się tym
        //czy już takie wierzchołki są czy ich jeszcze nie ma w grafie
        setOfEdges.add(newEdge);
        setOfVertexs.add(v1);
        setOfVertexs.add(v2);

        arrOfNeighbours[v1.getNumber()].add(v2);
        arrOfNeighbours[v2.getNumber()].add(v1);
        return newEdge;
    }

    //dodanie krawędzi z podaną wagą
    @Override
    public boolean addEdge(Vertex v1, Vertex v2, int wage) {
        Edge newEdge = new Edge(v1, v2, wage);
        if (setOfEdges.contains(newEdge)) return false;

        //mogę sobie tak o dodawać beztrosko bo set za mnie zajmnie się tym
        //czy już takie wierzchołki są czy ich jeszcze nie ma w grafie
        setOfEdges.add(newEdge);
        setOfVertexs.add(v1);
        setOfVertexs.add(v2);

        arrOfNeighbours[v1.getNumber()].add(v2);
        arrOfNeighbours[v2.getNumber()].add(v1);
        return true;
    }

    //dodanie wierzchołka
    @Override
    public Vertex addVertex(int vertexNumber) {
        Vertex newVertex = new Vertex(vertexNumber);

        //zwraca nulla jeśli istnieje już taki wierchołek
        if (setOfVertexs.contains(newVertex)) return null;
        //beztroskie dodawanie wierchołka do seta
        setOfVertexs.add(newVertex);
        return newVertex;
    }

    //sprawdza czy krawędź znajduje się w grafie
    @Override
    public boolean containsEdge(Edge e) {
        return setOfEdges.contains(e);
    }

    //sprawdza czy krawędź znajduje się w grafie
    @Override
    public boolean containsEdge(Vertex v1, Vertex v2) {
        Edge bufEdge = new Edge(v1, v2);
        return setOfEdges.contains(bufEdge);
    }

    //sprawdza czy wierchołek znajduje się w grafie
    @Override
    public boolean containsVertes(Vertex v) {
        return setOfVertexs.contains(v);
    }

    //zwraca ilość krawędzi kojarzonych z danym wierzchołkime
    @Override
    public int degreeOf(Vertex v) {
        int counter = 0;
        for (Edge e : setOfEdges)
            if (e.getVertex1().equals(v) || e.getVertex2().equals(v))
                counter++;

        return counter;
    }

    //zwraca set krawędzi
    @Override
    public Set<Edge> edgeSet() {
        return setOfEdges;
    }

    //zwraca set krawędzi kojarzonych z tym wierzchołkiem
    @Override
    public Set<Edge> edgesOf(Vertex v) {
        Set<Edge> edgesTouchingV = new HashSet<>();

        for (Edge e : setOfEdges)
            if (e.getVertex1().equals(v) || e.getVertex2().equals(v))
                edgesTouchingV.add(e);

        return edgesTouchingV;
    }

    //zwraca set wszystkich krawędzi łączących dane dwa wierzchołki - NIE IMPLEMENTUJEMY
    @Override
    public Set<Edge> getAllEdges(Vertex vertex1, Vertex vertex2) {
        throw new UnsupportedOperationException();
    }

    //zwraca krawędź pomiędzy danymi dwoma wierzchołkami
    @Override
    public Edge getEdge(Vertex vertex1, Vertex v2) {
        Edge edgeToFInd = new Edge(vertex1, v2);

        for (Edge e : setOfEdges)
            if (e.equals(edgeToFInd))
                return e;

        return null;
    }

    //zwraca początek krawędzi - NIE IMPLEMENTUJEMY
    @Override
    public Vertex getEdgeSource(Edge e) {
        throw new UnsupportedOperationException();
    }

    //zwraca Edge Supplier (?) - NIE IMPLEMENTUJEMY
    @Override
    public Supplier<Edge> getEdgeSupplier() {
        throw new UnsupportedOperationException();
    }

    //zwraca koniec krawędzi - NIE IMPLEMENTUJEMY
    @Override
    public Vertex getEdgeTarget(Edge e) {
        throw new UnsupportedOperationException();
    }

    //zwraca wagę danej krawaędzi
    @Override
    public int getEdgeWeight(Edge e) {
        for (Edge edge : setOfEdges)
            if (edge.equals(e)) return edge.getWage();
        throw new NoSuchEdgeExistsException(e);
    }

    //metoda pomocnicza do zwracania wagi danej krawaędzi
    public int getEdgeWeight(int v1, int v2) {
        if (v1 == v2) return 0;
        return getEdgeWeight(new Edge(new Vertex(v1), new Vertex(v2)));
    }

    //zwraca typ danego grafu
    @Override
    public String getType() {
        return GRAPH_TYPE;
    }

    //zwraca Vertex supplier (?) - NIE IMPLEMENTUJEMY
    @Override
    public Supplier<Vertex> getVertexSupplier() {
        throw new UnsupportedOperationException();
    }

    //zwraca krawędzie przychodzące - NIE IMPLEMENTUJEMY
    @Override
    public Set<Edge> incomingEdgesOf(Vertex v) {
        throw new UnsupportedOperationException();
    }

    //zwraca "in degree" (?) - NIE IMPLEMENTUJEMy
    @Override
    public int inDegreeOf(Vertex v) {
        throw new UnsupportedOperationException();
    }

    //zwraca "out degree" (?) - NIE IMPLEMENTUJEMy
    @Override
    public int outDegreeOf(Vertex v) {
        throw new UnsupportedOperationException();
    }

    //zwraca set krawędzi wychodzących z danego wierzchołka - NIE IMPLEMENTUJEMY
    @Override
    public Set<Edge> outgoingEdgesOf(Vertex v) {
        throw new UnsupportedOperationException();
    }

    //usuwa krawędzie znajdujące się w danym setcie
    @Override
    public boolean removeAllEdges(Set<Edge> edges) {
        boolean allRemoved = true;
        Edge removed;

        for (Edge e : edges) {
            removed = removeEdge(e);
            if (removed == null) allRemoved = false;
        }

        return allRemoved;
    }

    //usuwa krawędzie wszystkie krawędzie między danymi dwoma wierzchołkami - NIE IMPLEMENTUJEMY
    @Override
    public Set<Edge> removeAllEdges(Vertex vertex1, Vertex vertex2) {
        throw new UnsupportedOperationException();
    }

    //usuwa wierzchołki znajdujące się w danym setcie
    @Override
    public boolean removeAllVertices(Set<Vertex> vertices) {
        boolean allRemoved = true;
        boolean removed;

        for (Vertex v : vertices) {
            removed = removeVertex(v);
            if (!removed) allRemoved = false;
        }

        return allRemoved;
    }

    //usunięcie danej krawędzi
    @Override
    public Edge removeEdge(Edge e) {

        if (!setOfEdges.contains(e)){
            Edge newE = new Edge(e.getVertex2(), e.getVertex1());
            if(!setOfEdges.contains(newE))
                throw new NoSuchEdgeExistsException(e);
            else
                e = newE;
        }

        //usunięcie połączenie v1 z v2
        arrOfNeighbours[e.getVertex1().getNumber()].remove(e.getVertex2());
        //usunięcie połączenia v2 z v1
        arrOfNeighbours[e.getVertex2().getNumber()].remove(e.getVertex1());

        //usunięcie krawędzi z seta krawędzi
        for (Edge edge : setOfEdges)
            if (edge.equals(e)) {
                setOfEdges.remove(edge);
                return edge;
            }

        return null;
    }

    //usunięcie krawędzi łączącej dane dwa wierzchołki
    @Override
    public Edge removeEdge(Vertex vertex1, Vertex vertex2) {
        Edge edgeToRemove = new Edge(vertex1, vertex2);
        return removeEdge(edgeToRemove);
    }

    //usunięcie wierzchołka
    @Override
    public boolean removeVertex(Vertex v) {

        //sprawdzenie czy taki wierzchołek w ogóle istnieje
        if (!setOfVertexs.contains(v))
            throw new NoSuchVertexExistsException(v);

        //usunięcie wierchołka z tablicy sąsiedztwa
        arrOfNeighbours[v.getNumber()] = new ArrayList<>();

        for (ArrayList<Vertex> arr : arrOfNeighbours)
            arr.remove(v);

        //usunięcie wszelkich krawędzi związanych z tym wierzchołkiem
        removeAllEdges(edgesOf(v));

        return setOfVertexs.remove(v);
    }

    //ustalenie wagi danej krawędzi
    @Override
    public void setEdgeWeight(Edge e, int wage) {
        for (Edge edge : setOfEdges)
            if (e.equals(edge))
                edge.setWage(wage);
    }

    //ustawienie wagi danej krawędzi łączącej te dwa wierzchołki
    @Override
    public void setEdgeWeight(Vertex vertex1, Vertex vertex2, int wage) {
        Edge edgeToChangeWeight = new Edge(vertex1, vertex2);
        setEdgeWeight(edgeToChangeWeight, wage);
    }

    //zwraca set wszystkich wierzchołków
    @Override
    public Set<Vertex> vertexSet() {
        return setOfVertexs;
    }

    //metoda do wypisania wszystkich informacji o danym grafie
    @Override
    public void print() {
        //wypisanie wszystkich wierchołków
        System.out.println("Verticles: ");
        for (Vertex v : setOfVertexs)
            System.out.print(v.getNumber() + ", ");

        //wypisanie wszystkich krawędzi razem z wagami
        System.out.println("\nEdges: ");
        for (Edge e : setOfEdges)
            System.out.print(e.getVertex1().getNumber() + "<[" + e.getWage() + "]>" + e.getVertex2().getNumber() + ", ");

        //wypisanie tablicy sąsiedztwa
        System.out.println("\nArray of neighbours:");
        for (int i = 0; i < arrOfNeighbours.length; i++) {
            if (!arrOfNeighbours[i].isEmpty()){
                System.out.print("\nVertex: " + (i) + " -> ");
                for (Vertex v : arrOfNeighbours[i])
                    System.out.print(v.getNumber() + "-wg>" + getEdge(new Vertex(i), v).getWage() + ", ");
            }
        }
    }

    //pomocnicza metoda do nazywania wierchołków
    public void setVertexName(String name, Vertex v) {
        for (Vertex vertex : setOfVertexs)
            if (vertex.equals(v))
                vertex.setID(name);

        for (Edge e : setOfEdges) {
            if (e.getVertex1().equals(v))
                e.getVertex1().setID(name);
            if (e.getVertex2().equals(v))
                e.getVertex2().setID(name);

        }
    }

    //pomocnicza metoda do pomocniczej metody do nazywania wierzchołków
    public void setVertexName(String name, int v) {
        setVertexName(name, new Vertex(v));
    }

    //metoda do wypisania wszystkich informacji o danym grafie przy użyciu nazw wierzchołków
    public void printWithNames() {
        //wypisanie wszystkich wierchołków
        System.out.println("Verticles with names: ");
        for (Vertex v : setOfVertexs)
            System.out.print(v.getID() + ", ");

        //wypisanie wszystkich krawędzi razem z wagami
        System.out.println("\nEdges with names: ");
        for (Edge e : setOfEdges)
            System.out.print(e.getVertex1().getID() + "<[" + e.getWage() + "]>" + e.getVertex2().getID() + ", ");

        //wypisanie tablicy sąsiedztwa
        System.out.println("\nArray of neighbours with names: ");
        for (int i = 0; i < arrOfNeighbours.length; i++) {
            System.out.print("\nVertex: " + findVertex(i).getID() + " -> ");
            for (Vertex v : arrOfNeighbours[i])
                System.out.print(v.getID() + "-wg>" + getEdge(new Vertex(i), v).getWage() + ", ");
        }
    }

    //pomocnicza metoda do wyszukiwania wierzchołków
    public Vertex findVertex(int i) {
        for (Vertex v : setOfVertexs)
            if (v.getNumber() == i)
                return v;
        return null;
    }

    //algorytm Kruskala - wyszukiwanie minimalnego drzewa rozpinającego
    @Override
    public MyGraph kruskalsAlgorithm() {
        //pomocniczne
        ArrayList<Vertex> vertices = new ArrayList<>(vertexSet());

        //las L wierzchołków oryginalnego grafu
        ArrayList<MyGraph> L = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            MyGraph g = new MyGraph(vertexSet().size());
            g.addVertex(vertices.get(i).getNumber());
            L.add(g);
        }

        //zbiór S zawierający wszystkie krawędzie oryginalnego grafu
        ArrayList<Edge> S = new ArrayList<>(edgeSet());
        S.sort(Comparator.comparing(Edge::getWage));

        //Dopóki S nie jest pusty oraz L nie jest jeszcze drzewem rozpinającym
        while (!S.isEmpty() && L.size() > 1) {
            //wybranie i usunięcie z S jednej krawędzi o minimalnej wadze
            Edge smallestWageEdge = S.get(0);
            int minWage = smallestWageEdge.getWage();
            for (int i = 0; i < S.size(); i++) {
                if (S.get(i).getWage() < minWage) {
                    minWage = S.get(i).getWage();
                    smallestWageEdge = S.get(i);
                }
            }
            S.remove(smallestWageEdge);

            //jeżeli ta krawędź łączyła dwa różne drzewa to dodaj ją do lasu L
            //tak aby połączyła dwa odpowiadające drzewa w jedno
            MyGraph g1 = null, g2 = null;
            Vertex v1 = smallestWageEdge.getVertex1();
            Vertex v2 = smallestWageEdge.getVertex2();

            //szukanie drzewa z v1
            for (MyGraph g : L) {
                if (g.vertexSet().contains(v1)) {
                    g1 = g;
                    break;
                }
            }
            //szukanie drzewa z v2
            for (MyGraph g : L) {
                if (g.vertexSet().contains(v2)) {
                    g2 = g;
                    break;
                }
            }

            //łączymy drzewa
            if (g1 != null && g2 != null && g1 != g2) {
                L.remove(g2);
                for (Edge e : g2.edgeSet())
                    g1.addEdge(e.getVertex1(), e.getVertex2(), e.getWage());
                g1.addEdge(smallestWageEdge.getVertex1(), smallestWageEdge.getVertex2(), smallestWageEdge.getWage());
            }

            //w przeciwnym wypadku odrzucamy
        }

        return L.get(0);
    }

    //algorytm Prima - wyszukiwanie minimalnego drzewa rozpinającego
    @Override
    public MyGraph primsAlgorithm() {
        throw new UnsupportedOperationException();
    }

    //algorytm Boruvki - wyszukiwanie minimalnego drzewa rozpinającego - NIE IMPLEMENTUJEMY
    @Override
    public MyGraph boruvkasAlgorithm() {
        throw new UnsupportedOperationException();
    }

    //algorytm Dijkstry - znajdowanie najkrótszej ściezki z pojedynczego źródła
    @Override
    public ArrayList<Vertex> dijkstrasAlgorithm(Vertex source, Vertex end) {
        //sprawdzenie czy takie wierzchołki w ogóle istnieją
        if (findVertex(source.getNumber()) == null) throw new NoSuchVertexExistsException(source);
        if (findVertex(end.getNumber()) == null) throw new NoSuchVertexExistsException(end);

        //tworzymy mapy sprawdzonych i niesprawdzonych
        HashSet<Vertex> settled = new HashSet<>();
        HashSet<Vertex> unsettled = new HashSet<>();

        //tworzymy mapy odległości i poprzedników
        HashMap<Vertex, Integer> distance = new HashMap<>();
        HashMap<Vertex, Vertex> predecessors = new HashMap<>();

        //dystans z źródła do źródła to 0
        distance.put(source, 0);

        //dodajemy do nieustalonych
        unsettled.add(source);


        while (unsettled.size() > 0) {
            //szukamy takiego wierzchołka, żeby miał jak najmniejszy dystans
            Vertex buf = null;
            for (Vertex vertex : unsettled)
                if (buf == null) buf = vertex;
                else if (shortest(vertex, distance) < shortest(buf, distance))
                    buf = vertex;

            //dodanie do sprawdzonych v, usunięcie z niesprawdzonych v
            settled.add(buf);
            unsettled.remove(buf);

            //utworzenie listy sąsiadów
            ArrayList<Vertex> neighbours = new ArrayList<>();
            for (Edge e : edgesOf(buf))
                //znalezienie właściwego sąsiada z sąsiadującej krawędzi
                if (e.getVertex1() != buf && settled.contains(e.getVertex2())) neighbours.add(e.getVertex1());
                else if (e.getVertex2() != buf && settled.contains(e.getVertex1())) neighbours.add(e.getVertex2());

            //zaktualizowanie odległości
            for (Vertex vertex : neighbours) {
                if (shortest(vertex, distance) > shortest(buf, distance) + getEdgeWeight(buf.getNumber(), vertex.getNumber())) {
                    distance.put(vertex, shortest(buf, distance) + getEdgeWeight(buf.getNumber(), vertex.getNumber()));
                    predecessors.put(vertex, buf);
                    unsettled.add(vertex);
                }
            }
        }

        //wypisanie najkrótszej ścieżki:
        ArrayList<Vertex> reversePath = new ArrayList<>();
        ArrayList<Vertex> path = new ArrayList<>();

        if (predecessors.get(end) != null || end == source) {
            while (end != null) {
                reversePath.add(end);
                end = predecessors.get(end);
            }
        }

        for (int i = reversePath.size() - 1; i >= 0; i--)
            path.add(reversePath.get(i));

        //zwrócenie ściżeki od source'a do end'a
        return path;
    }

    //metoda pomocnicza do Dijkstry: do rozstrzygnięcia, czy ten dystans się nadaje
    private int shortest(Vertex v, HashMap<Vertex, Integer> distance) {
        return distance.get(v) == null ? Integer.MAX_VALUE : distance.get(v);
    }

    //algorytm Bellmana-Forda - znajdowanie najkrótszej ściezki z pojedynczego źródła - NIE IMPLEMENTUJEMY
    @Override
    public ArrayList<Vertex> bellmanFordsAlgorithm(Vertex source, Vertex end) {
        throw new UnsupportedOperationException();

    }

    //algorytm A* - znajdowanie najkrótszej ściezki z pojedynczego źródła - NIE IMPLEMENTUJEMY
    @Override
    public ArrayList<Vertex> aStarAlgorithm(Vertex source, Vertex end) {
        throw new UnsupportedOperationException();
    }

    //specjalne wyjątki dla tej implementacji interfejsu
    public class NoSuchEdgeExistsException extends RuntimeException {
        public NoSuchEdgeExistsException(Edge e) {
            super("Edge " + e.getVertex1().getNumber() + "<->" + e.getVertex2().getNumber() + " does not exists in this graph!");
        }
    }

    public class NoSuchVertexExistsException extends RuntimeException {
        public NoSuchVertexExistsException(Vertex v) {
            super("Vertex number " + v.getNumber() + " does not exists in this graph!");

        }
    }
}