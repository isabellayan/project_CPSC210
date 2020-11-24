package ui;

import model.SnakeGame;

import javax.swing.*;
import java.awt.*;

// Represents the panel in which the scoreboard is displayed
// From SpaceInvaders: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
public class ScorePanel extends JPanel {

    private static final String SCORE_TXT = "Current Score: ";
    private static final int LBL_WIDTH = 110;
    private static final int LBL_HEIGHT = 30;

    private SnakeGame game;
    private JLabel scoreLbl;

    // EFFECTS: sets the background colour and draws the initial labels;
    //          updates this with the game whose score is to be displayed
    public ScorePanel(SnakeGame g) {
        game = g;
        setBackground(new Color(235, 235, 235));
        scoreLbl = new JLabel(SCORE_TXT + game.getSnake().getCurrentScore());
        scoreLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        add(scoreLbl);
    }

    // MODIFIES: this
    // EFFECTS:  updates the score of the current game
    public void update() {
        scoreLbl.setText(SCORE_TXT + game.getSnake().getCurrentScore());
        repaint();
    }
}
