package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CarAdvisorySearch extends Page {

    @FindBy(how = How.XPATH, using = "//a[@title='Jak vybrat auto?']")
    private WebElement howToChooseAuto;

    public CarAdvisorySearch(WebDriver driver) {
        super(driver);
    }

    public HowToChooseAuto howToChooseAuto() {
        driverWait.until(ExpectedConditions.visibilityOf(howToChooseAuto));
        howToChooseAuto.click();
        return new HowToChooseAuto(driver);
    }
}
