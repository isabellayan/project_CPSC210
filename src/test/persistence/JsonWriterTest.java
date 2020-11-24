package persistence;

import model.Body;
import model.Pea;
import model.Snake;
import model.SnakeGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// From the JsonSerializationDemo project
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Snake snake = new Snake();
            Pea pea = new Pea();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterDefaultWorkroom() {
        try {
            Snake snake = new Snake();
            Pea pea = new Pea(0, 0);
            JsonWriter writer = new JsonWriter("./data/testWriterDefaultSnake.json");
            writer.open();
            writer.writeSnakeAndPea(snake, pea);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterDefaultSnake.json");
            snake = reader.readSnake();
            pea = reader.readPea();
            assertEquals(0, pea.getNodeX());
            assertEquals(0, pea.getNodeY());
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
    void testWriterGeneralWorkroom() {
        try {
            Snake snake = new Snake();
            Body body1 = new Body(0, 35, 50);
            Body body2 = new Body(0, 20, 50);
            snake.addBody(body1);
            snake.addBody(body2);
            Pea pea = new Pea(25, 25);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSnake.json");
            writer.open();
            writer.writeSnakeAndPea(snake, pea);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSnake.json");
            snake = reader.readSnake();
            pea = reader.readPea();
            assertEquals(25, pea.getNodeX());
            assertEquals(25, pea.getNodeY());
            assertEquals(0, snake.getCurrentScore());
            assertEquals(3, snake.getSnakeBody().size());
            assertEquals(SnakeGame.WIDTH / 2, snake.getSnakeBody().get(0).getNodeX());
            assertEquals(SnakeGame.HEIGHT / 2, snake.getSnakeBody().get(0).getNodeY());
            assertEquals(0, snake.getSnakeBody().get(0).getDirection());
            assertEquals(35, snake.getSnakeBody().get(1).getNodeX());
            assertEquals(50, snake.getSnakeBody().get(1).getNodeY());
            assertEquals(0, snake.getSnakeBody().get(1).getDirection());
            assertEquals(20, snake.getSnakeBody().get(2).getNodeX());
            assertEquals(50, snake.getSnakeBody().get(2).getNodeY());
            assertEquals(0, snake.getSnakeBody().get(2).getDirection());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
