package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CarSearchPage extends Page {

  @FindBy(how = How.XPATH, using = "//a[@data-gtm-event-label='Škoda Octavia']")
  private WebElement skodaOctaviaLink;

  public CarSearchPage(WebDriver driver) {
    super(driver);
  }

  public ChooseSkodaAutoPage selectSkodaAuto() {
    driverWait.until(ExpectedConditions.visibilityOf(skodaOctaviaLink));
    skodaOctaviaLink.click();
    return new ChooseSkodaAutoPage(driver);
  }
}
