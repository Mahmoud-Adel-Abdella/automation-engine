package Engine.Listeners;

import Engine.Utils.ClientContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class TestSummaryListener implements ITestListener {

    private int passed = 0;
    private int failed = 0;
    private int total = 0;
    private int skipped = 0;
    private List<String> failedTests = new ArrayList<>();
    private List<String> skippedTests = new ArrayList<>();
    private Instant startTime;

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

            Map<String, Object> summary = new HashMap<>();
            summary.put("status", failed > 0 ? "failed" : "passed");
            summary.put("total", total);
            summary.put("passed", passed);
            summary.put("failed", failed);
            summary.put("skipped", skipped);
            summary.put("failed_tests", failedTests);
            summary.put("duration", formatDuration(duration));
            summary.put("client", ClientContext.getClient());
            summary.put("timestamp",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .format(new Date())
            );
            summary.put("browser",
                    System.getProperty("browser", "chrome")
            );


            File resultsDir = new File(ClientContext.getResultsPath());
            resultsDir.mkdirs();

            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(
                            new File(ClientContext.getSummaryPath()),
                            summary
                    );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatDuration(Duration duration) {
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        return minutes + "m " + seconds + "s";
    }
}
