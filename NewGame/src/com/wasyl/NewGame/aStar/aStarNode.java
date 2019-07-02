package com.wasyl.NewGame.aStar;

public class aStarNode {

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
    private aStarNode parentNode;
    private boolean obstraction;

    //konstruktor, te trzy zmienne definiują każdego node'a
    public aStarNode(int x, int y, boolean obstraction) {
        this.x = x;
        this.y = y;
        this.obstraction = obstraction;
    }

    //gettery i settery
    public double getgScore() {
        return gScore;
    }

    public void setgScore(double gScore) {
        this.gScore = gScore;
    }

    public double gethScore() {
        return hScore;
    }

    public void sethScore(double hScore) {
        this.hScore = hScore;
    }

    public double getfScore() {
        return fScore;
    }

    public void setfScore(double fScore) {
        this.fScore = fScore;
    }

    public aStarNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(aStarNode parentNode) {
        this.parentNode = parentNode;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isObstraction() {
        return obstraction;
    }
}