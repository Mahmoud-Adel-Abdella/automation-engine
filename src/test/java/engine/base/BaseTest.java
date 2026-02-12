package engine.base;

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

import java.time.Duration;

public class BaseTest {

    public WebDriver driver;
    protected SoftAssert softAssert;
    protected Faker faker;

    String browser = ConfigManager.get("environment.browser");
    boolean headless = ConfigManager.getBoolean("environment.headless");
    String baseUrl = ConfigManager.get("environment.baseUrl");

    static {
        if (System.getProperty("client") == null) {
            throw new RuntimeException(
                    "Client not specified! Use -Dclient=clientName"
            );
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {

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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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

}
