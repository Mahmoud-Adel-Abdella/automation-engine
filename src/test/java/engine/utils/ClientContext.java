package engine.utils;

public class ClientContext {

    private static final String client =
            System.getProperty("client");

    static {
        if (client == null || client.isEmpty()) {
            throw new RuntimeException(
                    "Client not specified! Use -Dclient=clientName"
            );
        }
    }

    public static String getClient() {
        return client;
    }

    public static String getClientRoot() {
        return "clients/" + client + "/";
    }

    public static String getConfigPath() {
        return getClientRoot() + "config.json";
    }
}
