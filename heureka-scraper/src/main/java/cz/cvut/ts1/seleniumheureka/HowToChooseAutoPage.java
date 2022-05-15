package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HowToChooseAutoPage extends Page {

  @FindBy(how = How.XPATH, using = "//a[text()='Vybrat auta']")
  private WebElement chooseCar;

  public HowToChooseAutoPage(WebDriver driver) {
    super(driver);
  }

  public UserMainPage goBackToMainPage() {
    driverWait.until(ExpectedConditions.visibilityOf(chooseCar));
    jsClick(chooseCar);
    return new UserMainPage(driver);
  }
}
