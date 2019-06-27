package com.wasyl.NewGame.graph;

import java.util.Objects;

public class Vertex {
    private int number;
    private String ID;

    public Vertex(int number) {
        if(number<0)throw new WrongVertexNumberException(number);
        this.number = number;
        this.ID = "NULLLL";
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getNumber() {
        return number;
    }

    public String getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return getNumber() == vertex.getNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber());
    }

    public class WrongVertexNumberException extends RuntimeException {
        public WrongVertexNumberException(int number) {
            super("There cannot be vector with number smaller than 0: " + number + "<0!");
        }
    }
}
