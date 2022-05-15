package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BasketPage extends Page {

  @FindBy(
    how = How.XPATH,
    using = "//a[@class='c-product-card__close c-modal__toggle js-modal__toggle e-action']"
  )
  private WebElement deleteProductFromBasketLink;

  @FindBy(how = How.XPATH, using = "//a[@class='e-button e-button--negative']")
  private WebElement confirmDeleteLink;

  @FindBy(how = How.XPATH, using = "//a[@class='e-button']")
  private WebElement goToMainPageLink;

  public BasketPage(WebDriver driver) {
    super(driver);
  }

  public UserMainPage goToMainPage() {
    driverWait.until(
      ExpectedConditions.visibilityOf(deleteProductFromBasketLink)
    );
    deleteProductFromBasketLink.click();
    driverWait.until(ExpectedConditions.visibilityOf(confirmDeleteLink));
    confirmDeleteLink.click();
    driverWait.until(ExpectedConditions.visibilityOf(goToMainPageLink));
    goToMainPageLink.click();
    return new UserMainPage(driver);
  }
}
