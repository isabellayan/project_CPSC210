package model;

import exception.CannotMoveException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.event.KeyEvent;
import java.io.IOException;

// represents a snake game
public class SnakeGame {

    public static final int MOVE_BY = 15;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private Snake snake;
    private Pea pea;

    // EFFECTS: construct a new snake game with a snake a pea
    public SnakeGame() {
        snake = new Snake();
        pea = new Pea();
    }

    // EFFECTS: construct a new snake game with snake and pea
    public SnakeGame(Snake snake, Pea pea) {
        this.snake = snake;
        this.pea = pea;
    }

    // getters
    public Snake getSnake() {
        return snake;
    }

    public Body getHead() {
        return snake.getSnakeBody().get(0);
    }

    public Pea getPea() {
        return pea;
    }

    public int getSnakeLength() {
        return snake.getSnakeBody().size();
    }

    // MODIFIES: this
    // EFFECTS: change the direction of the snake (later will modify so that it changes by tick)
    public void changeDirection(int direction) {
        for (Body body : snake.getSnakeBody()) {
            body.changeDirection(direction);
        }
    }

    // EFFECTS: return true if head touches body or the walls
    public boolean gameOver() {
        return headTouchesBody() || headTouchesWalls();
    }

    // EFFECTS: return true if head touches body
    // Some ideas from https://stackoverflow.com/questions/47235090/java-snake-collision
    private boolean headTouchesBody() {
        Body head = snake.getSnakeBody().get(0);
        for (int i = 1; i < snake.getSnakeBody().size(); i++) {
            if (head.getNodeX() == snake.getSnakeBody().get(i).getNodeX()
                    && head.getNodeY() == snake.getSnakeBody().get(i).getNodeY()) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: return true if head touches walls
    private boolean headTouchesWalls() {
        Body head = snake.getSnakeBody().get(0);
        return head.getNodeX() < 0 || head.getNodeX() > WIDTH - Node.WIDTH
                || head.getNodeY() < 0 || head.getNodeY() > HEIGHT - Node.HEIGHT;
    }

    // EFFECTS: parse to the method in Snake class and return the current score
    public int returnCurrentScore() {
        return snake.getCurrentScore();
    }

    // MODIFIES: this
    // EFFECTS: parse to the method in Snake class and move X coordinate of snake by moveBy
    public void moveSnakeXCor(int moveBy) {
        snake.moveSnakeX(moveBy);
    }

    // MODIFIES: this
    // EFFECTS: parse to the method in Snake class and move Y coordinate of snake by moveBy
    public void moveSnakeYCor(int moveBy) {
        snake.moveSnakeY(moveBy);
    }

    // MODIFIES: this
    // EFFECTS: update the snake, if head touches pea then a new segment is added to the snake and
    //          a new pea is generated at random position
    //          if game is over, then the snake is cleared
    public void update() {
        snake.consumePea(pea);

        if (snake.headTouchesPea(pea)) {
            pea.newPea();
        }

        int prevX = getHead().nodeX;
        int prevY = getHead().nodeY;
        moveHead();
        moveBody(prevX, prevY);
    }

    // MODIFIES: this
    // EFFECTS: move head in the current direction
    private void moveHead() {
        Body head = getHead();
        if (head.getDirection() == Body.EAST) {
            head.changeNodeX(head.getNodeX() + MOVE_BY);
        } else if (head.getDirection() == Body.WEST) {
            head.changeNodeX(head.getNodeX() - MOVE_BY);
        } else if (head.getDirection() == Body.NORTH) {
            head.changeNodeY(head.getNodeY() - MOVE_BY);
        } else {
            head.changeNodeY(head.getNodeY() + MOVE_BY);
        }
    }

    // MODIFIES: this
    // EFFECTS: move body following the movement of the head
    private void moveBody(int headX, int headY) {
        int prevX = headX;
        int prevY = headY;
        for (int i = 1; i < getSnakeLength(); i++) {
            Body body = getSnake().getSnakeBody().get(i);
            int nextX = body.nodeX;
            int nextY = body.nodeY;
            body.changeNodeX(prevX);
            body.changeNodeY(prevY);
            prevX = nextX;
            prevY = nextY;
        }
    }

    // MODIFIES: this
    // EFFECTS:  moves the snake in up/down/left/right directions, and restart the game if game is over
    //           also exits the game when x is pressed
    //           if the second segment is at the place head is going to move next after the direction is changed
    //           then throw CannotMoveException
    // From SpaceInvaders: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
    public void keyPressed(int keyCode) throws CannotMoveException {
        snakeControl(keyCode);
    }

    // MODIFIES: this
    // EFFECTS: change the direction of the snake to up/down/left/right
    //          if the second segment is at the place head is going to move next after the direction is changed
    //          then throw CannotMoveException
    // From SpaceInvaders: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
    private void snakeControl(int keyCode) throws CannotMoveException {
        int headX = getHead().nodeX;
        int headY = getHead().nodeY;
        Body second;
        if (getSnakeLength() == 1) {
            second = getSnake().getSnakeBody().get(0);
        } else {
            second = getSnake().getSnakeBody().get(1);
        }
        if (keyCode == KeyEvent.VK_KP_LEFT || keyCode == KeyEvent.VK_LEFT) {
            if (headX - Node.WIDTH == second.getNodeX() && headY == second.getNodeY()) {
                throw new CannotMoveException();
            }
            changeDirection(Body.WEST);
        } else if (keyCode == KeyEvent.VK_KP_RIGHT || keyCode == KeyEvent.VK_RIGHT) {
            if (headX + Node.WIDTH == second.getNodeX() && headY == second.getNodeY()) {
                throw new CannotMoveException();
            }
            changeDirection(Body.EAST);
        } else {
            snakeControlUpDown(keyCode);
        }
    }

    // MODIFIES: this
    // EFFECTS: change the direction of the snake to up/down
    //          if the second segment is at the place head is going to move next after the direction is changed
    //          then throw CannotMoveException
    // From SpaceInvaders: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
    private void snakeControlUpDown(int keyCode) throws CannotMoveException {
        int headX = getHead().nodeX;
        int headY = getHead().nodeY;
        Body second;
        if (getSnakeLength() == 1) {
            second = getSnake().getSnakeBody().get(0);
        } else {
            second = getSnake().getSnakeBody().get(1);
        }
        if (keyCode == KeyEvent.VK_KP_UP || keyCode == KeyEvent.VK_UP) {
            if (headX == second.getNodeX() && headY - Node.HEIGHT == second.getNodeY()) {
                throw new CannotMoveException();
            }
            changeDirection(Body.NORTH);
        } else if (keyCode == KeyEvent.VK_KP_DOWN || keyCode == KeyEvent.VK_DOWN) {
            if (headX == second.getNodeX() && headY + Node.HEIGHT == second.getNodeY()) {
                throw new CannotMoveException();
            }
            changeDirection(Body.SOUTH);
        }
    }

    // EFFECTS: Call the writeSnakeAndPea method in JsonWriter
    public void writeSnakeAndPeaFromGame(JsonWriter jsonWriter) {
        jsonWriter.writeSnakeAndPea(snake, pea);
    }

    // MODIFIES: this
    // EFFECTS: call the readSnake and readPea functions and read the state of saved snake and pea from file
    //          if error occurs throw IOException
    public void readSnakeAndPea(JsonReader jsonReader) throws IOException {
        snake = jsonReader.readSnake();
        pea = jsonReader.readPea();
    }
}
