package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LaptopsSearchPage extends Page {

  @FindBy(how = How.XPATH, using = "//input[@id='price-4']")
  private WebElement choosePrice;

  @FindBy(how = How.XPATH, using = "//input[@id='review-1']")
  private WebElement bestReview;

  @FindBy(how = How.XPATH, using = "//input[@id='availability-1']")
  private WebElement available;

  @FindBy(how = How.XPATH, using = "//button[text()='Zobrazit další možnosti']")
  private WebElement showListOfLaptops;

  @FindBy(how = How.XPATH, using = "//a[text()='Apple']")
  private WebElement selectManufacturer;

  @FindBy(how = How.XPATH, using = "//a[text()='Procesor']")
  private WebElement openProcessor;

  @FindBy(how = How.XPATH, using = "//a[text()='Apple M1']")
  private WebElement selectProcessor;

  public LaptopsSearchPage(WebDriver driver) {
    super(driver);
  }

  public LaptopPickModelPage laptopInfo() {
    driverWait.until(ExpectedConditions.visibilityOf(choosePrice));
    jsClick(choosePrice);
    jsClick(bestReview);
    jsClick(available);
    jsClick(showListOfLaptops);
    driverWait.until(ExpectedConditions.visibilityOf(selectManufacturer));
    jsClick(selectManufacturer);
    jsClick(openProcessor);
    driverWait.until(ExpectedConditions.visibilityOf(selectProcessor));
    jsClick(selectProcessor);
    return new LaptopPickModelPage(driver);
  }
}
