package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WrapperClass {

    public static void click(WebDriver driver, By locator) {
        driver.findElement(locator).click();
    }

    public static void sendKeys(WebDriver driver, By locator, String keys) {
        driver.findElement(locator).sendKeys(keys);
    }

    public static String getText(WebDriver driver, By locator) {
        return driver.findElement(locator).getText();
    }

    public static String getText(WebDriver driver, WebElement webElement) {
        return webElement.getText();
    }

    public static String clickUsingJavaScript(WebDriver driver, By locator) {
        String script = "arguments[0].click();";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script, driver.findElement(locator));
        return "Clicked using JavaScript";
    }

    public static String sendKeysUsingJavaScript(WebDriver driver, By locator, String keys) {
        String script = "arguments[0].value='" + keys + "';";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script, driver.findElement(locator));
        return "Keys sent using JavaScript";
    }

    public static void verifyText(WebDriver driver, By locator, String expectedText) {
        String actualText = getText(driver, locator);
        if (!actualText.equals(expectedText)) {
            throw new AssertionError("Expected text: " + expectedText + ", but found: " + actualText);
        }
    }

    public static void waitForElement(WebDriver driver, By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitForElementToBeClickable(WebDriver driver, By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void scrollToElement(WebDriver driver, By locator) {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = "arguments[0].scrollIntoView(true);";
        js.executeScript(script, element);
    }

    public static void scrollToTop(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = "window.scrollTo(0, 0);";
        js.executeScript(script);
    }

    public static String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public static String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public static void navigateTo(WebDriver driver, String url) {
        driver.get(url);
    }

    public static void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public static void controlPlusClickUsingAction(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).click(element).keyUp(Keys.CONTROL).perform();
    }

    public static void mouseOver(WebDriver driver, By by) {
        WebElement element = driver.findElement(by);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

}
