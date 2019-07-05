package com.wasyl.NewGame.aStar;

import java.util.Objects;

public class Node {

    //kilka ważnych zmiennych na podstawie, których można wykonać a*
    //koordynaty x i y
    //g - score: wartość ścieżki od node'a start do celu
    //h - score: wartość ścieżki od aktualnego node'a do celu
    //f - score: suma g - cost i h - cost
    //parentNode - node, od którego najszybciej można do niego dojść
    //obstractionNode - czy dany node jest ścianą
    private int x;
    private int y;
    private double gScore;
    private double hScore;
    private double fScore;
    private Node parentNode;
    private boolean obstraction;

    //konstruktor, te trzy zmienne definiują każdego node'a
    public Node(int x, int y, boolean obstraction) {
        this.x = x;
        this.y = y;
        this.obstraction = obstraction;
    }

    //gettery i settery
    double getgScore() {
        return gScore;
    }

    void setgScore(double gScore) {
        this.gScore = gScore;
    }

    double gethScore() {
        return hScore;
    }

    void sethScore(double hScore) {
        this.hScore = hScore;
    }

    double getfScore() {
        return fScore;
    }

    void setfScore(double fScore) {
        this.fScore = fScore;
    }

    Node getParentNode() {
        return parentNode;
    }

    void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;

        boolean xxVyy = getX() == node.getX() && getY() == node.getY();
        boolean yyVxx = getY() == node.getX() && getX() == node.getY();

        return xxVyy || yyVxx;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    boolean isObstraction() {
        return obstraction;
    }
}