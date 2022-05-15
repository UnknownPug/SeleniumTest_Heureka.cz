package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UserMainPage extends Page {

  // Laptops search
  @FindBy(
    how = How.XPATH,
    using = "//span[text()='Elektronika']//ancestor::a[@class='c-categories-list__link']"
  )
  private WebElement electronicsSearch;

  @FindBy(how = How.XPATH, using = "//a[@href='https://notebooky.heureka.cz/']") // This should be fine, but it would be better to change it
  private WebElement laptopSearch;

  // Car search
  @FindBy(
    how = How.XPATH,
    using = "//span[text()='Auto-moto']//ancestor::a[@class='e-heading-link']"
  )
  private WebElement carSearch;

  @FindBy(how = How.XPATH, using = "//span[text()='Auta']")
  private WebElement chooseCar;

  // User profile
  @FindBy(
    how = How.XPATH,
    using = "//span[@class='c-user-controls__icon-container']" +
    "//ancestor::li[@class='c-user-controls__item c-user-controls__item--user']"
  )
  private WebElement clickOnProfile;

  @FindBy(how = How.XPATH, using = "//span[text()='Moje Heureka']")
  private WebElement openHeurekaProfile;

  public UserMainPage(WebDriver driver) {
    super(driver);
  }

  public LaptopsSearchPage navToLaptopsPage() {
    driverWait.until(ExpectedConditions.visibilityOf(electronicsSearch));
    electronicsSearch.click();
    driverWait.until(ExpectedConditions.visibilityOf(laptopSearch));
    jsClick(laptopSearch);
    return new LaptopsSearchPage(driver);
  }

  public ChooseSkodaAutoPage navToCarPage() {
    driverWait.until(ExpectedConditions.visibilityOf(carSearch));
    jsClick(carSearch);
    driverWait.until(ExpectedConditions.visibilityOf(chooseCar));
    jsClick(chooseCar);
    return new ChooseSkodaAutoPage(driver);
  }

  public ProfilePage navToHeurekaProfile() {
    driverWait.until(ExpectedConditions.visibilityOf(clickOnProfile));
    clickOnProfile.click();
    driverWait.until(ExpectedConditions.visibilityOf(openHeurekaProfile));
    jsClick(openHeurekaProfile);
    return new ProfilePage(driver);
  }
}
