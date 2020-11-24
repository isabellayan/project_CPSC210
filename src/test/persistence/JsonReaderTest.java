package persistence;

import model.Pea;
import model.Snake;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// From the JsonSerializationDemo project
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest {

    @Test
    void testReadSnakeNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Snake snake = reader.readSnake();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadPeaNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Pea pea = reader.readPea();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySnake() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySnake.json");
        try {
            Snake snake = reader.readSnake();
            Pea pea = reader.readPea();
            assertEquals(0, snake.getCurrentScore());
            assertEquals(0, snake.getSnakeBody().size());
            assertEquals(20, pea.getNodeX());
            assertEquals(40, pea.getNodeY());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSnake() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSnake.json");
        try {
            Snake snake = reader.readSnake();
            Pea pea = reader.readPea();
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
}
