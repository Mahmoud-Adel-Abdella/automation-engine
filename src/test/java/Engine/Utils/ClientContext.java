package Engine.Utils;

public class ClientContext {

    private static final String client =
            System.getProperty("client" ,"automationExcercise");

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

    public static String getResultsPath() {
        return getClientRoot() + "results/";
    }

    public static String getScreenshotsPath() {
        return getResultsPath() + "screenshots/";
    }

    public static String getReportPath() {
        return getResultsPath() + "report.html";
    }

    public static String getSummaryPath() {
        return getResultsPath() + "summary.json";
    }

    public static String getLogsPath() {
        return getResultsPath() + "logs/";
    }
}
