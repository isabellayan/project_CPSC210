package model;

// a node that has a width and height and coordinates x and y
public abstract class Node {
    public static int WIDTH = 15;
    public static int HEIGHT = 15;

    protected int nodeX;
    protected int nodeY;

    // getters
    public int getNodeX() {
        return nodeX;
    }

    public int getNodeY() {
        return nodeY;
    }

}
