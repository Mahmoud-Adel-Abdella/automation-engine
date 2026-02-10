package Utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class ConfigManager {

    private static JsonNode config;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = ConfigManager.class
                    .getClassLoader()
                    .getResourceAsStream("config.json");

            if (is == null) {
                throw new RuntimeException("config.json not found in resources");
            }

            config = mapper.readTree(is);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.json", e);
        }
    }

    public static String get(String keyPath) {
        String[] keys = keyPath.split("\\.");
        JsonNode node = config;

        for (String key : keys) {
            node = node.get(key);
            if (node == null) {
                throw new RuntimeException("Key not found in config.json: " + keyPath);
            }
        }

        return node.asText();
    }

    public static boolean getBoolean(String keyPath) {
        return Boolean.parseBoolean(get(keyPath));
    }
}
