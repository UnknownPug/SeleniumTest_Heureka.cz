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
  public void init() {
    System.setProperty(
      "webdriver.chrome.driver",
      "/Users/dmitry1/Desktop/CVUT/4. semestr/TS1/Zdroje/ChromeDriver/chromedriver"
    );
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
