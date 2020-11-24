package ui;

import model.Body;
import model.Node;
import model.Pea;
import model.SnakeGame;

import javax.swing.*;
import java.awt.*;

// render the game onto the screen
// From SpaceInvaders: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
public class GamePanel extends JPanel {

    private SnakeGame game;

    // EFFECTS:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(SnakeGame g) {
        setPreferredSize(new Dimension(SnakeGame.WIDTH, SnakeGame.HEIGHT));
        setBackground(Color.WHITE);
        this.game = g;
    }

    // MODIFIES: g
    // EFFECTS:  paint the game onto g, if the game it over display on screen
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);

        if (game.gameOver()) {
            gameOver(g);
        }
    }

    // MODIFIES: g
    // EFFECTS:  draws the game onto g
    private void drawGame(Graphics g) {
        drawSnake(g);
        drawEyes(g);
        drawPea(g);
    }

    // MODIFIES: g
    // EFFECTS:  draws the invaders onto g
    private void drawSnake(Graphics g) {
        for (Body next : game.getSnake().getSnakeBody()) {
            drawBody(g, next);
        }
    }

    // MODIFIES: g
    // EFFECTS:  draws the body onto g
    private void drawBody(Graphics g, Body body) {
        Color savedCol = g.getColor();
        g.setColor(Body.COLOR);
        g.fillOval(body.getNodeX(), body.getNodeY(), Node.WIDTH, Node.HEIGHT);
        g.setColor(savedCol);
    }

    // MODIFIES: g
    // EFFECTS:  draws eyes onto graphics
    // Some ideas from lab 6
    private void drawEyes(Graphics graphics) {
        final int OFFSET = Node.WIDTH / 5;
        final int EYE_DIAMETER = Node.WIDTH / 5;
        int headX = game.getHead().getNodeX();
        int headY = game.getHead().getNodeY();

        graphics.setColor(Color.BLACK);
        if (game.getHead().getDirection() == Body.EAST) {
            graphics.fillOval(headX + Node.WIDTH - OFFSET * 2, headY + OFFSET, EYE_DIAMETER, EYE_DIAMETER);
            graphics.fillOval(headX + Node.WIDTH - OFFSET * 2, headY + OFFSET * 3, EYE_DIAMETER, EYE_DIAMETER);
        } else if (game.getHead().getDirection() == Body.WEST) {
            graphics.fillOval(headX + OFFSET, headY + OFFSET, EYE_DIAMETER, EYE_DIAMETER);
            graphics.fillOval(headX + OFFSET, headY + OFFSET * 3, EYE_DIAMETER, EYE_DIAMETER);
        } else if (game.getHead().getDirection() == Body.NORTH) {
            graphics.fillOval(headX + OFFSET, headY + OFFSET, EYE_DIAMETER, EYE_DIAMETER);
            graphics.fillOval(headX + OFFSET * 3, headY + OFFSET, EYE_DIAMETER, EYE_DIAMETER);
        } else {
            graphics.fillOval(headX + OFFSET, headY + Node.HEIGHT - OFFSET * 2, EYE_DIAMETER, EYE_DIAMETER);
            graphics.fillOval(headX + OFFSET * 3, headY + Node.HEIGHT - OFFSET * 2, EYE_DIAMETER, EYE_DIAMETER);
        }
    }

    // MODIFIES: g
    // EFFECTS:  draws the pea onto g
    private void drawPea(Graphics g) {
        Pea pea = game.getPea();
        Color savedCol = g.getColor();
        g.setColor(Pea.COLOR);
        g.fillOval(pea.getNodeX(), pea.getNodeY(), Node.WIDTH, Node.HEIGHT);
        g.setColor(savedCol);
    }

    // MODIFIES: g
    // EFFECTS:  draws "game over" and replay instructions onto g
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString("Game over!", g, fm, SnakeGame.HEIGHT / 2);
        g.setColor(saved);
    }

    // MODIFIES: g
    // EFFECTS:  centres the string str horizontally onto g at vertical position yPos
    private void centreString(String str, Graphics g, FontMetrics fm, int y) {
        int width = fm.stringWidth(str);
        g.drawString(str, (SnakeGame.WIDTH - width) / 2, y);
    }
}
