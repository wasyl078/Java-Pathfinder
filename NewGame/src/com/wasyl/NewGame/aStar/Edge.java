package com.wasyl.NewGame.aStar;

import java.util.Objects;

public class Edge {

    private Node node1;
    private Node node2;

    public Edge(Node node1, Node node2) {

        if(node1.getX() + node1.getY() > node2.getX() + node2.getY()){
            this.node1 = node1;
            this.node2 = node2;
        }else{
            this.node1 = node2;
            this.node2 = node1;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;

        return (Objects.equals(getNode1(), edge.getNode1()) &&
                Objects.equals(getNode2(), edge.getNode2()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNode1(), getNode2());
    }

    public Node getNode1() {
        return node1;
    }

    public Node getNode2() {
        return node2;
    }

}
