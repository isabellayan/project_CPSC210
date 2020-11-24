package model;

import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.util.Random;

// Represents a pea that the snake can consume
public class Pea extends Node implements Writable {

    public static final Color COLOR = new Color(255, 198, 145);

    // EFFECTS: create a pea with random x and y of the top left corner that is smaller than WIDTH and HEIGHT -
    //          WIDTH and HEIGHT of the pea
    public Pea() {
        Random randomX = new Random();
        Random randomY = new Random();
        nodeX = randomX.nextInt(SnakeGame.WIDTH - Node.WIDTH + 1);
        nodeY = randomY.nextInt(SnakeGame.HEIGHT - Node.HEIGHT + 1);
    }

    // EFFECTS: create a pea with x and y coordinates of the top left corner
    public Pea(int x, int y) {
        nodeX = x;
        nodeY = y;
    }

    // MODIFIES: this
    // EFFECTS: change the x and y coordinate of the pea so it appears in a new random position
    public void newPea() {
        Random randomX = new Random();
        Random randomY = new Random();
        nodeX = randomX.nextInt(SnakeGame.WIDTH - Node.WIDTH + 1);
        nodeY = randomY.nextInt(SnakeGame.HEIGHT - Node.HEIGHT + 1);
    }

    // MODIFIES: this
    // EFFECTS: change the x and y coordinate of the pea to the ones assigned
    public void newPea(int x, int y) {
        nodeX = x;
        nodeY = y;
    }

    // From the JsonSerializationDemo project
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("peaX", nodeX);
        json.put("peaY", nodeY);
        return json;
    }
}
