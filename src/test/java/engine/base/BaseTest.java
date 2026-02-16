package engine.base;

import engine.run.RunManager;
import engine.utils.ConfigManager;
import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import engine.listeners.WebDriverListeners;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public class BaseTest {

    public WebDriver driver;
    protected SoftAssert softAssert;
    protected Faker faker;


    @BeforeSuite
    public void beforeSuite() {

        String client = System.getProperty("client");

        if (client == null || client.isBlank()) {
            throw new RuntimeException(
                    "Client not specified! Use -Dclient=clientName"
            );
        }

        RunManager.initialize();
        ConfigManager.initialize();
    }



    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {

        String browser = ConfigManager.getBrowser();
        String baseUrl = ConfigManager.getBaseUrl();
        boolean headless = ConfigManager.isHeadless();

        switch (browser.toLowerCase()) {

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOptions = new FirefoxOptions();
                if (headless) {
                    ffOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(ffOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions meOptions = new EdgeOptions();
                if (headless) {
                    meOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(meOptions);
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        WebDriverListeners myListeners = new WebDriverListeners();
        driver = new EventFiringDecorator(myListeners).decorate(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigManager.getTimeout()));
        driver.get(baseUrl);

        faker = new Faker();
        softAssert = new SoftAssert();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        softAssert.assertAll();

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void afterSuite() throws IOException {
        String source = "target/surefire-reports/testng-results.xml";
        String destination = RunManager.getResultsPath() + "testng-results.xml";

        Files.copy(
                Paths.get(source),
                Paths.get(destination),
                StandardCopyOption.REPLACE_EXISTING
        );

    }
}
