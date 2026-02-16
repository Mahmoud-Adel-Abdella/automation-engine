package engine.listeners;

import engine.run.RunManager;
import engine.utils.ClientContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import engine.utils.ConfigManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static engine.listeners.TestListeners.failures;

public class TestSummaryListener implements ITestListener {

    private int passed = 0;
    private int failed = 0;
    private int total = 0;
    private int skipped = 0;
    private List<String> failedTests = new ArrayList<>();
    private List<String> skippedTests = new ArrayList<>();
    private Instant startTime;
    String flowName = System.getProperty("flow");

    @Override
    public void onStart(ITestContext context) {
        startTime = Instant.now();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        passed++;
        total++;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failed++;
        total++;
        failedTests.add(result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skipped++;
        total++;
        skippedTests.add(result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {

        try {
            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);

            double passRate = total == 0 ? 0 :
                    ((double) passed / total) * 100;

            Map<String, Object> summary = new HashMap<>();
            summary.put("runId", RunManager.getRunId());
            summary.put("client", RunManager.getClient());
            summary.put("status", failed > 0 ? "FAILED" : "PASSED");
            summary.put("total", total);
            summary.put("passed", passed);
            summary.put("failed", failed);
            summary.put("skipped", skipped);
            summary.put("passRate", Math.round(passRate));
            summary.put("durationSeconds", duration.getSeconds());
            String flow = System.getProperty("flow", "unknown");
            summary.put("flow", flow);
            summary.put("failures", failures);
            summary.put("timestamp",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .format(new Date())
            );
            summary.put("browser",
                    System.getProperty("browser", "chrome")
            );

            File resultsDir = new File(RunManager.getResultsPath());
            resultsDir.mkdirs();

            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(
                            new File(RunManager.getResultsPath() + "summary.json"),
                            summary
                    );

            String runResultJson = "{"
                    + "\"runId\":\"" + RunManager.getRunId() + "\","
                    + "\"client\":\"" + RunManager.getClient() + "\","
                    + "\"summaryPath\":\"" + RunManager.getResultsPath() + "summary.json\""
                    + "}";

            System.out.println("RUN_RESULT=" + runResultJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
