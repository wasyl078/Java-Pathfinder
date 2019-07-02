package com.wasyl.NewGame.aStar;

public class NoPathFoundException extends RuntimeException{
    public NoPathFoundException(aStarNode start, aStarNode end){
        super("Impossible to find path from " + start.getX() + "x" +start.getY() +" to " +end.getX() + "x" + end.getY());
    }
}
