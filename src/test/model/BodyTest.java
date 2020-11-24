package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BodyTest {
    private final int ORIGIN = 0;
    private Body body;

    @BeforeEach
    public void setUp() {
        body = new Body(Body.EAST, ORIGIN, ORIGIN);
    }

    @Test
    public void testConstructor() {
        assertEquals(Body.EAST, body.getDirection());
        assertEquals(ORIGIN, body.getNodeX());
        assertEquals(ORIGIN, body.getNodeY());
    }

    @Test
    public void testChangeDirection() {
        body.changeDirection(Body.NORTH);
        assertEquals(Body.NORTH, body.getDirection());
        body.changeDirection(Body.WEST);
        assertEquals(Body.WEST, body.getDirection());
        body.changeDirection(Body.SOUTH);
        assertEquals(Body.SOUTH, body.getDirection());
    }

    @Test
    public void testChangeNodeX() {
        body.changeNodeX(ORIGIN + 1);
        assertEquals(ORIGIN + 1, body.getNodeX());
        body.changeNodeX(ORIGIN + 2);
        assertEquals(ORIGIN + 2, body.getNodeX());
    }

    @Test
    public void testChangeNodeY() {
        body.changeNodeY(ORIGIN + 1);
        assertEquals(ORIGIN + 1, body.getNodeY());
        body.changeNodeY(ORIGIN + 2);
        assertEquals(ORIGIN + 2, body.getNodeY());
    }
}