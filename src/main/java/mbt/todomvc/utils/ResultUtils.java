package mbt.todomvc.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.graphwalker.java.test.Result;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static mbt.todomvc.utils.JsonUtils.readJson;

public final class ResultUtils {
    public static void saveResults(String resultFileName, Object object) {
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = prettyGson.toJson(object);
        System.out.println(jsonString);
        try (FileWriter file = new FileWriter(resultFileName)) {
            file.write(jsonString);
            file.flush();
            System.out.println("JSON data saved to " + resultFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    record GraphWalkerReport(Object graphwalkermodel, Object sequence, Result simulationSummary) {}

    public static void generateJsonResult(Path modelPath, Object sequence, Result result, String jsonname) {
        var graphwalkerReport = new GraphWalkerReport(readJson(modelPath), sequence, result);
        saveResults(jsonname + ".json", graphwalkerReport);
    }
}
