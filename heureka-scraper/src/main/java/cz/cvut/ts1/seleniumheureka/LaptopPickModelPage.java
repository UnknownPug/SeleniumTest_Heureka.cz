package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LaptopPickModelPage extends Page {

    @FindBy(how = How.XPATH, using = "//img[@alt='Apple Macbook Pro 2020 Space Grey MYD82CZ/A']")
    private WebElement goToPriceCompareOne;

    @FindBy(how = How.XPATH, using = "//img[@alt='Apple MacBook Air 2020 Space Grey MGN73CZ/A']")
    private WebElement goToPriceCompareTwo;

    @FindBy(how = How.XPATH, using = "//img[@alt='Apple Macbook Air 2020 Silver MGNA3CZ/A']")
    private WebElement openLaptopDescription;

    public LaptopPickModelPage(WebDriver driver) {
        super(driver);
    }

    public LaptopPageDescriptionPage laptopPriceDescriptionOne() {
        driverWait.until(ExpectedConditions.visibilityOf(goToPriceCompareOne));
        jsClick(goToPriceCompareOne);
        return new LaptopPageDescriptionPage(driver);
    }

    public LaptopPageDescriptionPage laptopPriceDescriptionTwo() {
        driverWait.until(ExpectedConditions.visibilityOf(goToPriceCompareTwo));
        jsClick(goToPriceCompareTwo);
        return new LaptopPageDescriptionPage(driver);
    }

    public LaptopPageDescriptionPage openThirdLaptopDescription() {
        driverWait.until(ExpectedConditions.visibilityOf(openLaptopDescription));
        jsClick(openLaptopDescription);
        return new LaptopPageDescriptionPage(driver);
    }
}
