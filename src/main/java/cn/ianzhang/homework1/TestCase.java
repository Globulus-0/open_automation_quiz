package cn.ianzhang.homework1;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class TestCase {

    private static Logger logger = Logger.getLogger(TestCase.class);

    private RemoteWebDriver driver;

    @BeforeClass
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "src/main/Drive/chromedriver.exe");
        RemoteWebDriver webDriver = null;
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--ignore-certificate-errors");
//        DesiredCapabilities chrome = DesiredCapabilities.chrome();
//        chrome.setCapability(ChromeOptions.CAPABILITY, options);
//        chrome.setCapability("acceptInsecureCerts", true);
//        webDriver = new ChromeDriver(chrome);
        webDriver = new ChromeDriver();
        driver = webDriver;
        driver.manage().window().maximize();
    }

    @Test
    public void test() throws InterruptedException {
        driver.get("https://www.bing.com/?mkt=zh-CN");
        sendKey("Globulus");
        clickSearch();
        clickPageTwo();
        List<String> title = getTitle();
        List<String> link = getLink();
        Map<String, String> map = IntStream.range(0, title.size()).collect(HashMap::new, (m, i) -> m.put(title.get(i), link.get(i)), (m, n) -> {
        });
        System.out.println("=====================结果=======================");
        map.keySet().forEach(s -> {
            System.out.println(s + "-->" + map.get(s));
        });
        List<String> domain = new ArrayList<>();
        for (String s : link) {
            String s1 = Until.urlTest(s);
            domain.add(s1);
        }
        System.out.println("===============统计==================");
        Map<String, Long> numbers = Until.getNumbers(domain);
        numbers.keySet().forEach(s -> {
            System.out.println(s + "-->" + numbers.get(s));
        });
    }

    @AfterClass
    public void close() {
        driver.close();
    }

    public void scrollElement(By by) {
        WebElement element = waitElementVisible(driver, by, 5);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        logger.info("滚动到指定元素");
    }

    public void sendKey(String name) {
        waitElementVisible(driver, By.id("sb_form_q"), 20).sendKeys(name);
    }

    public void clickSearch() {
        waitElementVisible(driver, By.id("search_icon"), 20).click();
    }

    public void clickPageTwo() {
        WebElement element = waitElementVisible(driver, By.xpath("//a[contains(@class,'b_widePag sb_bp') and text()='2']"), 10);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public List<String> getTitle() {
        List<String> list = new ArrayList<>();
        List<WebElement> elements = driver.findElements(By.xpath("//ol[@id='b_results']//h2/a"));
        for (WebElement element : elements) {
            list.add(element.getText());
        }
        return list;
    }

    public List<String> getLink() {
        List<String> list = new ArrayList<>();
        List<WebElement> elements = driver.findElements(By.xpath("//ol[@id='b_results']//h2/a"));
        for (WebElement element : elements) {
            list.add(element.getAttribute("href"));
        }
        return list;
    }

    public WebElement waitElementVisible(WebDriver driver, By by, int time) {
        WebElement webElement = null;
        try {
            WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(time));
            webElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            logger.error("定位元素【" + by + "】异常");
        }
        return webElement;
    }

    public WebElement waitElementClickble(WebDriver driver, By by, int time) {
        WebElement webElement = null;
        try {
            WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(time));
            webElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            logger.error("定位元素【" + by + "】异常");
        }
        return webElement;
    }
}
