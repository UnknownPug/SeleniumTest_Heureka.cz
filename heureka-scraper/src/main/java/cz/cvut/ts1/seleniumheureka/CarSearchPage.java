package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CarSearchPage extends Page {
    @FindBy(how = How.XPATH, using = "//a[@data-gtm-event-label='Škoda Octavia']")

    private WebElement chooseBestCar;

    public CarSearchPage(WebDriver driver) {
        super(driver);
    }

    public ChooseSkodaAuto skodaAuto() {
        driverWait.until(ExpectedConditions.visibilityOf(chooseBestCar));
        chooseBestCar.click();
        return new ChooseSkodaAuto(driver);
    }
}
