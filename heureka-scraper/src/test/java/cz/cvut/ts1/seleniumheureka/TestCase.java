package cz.cvut.ts1.seleniumheureka;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class TestCase {

  //TODO: CMD + OPT + F (V) - new variable
  // TODO: CMD + OPT + I - developer settings for web
  private WebDriver driver;

  @BeforeEach
  public void init() { // Download ChromeDriver and overwrite the path on line 18
    System.setProperty("webdriver.chrome.driver", "C:/path/chromedriver.exe");
    driver = new ChromeDriver();
  }

  public WebDriver getDriver() {
    return driver;
  }

  @AfterEach
  public void clean() {
    driver.quit();
  }
}
