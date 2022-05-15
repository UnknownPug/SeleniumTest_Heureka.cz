package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SkodaAutoDetails extends Page {

  @FindBy(how = How.XPATH, using = "//a[@aria-label='Specifikace']")
  private WebElement specifications;

  @FindBy(
    how = How.XPATH,
    using = "//button[text()='Zobrazit další parametry']"
  )
  private WebElement seeMoreSpecs;

  @FindBy(
    how = How.XPATH,
    using = "//*[@id=\"__next\"]/main/div[2]/div[1]/div/div[12]/aside/div/button"
  )
  private WebElement closeButton;

  @FindBy(how = How.XPATH, using = "//a[@aria-label='Recenze']")
  private WebElement review;

  @FindBy(how = How.XPATH, using = "//a[@aria-label='Poradna']")
  private WebElement advisory;

  public SkodaAutoDetails(WebDriver driver) {
    super(driver);
  }

  public CarAdvisorySearchPage advisoryCarSearch() {
    driverWait.until(ExpectedConditions.visibilityOf(specifications));
    jsClick(specifications);
    jsClick(seeMoreSpecs);
    driverWait.until(ExpectedConditions.visibilityOf(closeButton));
    jsClick(closeButton);
    jsClick(review);
    jsClick(advisory);
    return new CarAdvisorySearchPage(driver);
  }
}
