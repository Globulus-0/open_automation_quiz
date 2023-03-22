package cn.ianzhang.homework2;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import org.apache.log4j.Logger;

public class ExampleStepDefs {

    private static Logger logger = Logger.getLogger(ExampleStepDefs.class);

    private WebDriver driver;

    @When("用户打开企业复工申请资料提交表 \"([^\\\"]*)\"")
    public void openUrl(String arg0) {
        driver.get(arg0);
    }

    @When("^用户在第一页选择 \"([^\\\"]*)\"")
    public void userChoose(String arg0) {
        String xpath = "//span[contains(text(),'%s')]/../..//input";
        String format = String.format(xpath, arg0);
        By checkbox = By.xpath(format);
        driver.findElement(checkbox).click();
    }

    @And("用户截图 \"([^\\\"]*)\"")
    public void pngFrist(String arg0) {
        getScreenShot(arg0);
    }

    @And("用户点击下一页按钮")
    public void clicknext() {
        driver.findElement(By.xpath("//*[@id=\"root\"]//button/span[text()='下一页']/..")).click();
    }

    @Then("选中了 \"([^\\\"]*)\"")
    public void ischeck(String arg0) {
        String xpath = "//span[contains(text(),'%s')]/../..//input";
        String format = String.format(xpath, arg0);
        By xpath1 = By.xpath(format);
        boolean selected = driver.findElement(xpath1).isSelected();
        Assert.assertEquals(true, selected);
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

    public void getScreenShot(String name) {
        try {
            File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(file, new File(String.format("src/main/resources/png/%s.png", name)));
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    public String getData() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        return simpleDateFormat.format(date);
    }

    @Then("关闭浏览器")
    public void close() {
        driver.close();
    }

    @And("填写申请日期运行脚本的当年元旦日期")
    public void addDataInfo() {
        String data = getData();
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/form/div[2]/div/div[4]/div/div/div[2]/div[1]/div/div/div/div/span/input")).sendKeys(data);
    }

    @And("填写申请人 \"([^\\\"]*)\"")
    public void addUser(String arg0) {
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/form/div[2]/div/div[6]/div/div/div[2]/div[1]/div/div/div/span/input")).sendKeys(arg0);
    }

    @And("填写联系方式 \"([^\\\"]*)\"")
    public void addPhone(String arg0) {
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/form/div[2]/div/div[8]/div/div/div[2]/div[1]/div/div/div/div/span/input")).sendKeys(arg0);
    }

    @When("滑动到底部")
    public void scrossToEnd() {
        driver.switchTo().defaultContent();
        By frame = By.xpath("//*[@class='iframe iframe']");
        // 切换到 iframe
        WebElement iframe = driver.findElement(frame);
        driver.switchTo().frame(iframe);
        // 执行滚动操作
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    @Then("^等待 (\\d+) 秒")
    public void waitTime(int arg0) throws InterruptedException {
        Thread.sleep(arg0 * 1000);
    }

    @Given("打开浏览器")
    public void openDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/Drive/chromedriver.exe");
        RemoteWebDriver webDriver = null;
        /*ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        DesiredCapabilities chrome = DesiredCapabilities.chrome();
        chrome.setCapability(ChromeOptions.CAPABILITY, options);
        chrome.setCapability("acceptInsecureCerts", true);
        webDriver = new ChromeDriver(chrome);*/
        webDriver = new ChromeDriver();
        driver = webDriver;
        driver.manage().window().maximize();
    }

    @And("填写单位 \"([^\\\"]*)\"")
    public void addUnit(String arg0) {
        driver.findElement(By.xpath(
                "//*[@id=\"root\"]/div/form/div[2]/div/div[4]/div/div/div[2]/div[1]/div/div/div/span/input"
        )).sendKeys(arg0);
    }

    @And("填写人数 (\\d+)")
    public void addPersonNumber(int arg0) {
        driver.findElement(By.xpath(
                "//*[@id=\"root\"]/div/form/div[2]/div/div[6]/div/div/div[2]/div[1]/div/div/div/div/div/div/div[2]/input"
        )).sendKeys(String.valueOf(arg0));
    }

    @And("填写湖北籍员工、前往湖北以及与湖北人员密切接触的员工（人数） (\\d+)")
    public void addSpecialNmber(int arg0) {
        driver.findElement(By.xpath(
                "//*[@id=\"root\"]/div/form/div[2]/div/div[10]/div/div/div[2]/div[1]/div/div/div/div/div/div/div[2]/input"
        )).sendKeys(String.valueOf(arg0));
    }

    @And("填写时间测试时间")
    public void addTestTime() {
        driver.findElement(By.xpath(
                "//*[@id=\"root\"]/div/form/div[2]/div/div[8]/div/div/div[2]/div[1]/div/div/div/div/span/input"
        )).sendKeys(getData());
    }

    @And("填写单位负责人 \"([^\\\"]*)\"")
    public void addManageName(String arg0) {
        driver.findElement(By.xpath(
                "//*[@id=\"root\"]/div/form/div[2]/div/div[12]/div/div/div[2]/div[1]/div/div/div/span/input"
        )).sendKeys(arg0);
    }

    @And("填写疫情防控方案 \"([^\\\"]*)\"")
    public void addText(String arg0) {
        driver.findElement(By.xpath(
                "//*[@id=\"root\"]/div/form/div[2]/div/div[16]/div/div/div[2]/div[1]/div/div[2]/div/span/textarea"
        )).sendKeys(arg0);
    }

    @And("填写新联系方式 \"([^\\\"]*)\"")
    public void addPhoneNumber(String arg0) {
        driver.findElement(By.xpath(
                " //*[@id=\"root\"]/div/form/div[2]/div/div[14]/div/div/div[2]/div[1]/div/div/div/div/span/input"
        )).sendKeys(arg0);
    }

    @And("点击提交")
    public void addCommit() {
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/form/div[3]/div[1]/button[2]")).click();
    }

    @Then("提交成功验证")
    public void addSuccess() {
        String text = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[1]")).getText();
        boolean flag = text.contains("提交成功");
        Assert.assertEquals(true, flag);
    }
}
