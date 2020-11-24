package persistence;

import model.Body;
import model.Pea;
import model.Snake;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
// From the JsonSerializationDemo project
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads snake from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public Snake readSnake() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSnake(jsonObject);
    }

    // EFFECTS: reads pea from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public Pea readPea() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePea(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    //          throws IOException if an error occurs reading data from file
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Snake from JSON object and returns it
    private Snake parseSnake(JSONObject jsonObject) {
        int score = jsonObject.getInt("score");
        Snake s = new Snake();
        s.setCurrentScore(score);
        addBodies(s, jsonObject);
        return s;
    }

    // EFFECTS: parses Pea from JSON object and returns it
    private Pea parsePea(JSONObject jsonObject) {
        int nodeX = jsonObject.getInt("peaX");
        int nodeY = jsonObject.getInt("peaY");
        return new Pea(nodeX, nodeY);
    }

    // MODIFIES: s
    // EFFECTS: parses bodies from JSON object and adds them to snake
    private void addBodies(Snake s, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("bodies");
        for (Object json : jsonArray) {
            JSONObject nextBody = (JSONObject) json;
            addBody(s, nextBody);
        }
        s.removeFirstBody();
    }

    // MODIFIES: s
    // EFFECTS: parses body from JSON object and adds it to snake
    private void addBody(Snake s, JSONObject jsonObject) {
        int nodeX = jsonObject.getInt("bodyX");
        int nodeY = jsonObject.getInt("bodyY");
        int direction = jsonObject.getInt("direction");
        Body body = new Body(direction, nodeX, nodeY);
        s.addBody(body);
    }
}
