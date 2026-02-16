package engine.listeners;

import engine.base.BaseTest;
import engine.utils.ExtentManger;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestListeners implements ITestListener {

    public static List<Map<String, String>> failures = new ArrayList<>();
    File destination;

    @Override
    public void onStart(ITestContext context) {
        System.out.println("============= Start Selenium Framework =============");
        ExtentManger.initReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentManger.createTest(result.getMethod().getMethodName());
        ExtentManger.getTest().log(Status.INFO, "Test Started: " + result.getMethod().getMethodName());
        System.out.println("======== Start Test ["+ result.getName()+"] ========");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManger.getTest().log(Status.INFO, "Test Suite Finished: " + context.getName());
        System.out.println("================== Report Flushed ==================");
        ExtentManger.flushReports();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManger.getTest().log(Status.PASS, "✅✅ Test Passed ✅✅");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentManger.getTest().log(Status.SKIP, "⛔⛔ Test Skipped ⛔⛔: " + result.getThrowable());
    }

    @Override
    public void onTestFailure(ITestResult result) {

        ExtentManger.getTest()
                .log(Status.FAIL, "⚠️⚠️ Test Failed ⚠️⚠️: " + result.getThrowable());

        try {
            Object testClass = result.getInstance();

            WebDriver driver = ((BaseTest) testClass).driver;

            destination = ExtentManger.screenShot(driver, result.getName());
            String screenShotPath = destination.getAbsolutePath();

            ExtentManger.getTest().addScreenCaptureFromPath(screenShotPath);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass().getName();
        Throwable throwable = result.getThrowable();

        String errorMessage = throwable != null
                ? throwable.getMessage()
                : "No error message";

        String fullStackTrace = "";

        if (throwable != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            fullStackTrace = sw.toString();
        }

        Map<String, String> failureData = new HashMap<>();
        failureData.put("className", className);
        failureData.put("testName", testName);
        failureData.put("errorMessage", errorMessage);
        failureData.put("fullStackTrace", fullStackTrace);
        failureData.put("screenshot", "screenshots/" + destination.getName());

        failures.add(failureData);
    }
}


