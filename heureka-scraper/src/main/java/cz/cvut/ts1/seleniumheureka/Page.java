package cz.cvut.ts1.seleniumheureka;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {

  protected final WebDriver driver;
  protected WebDriverWait driverWait;

  public Page(WebDriver driver) {
    this.driver = driver;
    driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    PageFactory.initElements(this.driver, this);
  }

  protected void jsClick(WebElement element) {
    JavascriptExecutor executor = (JavascriptExecutor) driver;
    executor.executeScript("arguments[0].click();", element);
  }
}
