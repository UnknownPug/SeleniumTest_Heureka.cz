package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LaptopPageDescription extends Page {
    @FindBy(how = How.XPATH, using = "//a[@data-gtm-event-label='Action - Compare']")
    private WebElement addToCompare;

    @FindBy(
            how = How.XPATH,
            using = "//*[name()='use' and @*='#location']" +
                    "//ancestor::button[@class='e-button e-button--simple e-action']"
    )
    private WebElement choosePlace;

    @FindBy(how = How.XPATH, using = "//li[text()='Praha']")
    private WebElement choosePrague;

    @FindBy(how = How.XPATH, using = "//a[text()='Nejlevnější včetně dopravy']")
    private WebElement cheapestChoice;

    @FindBy(how = How.XPATH, using = "//button[@class='c-top-offer__cart-button e-button e-button--highlight']")
    private WebElement addToBasket;

    @FindBy(how = How.XPATH, using = "//a[@class='c-cart-confirm__button--cart e-button e-button--highlight']")
    private WebElement openBasket;

    public LaptopPageDescription(WebDriver driver) {
        super(driver);
    }

    public LaptopPickModel backToLaptopPage() {
        driverWait.until(ExpectedConditions.visibilityOf(addToCompare));
        addToCompare.click();
        driver.navigate().back();
        return new LaptopPickModel(driver);
    }

    public UserBasket addThirdLaptopToUserBasket() {
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driverWait.until(ExpectedConditions.visibilityOf(choosePlace));
        jsClick(choosePlace);
        driverWait.until(ExpectedConditions.visibilityOf(choosePrague));
        jsClick(choosePrague);
        jsClick(cheapestChoice);
        jsClick(addToBasket);
        driverWait.until(ExpectedConditions.visibilityOf(openBasket));
        jsClick(openBasket);
        return new UserBasket(driver);
    }
}
