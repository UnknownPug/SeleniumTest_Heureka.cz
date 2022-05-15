package cz.cvut.ts1.seleniumheureka;

import java.time.Duration;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

  public Boolean isThereACookiePopUp() {
    driverWait.withTimeout(Duration.ofSeconds(1));
    return !driver
      .findElements(By.xpath("//div[@id='didomi-popup']"))
      .isEmpty();
  }

  final String profileLableXpath =
    "//div[@id='rootHead']//ul//li[@class='c-user-controls__item c-user-controls__item--user']" +
    "//a//span[@class='c-user-controls__label']";

  @FindBy(how = How.XPATH, using = profileLableXpath)
  private WebElement profileLabel;

  public Boolean isLoggedIn() throws InterruptedException {
    driverWait.until(ExpectedConditions.visibilityOf(profileLabel));
    Thread.sleep(500);
    System.out.println("User name: " + profileLabel.getText());
    return profileLabel.getText().compareTo("Přihlásit se") != 0;
  }

  public String getUserName() {
    driverWait.until(
      ExpectedConditions.and(
        ExpectedConditions.visibilityOf(profileLabel),
        ExpectedConditions.textMatches(
          By.xpath(profileLableXpath),
          Pattern.compile(".+@.+\\..+")
        )
      )
    );
    return profileLabel.getText();
  }

  @FindBy(how = How.XPATH, using = "//button[@id='didomi-notice-agree-button']")
  private WebElement cookieAgreeButton;

  public void acceptCookies() {
    driverWait.until(ExpectedConditions.visibilityOf(cookieAgreeButton));
    jsClick(cookieAgreeButton);
  }
}
