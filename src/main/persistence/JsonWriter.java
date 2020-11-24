package persistence;

import model.Pea;
import model.Snake;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of workroom to file
// From the JsonSerializationDemo project
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer
    //          throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of snake and pea to file
    public void writeSnakeAndPea(Snake s, Pea p) {
        JSONObject jsonSnake = s.toJson();
        JSONObject jsonPea = p.toJson();
        JSONObject mergedJson = mergeJson(jsonSnake, jsonPea);
        saveToFile(mergedJson.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    // EFFECTS: merge jsonSnake and jsonPea together
    // from https://stackoverflow.com/questions/2403132/merge-concat-multiple-jsonobjects-in-java
    private JSONObject mergeJson(JSONObject snake, JSONObject pea) {
        JSONObject merged = new JSONObject(snake, JSONObject.getNames(snake));
        for (String key : JSONObject.getNames(pea)) {
            merged.put(key, pea.get(key));
        }
        return merged;
    }
}
