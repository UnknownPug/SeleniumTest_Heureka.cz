package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage extends Page {

    @FindBy(
            how = How.XPATH,
            using = "//div[@id='rootHead']//ul//li[@class='c-user-controls__item c-user-controls__item--user']//a"
    )
    private WebElement loginLink;

    public MainPage(WebDriver driver) {
        super(driver);
        driver.get("https://www.heureka.cz/");
    }

    public LoginPage goToLoginPage() {
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driverWait.until(ExpectedConditions.visibilityOf(loginLink));
        jsClick(loginLink);
        return new LoginPage(driver);
    }
}