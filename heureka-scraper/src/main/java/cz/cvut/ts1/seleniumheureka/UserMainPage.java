package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UserMainPage extends Page {

    // Laptops search
    @FindBy(how = How.XPATH, using = "//span[text()='Elektronika']//ancestor::a[@class='c-categories-list__link']")
    private WebElement electronicsSearch;

    @FindBy(how = How.XPATH, using = "//a[@href='https://notebooky.heureka.cz/']")
    private WebElement laptopSearch;

    // Car search
    @FindBy(how = How.XPATH, using = "//span[text()='Auto-moto']//ancestor::a[@class='e-heading-link']")
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

    public LaptopsSearchPage laptopsPage() {
        driverWait.until(ExpectedConditions.visibilityOf(electronicsSearch));
        electronicsSearch.click();
        driverWait.until(ExpectedConditions.visibilityOf(laptopSearch));
        jsClick(laptopSearch);
        return new LaptopsSearchPage(driver);
    }

    public CarSearchPage carPage() {
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driverWait.until(ExpectedConditions.visibilityOf(carSearch));
        jsClick(carSearch);
        driverWait.until(ExpectedConditions.visibilityOf(chooseCar));
        jsClick(chooseCar);
        return new CarSearchPage(driver);
    }

    public OpenHeurekaProfile heurekaProfile() {
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driverWait.until(ExpectedConditions.visibilityOf(clickOnProfile));
        clickOnProfile.click();
        driverWait.until(ExpectedConditions.visibilityOf(openHeurekaProfile));
        jsClick(openHeurekaProfile);
        return new OpenHeurekaProfile(driver);
    }
}
