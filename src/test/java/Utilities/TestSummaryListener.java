package Utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSummaryListener implements ITestListener {

    private int passed = 0;
    private int failed = 0;
    private int total = 0;
    private List<String> failedTests = new ArrayList<>();
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
    public void onFinish(ITestContext context) {
        try {
            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);

            Map<String, Object> summary = new HashMap<>();
            summary.put("status", failed > 0 ? "failed" : "passed");
            summary.put("total", total);
            summary.put("passed", passed);
            summary.put("failed", failed);
            summary.put("failed_tests", failedTests);
            summary.put("duration", formatDuration(duration));

            File resultsDir = new File("results");
            if (!resultsDir.exists()) {
                resultsDir.mkdirs();
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(resultsDir, "summary.json"), summary);

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
