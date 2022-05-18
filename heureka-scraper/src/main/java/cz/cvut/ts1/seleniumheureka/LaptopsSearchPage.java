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

  @FindBy(how = How.XPATH, using = "//ul[@class='c-product-list__items']")
  private WebElement productList;

  public List<BaseLaptopData> getLaptopData() throws InterruptedException {
    List<BaseLaptopData> data = new ArrayList<>();

    Thread.sleep(1000);

    if (noResultsFound()) {
      return null;
    }

    List<WebElement> laptops = productList.findElements(
      By.xpath(".//li//section")
    );

    for (WebElement laptop : laptops) {
      driverWait.until(ExpectedConditions.visibilityOf(laptop));

      String url = laptop
        .findElement(By.xpath(".//a[@class='c-product__overlay-link']"))
        .getAttribute("href");

      String name = laptop
        .findElement(By.xpath(".//a[@class='c-product__overlay-link']"))
        .getText();

      WebElement price = laptop.findElement(
        By.xpath(
          ".//div[@class='c-product__container']" +
          "//div" +
          "//div[@class='c-product__actions o-block-list']" +
          "//div" +
          "//a[@class='c-product__price']"
        )
      );
      String priceText = price.getText();
      Integer minPrice, maxPrice;
      if (priceText.contains("–")) {
        var prices = priceText.split("–");
        minPrice = Integer.parseInt(prices[0].replaceAll("[^\\d.]", ""));
        maxPrice = Integer.parseInt(prices[1].replaceAll("[^\\d.]", ""));
      } else {
        minPrice = Integer.parseInt(priceText.replaceAll("[^\\d.]", ""));
        maxPrice = null;
      }

      var rating = laptop.findElements(
        By.xpath(
          ".//div[@class='c-product__container']" +
          "//div" +
          "//div[@class='c-product__content']" +
          "//ul[@class='c-product__stats c-pipe-list is-available']" +
          "//li" +
          "//div" +
          "//a[@class='c-star-rating__rating c-product__rating c-product__link']" +
          "//span[@class='c-star-rating__rating-value u-base']"
        )
      );
      Integer ratingValue;
      if (rating.size() == 0) {
        ratingValue = null;
      } else {
        ratingValue =
          Integer.parseInt(rating.get(0).getText().replaceAll("[^\\d.]", ""));
      }
      var newData = new BaseLaptopData(
        name,
        ratingValue,
        minPrice,
        maxPrice,
        url
      );

      if (!data.contains(newData)) {
        data.add(newData);
      }
    }

    return data;
  }

  public boolean noResultsFound() {
    driverWait.until(
      ExpectedConditions.or(
        ExpectedConditions.visibilityOf(productList),
        ExpectedConditions.visibilityOf(noResults)
      )
    );
    return (
      driver
        .findElements(
          By.xpath(
            "//span[text()='Vašim požadavkům neodpovídají žádné produkty.']"
          )
        )
        .size() ==
      1
    );
  }
}
