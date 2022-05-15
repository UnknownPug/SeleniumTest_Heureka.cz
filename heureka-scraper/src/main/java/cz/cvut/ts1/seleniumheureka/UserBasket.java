package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UserBasket extends Page {

    @FindBy(how = How.XPATH, using = "//a[@class='c-product-card__close c-modal__toggle js-modal__toggle e-action']")
    private WebElement deleteProductFromBasket;

    @FindBy(how = How.XPATH, using = "//a[@class='e-button e-button--negative']")
    private WebElement confirmDelete;

    @FindBy(how = How.XPATH, using = "//a[@class='e-button']")
    private WebElement goToMainPage;

    public UserBasket(WebDriver driver) {
        super(driver);
    }

    public UserMainPage goToMainPage() {
        driverWait.until(ExpectedConditions.visibilityOf(deleteProductFromBasket));
        deleteProductFromBasket.click();
        driverWait.until(ExpectedConditions.visibilityOf(confirmDelete));
        confirmDelete.click();
        driverWait.until(ExpectedConditions.visibilityOf(goToMainPage));
        goToMainPage.click();
        return new UserMainPage(driver);
    }
}
