package engine.run;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class RunManager {

    private static String runId;
    private static String client;
    private static String resultsPath;

    public static void initialize() {

        client = System.getProperty("client", "default");

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS")
                .format(new Date());

        String uuid = UUID.randomUUID().toString().substring(0, 8);

        runId = timestamp + "_" + uuid + "_" + client;

        resultsPath = "clients/" + client + "/results/" + runId + "/";

        new File(resultsPath).mkdirs();

        System.setProperty("runId", runId);
        System.setProperty("resultsPath", resultsPath);
    }

    public static String getRunId() {
        return runId;
    }

    public static String getResultsPath() {
        return resultsPath;
    }

    public static String getClient() {
        return client;
    }
}
