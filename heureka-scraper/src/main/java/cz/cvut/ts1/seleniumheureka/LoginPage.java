package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends Page {

    @FindBy(how = How.XPATH, using = "//input[@id='frm-loginForm-loginForm-email']")
    private WebElement emailField;

    @FindBy(how = How.XPATH, using = "//input[@id='frm-loginForm-loginForm-password']")
    private WebElement passwordField;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Přihlásit se')]")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public UserMainPage login(String login, String password) {
        driverWait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.sendKeys(login);
        driverWait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.sendKeys(password);
        jsClick(loginButton);
        return new UserMainPage(driver);
    }
}
