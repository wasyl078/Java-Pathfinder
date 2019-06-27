package com.wasyl.NewGame.graph;

import java.util.Objects;

public class Edge {

    private Vertex vertex1;
    private Vertex vertex2;
    private int wage;
    private final static int DEFAULT_WAGE = 0;

    public Edge(Vertex vertex1, Vertex vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        wage = DEFAULT_WAGE;
    }

    public Edge(Vertex vertex1, Vertex vertex2, int wage) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.wage = wage;
    }

    public Vertex getVertex1() {
        return vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }

    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        //sprawdzenie czy v1 == v1 i v2 == v2
        boolean v11 = getVertex1().equals(edge.getVertex1()) && getVertex2().equals(edge.getVertex2());
        boolean v22 = getVertex1().equals(edge.getVertex2()) && getVertex2().equals(edge.getVertex1());
        //zwrócenie wyniku
        return v11 || v22;
    }

    @Override
    public int hashCode() {

        if (getVertex1().getNumber() < getVertex2().getNumber())
            return Objects.hash(getVertex1(), getVertex2());
        else return Objects.hash(getVertex2(), getVertex1());
    }

    @Override
    public String toString() {

        if (getVertex1().getNumber() < getVertex2().getNumber())
            return getVertex1().getNumber() + "<[" + getWage() + "]>" + getVertex2().getNumber();
        else
            return getVertex2().getNumber() + "<[" + getWage() + "]>" + getVertex1().getNumber();
    }
}
