package cz.cvut.ts1.seleniumheureka;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage extends Page {

  @FindBy(
    how = How.XPATH,
    using = "//span[text()='Odhlásit se']//ancestor::a[@class='l-user-nav__link']"
  )
  private WebElement exit;

  public ProfilePage(WebDriver driver) {
    super(driver);
  }

  public void logOut() {
    WebDriverWait driverWait = new WebDriverWait(
      driver,
      Duration.ofSeconds(10)
    );
    driverWait.until(ExpectedConditions.visibilityOf(exit));
    jsClick(exit);
  }
}
