package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PeaTest {
    Pea randomPea;
    Pea assignPea;

    @BeforeEach
    public void setUp() {
        randomPea = new Pea();
        assignPea = new Pea(SnakeGame.WIDTH / 2, SnakeGame.HEIGHT / 2);
    }

    @Test
    public void testRandomConstructor() {
        for (int i = 0; i < 500; i++) {
            if (randomPea.getNodeX() > SnakeGame.WIDTH - Node.WIDTH || randomPea.getNodeY() > SnakeGame.HEIGHT - Node.HEIGHT) {
                fail();
            }
        }
    }

    @Test
    public void testAssignConstructor() {
        assertEquals(SnakeGame.WIDTH / 2, assignPea.getNodeX());
        assertEquals(SnakeGame.HEIGHT / 2, assignPea.getNodeY());
    }

    @Test
    public void testNewPea() {
        for (int i = 0; i < 10; i++) {
            int oldX = randomPea.getNodeX();
            int oldY = randomPea.getNodeY();
            randomPea.newPea();
            if (randomPea.getNodeX() == oldX && randomPea.getNodeY() == oldY) {
                oldX = randomPea.getNodeX();
                oldY = randomPea.getNodeY();
                if (randomPea.getNodeX() == oldX && randomPea.getNodeY() == oldY) {
                    fail();
                }
            }
        }
    }

    @Test
    public void testAssignNewPea() {
        assignPea.newPea(10, 10);
        assertEquals(10, assignPea.getNodeX());
        assertEquals(10, assignPea.getNodeY());
    }
}
