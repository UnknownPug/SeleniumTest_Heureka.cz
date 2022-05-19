package cz.cvut.ts1.seleniumheureka;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class TestCase {
  private WebDriver driver;

  @BeforeEach
  public void init() { // Download ChromeDriver and overwrite the path on line 18
    System.setProperty(Consts.DRIVER_TYPE, Consts.DRIVER_MAC_LOCATIONS);
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
