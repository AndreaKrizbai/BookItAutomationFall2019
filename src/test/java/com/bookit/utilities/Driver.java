package com.bookit.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
public class Driver {
    public static final String USERNAME = "andreacybertek1";
    public static final String AUTOMATE_KEY = "dFzQkYy4QTpX7YYyGrb6";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
    //same for everyone
    private static final ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();
    //so no one can create object of Driver class
    //everyone should call static getter method instead
    private Driver() {
    }
    /**
     * synchronized makes method thread safe. It ensures that only 1 thread can use it at the time.
     * <p>
     * Thread safety reduces performance but it makes everything safe.
     *
     * @return
     */
    public synchronized static WebDriver getDriver() {
        //String GRID_URL = "http://35.171.158.59:4444/wd/hub";
        //String GRID_URL = "http://3.236.14.239:4444/wd/hub";
        String GRID_URL = "http://34.204.195.171:4444/wd/hub";
        //if webdriver object doesn't exist
        //create it
        if (driverPool.get() == null) {
            //specify browser type in configuration.properties file
            String browser = ConfigurationReader.getProperty("browser").toLowerCase();
            // -Dbrowser=firefox
            if (System.getProperty("browser") != null) {
                browser = System.getProperty("browser");
            }
            if (System.getProperty("grid_url") != null) {
                GRID_URL = System.getProperty("grid_url");
            }
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    driverPool.set(new ChromeDriver(chromeOptions));
                    break;
                case "chromeheadless":
                    //to run chrome without interface (headless mode)
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.setHeadless(true);
                    driverPool.set(new ChromeDriver(options));
                    break;
                case "chrome-remote":
                    try {
                        //we create object of URL and specify
                        //selenium grid hub as a parameter
                        //make sure it ends with /wd/hub
                        URL url = new URL(GRID_URL);
                        //desiredCapabilities used to specify what kind of node
                        //is required for testing
                        //such as: OS type, browser, version, etc...
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName(BrowserType.CHROME);
                        desiredCapabilities.setPlatform(Platform.ANY);
                        driverPool.set(new RemoteWebDriver(url, desiredCapabilities));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "firefox-remote":
                    try {
                        //we create object of URL and specify
                        //selenium grid hub as a parameter
                        //make sure it ends with /wd/hub
                        URL url = new URL(GRID_URL);
                        //desiredCapabilities used to specify what kind of node
                        //is required for testing
                        //such as: OS type, browser, version, etc...
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName(BrowserType.FIREFOX);
                        desiredCapabilities.setPlatform(Platform.ANY);
                        driverPool.set(new RemoteWebDriver(url, desiredCapabilities));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "safari-remote":
                    try {
                        //we create object of URL and specify
                        //selenium grid hub as a parameter
                        //make sure it ends with /wd/hub
                        URL url = new URL(GRID_URL);
                        //desiredCapabilities used to specify what kind of node
                        //is required for testing
                        //such as: OS type, browser, version, etc...
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName(BrowserType.SAFARI);
                        desiredCapabilities.setPlatform(Platform.ANY);
                        driverPool.set(new RemoteWebDriver(url, desiredCapabilities));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver());
                    break;
                case "browser-stack-chrome":
                    DesiredCapabilities caps = new DesiredCapabilities();
                    caps.setCapability("browser", "Chrome");
                    caps.setCapability("browser_version", "83.0");
                    caps.setCapability("os", "Windows");
                    caps.setCapability("os_version", "10");
                    caps.setCapability("resolution", "1920x1080");
                    caps.setCapability("name", "BookIT Automation");
                    try {
                        driverPool.set(new RemoteWebDriver(new URL(URL), caps));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
//                case "browser-stack-chrome":
//                    DesiredCapabilities caps3 = new DesiredCapabilities();
//                    caps3.setCapability("browser", "Chrome");
//                    caps3.setCapability("browser_version", "83.0");
//                    caps3.setCapability("os", "OS X");
//                    caps3.setCapability("os_version", "Catalina");
//                    caps3.setCapability("resolution", "1024x768");
//                    caps3.setCapability("name", "BookIT Automation with Mac");
//                    try {
//                        driverPool.set(new RemoteWebDriver(new URL(URL), caps3));
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }
//                    break;
                case "browser-stack-safari":
                    DesiredCapabilities caps2 = new DesiredCapabilities();
                    caps2.setCapability("browser", "Safari");
                    caps2.setCapability("browser_version", "11.1");
                    caps2.setCapability("os", "OS X");
                    caps2.setCapability("os_version", "High Sierra");
                    caps2.setCapability("resolution", "1024x768");
                    caps2.setCapability("name", "Bstack-[Java] Sample Test BOOKIT");
                    try {
                        driverPool.set(new RemoteWebDriver(new URL(URL), caps2));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "browser-stack-ios":
                    DesiredCapabilities capsIOS = new DesiredCapabilities();
                    capsIOS.setCapability("browserName", "iPhone");
                    capsIOS.setCapability("device", "iPhone 11 Pro Max");
                    capsIOS.setCapability("realMobile", "true");
                    capsIOS.setCapability("os_version", "13");
                    capsIOS.setCapability("name", "BookIT Automation");
                    try {
                        driverPool.set(new RemoteWebDriver(new URL(URL), capsIOS));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "browser-stack-android":
                    DesiredCapabilities capsAndroid = new DesiredCapabilities();
                    capsAndroid.setCapability("browserName", "android");
                    capsAndroid.setCapability("device", "Samsung Galaxy S20 Ultra");
                    capsAndroid.setCapability("realMobile", "true");
                    capsAndroid.setCapability("os_version", "10.0");
                    capsAndroid.setCapability("name", "BookIT Automation");
                    try {
                        driverPool.set(new RemoteWebDriver(new URL(URL), capsAndroid));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    throw new RuntimeException("Wrong browser name!");
            }
        }
        return driverPool.get();
    }
    public static void closeDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }
}