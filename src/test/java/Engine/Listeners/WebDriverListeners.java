package Engine.Listeners;

import Engine.Utils.ExtentManger;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;

import java.util.Arrays;

import static Engine.Utils.ExtentManger.extentTest;


public class WebDriverListeners implements WebDriverListener {

    long loadTime;

    //New Window
    @Override
    public void beforeNewWindow(WebDriver.TargetLocator targetLocator, WindowType typeHint) {
        System.out.println("Opening new window......");
    }

    @Override
    public void afterNewWindow(WebDriver.TargetLocator targetLocator, WindowType typeHint, WebDriver driver) {
        System.out.println("Open new window Done");
    }


    //Get
    @Override
    public void beforeGet(WebDriver driver, String url) {
        System.out.println("Navigating to: " + url);
        loadTime = System.currentTimeMillis();
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        System.out.println("Navigate Done: " + driver.getCurrentUrl());
        loadTime = System.currentTimeMillis() - loadTime;

        ExtentManger.getTest().log(Status.INFO, "Page Loaded: " +
                driver.getCurrentUrl() + "_ Load Time: " + loadTime);
    }

    //FindElement
    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        System.out.println("Finding Element: " + locator);
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        System.out.println("FindElement Done: " + locator);
    }

    //GetText
    @Override
    public void beforeGetText(WebElement element) {
        System.out.println("Getting Text: " + element.getText());
    }

    @Override
    public void afterGetText(Alert alert, String result) {
        System.out.println("Get Text Done: " + result);
    }

    //SendKeys
    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        System.out.println("Sending Keys: "+ Arrays.toString(keysToSend));
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        System.out.println("Send keys Done: "+ Arrays.toString(keysToSend));
        extentTest.info("Test Data: " + Arrays.toString(keysToSend));
    }


    //Click
    @Override
    public void beforeClick(WebElement element) {
        System.out.println("Clicking on: " + element);
    }

    @Override
    public void afterClick(WebElement element) {
        System.out.println("Click Done: " + element.toString());
    }

    @Override
    public void beforeIsEnabled(WebElement element) {
        System.out.println();
    }
}
