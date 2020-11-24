package model;

import exception.CannotMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.event.KeyEvent;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeGameTest {

    private SnakeGame game;
    private SnakeGame gameAssigned;

    @BeforeEach
    public void setUp() {
        game = new SnakeGame();
        gameAssigned = new SnakeGame(new Snake(), new Pea(50, 50));
    }

    @Test
    public void testConstructor() {
        assertTrue(game.getPea().nodeX <= SnakeGame.WIDTH - Node.WIDTH + 1);
        assertTrue(game.getPea().nodeY <= SnakeGame.HEIGHT - Node.HEIGHT + 1);
        assertEquals(1, game.getSnakeLength());
        assertEquals(SnakeGame.WIDTH / 2, game.getHead().getNodeX());
        assertEquals(SnakeGame.HEIGHT / 2, game.getHead().getNodeY());
        assertEquals(0, game.getHead().getDirection());
    }

    @Test
    public void testAssignConstructor() {
        assertEquals(1, gameAssigned.getSnakeLength());
        assertEquals(SnakeGame.WIDTH / 2, gameAssigned.getHead().getNodeX());
        assertEquals(SnakeGame.HEIGHT / 2, gameAssigned.getHead().getNodeY());
        assertEquals(0, gameAssigned.getHead().getDirection());

        assertEquals(50, gameAssigned.getPea().nodeX);
        assertEquals(50, gameAssigned.getPea().nodeY);
    }


    @Test
    public void testChangeDirection() {
        game.getSnake().addBody(new Body(0, SnakeGame.WIDTH / 2,
                SnakeGame.HEIGHT / 2 + Node.HEIGHT));
        game.changeDirection(1);
        assertEquals(1, game.getHead().getDirection());
        assertEquals(1, game.getSnake().getSnakeBody().get(1).getDirection());
    }

    @Test
    public void testGameOverHeadTouchesBody() {
        assertFalse(game.gameOver());
        game.getSnake().addBody(new Body(0, SnakeGame.WIDTH / 2,
                SnakeGame.HEIGHT / 2));
        assertTrue(game.gameOver());
    }

    @Test
    public void testGameOverHeadTouchesBodyMultipleBodySegments() {
        assertFalse(game.gameOver());
        game.getSnake().addBody(new Body(0, SnakeGame.WIDTH / 2 - Node.WIDTH,
                SnakeGame.HEIGHT / 2));
        game.getSnake().addBody(new Body(0, SnakeGame.WIDTH / 2 - Node.WIDTH * 2,
                SnakeGame.HEIGHT / 2));
        game.getSnake().addBody(new Body(0, SnakeGame.WIDTH / 2,
                SnakeGame.HEIGHT / 2));
        assertTrue(game.gameOver());
    }

    @Test
    public void testGameOverHeadTouchesBodySameXDifferentY() {
        assertFalse(game.gameOver());
        game.getSnake().addBody(new Body(0, SnakeGame.WIDTH / 2 - Node.WIDTH,
                SnakeGame.HEIGHT / 2));
        game.getSnake().addBody(new Body(0, SnakeGame.WIDTH / 2 - Node.WIDTH * 2,
                SnakeGame.HEIGHT / 2));
        game.getSnake().addBody(new Body(0, SnakeGame.WIDTH / 2,
                SnakeGame.HEIGHT / 2 + Node.HEIGHT));
        assertFalse(game.gameOver());
    }

    @Test
    public void testGameOverHeadTouchesWallsLeftWall() {
        game.moveSnakeXCor(-SnakeGame.WIDTH / 2);
        assertFalse(game.gameOver());
        game.moveSnakeXCor(-1);
        assertTrue(game.gameOver());
    }

    @Test
    public void testGameOverHeadTouchesWallsRightWall() {
        game.moveSnakeXCor(SnakeGame.WIDTH / 2 - Node.WIDTH);
        assertFalse(game.gameOver());
        game.moveSnakeXCor(1);
        assertTrue(game.gameOver());
    }

    @Test
    public void testGameOverHeadTouchesWallsUpperWall() {
        game.moveSnakeYCor(-SnakeGame.HEIGHT / 2);
        assertFalse(game.gameOver());
        game.moveSnakeYCor(-1);
        assertTrue(game.gameOver());
    }

    @Test
    public void testGameOverHeadTouchesWallsLowerWall() {
        game.moveSnakeYCor(SnakeGame.HEIGHT / 2 - Node.HEIGHT);
        assertFalse(game.gameOver());
        game.moveSnakeYCor(1);
        assertTrue(game.gameOver());
    }

    @Test
    public void testReturnCurrentScore() {
        assertEquals(0, game.returnCurrentScore());
    }

    @Test
    public void testMoveSnakeXCor() {
        game.moveSnakeXCor(5);
        assertEquals(SnakeGame.WIDTH / 2 + 5, game.getHead().getNodeX());
        assertEquals(SnakeGame.WIDTH / 2, game.getHead().getNodeY());
        assertEquals(0, game.getHead().getDirection());
    }

    @Test
    public void testMoveSnakeYCor() {
        game.moveSnakeYCor(5);
        assertEquals(SnakeGame.WIDTH / 2, game.getHead().getNodeX());
        assertEquals(SnakeGame.WIDTH / 2 + 5, game.getHead().getNodeY());
        assertEquals(0, game.getHead().getDirection());
    }

    @Test
    public void testUpdateHeadDidNotTouchesPea() {
        Snake snake = game.getSnake();
        Pea pea = game.getPea();

        pea.newPea(SnakeGame.WIDTH / 2, SnakeGame.HEIGHT / 2);
        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() - Node.WIDTH - 2);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY());

        game.update();
        assertEquals(0, game.returnCurrentScore());
        assertEquals(1, game.getSnakeLength());
        assertEquals(pea.getNodeX() - 2, game.getHead().getNodeX());
        assertEquals(pea.getNodeY(), game.getHead().getNodeY());
        assertEquals(0, game.getHead().getDirection());
    }

    @Test
    public void testUpdateHeadTouchesPea() {
        Snake snake = game.getSnake();
        Pea pea = game.getPea();

        pea.newPea(SnakeGame.WIDTH / 2, SnakeGame.HEIGHT / 2);
        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() - Node.WIDTH - 1);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY());

        game.update();
        assertEquals(1, game.returnCurrentScore());
        assertEquals(2, game.getSnakeLength());
        Body body = game.getSnake().getSnakeBody().get(1);
        assertEquals(SnakeGame.WIDTH / 2 - 1, game.getHead().getNodeX());
        assertEquals(SnakeGame.HEIGHT / 2, game.getHead().getNodeY());
        assertEquals(0, game.getHead().getDirection());
        assertEquals(SnakeGame.WIDTH / 2 - Node.WIDTH - 1, body.getNodeX());
        assertEquals(SnakeGame.HEIGHT / 2, body.getNodeY());
        assertEquals(0, body.getDirection());
        assertTrue(game.getPea().nodeX <= SnakeGame.WIDTH - Node.WIDTH + 1);
        assertTrue(game.getPea().nodeY <= SnakeGame.HEIGHT - Node.HEIGHT + 1);
    }

    @Test
    public void testUpdateMoveHeadEast() {
        Snake snake = game.getSnake();
        Pea pea = game.getPea();

        pea.newPea(SnakeGame.WIDTH / 2, SnakeGame.HEIGHT / 2);
        assertEquals(Body.EAST, snake.getSnakeBody().get(0).getDirection());
        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() - Node.WIDTH - 2);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY());

        game.update();
        assertEquals(pea.getNodeX() - 2, game.getHead().getNodeX());
        assertEquals(pea.getNodeY(), game.getHead().getNodeY());
        assertEquals(Body.EAST, game.getHead().getDirection());
    }

    @Test
    public void testUpdateMoveHeadWest() {
        Snake snake = game.getSnake();
        Pea pea = game.getPea();

        pea.newPea(SnakeGame.WIDTH / 2, SnakeGame.HEIGHT / 2);
        game.changeDirection(Body.WEST);
        assertEquals(Body.WEST, snake.getSnakeBody().get(0).getDirection());
        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + SnakeGame.MOVE_BY - 2);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() - Node.HEIGHT - 1);

        game.update();
        assertEquals(pea.getNodeX() - 2, game.getHead().getNodeX());
        assertEquals(pea.getNodeY() - Node.HEIGHT - 1, game.getHead().getNodeY());
        assertEquals(Body.WEST, game.getHead().getDirection());
    }

    @Test
    public void testUpdateMoveHeadNorth() {
        Snake snake = game.getSnake();
        Pea pea = game.getPea();

        pea.newPea(SnakeGame.WIDTH / 2, SnakeGame.HEIGHT / 2);
        game.changeDirection(Body.NORTH);
        assertEquals(Body.NORTH, snake.getSnakeBody().get(0).getDirection());
        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + SnakeGame.MOVE_BY);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() + Node.HEIGHT - 1);

        game.update();
        assertEquals(pea.getNodeX() + SnakeGame.MOVE_BY, game.getHead().getNodeX());
        assertEquals(pea.getNodeY() - 1, game.getHead().getNodeY());
        assertEquals(Body.NORTH, game.getHead().getDirection());
    }

    @Test
    public void testUpdateMoveHeadSouth() {
        Snake snake = game.getSnake();
        Pea pea = game.getPea();

        pea.newPea(SnakeGame.WIDTH / 2, SnakeGame.HEIGHT / 2);
        game.changeDirection(Body.SOUTH);
        assertEquals(Body.SOUTH, snake.getSnakeBody().get(0).getDirection());
        snake.getSnakeBody().get(0).changeNodeX(pea.getNodeX() + SnakeGame.MOVE_BY);
        snake.getSnakeBody().get(0).changeNodeY(pea.getNodeY() - Node.HEIGHT - 1);

        game.update();
        assertEquals(pea.getNodeX() + SnakeGame.MOVE_BY, game.getHead().getNodeX());
        assertEquals(pea.getNodeY() - 1, game.getHead().getNodeY());
        assertEquals(Body.SOUTH, game.getHead().getDirection());
    }

    @Test
    public void testWriteSnakeAndPeaFromGame() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterDefaultSnakeInGame.json");
            writer.open();
            gameAssigned.writeSnakeAndPeaFromGame(writer);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterDefaultSnakeInGame.json");
            gameAssigned = new SnakeGame(reader.readSnake(), reader.readPea());
            Snake snake = gameAssigned.getSnake();
            Pea pea = gameAssigned.getPea();
            assertEquals(50, pea.getNodeX());
            assertEquals(50, pea.getNodeY());
            assertEquals(0, snake.getCurrentScore());
            assertEquals(1, snake.getSnakeBody().size());
            assertEquals(SnakeGame.WIDTH / 2, snake.getSnakeBody().get(0).getNodeX());
            assertEquals(SnakeGame.HEIGHT / 2, snake.getSnakeBody().get(0).getNodeY());
            assertEquals(0, snake.getSnakeBody().get(0).getDirection());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testReadSnakeAndPeaException() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            game.readSnakeAndPea(reader);
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReadSnakeAndPeaNoException() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSnakeInGame.json");
        try {
            gameAssigned.readSnakeAndPea(reader);
            Pea pea = gameAssigned.getPea();
            Snake snake = gameAssigned.getSnake();
            assertEquals(4, pea.getNodeX());
            assertEquals(72, pea.getNodeY());
            assertEquals(1, snake.getCurrentScore());
            assertEquals(2, snake.getSnakeBody().size());
            assertEquals(65, snake.getSnakeBody().get(0).getNodeX());
            assertEquals(35, snake.getSnakeBody().get(0).getNodeY());
            assertEquals(3, snake.getSnakeBody().get(0).getDirection());
            assertEquals(50, snake.getSnakeBody().get(1).getNodeX());
            assertEquals(35, snake.getSnakeBody().get(1).getNodeY());
            assertEquals(3, snake.getSnakeBody().get(1).getDirection());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testSnakeControlLeft() {
        try {
            game.keyPressed(KeyEvent.VK_KP_LEFT);
            game.keyPressed(KeyEvent.VK_LEFT);
        } catch (CannotMoveException e) {
            fail("exception shouldn't be thrown");
        }
        assertEquals(Body.WEST, game.getHead().getDirection());
    }

    @Test
    public void testSnakeControlRight() {
        try {
            game.keyPressed(KeyEvent.VK_KP_RIGHT);
            assertEquals(Body.EAST, game.getHead().getDirection());
            game.keyPressed(KeyEvent.VK_RIGHT);
        } catch (CannotMoveException e) {
            fail("exception shouldn't be thrown");
        }
        assertEquals(Body.EAST, game.getHead().getDirection());
    }

    @Test
    public void testSnakeControlUp() {
        try {
            game.keyPressed(KeyEvent.VK_KP_UP);
            assertEquals(Body.NORTH, game.getHead().getDirection());
            game.keyPressed(KeyEvent.VK_UP);
        } catch (CannotMoveException e) {
            fail("exception shouldn't be thrown");
        }
        assertEquals(Body.NORTH, game.getHead().getDirection());
    }

    @Test
    public void testSnakeControlDownKP() {
        try {
            game.keyPressed(KeyEvent.VK_KP_DOWN);
            assertEquals(Body.SOUTH, game.getHead().getDirection());
        } catch (CannotMoveException e) {
            fail("exception shouldn't be thrown");
        }
    }

    @Test
    public void testSnakeControlDown() {
        try {
            game.keyPressed(KeyEvent.VK_DOWN);
            assertEquals(Body.SOUTH, game.getHead().getDirection());
        } catch (CannotMoveException e) {
            fail("exception shouldn't be thrown");
        }
    }

    @Test
    public void testSnakeControlOtherKey() {
        try {
            game.keyPressed(KeyEvent.VK_ENTER);
            assertEquals(Body.EAST, game.getHead().getDirection());
        } catch (CannotMoveException e) {
            fail("exception shouldn't be thrown");
        }
    }

    @Test
    public void testSnakeControlEastMoveWestNoException() {
        Snake snake = game.getSnake();
        Body body = new Body(0, SnakeGame.WIDTH / 2 - Node.WIDTH, SnakeGame.HEIGHT / 2 - Node.HEIGHT);
        snake.addBody(body);
        try {
            game.keyPressed(KeyEvent.VK_KP_LEFT);
            assertEquals(Body.WEST, game.getHead().getDirection());
        } catch (CannotMoveException e) {
            fail("should throw no exception");
        }
    }

    @Test
    public void testSnakeControlEastMoveWestException() {
        Snake snake = game.getSnake();
        Body body = new Body(0, SnakeGame.WIDTH / 2 - Node.WIDTH, SnakeGame.HEIGHT / 2);
        snake.addBody(body);
        try {
            game.keyPressed(KeyEvent.VK_KP_LEFT);
            fail("should throw exception");
        } catch (CannotMoveException e) {
            // expected
        }
    }

    @Test
    public void testSnakeControlWestMoveEastNoException() {
        Snake snake = game.getSnake();
        Body body = new Body(Body.WEST, SnakeGame.WIDTH / 2 + Node.WIDTH, SnakeGame.HEIGHT / 2 - Node.HEIGHT);
        snake.addBody(body);
        game.changeDirection(Body.WEST);
        try {
            game.keyPressed(KeyEvent.VK_KP_RIGHT);
            assertEquals(Body.EAST, game.getHead().getDirection());
        } catch (CannotMoveException e) {
            fail("should throw no exception");
        }
    }

    @Test
    public void testSnakeControlWestMoveEastException() {
        Snake snake = game.getSnake();
        Body body = new Body(Body.WEST, SnakeGame.WIDTH / 2 + Node.WIDTH, SnakeGame.HEIGHT / 2);
        snake.addBody(body);
        game.changeDirection(Body.WEST);
        try {
            game.keyPressed(KeyEvent.VK_KP_RIGHT);
            fail("should throw exception");
        } catch (CannotMoveException e) {
            // expected
        }
    }

    @Test
    public void testSnakeControlNorthMoveSouthNoException() {
        Snake snake = game.getSnake();
        Body body = new Body(Body.NORTH, SnakeGame.WIDTH / 2 - Node.WIDTH, SnakeGame.HEIGHT / 2 + Node.HEIGHT);
        snake.addBody(body);
        game.changeDirection(Body.NORTH);
        try {
            game.keyPressed(KeyEvent.VK_KP_DOWN);
            assertEquals(Body.SOUTH, game.getHead().getDirection());
        } catch (CannotMoveException e) {
            fail("should throw no exception");
        }
    }

    @Test
    public void testSnakeControlNorthMoveSouthException() {
        Snake snake = game.getSnake();
        Body body = new Body(Body.NORTH, SnakeGame.WIDTH / 2, SnakeGame.HEIGHT / 2 + Node.HEIGHT);
        snake.addBody(body);
        game.changeDirection(Body.NORTH);
        try {
            game.keyPressed(KeyEvent.VK_KP_DOWN);
            fail("should throw exception");
        } catch (CannotMoveException e) {
            // expected
        }
    }

    @Test
    public void testSnakeControlSouthMoveNorthNoException() {
        Snake snake = game.getSnake();
        Body body = new Body(Body.SOUTH, SnakeGame.WIDTH / 2 - Node.WIDTH, SnakeGame.HEIGHT / 2 - Node.HEIGHT);
        snake.addBody(body);
        game.changeDirection(Body.SOUTH);
        try {
            game.keyPressed(KeyEvent.VK_KP_UP);
            assertEquals(Body.NORTH, game.getHead().getDirection());
        } catch (CannotMoveException e) {
            fail("should throw no exception");
        }
    }

    @Test
    public void testSnakeControlSouthMoveNorthException() {
        Snake snake = game.getSnake();
        Body body = new Body(Body.SOUTH, SnakeGame.WIDTH / 2, SnakeGame.HEIGHT / 2 - Node.HEIGHT);
        snake.addBody(body);
        game.changeDirection(Body.SOUTH);
        try {
            game.keyPressed(KeyEvent.VK_KP_UP);
            fail("should throw exception");
        } catch (CannotMoveException e) {
            // expected
        }
    }
}
