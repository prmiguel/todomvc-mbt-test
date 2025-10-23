package mbt.todomvc.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

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
}
