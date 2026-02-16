package engine.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import engine.run.RunManager;

import java.io.File;

public class ConfigManager {

    private static JsonNode config;
    private static boolean initialized = false;

    public static void initialize() {
        if (initialized) return;

        try {
            String client = RunManager.getClient();
            String path = "clients/" + client + "/config.json";

            ObjectMapper mapper = new ObjectMapper();
            config = mapper.readTree(new File(path));

            initialized = true;

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.json", e);
        }
    }

    public static String getBaseUrl() {
        return config.get("environment").get("baseUrl").asText();
    }

    public static String getBrowser() {
        return config.get("environment").get("browser").asText();
    }

    public static boolean isHeadless() {
        return config.get("environment").get("headless").asBoolean();
    }

    public static int getTimeout() {
        return config.get("environment").get("timeoutSeconds").asInt();
    }

    public static String getUsername() {
        return config.get("credentials").get("username").asText();
    }

    public static String getPassword() {
        return config.get("credentials").get("passwordEnv").asText();
    }

    public static int getRetryCount() {
        return config.get("execution").get("retryCount").asInt();
    }

    public static String getNotifyPolicy() {
        return config.get("execution").get("notifyPolicy").asText();
    }

    public static JsonNode getFlow(String flowName) {

        JsonNode flows = config.get("flows");

        if (flows == null || flows.get(flowName) == null) {
            throw new RuntimeException("Flow '" + flowName + "' not defined for this client.");
        }

        return flows.get(flowName);
    }

}
