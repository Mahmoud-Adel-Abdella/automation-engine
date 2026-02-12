package engine.base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import engine.utils.ClickUtils;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class BasePage {
    //Globals
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected Actions actions;

    //Constructor
    public BasePage(WebDriver driver) {
        BasePage.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    //Methods
    public void write(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    public String read(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        return element.getText();
    }

    public String readByAttribute(By locator, String attribute) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        return element.getAttribute(attribute);
    }

    public double readDigits(By locator) {
        return Double.parseDouble(read(locator).replaceAll("[^0-9]", ""));
    }

    public double readDigits(String text) {
        return Double.parseDouble(text.replaceAll("[^0-9]", ""));
    }

    public WebElement getWebElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        actions.moveToElement(element).perform();

        return element;
    }

    public void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        actions.moveToElement(element).perform();

        wait.until(ExpectedConditions.elementToBeClickable(locator));
        actions.scrollToElement(element).perform();
        element.click();
    }

    public void click(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        actions.moveToElement(element).perform();

        wait.until(ExpectedConditions.elementToBeClickable(element));
        actions.scrollToElement(element).perform();
        element.click();
    }

    public void checkCookies(By locator) {
        try {
            WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement element = driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            click(element);
        } catch (TimeoutException _) {
        }
    }

    public void radioRandomSelect(By locator) {
        List<WebElement> radios = driver.findElements(locator);

        Random random = new Random();
        int randomIndex = random.nextInt(radios.size());

        WebElement selector = radios.get(randomIndex);
        selector.click();
    }

    public void dropdownRandomSelect(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        Select selects = new Select(element);

        List<WebElement> options = selects.getOptions();

        Random random = new Random();
        int randomIndex = random.nextInt(options.size());

        selects.selectByIndex(randomIndex);
    }

    public void selectCustomDropdown(By locator, String value) {
        WebElement input = driver.findElement(locator);
        try {
            input.click();

            input.clear();
            input.sendKeys(value);

            actions.sendKeys(Keys.ENTER).perform();

        } catch (Exception e) {
            System.out.println("❌ Failed to select value '" + value + "' from custom dropdown");
            e.printStackTrace();
        }
    }

    public void dropdownIndexSelect(By locator, int index) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        Select selects = new Select(element);

        selects.selectByIndex(index);
    }

    public String getAttribute(By locator, String attribute) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getAttribute(attribute);
    }

    public void navigateToAndVerify(String url) {
        driver.navigate().to(url);

        String currentURL = driver.getCurrentUrl();

        assert currentURL != null;
        if (currentURL.equals(url)) {
            System.out.println("Navigated Successfully to : " + url);
        } else {
            System.out.println("loginPageURL mismatch!...");
        }
    }

    public boolean ratioAssert(By locator, String title) {
        List<WebElement> elements = driver.findElements(locator);

        int found = 0;
        int size = elements.size();

        for (WebElement element : elements) {
            String productTitle = element.getText().toLowerCase();

            if (productTitle.contains(title)) {
                found++;
            }
        }

        return size / 4 < found;
    }

    public boolean ratioAssert(By locator, String title, String attribute) {
        List<WebElement> elements = driver.findElements(locator);

        int found = 0;
        int size = elements.size();

        for (WebElement element : elements) {
            String productTitle = element.getAttribute(attribute);

            assert productTitle != null;
            if (productTitle.contains(title)) {
                found++;
            }
        }

        return size / 4 < found;
    }

    public void randomClick(By locator) {
        List<WebElement> elements = driver.findElements(locator);

        Random random = new Random();
        int randomIndex = random.nextInt(elements.size());

        WebElement selector = elements.get(randomIndex);
        ClickUtils.guaranteedClick(selector);
    }

    public boolean verifySort(By locator) {
        List<WebElement> elements = driver.findElements(locator);

        int price, priceNext;
        boolean check = false;

        for (int i = 0; i < elements.size() - 1; i++) {
            price = (int) readDigits(elements.get(i).getText());
            priceNext = (int) readDigits(elements.get(i + 1).getText());

            if (price < priceNext) check = true;
        }

        return check;
    }

    public boolean verifyRange(By locator, String min, String max) {
        List<WebElement> elements = driver.findElements(locator);

        int checkCount = 0;

        for (WebElement element : elements) {
            double face = readDigits(element.getText());

            if (face < Double.parseDouble(max) && face > Double.parseDouble(min)) {
                checkCount++;
            }
        }
        return checkCount > 0.9 * elements.size();
    }

    public boolean verifyRange(By locator, String attribute, String min, String max) {
        List<WebElement> elements = driver.findElements(locator);

        int checkCount = 0;

        for (WebElement element : elements) {
            double face = readDigits(Objects.requireNonNull(element.getAttribute(attribute)));

            if (face <= Double.parseDouble(max) && face >= Double.parseDouble(min)) {
                checkCount++;
            }
        }

        return checkCount > 0.9 * elements.size();
    }

    public String getElementsSize(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        List<WebElement> elements = driver.findElements(locator);
        return String.valueOf(elements.size());
    }

    public String[] getElementsText(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        String[] values = new String[elements.size()];

        for (int i = 0; i < elements.size(); i++) {
            values[i] = elements.get(i).getText();
        }

        return values;
    }

    public static boolean isTextExists(
            By listLocator,
            int index,
            String expectedText
    ) {
        List<WebElement> elements = driver.findElements(listLocator);

        if (elements.isEmpty()) return false;

        if (index >= 0) {
            if (index >= elements.size()) return false;
            return elements.get(index).getText().contains(expectedText);
        }

        // index = -1 → search in all
        for (WebElement element : elements) {
            if (element.getText().contains(expectedText)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPageContains(String text){
        String locator = "//*[normalize-space()='"+text+"']";

        return driver.findElement(By.xpath(locator)).getText().contains(text);
    };

    public boolean checkDisplayed(By locator ){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return  element.isDisplayed();
    }
}
