package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LaptopPickModel extends Page {

    @FindBy(
            how = How.XPATH,
            using = "//a[@class='c-product__cta e-button']" +
                    "//ancestor::a[@href='https://notebooky.heureka.cz/apple-macbook-pro-2020-space-grey-myd82cz-a/']"
    )
    private WebElement goToPriceCompareOne;

    @FindBy(how = How.XPATH, using = "//a[@class='c-product__cta e-button']" +
            "//ancestor::a[@href='https://notebooky.heureka.cz/apple-macbook-air-2020-space-grey-mgn73cz-a/']"
    )
    private WebElement goToPriceCompareTwo;

    @FindBy(how = How.XPATH, using = "//img[@alt='Apple Macbook Air 2020 Silver MGNA3CZ/A']")
    private WebElement openLaptopDescription;

    public LaptopPickModel(WebDriver driver) {
        super(driver);
    }

    public LaptopPageDescription laptopPriceDescriptionOne() {
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driverWait.until(ExpectedConditions.visibilityOf(goToPriceCompareOne));
        jsClick(goToPriceCompareOne);
        return new LaptopPageDescription(driver);
    }

    public LaptopPageDescription laptopPriceDescriptionTwo() {
        driverWait.until(ExpectedConditions.visibilityOf(goToPriceCompareTwo));
        jsClick(goToPriceCompareTwo);
        return new LaptopPageDescription(driver);
    }

    public LaptopPageDescription openThirdLaptopDescription() {
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driverWait.until(ExpectedConditions.visibilityOf(openLaptopDescription));
        jsClick(openLaptopDescription);
        return new LaptopPageDescription(driver);
    }
}
