package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ChooseSkodaAutoPage extends Page {

  @FindBy(
    how = How.XPATH,
    using = "//span[text()='Detail produktu']" + //FIXME: don't use hardcoded href so we can test multiple cars?
    "//ancestor::a[@href='https://auta.heureka.cz/skoda-octavia-2_0-tsi-manual-rs_160/']"
  )
  private WebElement carInfoLink;

  public ChooseSkodaAutoPage(WebDriver driver) {
    super(driver);
  }

  public SkodaAutoDetails skodaAutoDetails() {
    driverWait.until(ExpectedConditions.visibilityOf(carInfoLink));
    carInfoLink.click();
    return new SkodaAutoDetails(driver);
  }
}
