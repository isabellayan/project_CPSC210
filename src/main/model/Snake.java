package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a snake with unlimited number of bodies
public class Snake implements Writable {

    private List<Body> snakeBody;
    private int currentScore;

    // EFFECTS: create a new snake with one body at the origin facing east
    public Snake() {
        snakeBody = new ArrayList<>();
        Body body = new Body(Body.EAST, SnakeGame.WIDTH / 2, SnakeGame.HEIGHT / 2);
        snakeBody.add(body);
    }

    // getters
    public List<Body> getSnakeBody() {
        return snakeBody;
    }

    public Integer getCurrentScore() {
        return currentScore;
    }

    // setters
    public void setCurrentScore(int score) {
        currentScore = score;
    }

    // MODIFIES: this
    // EFFECTS: if the snake touches the pea, then a new body is added to the snake
    //          to the position that is just after the head
    //          A point is also added to current score
    public void consumePea(Pea pea) {
        Body head = snakeBody.get(0);
        if (headTouchesPea(pea)) {
            if (head.getDirection() == Body.EAST) {
                Body body = new Body(Body.EAST, head.getNodeX(), head.getNodeY());
                snakeBody.add(1, body);
            } else if (head.getDirection() == Body.WEST) {
                Body body = new Body(Body.WEST, head.getNodeX(), head.getNodeY());
                snakeBody.add(1, body);
            } else if (head.getDirection() == Body.NORTH) {
                Body body = new Body(Body.NORTH, head.getNodeX(), head.getNodeY());
                snakeBody.add(1, body);
            } else {
                Body body = new Body(Body.SOUTH, head.getNodeX(), head.getNodeY());
                snakeBody.add(1, body);
            }
            currentScore += 1;
        }
    }

    // EFFECTS: return true if the head of the snake touches the pea
    public boolean headTouchesPea(Node node) {
        Body head = snakeBody.get(0);
        if (head.getDirection() == Body.EAST) {
            return touchPeaEast(head, node);
        } else if (head.getDirection() == Body.WEST) {
            return touchPeaWest(head, node);
        } else if (head.getDirection() == Body.NORTH) {
            return touchPeaNorth(head, node);
        } else {
            return touchPeaSouth(head, node);
        }
    }

    // EFFECTS: produce true if the head touches the pea in east direction
    private boolean touchPeaEast(Body head, Node node) {
        return head.getNodeX() >= node.getNodeX() - Node.WIDTH - 1
                && head.getNodeY() >= node.getNodeY() - Node.HEIGHT + 1
                && head.getNodeY() <= node.getNodeY() + Node.HEIGHT - 1
                && head.getNodeX() <= node.getNodeX() + Node.WIDTH;
    }

    // EFFECTS: produce true if the head touches the pea in west direction
    private boolean touchPeaWest(Body head, Node node) {
        return head.getNodeX() <= node.getNodeX() + Node.WIDTH + 1
                && head.getNodeY() >= node.getNodeY() - Node.HEIGHT + 1
                && head.getNodeY() <= node.getNodeY() + Node.HEIGHT - 1
                && head.getNodeX() >= node.getNodeX() - Node.WIDTH;
    }

    // EFFECTS: produce true if the head touches the pea in north direction
    private boolean touchPeaNorth(Body head, Node node) {
        return head.getNodeY() >= node.getNodeY() - Node.HEIGHT - 1
                && head.getNodeX() >= node.getNodeX() - Node.WIDTH + 1
                && head.getNodeX() <= node.getNodeX() + Node.WIDTH - 1
                && head.getNodeY() <= node.getNodeY() + Node.HEIGHT;
    }

    // EFFECTS: produce true if the head touches the pea in south direction
    private boolean touchPeaSouth(Body head, Node node) {
        return head.getNodeY() <= node.getNodeY() + Node.HEIGHT + 1
                && head.getNodeX() >= node.getNodeX() - Node.WIDTH + 1
                && head.getNodeX() <= node.getNodeX() + Node.WIDTH - 1
                && head.getNodeY() >= node.getNodeY() - Node.HEIGHT;
    }

    // MODIFIES: this
    // EFFECTS: moves the x coordinate of each body by snakeX
    public void moveSnakeX(Integer snakeX) {
        for (Body body : snakeBody) {
            body.changeNodeX(body.getNodeX() + snakeX);
        }
    }

    // MODIFIES: this
    // EFFECTS: moves the y coordinate of each body by snakeY
    public void moveSnakeY(Integer snakeY) {
        for (Body body : snakeBody) {
            body.changeNodeY(body.getNodeY() + snakeY);
        }
    }

    // MODIFIES: this
    // EFFECTS: add a body onto the snake
    public void addBody(Body body) {
        snakeBody.add(body);
    }

    // MODIFIES: this
    // EFFECTS: remove the first body(head) from the snake
    public void removeFirstBody() {
        snakeBody.remove(0);
    }

    // From the JsonSerializationDemo project
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("score", currentScore);
        json.put("bodies", snakeBodyToJson());
        return json;
    }

    // EFFECTS: Converts the snake body into Json
    private JSONArray snakeBodyToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Body body : snakeBody) {
            jsonArray.put(body.toJson());
        }

        return jsonArray;
    }
}
