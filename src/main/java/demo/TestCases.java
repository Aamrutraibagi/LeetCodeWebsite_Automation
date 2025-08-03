package demo;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.bouncycastle.jcajce.provider.symmetric.AES.Wrap;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Verify;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    By createAccountButton = By.xpath("//a[contains(., 'Create Account')]");
    By viewQuestions = By.xpath("//a[contains(., 'View Questions')]");
    By Problem1 = By.xpath("//a[contains(., 'Two Sum')]");
    By ListOfQuestions = By
            .xpath("(//div[contains(@class, 'ellipsis line-clamp-1')])[position() > 1 and position() <= 6]");
    By submitButton = By.xpath("//span[text()='Submit']");
    By Login_SignInMessage = By.xpath("(//div[@class='rounded-sd-md relative px-3 py-1.5'])[1]");
    By LoginLink = By.xpath("//a[text()='log in / sign up']");
    By SubmissionTab = By.xpath("(//div[normalize-space(text())='Submissions'])[2]");
    By RegisterOrLoginButton = By.xpath("//a[text()='Register or Log in']");

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(TestCases.class.getName());

    // 🔥 Add this static block here
    static {
        java.util.logging.Logger rootLogger = java.util.logging.Logger.getLogger("");
        rootLogger.setLevel(Level.ALL);
        for (java.util.logging.Handler handler : rootLogger.getHandlers()) {
            handler.setLevel(Level.ALL);
        }
    }

    ChromeDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://leetcode.com/");
    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    public void testCase01() throws InterruptedException {
        System.out.println("Start Test case: testCase01");

        // Add your test case logic here
        // Navigate to the Leetcode website ( https://leetcode.com/ ).
        // Verify that the URL contains "leetcode"

        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains("LeetCode"));

        String url = WrapperClass.getCurrentUrl(driver);
        if (url.contains("leetcode.com")) {
            logger.info("Successfully navigated to LeetCode");
        } else {
            logger.warning("Failed to navigate to LeetCode, current URL: " + url);
        }
        System.out.println("end Test case: testCase01");
    }

    // Add your test case logic here
    // Click on the "Questions" link on the Leetcode homepage.
    // Verify that you are on the problem set page, by checking the URL contains
    // "problemset".
    // Retrieve the details of the first 5 questions and print them.
    // Make sure to check that the title of each question is correct and that you
    // are selecting the questions from the first problem, i.e., "Two Sum".
    public void testCase02() {
        System.out.println("Start Test case: testCase02");
        WrapperClass.waitForElementToBeClickable(driver, viewQuestions, 10);
        // Click on the "Questions" link
        WrapperClass.clickUsingJavaScript(driver, viewQuestions);

        // Verify that we are on the problem set page
        String url = WrapperClass.getCurrentUrl(driver);
        if (url.contains("problemset")) {
            System.out.println("Successfully navigated to Problem Set");
        } else {
            System.out.println("Failed to navigate to Problem Set, current URL: " + url);
        }

        WrapperClass.waitForElementToBeClickable(driver, Problem1, 10);
        // Retrieve and print details of the first 5 questions
        List<WebElement> questions = driver.findElements(ListOfQuestions);
        System.out.println("Retrieving details of the first 5 questions...");

        // get the main window
        String mainWindow = driver.getWindowHandle();
        for (int i = 0; i < 5; i++) {
            // Here we are just printing a placeholder message
            System.out.println("Question " + (i) + " : " + WrapperClass.getText(driver,
                    questions.get(i)));
            String questionText = WrapperClass.getText(driver, questions.get(i));

            // Open new window using ActionsClass
            // This will open the question in a new tab without losing the main window
            WrapperClass.controlPlusClickUsingAction(driver, questions.get(i));

            // Switch to the new window
            Set<String> windowHandles = driver.getWindowHandles();
            windowHandles.remove(mainWindow); // Remove main window handle to get the new one

            String newWindowHandle = windowHandles.iterator().next();
            driver.switchTo().window(newWindowHandle);

            // Wait for the page to load and check title
            String pageTitle = WrapperClass.getPageTitle(driver);
            if (questionText.contains(pageTitle.split(" - ")[0])) {
                System.out.println("Successfully navigated to question: " + questionText);
            } else {
                System.out.println(
                        "Failed to navigate to question: " + questionText + ", current page title: "
                                + pageTitle);
            }

            // close the current window
            driver.close();
            // Switch back to the main window
            driver.switchTo().window(mainWindow);

        }

        System.out.println("end Test case: testCase02");

    }

    // Add your test case logic here
    // Open the first problem i.e, Two Sum.
    // Verify that the URL contains "two-sum"
    // Expected Result: The URL of the problem contains "two-sum"

    public void testCase03() {
        System.out.println("Start Test case: testCase03");
        WrapperClass.waitForElementToBeClickable(driver, Problem1, 10);
        // Click on the "Two Sum" problem link
        WrapperClass.clickUsingJavaScript(driver, Problem1);
        // Verify that the URL contains "two-sum"
        String url = WrapperClass.getCurrentUrl(driver);
        if (url.contains("two-sum")) {
            System.out.println("Successfully navigated to Two Sum problem");
        } else {
            System.out.println("Failed to navigate to Two Sum problem, current URL: " + url);
        }
        System.out.println("end Test case: testCase03");
    }

    // Add your test case logic here
    // Click on the submission tab.
    // Verify that it displays "Register or Login"
    public void testCase04() throws InterruptedException {
        System.out.println("Start Test case: testCase04");

        WrapperClass.waitForElementToBeClickable(driver, submitButton, 0);
        WrapperClass.mouseOver(driver, submitButton);
        String LoginText = WrapperClass.getText(driver, Login_SignInMessage);
        // Verify that it displays "Register or Login"
        System.out.println("Login Or SignIn Text If not Logged In: " + LoginText);
        WrapperClass.click(driver, submitButton);

        // Click on the Submission tab
        Thread.sleep(2000);
        WrapperClass.waitForElement(driver, SubmissionTab, 1);
        WrapperClass.click(driver, SubmissionTab);
        WrapperClass.waitForElement(driver, RegisterOrLoginButton, 1);
        System.out.println("Login Or Register Button Text If not Logged In: "
                + WrapperClass.getText(driver, RegisterOrLoginButton));
        if (driver.findElement(RegisterOrLoginButton).isDisplayed()) {
            System.out.println("Register or Login button is visible");
        } else {
            System.out.println("Register or Login button is not visible");
        }

        // Click on the Login link
        // WrapperClass.clickUsingJavaScript(driver, LoginLink);
        // String LoginURL = WrapperClass.getCurrentUrl(driver);
        // if (LoginURL.contains("login")) {
        // System.out.println("Successfully navigated to Login page");
        // } else {
        // System.out.println("Failed to navigate to Login page, current URL: " +
        // LoginURL);
        // }

        System.out.println("end Test case: testCase04");
    }

}
