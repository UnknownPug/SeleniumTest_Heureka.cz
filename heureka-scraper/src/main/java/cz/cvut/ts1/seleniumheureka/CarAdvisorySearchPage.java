package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CarAdvisorySearchPage extends Page {

  @FindBy(how = How.XPATH, using = "//a[@title='Jak vybrat auto?']")
  private WebElement howToChooseAuto;

  public CarAdvisorySearchPage(WebDriver driver) {
    super(driver);
  }

  public HowToChooseAutoPage navToHowToChooseAuto() {
    driverWait.until(ExpectedConditions.visibilityOf(howToChooseAuto));
    howToChooseAuto.click();
    return new HowToChooseAutoPage(driver);
  }
}
