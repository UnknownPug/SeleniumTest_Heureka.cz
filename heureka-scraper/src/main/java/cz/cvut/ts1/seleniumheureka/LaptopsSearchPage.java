package cz.cvut.ts1.seleniumheureka;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LaptopsSearchPage extends Page {

  @FindBy(how = How.XPATH, using = "//input[@id='price-range-min']")
  private WebElement chooseMinPriceField;

  @FindBy(how = How.XPATH, using = "//input[@id='price-range-max']")
  private WebElement chooseMaxPriceField;

  @FindBy(
    how = How.XPATH,
    using = "//button[@type='submit']//span[text()='Ok']"
  )
  private WebElement priceOkButton;

  @FindBy(
    how = How.XPATH,
    using = "//div[@id='zakaznicke-hodnoceni']//div//div//div//div//ul"
  )
  private WebElement reviewSelection;

  @FindBy(how = How.XPATH, using = "//input[@id='availability-1']")
  private WebElement available;

  @FindBy(how = How.XPATH, using = "//button[text()='Zobrazit další možnosti']")
  private WebElement showListOfLaptops;

  @FindBy(how = How.XPATH, using = "//a[text()='Apple']")
  private WebElement selectManufacturer;

  @FindBy(how = How.XPATH, using = "//a[text()='Procesor']")
  private WebElement openProcessor;

  @FindBy(how = How.XPATH, using = "//a[text()='Apple M1']")
  private WebElement selectProcessor;

  public LaptopsSearchPage(WebDriver driver) {
    super(driver);
  }

  public LaptopPickModelPage laptopInfo() {
    //TODO: select price range
    //TODO: select review tier
    driverWait.until(ExpectedConditions.visibilityOf(available));
    jsClick(showListOfLaptops);
    driverWait.until(ExpectedConditions.visibilityOf(selectManufacturer));
    jsClick(selectManufacturer);
    jsClick(openProcessor);
    driverWait.until(ExpectedConditions.visibilityOf(selectProcessor));
    jsClick(selectProcessor);
    return new LaptopPickModelPage(driver);
  }

  public LaptopsSearchPage setPriceRange(Integer min, Integer max) {
    if (min == null && max == null) {
      return this;
    }

    if (min > max) {
      Integer tmp = min;
      min = max;
      max = tmp;
    }

    if (min != null) {
      driverWait.until(ExpectedConditions.visibilityOf(chooseMinPriceField));
      chooseMinPriceField.sendKeys(min.toString());
    }
    if (max != null) {
      driverWait.until(ExpectedConditions.visibilityOf(chooseMaxPriceField));
      chooseMaxPriceField.sendKeys(max.toString());
    }

    driverWait.until(ExpectedConditions.visibilityOf(priceOkButton));
    jsClick(priceOkButton);
    return this;
  }

  public LaptopsSearchPage setReviewTier(int tier) {
    if (tier < 1 || tier > 3) {
      throw new IllegalArgumentException("Review tier must be between 1 and 3");
    }
    driverWait.until(ExpectedConditions.visibilityOf(reviewSelection));

    WebElement chosenRewiewTier = driver.findElement(
      By.xpath(String.format("//input[@id='review-%d']", tier))
    );
    driverWait.until(ExpectedConditions.visibilityOf(chosenRewiewTier));
    jsClick(chosenRewiewTier);

    return this;
  }

  public LaptopsSearchPage requireAvailability() {
    driverWait.until(ExpectedConditions.visibilityOf(available));
    jsClick(available);

    return this;
  }

  public List<int[]> getPriceRange() {
    List<int[]> priceRanges = new ArrayList<>();
    List<WebElement> priceRange = driver.findElements(
      By.xpath("//div[@class='c-product__price']//span")
    );
    for (WebElement price : priceRange) {
      WebElement first = price.findElement(By.xpath(".//span[1]"));
      String firstStr = first.getText();
      firstStr = firstStr.replaceAll("[^\\d.]", "");
      int firstPrice = Integer.parseInt(firstStr);

      WebElement second = price.findElement(By.xpath(".//span[3]"));
      String secondStr = second.getText();
      secondStr = secondStr.replaceAll("[^\\d.]", "");
      int secondPrice = Integer.parseInt(secondStr);

      System.out.println(String.format("%d - %d", firstPrice, secondPrice));
      priceRanges.add(new int[] { firstPrice, secondPrice });
    }
    return priceRanges;
  }
}
