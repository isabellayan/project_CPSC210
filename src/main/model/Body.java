package model;

import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;

// Represents a node of a snake having a fixed size and a direction it's facing
public class Body extends Node implements Writable {

    public static final Color COLOR = new Color(210, 255, 105);
    public static final int EAST = 0;
    public static final int NORTH = 1;
    public static final int WEST = 2;
    public static final int SOUTH = 3;

    private int direction;

    // EFFECTS: create a body with width and height, x and y coordinates of the top left corner based on x and y,
    //          and a direction of NORTH, SOUTH, EAST, and WEST
    public Body(int direction, int x, int y) {
        this.direction = direction;
        nodeX = x;
        nodeY = y;
    }

    // getters
    public int getDirection() {
        return direction;
    }

    // MODIFIES: this
    // EFFECTS: changes the direction of the body
    public void changeDirection(int direction) {
        this.direction = direction;
    }

    // MODIFIES: this
    // EFFECTS: changes the x coordinate of the node
    public void changeNodeX(int x) {
        nodeX = x;
    }

    // MODIFIES: this
    // EFFECTS: changes the y coordinate of the node
    public void changeNodeY(int y) {
        nodeY = y;
    }

    // From the JsonSerializationDemo project
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("bodyX", nodeX);
        json.put("bodyY", nodeY);
        json.put("direction", direction);
        return json;
    }
}
