package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Optional;


public class BaseClass {
    public static WebDriver driver;
    public Logger logger;
    public static Properties p;

    @BeforeClass(groups = {"Sanity" , "Regression" , "Master"} )
    @Parameters({"os" , "browser"})
    public void setup(@Optional("Windows") String os , String br) throws IOException {
    	FileReader file = new FileReader("./src//test//resources//config_properties");
    	p = new Properties();
    	p.load(file);
    	
        logger = LogManager.getLogger(this.getClass());
        
        if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
            DesiredCapabilities cap = new DesiredCapabilities();
            if (os.equalsIgnoreCase("windows")) {
                cap.setPlatform(Platform.WIN11);
            } else if (os.equalsIgnoreCase("mac")) {
                cap.setPlatform(Platform.MAC);
            } else {
                logger.error("Unsupported OS specified.");
                return;
            }

            switch (br.toLowerCase()) {
                case "chrome":
                    cap.setBrowserName("chrome");
                    break;
                case "edge":
                    cap.setBrowserName("edge");
                    break;
                case "firefox":
                    cap.setBrowserName("firefox");
                    break;
                default:
                    logger.error("Unsupported browser specified.");
                    return;
            }

            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
            logger.info("Remote WebDriver initialized with browser: " + br);
        } else if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
            switch (br.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                default:
                    logger.error("Unsupported browser specified for local execution.");
                    return;
            }
            logger.info("Local WebDriver initialized with browser: " + br);
        }

        // Browser configuration
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://tutorialsninja.com/demo/"); // Replace with your application URL
        logger.info("Navigated to application URL.");
    }

    @AfterClass(groups = {"Sanity" , "Regression" , "Master"})
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }

    // Utility methods for random data generation
    public String randomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomAlphaNumeric() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    public String randomNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }
    public String captureScreen(String tname) throws IOException {
        // Generate a timestamp to make the screenshot file name unique
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // Cast the WebDriver instance to TakesScreenshot
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;

        // Capture the screenshot and store it in a temporary file
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        // Define the target file path for the screenshot
        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";

        // Create a new file object for the target file
        File targetFile = new File(targetFilePath);

        // Move the temporary file to the target location
        FileUtils.copyFile(sourceFile, targetFile);

        // Return the target file path for reference
        return targetFilePath;
    }

}
