package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends Page {

  public MainPage(WebDriver driver) {
    super(driver);
  }

  public MainPage open() {
    driver.get("https://www.heureka.cz/");
    return this;
  }

  @FindBy(
    how = How.XPATH,
    using = "//span[text()='Elektronika']//ancestor::a[@class='e-heading-link']"
  )
  private WebElement electronicsSearch;

  @FindBy(how = How.XPATH, using = "//a[@href='https://notebooky.heureka.cz/']")
  private WebElement notebookSearch;

  @FindBy(
    how = How.XPATH,
    using = "//div[@id='rootHead']//ul//li[@class='c-user-controls__item c-user-controls__item--user']//a"
  )
  private WebElement loginLink;

  public LaptopsPage laptopsPage() {
    driverWait.until(ExpectedConditions.visibilityOf(electronicsSearch));
    jsClick(electronicsSearch);
    driverWait.until(ExpectedConditions.visibilityOf(notebookSearch));
    jsClick(notebookSearch);
    return new LaptopsPage(driver);
  }

  public LoginPage goToLoginPage() {
    driverWait.until(ExpectedConditions.visibilityOf(loginLink));
    jsClick(loginLink);
    return new LoginPage(driver);
  }
}
