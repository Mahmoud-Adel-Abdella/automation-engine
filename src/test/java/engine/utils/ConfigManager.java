package engine.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigManager {

    private static JsonNode config;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();

            String configPath = ClientContext.getConfigPath();

            byte[] jsonData = Files.readAllBytes(
                    Paths.get(configPath)
            );

            config = mapper.readTree(jsonData);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to load config file", e
            );
        }
    }

    public static String get(String keyPath) {

        String[] keys = keyPath.split("\\.");
        JsonNode node = config;

        for (String key : keys) {
            node = node.get(key);
            if (node == null) {
                throw new RuntimeException(
                        "Key not found: " + keyPath
                );
            }
        }

        return node.asText();
    }

    public static boolean getBoolean(String keyPath) {
        return Boolean.parseBoolean(get(keyPath));
    }
}
