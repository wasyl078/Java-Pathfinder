package com.wasyl.NewGame.graph;

import java.util.List;

public interface GraphAlgorithmsInterface {

    //algorytm Kruskala - wyszukiwanie minimalnego drzewa rozpinającego
    AbstractGraph kruskalsAlgorithm();

    //algorytm Prima - wyszukiwanie minimalnego drzewa rozpinającego
    AbstractGraph primsAlgorithm();

    //algorytm Boruvki - wyszukiwanie minimalnego drzewa rozpinającego
    AbstractGraph boruvkasAlgorithm();

    //algorytm Dijkstry - znajdowanie najkrótszej ściezki z pojedynczego źródła
    List<Vertex> dijkstrasAlgorithm(Vertex source, Vertex end);

    //algorytm Bellmana-Forda - znajdowanie najkrótszej ściezki z pojedynczego źródła
    List<Vertex> bellmanFordsAlgorithm(Vertex source, Vertex end);

    //algorytm A* - znajdowanie najkrótszej ściezki z pojedynczego źródła
    List<Vertex> aStarAlgorithm(Vertex source, Vertex end);
}
