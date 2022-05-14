package cz.cvut.ts1.seleniumheureka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Hello world!
 *
 */
public class App {

  public static void main(String[] args) {
    String DRIVER = "C:/path/chromedriver.exe";
    System.setProperty("webdriver.chrome.driver", DRIVER);
    WebDriver driver = new ChromeDriver();

    var mainPage = new MainPage(driver).open();

    String email = "gvarph006+ts1_heureka@gmail.com";
    String password = "cZs%f*Hmh&RZqKw2FPMH";

    mainPage = mainPage.goToLoginPage().login(email, password);
  }
}
