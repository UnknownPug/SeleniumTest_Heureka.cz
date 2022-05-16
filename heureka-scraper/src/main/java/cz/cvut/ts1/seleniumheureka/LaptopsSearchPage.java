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
  private WebElement reviewSelectionUl;

  @FindBy(how = How.XPATH, using = "//input[@id='availability-1']")
  private WebElement needsToBeAvailableRadio;

  @FindBy(how = How.XPATH, using = "//button[text()='Zobrazit další možnosti']")
  private WebElement showListOfManufacturers;

  private boolean manufacturerListExpanded = false;

  @FindBy(how = How.XPATH, using = "//a[text()='Procesor']")
  private WebElement openProcessor;

  @FindBy(how = How.XPATH, using = "//a[text()='Apple M1']")
  private WebElement selectProcessor;

  @FindBy(
    how = How.XPATH,
    using = "//span[@class='c-star-rating__rating-value u-base']"
  )
  private WebElement reviews;

  @FindBy(
    how = How.XPATH,
    using = "//span[text()='Vašim požadavkům neodpovídají žádné produkty.']"
  )
  private WebElement noResults;

  public LaptopsSearchPage(WebDriver driver) {
    super(driver);
  }

  public LaptopPickModelPage laptopInfo() {
    jsClick(openProcessor);
    driverWait.until(ExpectedConditions.visibilityOf(selectProcessor));
    jsClick(selectProcessor);
    return new LaptopPickModelPage(driver);
  }

  public LaptopsSearchPage setPriceRange(Integer min, Integer max) {
    if (min == null && max == null) {
      return this;
    }

    if (min != null && max != null && min > max) {
      int tmp = min;
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
    driverWait.until(ExpectedConditions.visibilityOf(reviewSelectionUl));

    WebElement chosenRewiewTier = driver.findElement(
      By.xpath(String.format("//input[@id='review-%d']", tier))
    );
    driverWait.until(ExpectedConditions.visibilityOf(chosenRewiewTier));
    jsClick(chosenRewiewTier);

    return this;
  }

  public LaptopsSearchPage requireAvailability() {
    driverWait.until(ExpectedConditions.visibilityOf(needsToBeAvailableRadio));
    jsClick(needsToBeAvailableRadio);

    return this;
  }

  @FindBy(
    how = How.XPATH,
    using = "//div[@id='vyrobce']//div//div//div//div//ul"
  )
  private WebElement manufacturerList;

  public LaptopsSearchPage selectManufacturers(List<String> manufacturers) {
    if (!manufacturerListExpanded) {
      driverWait.until(
        ExpectedConditions.visibilityOf(showListOfManufacturers)
      );
      jsClick(showListOfManufacturers);
      manufacturerListExpanded = true;
    }

    for (String manufacturer : manufacturers) {
      WebElement manufacturerLi;
      try {
        manufacturerLi =
          driver.findElement(
            By.xpath(
              String.format(
                "//li//div//label[@class='c-form-cell__label c-form-cell__label--subtle e-counter']" +
                "//a[text()='%s']",
                manufacturer
              )
            )
          );
      } catch (Exception e) {
        throw new IllegalArgumentException(
          String.format("Manufacturer %s not found", manufacturer)
        );
      }
      driverWait.until(ExpectedConditions.visibilityOf(manufacturerLi));
      jsClick(manufacturerLi);
    }

    return this;
  }

  public List<int[]> getAllPriceRanges() {
    List<int[]> priceRanges = new ArrayList<>();
    List<WebElement> priceRangeTexts = driver.findElements(
      By.xpath("//div[@class='c-product__price']//span")
    );
    if (priceRangeTexts.size() == 0) {
      return priceRanges;
    }
    for (WebElement price : priceRangeTexts) {
      WebElement first = price.findElement(By.xpath(".//span[1]"));
      String firstStr = first.getText();
      firstStr = firstStr.replaceAll("[^\\d.]", "");
      int firstPrice = Integer.parseInt(firstStr);

      WebElement second = price.findElement(By.xpath(".//span[3]"));
      String secondStr = second.getText();
      secondStr = secondStr.replaceAll("[^\\d.]", "");
      int secondPrice = Integer.parseInt(secondStr);

      priceRanges.add(new int[] { firstPrice, secondPrice });
    }
    return priceRanges;
  }

  public List<Integer> getAllRatings() throws InterruptedException {
    driverWait.until(ExpectedConditions.visibilityOf(reviews));
    Thread.sleep(1000);
    List<Integer> ratings = new ArrayList<>();
    List<WebElement> ratingsElements = driver.findElements(
      By.xpath("//span[@class='c-star-rating__rating-value u-base']")
    );
    if (ratingsElements.size() == 0) {
      return ratings;
    }
    for (WebElement rating : ratingsElements) {
      String ratingStr = rating.getAttribute("innerHTML");

      ratingStr = ratingStr.replaceAll("[^0-9.]", "");
      if (ratingStr.equals("")) {
        continue;
      }

      int ratingInt = Integer.parseInt(ratingStr);
      ratings.add(ratingInt);
    }
    return ratings;
  }

  public List<String> getNamesOfAllResults() throws InterruptedException {
    List<String> manufacturers = new ArrayList<>();
    Thread.sleep(1000);
    List<WebElement> manufacturerElements = driver.findElements(
      By.xpath("//a[@class='c-product__link']")
    );
    if (manufacturerElements.size() == 0) {
      return manufacturers;
    }
    for (WebElement manufacturer : manufacturerElements) {
      String ProductName = manufacturer.getText();
      manufacturers.add(ProductName);
    }
    return manufacturers;
  }

  public boolean noResultsFound() {
    driverWait.until(ExpectedConditions.visibilityOf(noResults));
    return noResults.isDisplayed();
  }
}
