package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.SnakeConsoleApp;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {
    Snake snake;
    Pea pea;

    @BeforeEach
    public void setUp() {
        snake = new Snake();
        pea = new Pea(SnakeGame.WIDTH / 2, SnakeGame.HEIGHT / 2);
    }

    @Test
    public void testConstructor() {
        assertEquals(Body.EAST, snake.getSnakeBody().get(0).getDirection());
        assertEquals(SnakeGame.WIDTH / 2, snake.getSnakeBody().get(0).getNodeX());
        assertEquals(SnakeGame.HEIGHT / 2, snake.getSnakeBody().get(0).getNodeY());
    }

    @Test
    public void testHeadTouchesPeaEast() {
        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() - Node.WIDTH - 2);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY());
        assertFalse(snake.headTouchesPea(pea));

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() - Node.WIDTH - 1);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY());
        assertTrue(snake.headTouchesPea(pea));
    }

    @Test
    public void testHeadTouchesPeaWest() {
        snake.getSnakeBody().get(0).changeDirection(Body.WEST);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + Node.WIDTH + 2);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY());
        assertFalse(snake.headTouchesPea(pea));

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + Node.WIDTH);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY());
        assertTrue(snake.headTouchesPea(pea));
    }

    @Test
    public void testHeadTouchesPeaNorth() {
        snake.getSnakeBody().get(0).changeDirection(Body.NORTH);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX());
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() - Node.HEIGHT - 2);
        assertFalse(snake.headTouchesPea(pea));

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX());
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() - Node.HEIGHT);
        assertTrue(snake.headTouchesPea(pea));
    }

    @Test
    public void testHeadTouchesPeaSouth() {
        snake.getSnakeBody().get(0).changeDirection(Body.SOUTH);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX());
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() + Node.HEIGHT + 2);
        assertFalse(snake.headTouchesPea(pea));

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX());
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() + Node.HEIGHT);
        assertTrue(snake.headTouchesPea(pea));
    }

    @Test
    public void testConsumePeaNotTouchEast() {
        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() - Node.WIDTH - 2);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY());
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() - Node.WIDTH - 1);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() + Node.HEIGHT);
        snake.consumePea(pea);
        bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() - Node.WIDTH - 1);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() - Node.HEIGHT);
        snake.consumePea(pea);
        bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + Node.WIDTH + 1);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY());
        snake.consumePea(pea);
        bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());
    }

    @Test
    public void testConsumePeaTouchEastSameY() {
        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() - Node.WIDTH - 1);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY());
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(2, bodies.size());
        Body first = bodies.get(0);
        Body last = bodies.get(1);
        assertEquals(Body.EAST, last.getDirection());
        assertEquals(first.getNodeX(), last.getNodeX());
        assertEquals(first.getNodeY(), last.getNodeY());
    }

    @Test
    public void testConsumePeaTouchEastUpY() {
        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() - Node.WIDTH);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() + Node.HEIGHT - 1);
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(2, bodies.size());
        Body first = bodies.get(0);
        Body last = bodies.get(1);
        assertEquals(Body.EAST, last.getDirection());
        assertEquals(first.getNodeX(), last.getNodeX());
        assertEquals(first.getNodeY(), last.getNodeY());
    }

    @Test
    public void testConsumePeaTouchEastLowY() {
        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() - Node.WIDTH);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() - Node.HEIGHT + 1);
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(2, bodies.size());
        Body first = bodies.get(0);
        Body last = bodies.get(1);
        assertEquals(Body.EAST, last.getDirection());
        assertEquals(first.getNodeX(), last.getNodeX());
        assertEquals(first.getNodeY(), last.getNodeY());
    }

    @Test
    public void testConsumePeaNotTouchWest() {
        snake.getSnakeBody().get(0).changeDirection(Body.WEST);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + Node.WIDTH + 2);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY());
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + Node.WIDTH - 1);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() + Node.HEIGHT);
        snake.consumePea(pea);
        bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + Node.WIDTH - 1);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() - Node.HEIGHT);
        snake.consumePea(pea);
        bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() - Node.WIDTH - 1);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY());
        snake.consumePea(pea);
        bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());
    }

    @Test
    public void testConsumePeaTouchWestSameY() {
        snake.getSnakeBody().get(0).changeDirection(Body.WEST);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + Node.WIDTH);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY());
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(2, bodies.size());
        Body first = bodies.get(0);
        Body last = bodies.get(1);
        assertEquals(Body.WEST, last.getDirection());
        assertEquals(first.getNodeX(), last.getNodeX());
        assertEquals(first.getNodeY(), last.getNodeY());
    }

    @Test
    public void testConsumePeaTouchWestUpY() {
        snake.getSnakeBody().get(0).changeDirection(Body.WEST);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + Node.WIDTH);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() + Node.HEIGHT - 1);
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(2, bodies.size());
        Body first = bodies.get(0);
        Body last = bodies.get(1);
        assertEquals(Body.WEST, last.getDirection());
        assertEquals(first.getNodeX(), last.getNodeX());
        assertEquals(first.getNodeY(), last.getNodeY());
    }

    @Test
    public void testConsumePeaTouchWestLowY() {
        snake.getSnakeBody().get(0).changeDirection(Body.WEST);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + Node.WIDTH);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() - Node.HEIGHT + 1);
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(2, bodies.size());
        Body first = bodies.get(0);
        Body last = bodies.get(1);
        assertEquals(Body.WEST, last.getDirection());
        assertEquals(first.getNodeX(), last.getNodeX());
        assertEquals(first.getNodeY(), last.getNodeY());
    }

    @Test
    public void testConsumePeaNorthSouthSecondStatementFalse() {
        snake.getSnakeBody().get(0).changeDirection(Body.NORTH);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() - Node.WIDTH * 2);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() - Node.HEIGHT / 2);
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());

        snake.getSnakeBody().get(0).changeDirection(Body.SOUTH);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() - Node.WIDTH * 2);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() + Node.HEIGHT / 2);
        snake.consumePea(pea);
        bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());
    }

    @Test
    public void testConsumePeaNotTouchNorth() {
        snake.getSnakeBody().get(0).changeDirection(Body.NORTH);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX());
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() - Node.HEIGHT - 2);
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + Node.WIDTH);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() - Node.HEIGHT - 1);
        snake.consumePea(pea);
        bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + Node.WIDTH);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() - Node.HEIGHT - 1);
        snake.consumePea(pea);
        bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX());
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() + Node.HEIGHT + 1);
        snake.consumePea(pea);
        bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());
    }

    @Test
    public void testConsumePeaTouchNorthSameX() {
        snake.getSnakeBody().get(0).changeDirection(Body.NORTH);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX());
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() - Node.HEIGHT);
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(2, bodies.size());
        Body first = bodies.get(0);
        Body last = bodies.get(1);
        assertEquals(Body.NORTH, last.getDirection());
        assertEquals(first.getNodeX(), last.getNodeX());
        assertEquals(first.getNodeY(), last.getNodeY());
    }

    @Test
    public void testConsumePeaTouchNorthLeftX() {
        snake.getSnakeBody().get(0).changeDirection(Body.NORTH);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() - Node.WIDTH + 1);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() - Node.HEIGHT);
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(2, bodies.size());
        Body first = bodies.get(0);
        Body last = bodies.get(1);
        assertEquals(Body.NORTH, last.getDirection());
        assertEquals(first.getNodeX(), last.getNodeX());
        assertEquals(first.getNodeY(), last.getNodeY());
    }

    @Test
    public void testConsumePeaTouchNorthRightX() {
        snake.getSnakeBody().get(0).changeDirection(Body.NORTH);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + Node.WIDTH - 1);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() - Node.HEIGHT);
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(2, bodies.size());
        Body first = bodies.get(0);
        Body last = bodies.get(1);
        assertEquals(Body.NORTH, last.getDirection());
        assertEquals(first.getNodeX(), last.getNodeX());
        assertEquals(first.getNodeY(), last.getNodeY());
    }

    @Test
    public void testConsumePeaNotTouchSouth() {
        snake.getSnakeBody().get(0).changeDirection(Body.SOUTH);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX());
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() + Node.HEIGHT + 2);
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + Node.WIDTH);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() + Node.HEIGHT - 1);
        snake.consumePea(pea);
        bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + Node.WIDTH);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() + Node.HEIGHT - 1);
        snake.consumePea(pea);
        bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX());
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() - Node.HEIGHT - 1);
        snake.consumePea(pea);
        bodies = snake.getSnakeBody();
        assertEquals(1, bodies.size());
    }

    @Test
    public void testConsumePeaTouchSouthSameX() {
        snake.getSnakeBody().get(0).changeDirection(Body.SOUTH);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX());
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() + Node.HEIGHT);
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(2, bodies.size());
        Body first = bodies.get(0);
        Body last = bodies.get(1);
        assertEquals(Body.SOUTH, last.getDirection());
        assertEquals(first.getNodeX(), last.getNodeX());
        assertEquals(first.getNodeY(), last.getNodeY());
    }

    @Test
    public void testConsumePeaTouchSouthLeftX() {
        snake.getSnakeBody().get(0).changeDirection(Body.SOUTH);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() - Node.WIDTH + 1);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() + Node.HEIGHT);
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(2, bodies.size());
        Body first = bodies.get(0);
        Body last = bodies.get(1);
        assertEquals(Body.SOUTH, last.getDirection());
        assertEquals(first.getNodeX(), last.getNodeX());
        assertEquals(first.getNodeY(), last.getNodeY());
    }

    @Test
    public void testConsumePeaTouchSouthRightX() {
        snake.getSnakeBody().get(0).changeDirection(Body.SOUTH);

        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + Node.WIDTH - 1);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() + Node.HEIGHT);
        snake.consumePea(pea);
        List<Body> bodies = snake.getSnakeBody();
        assertEquals(2, bodies.size());
        Body first = bodies.get(0);
        Body last = bodies.get(1);
        assertEquals(Body.SOUTH, last.getDirection());
        assertEquals(first.getNodeX(), last.getNodeX());
        assertEquals(first.getNodeY(), last.getNodeY());
    }

    @Test
    public void testConsumePeaAddCurrentScore() {
        snake.getSnakeBody().get(0).changeDirection(Body.SOUTH);
        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + Node.WIDTH - 1);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() + Node.HEIGHT);
        snake.consumePea(pea);
        assertEquals(1, snake.getCurrentScore());
        snake.consumePea(pea);
        assertEquals(2, snake.getCurrentScore());
    }

    @Test
    public void testMoveSnakeXEast() {
        int originalX = snake.getSnakeBody().get(0).getNodeX();
        snake.moveSnakeX(SnakeConsoleApp.MOVE_BY);
        assertEquals(originalX + SnakeConsoleApp.MOVE_BY, snake.getSnakeBody().get(0).getNodeX());
    }

    @Test
    public void testMoveSnakeXWest() {
        int originalX = snake.getSnakeBody().get(0).getNodeX();
        snake.moveSnakeX(-SnakeConsoleApp.MOVE_BY);
        assertEquals(originalX - SnakeConsoleApp.MOVE_BY, snake.getSnakeBody().get(0).getNodeX());
    }

    @Test
    public void testMoveSnakeYNorth() {
        int originalY = snake.getSnakeBody().get(0).getNodeY();
        snake.moveSnakeY(SnakeConsoleApp.MOVE_BY);
        assertEquals(originalY + SnakeConsoleApp.MOVE_BY, snake.getSnakeBody().get(0).getNodeY());
    }

    @Test
    public void testMoveSnakeYSouth() {
        int originalY = snake.getSnakeBody().get(0).getNodeY();
        snake.moveSnakeY(-SnakeConsoleApp.MOVE_BY);
        assertEquals(originalY - SnakeConsoleApp.MOVE_BY, snake.getSnakeBody().get(0).getNodeY());
    }

    @Test
    public void testSetCurrentScore() {
        snake.setCurrentScore(5);
        assertEquals(5, snake.getCurrentScore());
    }

    @Test
    public void testAddBody() {
        Body body = new Body(1, 25, 75);
        snake.addBody(body);
        assertEquals(2, snake.getSnakeBody().size());
        assertEquals(1, snake.getSnakeBody().get(1).getDirection());
        assertEquals(25, snake.getSnakeBody().get(1).getNodeX());
        assertEquals(75, snake.getSnakeBody().get(1).getNodeY());
    }

    @Test
    public void testRemoveFirstBody() {
        Body body = new Body(1, 50, 75);
        snake.addBody(body);
        snake.removeFirstBody();
        assertEquals(1, snake.getSnakeBody().size());
        assertEquals(1, snake.getSnakeBody().get(0).getDirection());
        assertEquals(50, snake.getSnakeBody().get(0).getNodeX());
        assertEquals(75, snake.getSnakeBody().get(0).getNodeY());
    }
}
