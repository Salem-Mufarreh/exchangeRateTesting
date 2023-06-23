package QA.Project.Tests;// Generated by Selenium IDE
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class FailedConversionTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  private ExtentReports extent;
  private ExtentTest test;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("build/reports/ExtentHtmlReport/Failed-report.html");
    extent = new ExtentReports();
    extent.attachReporter(htmlReporter);
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    extent.flush();
    driver.quit();
  }
  @Test
  public void failedConversion() {
    test = extent.createTest("Failed Conversion Test", "Verify Failed currency conversion");
    test.log(Status.INFO, "Starting the WebDriver");
    driver.get("http://localhost:8080/");
    test.log(Status.INFO, "Entering amount");
    driver.manage().window().setSize(new Dimension(889, 760));
    driver.findElement(By.id("amount")).click();
    driver.findElement(By.id("amount")).sendKeys("100");
    test.log(Status.INFO, "Entering Source Currency");
    driver.findElement(By.id("from")).sendKeys("USDa");
    test.log(Status.INFO, "Entering Target Currency");
    driver.findElement(By.id("to")).sendKeys("ILS");
    test.log(Status.INFO, "Click Convert Button");
    driver.findElement(By.cssSelector(".btn")).click();
    try {
      {
        List<WebElement> elements = driver.findElements(By.id("toastr"));
        assert (elements.size() > 0);
      }
      assertThat(driver.findElement(By.id("toastr")).getText(), is("Conversion Failed"));
      test.log(Status.PASS, "Conversion was Unsuccessful");
    }catch (Exception ex){
      test.log(Status.FAIL, "Error During Conversion");
    }
  }
}
