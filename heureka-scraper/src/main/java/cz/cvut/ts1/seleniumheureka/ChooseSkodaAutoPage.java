package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ChooseSkodaAutoPage extends Page {

    @FindBy(
            how = How.XPATH,
            using = "//span[text()='Detail produktu']" +
                    "//ancestor::a[@class='c-product__secondary-cta c-product__link u-base']"
    )
    private List<WebElement> carInfoLink;

    public ChooseSkodaAutoPage(WebDriver driver) {
        super(driver);
    }

    public SkodaAutoDetails skodaAutoDetails() {
        driverWait.until(ExpectedConditions.visibilityOf(carInfoLink.get(1)));
        carInfoLink.get(1).click();
        return new SkodaAutoDetails(driver);
    }
}
