package mbt.todomvc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public final class JsonUtils {
    public static Object readJson(Path jsonPath) {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = JsonUtils.class.getClassLoader().getResourceAsStream(jsonPath.toString());
        Object genericObject;
        try {
            genericObject = objectMapper.readValue(inputStream, Object.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return genericObject;
    }
}
