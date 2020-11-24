package ui;

import exception.CannotMoveException;
import model.SnakeGame;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents a snake game
// From the JsonSerializationDemo project: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// From the AlarmSystem: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
public class SnakeApp extends JFrame {

    private static final int INTERVAL = 200;
    private static final String JSON_STORE = "./data/snake.json";

    private SnakeGame game;
    private GamePanel gp;
    private ScorePanel sp;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private boolean running;

    // EFFECTS: sets up window in which snake game will be played
    //          if the file is not found throw FileNotFoundException
    // From SpaceInvaders: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
    public SnakeApp() throws FileNotFoundException {
        super("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        game = new SnakeGame();
        gp = new GamePanel(game);
        sp = new ScorePanel(game);
        running = true;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        add(gp);
        add(sp, BorderLayout.SOUTH);
        addKeyListener(new KeyHandler());
        addMenu();
        pack();
        addTimer();
        centreOnScreen();
    }

    // EFFECTS: runs the snake game
    // From the JsonSerializationDemo project
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public static void main(String[] args) {
        try {
            new SnakeApp();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to run application: file not found", "System Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    // From SpaceInvaders: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
    private void addTimer() {
        final Timer t = new Timer(INTERVAL, null);
        t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (game.gameOver()) {
                    t.stop();
                } else if (!running) {
                    try {
                        Thread.sleep(INTERVAL);
                    } catch (InterruptedException e) {
                        JOptionPane.showMessageDialog(null,
                                "Error occurred while the game is paused. ", "System Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    playSoundIfTouchesPea();
                    game.update();
                    gp.repaint();
                    sp.update();
                }
            }
        });

        t.start();
    }

    // EFFECTS: play sound if the head touches the pea
    // From: https://stackoverflow.com/questions/30118997/how-can-i-play-a-wav-file-in-java
    private void playSoundIfTouchesPea() {
        try {
            if (game.getSnake().headTouchesPea(game.getPea())) {
                String soundName = "./data/sound.wav";
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                        new File(soundName).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            }
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            JOptionPane.showMessageDialog(null,
                    "Error occurred while playing the sound. ", "System Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    // From SpaceInvaders: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    // MODIFIES: this
    // EFFECTS: construct a menu on the top of the screen
    private void addMenu() {
        BackgroundMenuBar menuBar = new BackgroundMenuBar();

        menuBar.setOpaque(false);
        menuBar.setColor(new Color(235, 235, 235));
        menuBar.setBorderPainted(false);

        JMenu snakeMenu = new JMenu("Snake App");
        snakeMenu.setOpaque(false);
        snakeMenu.setMnemonic('S');
        addMenuItem(snakeMenu, new ExitAction(), KeyStroke.getKeyStroke("E"));
        menuBar.add(snakeMenu);

        JMenu dataMenu = new JMenu("File");
        dataMenu.setOpaque(false);
        dataMenu.setMnemonic('D');
        addMenuItem(dataMenu, new SaveStateAction(), null);
        addMenuItem(dataMenu, new LoadStateAction(), null);
        menuBar.add(dataMenu);

        JMenu optionsMenu = new JMenu("Options");
        optionsMenu.setOpaque(false);
        optionsMenu.setMnemonic('O');
        addMenuItem(optionsMenu, new PauseAction(), KeyStroke.getKeyStroke("P"));
        addMenuItem(optionsMenu, new ResumeAction(), KeyStroke.getKeyStroke("R"));
        menuBar.add(optionsMenu);

        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: Adds an item with given handler to the given menu
    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

    // Represents a KeyHandler that manages key presses
    // From SpaceInvaders: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
    private class KeyHandler extends KeyAdapter {
        // MODIFIES: this
        // EFFECTS: A key handler to respond to key events
        @Override
        public void keyPressed(KeyEvent e) {
            try {
                game.keyPressed(e.getKeyCode());
            } catch (CannotMoveException cannotMoveException) {
                // Do nothing, continue with game
            }
        }
    }

    // Represents a menu bar that the background color of the bar can be changed by calling paintComponent
    // from https://stackoverflow.com/questions/15648030/
    // change-background-and-text-color-of-jmenubar-and-jmenu-objects-inside-it/15649006
    private class BackgroundMenuBar extends JMenuBar {
        Color bgColor;

        public void setColor(Color color) {
            bgColor = color;
        }

        // MODIFIES: this
        // EFFECTS: sets the background color for menu bar
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(bgColor);
            g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

        }
    }

    // Represents an action that saves the current state of the game
    private class SaveStateAction extends AbstractAction {

        SaveStateAction() {
            super("Save State");
        }

        // EFFECTS: saves the snake and pea to file
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                game.writeSnakeAndPeaFromGame(jsonWriter);
                jsonWriter.close();
                JOptionPane.showMessageDialog(null,
                        "Saved current snake and pea to " + JSON_STORE, "System Information",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException exception) {
                JOptionPane.showMessageDialog(null,
                        "Unable to write to file: " + JSON_STORE, "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Represents an action that loads the saved state from file
    private class LoadStateAction extends AbstractAction {

        LoadStateAction() {
            super("Load State");
        }

        // MODIFIES: this
        // EFFECTS: loads workroom from file
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                game.readSnakeAndPea(jsonReader);
                JOptionPane.showMessageDialog(null,
                        "Loaded saved snake and pea from " + JSON_STORE, "System Information",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(null,
                        "Unable to read from file: " + JSON_STORE, "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Represents an action that pauses the game
    // Some ideas from: https://stackoverflow.com/questions/29822905/implementing-pause-resume-function-in-java-game
    private class PauseAction extends AbstractAction {

        PauseAction() {
            super("Pause");
        }

        // MODIFIES: this
        // EFFECTS: pause the game
        @Override
        public void actionPerformed(ActionEvent e) {
            running = false;
        }
    }

    // Represents an action that resumes the paused game
    // Some ideas from: https://stackoverflow.com/questions/29822905/implementing-pause-resume-function-in-java-game
    private class ResumeAction extends AbstractAction {

        ResumeAction() {
            super("Resume");
        }

        // MODIFIES: this
        // EFFECTS: resume the game
        @Override
        public void actionPerformed(ActionEvent e) {
            running = true;
        }
    }

    // Represents an action that exits the game(closes the window)
    // From: https://stackoverflow.com/questions/2352727/closing-jframe-with-button-click
    private class ExitAction extends AbstractAction {

        ExitAction() {
            super("Exit");
        }

        // MODIFIES: this
        // EFFECTS: exit the game
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
