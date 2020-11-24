package ui;

import model.Body;
import model.Pea;
import model.SnakeGame;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Represents a console based snake application
// From the JsonSerializationDemo project
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Also based on TellerApp: https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class SnakeConsoleApp {

    public static final int MOVE_BY = 5;
    private static final String JSON_STORE = "./data/snake.json";

    private SnakeGame game;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the snake application
    //          if the file cannot be found throw FileNotFoundException
    public SnakeConsoleApp() throws FileNotFoundException {
        runSnake(); // based on TellerApp
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runSnake() { // based on TellerApp
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {

            if (game.gameOver()) {

                keepGoing = false;
                System.out.println("\nGame over!");

            } else {

                displayMenu();
                command = input.next().toLowerCase();

                if (command.equals("q")) {
                    keepGoing = false;
                } else {
                    processCommand(command);
                }
            }
        }

        System.out.println("\nYour score is: " + game.returnCurrentScore());
        System.out.println("Thank you for playing! ");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) { // partly based on TellerApp
        if (command.equals("w")) {
            game.moveSnakeYCor(MOVE_BY);
            game.changeDirection(Body.NORTH);
        } else if (command.equals("s")) {
            game.moveSnakeYCor(-MOVE_BY);
            game.changeDirection(Body.SOUTH);
        } else if (command.equals("a")) {
            game.moveSnakeXCor(-MOVE_BY);
            game.changeDirection(Body.WEST);
        } else if (command.equals("d")) {
            game.moveSnakeXCor(MOVE_BY);
            game.changeDirection(Body.EAST);
        } else if (command.equals("save")) {
            saveSnakeAndPea();
        } else if (command.equals("load")) {
            loadSnakeAndPea();
        } else {
            System.out.println("Selection not valid...");
        }

        game.update();
    }

    // MODIFIES: this
    // EFFECTS: initialize a snake at the center of the screen and a pea at a random position
    //          and a highest score of 0
    private void init() { // based on TellerApp
        game = new SnakeGame();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: display menu options to the user
    private void displayMenu() { // partly based on TellerApp
        Body head = game.getHead();
        Pea pea = game.getPea();
        System.out.println("Welcome to the snake game! ");
        System.out.println("Your current score is: " + game.returnCurrentScore());
        System.out.println("\nThe current length of the snake is: " + game.getSnakeLength());
        System.out.println("The current coordinate of the top left corner of the snake's head is: ");
        System.out.println(head.getNodeX() + ", " + head.getNodeY());
        System.out.println("The current coordinate of the top left corner of the pea is: ");
        System.out.println(pea.getNodeX() + ", " + pea.getNodeY());
        System.out.println("\nSelect from: ");
        System.out.println("w -> move up 5 units");
        System.out.println("s -> move down 5 units");
        System.out.println("a -> move left 5 units");
        System.out.println("d -> move right 5 units");
        System.out.println("\nsave -> save current state of game to file");
        System.out.println("load -> load previous game from file");
        System.out.println("q -> quit");
    }

    // EFFECTS: saves the snake and pea to file
    private void saveSnakeAndPea() {
        try {
            jsonWriter.open();
            game.writeSnakeAndPeaFromGame(jsonWriter);
            jsonWriter.close();
            System.out.println("Saved current snake and pea to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadSnakeAndPea() {
        try {
            game.readSnakeAndPea(jsonReader);
            System.out.println("Loaded saved snake and pea from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
