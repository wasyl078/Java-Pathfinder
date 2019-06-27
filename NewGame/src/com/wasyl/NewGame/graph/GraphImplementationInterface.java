package com.wasyl.NewGame.graph;

import java.util.Set;
import java.util.function.Supplier;

interface GraphImplementationInterface {

    //dodanie krawędzi z defaultową wagą
    Edge addEdge(Vertex v1, Vertex v2);

    //dodanie krawędzi z podaną wagą
    boolean addEdge(Vertex v1, Vertex v2, int wage);

    //dodanie wierzchołka
    Vertex addVertex(int vertexNumber);

    //sprawdza czy krawędź znajduje się w grafie
    boolean containsEdge(Edge e);

    //sprawdza czy krawędź znajduje się w grafie
    boolean containsEdge(Vertex v1, Vertex v2);

    //sprawdza czy wierchołek znajduje się w grafie
    boolean containsVertes(Vertex v);

    //zwraca ilość krawędzi kojarzonych z danym wierzchołkime
    int degreeOf(Vertex v);

    //zwraca set krawędzi
    Set<Edge>edgeSet();

    //zwraca set krawędzi kojarzonych z tym wierzchołkiem
    Set<Edge>edgesOf(Vertex v);

    //zwraca set wszystkich krawędzi łączących dane dwa wierzchołki
    Set<Edge> getAllEdges(Vertex vertex1, Vertex vertex2);

    //zwraca krawędź pomiędzy danymi dwoma wierzchołkami
    Edge getEdge(Vertex vertex1, Vertex v2);

    //zwraca początek krawędzi
    Vertex getEdgeSource(Edge e);

    //zwraca Edge Supplier (?)
    Supplier<Edge>getEdgeSupplier();

    //zwraca koniec krawędzi
    Vertex getEdgeTarget(Edge e);

    //zwraca wagę danej krawaędzi
    int getEdgeWeight(Edge e);

    //zwraca typ danego grafu
    String getType();

    //zwraca Vertex supplier (?)
    Supplier<Vertex>getVertexSupplier();

    //zwraca krawędzie przychodzące
    Set<Edge>incomingEdgesOf(Vertex v);

    //zwraca "in degree" (?)
    int inDegreeOf(Vertex v);

    //zwraca "out degree" (?)
    int outDegreeOf(Vertex v);

    //zwraca set krawędzi wychodzących z danego wierzchołka
    Set<Edge>outgoingEdgesOf(Vertex v);

    //usuwa krawędzie znajdujące się w danym setcie
    boolean removeAllEdges(Set<Edge>edges);

    //usuwa krawędzie wszystkie krawędzie między danymi dwoma wierzchołkami
    Set<Edge>removeAllEdges(Vertex vertex1, Vertex vertex2);

    //usuwa wierzchołki znajdujące się w danym setcie
    boolean removeAllVertices(Set<Vertex>vertices);

    //usunięcie danej krawędzi
    Edge removeEdge(Edge e);

    //usunięcie krawędzi łączącej dane dwa wierzchołki
    Edge removeEdge(Vertex vertex1, Vertex vertex2);

    //usunięcie wierzchołka
    boolean removeVertex(Vertex v);

    //ustalenie wagi danej krawędzi
    void setEdgeWeight(Edge e, int wage);

    //ustawienie wagi danej krawędzi łączącej te dwa wierzchołki
    void setEdgeWeight(Vertex vertex1, Vertex vertex2, int wage);

    //zwraca set wszystkich wierzchołków
    Set<Vertex> vertexSet();

    //metoda do wypisania wszystkich informacji o danym grafie
    void print();
}
