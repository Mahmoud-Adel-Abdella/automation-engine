package engine.utils;

import engine.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class ClickUtils extends BasePage {

    public ClickUtils(WebDriver driver) {
        super(driver);
    }

    public static void guaranteedClick( By locator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions actions = new Actions(driver);

        java.util.function.Supplier<Boolean> tryClickInCurrentContext = () -> {
            try {
                WebElement element = null;
                try {
                    element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                } catch (Exception ignored) {
                    element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                }

                js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);

                try {
                    element.click();
                    return true;
                } catch (ElementClickInterceptedException | MoveTargetOutOfBoundsException e) {
                } catch (StaleElementReferenceException sere) {
                    return false;
                }

                try {
                    actions.moveToElement(element).pause(Duration.ofMillis(200)).click().perform();
                    return true;
                } catch (Exception ignored) {}

                try {
                    js.executeScript("arguments[0].click();", element);
                    return true;
                } catch (Exception ignored) {}

                try {
                    String script =
                            "const el = arguments[0];" +
                                    "var ev = new MouseEvent('click', {bubbles: true, cancelable: true, view: window});" +
                                    "el.dispatchEvent(ev);";
                    js.executeScript(script, element);
                    return true;
                } catch (Exception ignored) {}

                try {
                    WebElement el = driver.findElement(locator);
                    String type = el.getAttribute("type");
                    if (type != null && (type.equalsIgnoreCase("checkbox") || type.equalsIgnoreCase("radio"))) {
                        js.executeScript("arguments[0].checked = true; arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", el);
                        return true;
                    }
                } catch (Exception ignored) {}

            } catch (Exception e) {
            }
            return false;
        };

        if (tryClickInCurrentContext.get()) {
            System.out.println("✅ Click succeeded in current context for: " + locator);
            return;
        }

        List<WebElement> frames = driver.findElements(By.tagName("iframe"));
        for (int i = 0; i < frames.size(); i++) {
            try {
                driver.switchTo().defaultContent();
                driver.switchTo().frame(frames.get(i));
                if (tryClickInCurrentContext.get()) {
                    System.out.println("✅ Click succeeded inside iframe index " + i + " for: " + locator);
                    driver.switchTo().defaultContent();
                    return;
                }
            } catch (Exception ignored) {
            } finally {
                driver.switchTo().defaultContent();
            }
        }

        try {
            String shadowQueryScript =
                    "function queryDeep(selector){" +
                            "  const find = (root) => {" +
                            "    const el = root.querySelector(selector);" +
                            "    if (el) return el;" +
                            "    const children = root.querySelectorAll('*');" +
                            "    for (let i=0;i<children.length;i++){" +
                            "      const child = children[i];" +
                            "      if (child.shadowRoot) {" +
                            "        const found = find(child.shadowRoot);" +
                            "        if (found) return found;" +
                            "      }" +
                            "    }" +
                            "    return null;" +
                            "  }" +
                            "  return find(document);" +
                            "}" +
                            "return queryDeep(arguments[0]);";
            String selector = locatorToCss(locator);
            if (selector != null) {
                Object shadowEl = js.executeScript(shadowQueryScript, selector);
                if (shadowEl instanceof WebElement) {
                    WebElement real = (WebElement) shadowEl;
                    try {
                        js.executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", real);
                        System.out.println("✅ Click succeeded in Shadow DOM for: " + locator);
                        return;
                    } catch (Exception ignored) {}
                }
            }
        } catch (Exception ignored) {}

        System.out.println("❌ guaranteedClick FAILED for: " + locator);
    }

    private static String locatorToCss(By locator) {
        try {
            String s = locator.toString();
            if (s.startsWith("By.cssSelector: ")) {
                return s.replace("By.cssSelector: ", "").trim();
            }
            if (s.startsWith("By.selector: ")) { // بعض الإصدارات
                return s.replace("By.selector: ", "").trim();
            }
        } catch (Exception ignored) {}
        return null;
    }

    public static void guaranteedClick(WebElement elementParam) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions actions = new Actions(driver);

        java.util.function.Supplier<Boolean> tryClickInCurrentContext = () -> {
            try {
                WebElement element = elementParam;

                try {
                    if (!element.isDisplayed() || !element.isEnabled()) {
                    }
                } catch (StaleElementReferenceException sere) {
                    return false;
                } catch (Exception ignored) {}

                try {
                    js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
                } catch (Exception ignored) {}

                try {
                    element.click();
                    return true;
                } catch (ElementClickInterceptedException | MoveTargetOutOfBoundsException e) {

                } catch (StaleElementReferenceException sere) {
                    return false;
                } catch (Exception ignored) {}

                try {
                    actions.moveToElement(element).pause(Duration.ofMillis(200)).click().perform();
                    return true;
                } catch (Exception ignored) {}

                try {
                    js.executeScript("arguments[0].click();", element);
                    return true;
                } catch (Exception ignored) {}

                try {
                    String script =
                            "const el = arguments[0];" +
                                    "var ev = new MouseEvent('click', {bubbles: true, cancelable: true, view: window});" +
                                    "el.dispatchEvent(ev);";
                    js.executeScript(script, element);
                    return true;
                } catch (Exception ignored) {}

                try {
                    String type = element.getAttribute("type");
                    if (type != null && (type.equalsIgnoreCase("checkbox") || type.equalsIgnoreCase("radio"))) {
                        js.executeScript("arguments[0].checked = true; arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", element);
                        return true;
                    }
                } catch (Exception ignored) {}

            } catch (Exception e) {
            }
            return false;
        };

        if (tryClickInCurrentContext.get()) {
            System.out.println("✅ Click succeeded in current context for WebElement");
            return;
        }

        System.out.println("❌ guaranteedClick FAILED for provided WebElement (current context). " +
                "If the element might be inside a different iframe or shadow root, call the overloaded guaranteedClick(By locator) instead.");
    }

}
