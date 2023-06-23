package QA.Project;// Generated by Selenium IDE

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SuccessConversionTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void successConversion() {
    driver.get("http://localhost:8080/");
    driver.manage().window().setSize(new Dimension(887, 760));
    driver.findElement(By.id("amount")).click();
    driver.findElement(By.id("amount")).sendKeys("100");
    driver.findElement(By.id("from")).sendKeys("USD");
    driver.findElement(By.id("to")).sendKeys("ILS");
    driver.findElement(By.cssSelector(".btn")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toastr")));
    }
    assertThat(driver.findElement(By.id("toastr")).getText(), is("Conversion successful"));
  }
}
