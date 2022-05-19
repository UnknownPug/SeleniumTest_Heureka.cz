package cz.cvut.ts1.seleniumheureka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

public class IndividualTests extends TestCase {

  @ParameterizedTest
  @CsvFileSource(resources = "/loginInfo.csv")
  public void logIn(String login, String password) {
    var name = new MainPage(getDriver())
      .goToLoginPage()
      .login(login, password)
      .getUserName();
    assertEquals(login, name);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/loginInfo.csv")
  public void logOut(String login, String password)
    throws InterruptedException {
    MainPage mainPage = new MainPage(getDriver());
    mainPage.acceptCookies();
    assertFalse(mainPage.isLoggedIn());
    UserMainPage ump = mainPage.goToLoginPage().login(login, password);
    assertTrue(ump.isLoggedIn());
    ProfilePage pp = ump.navToHeurekaProfile();
    pp.logOut();
    assertFalse(pp.isLoggedIn());
  }

  @Test
  public void dismissCookies() {
    MainPage page = new MainPage(getDriver());
    assertTrue(page.isThereACookiePopUp()); // need to start with cookie pop-up
    page.acceptCookies();
    assertFalse(page.isThereACookiePopUp());
    LoginPage loginPage = page.goToLoginPage();
    assertFalse(loginPage.isThereACookiePopUp()); // check if it persists
  }

  @ParameterizedTest
  @CsvSource({ "10000,30000", "25000,20000", "60000,70000" })
  public void laptopPrice(int min, int max) throws InterruptedException {
    if (min > max) {
      int tmp = min;
      min = max;
      max = tmp;
    }
    var driver = getDriver();
    driver.get("https://notebooky.heureka.cz/");
    var pg = new LaptopsSearchPage(driver);
    pg.acceptCookies();
    pg.setPriceRange(min, max);
    List<BaseLaptopData> laptops = pg.getLaptopData();
    for (BaseLaptopData laptop : laptops) {
      if (laptop.minPrice() != null) {
        assertTrue(laptop.minPrice() >= min, laptop.minPrice() + " < " + min);
      }
      if (laptop.maxPrice() != null) {
        assertTrue(laptop.minPrice() <= max, laptop.minPrice() + " > " + max);
      }
    }
  }

  @ParameterizedTest
  @CsvSource({ "30000", "20000", "70000" })
  public void laptopPriceNoMin(int max) throws InterruptedException {
    var driver = getDriver();
    driver.get("https://notebooky.heureka.cz/");
    var pg = new LaptopsSearchPage(driver);
    pg.acceptCookies();
    pg.setPriceRange(null, max); //.setReviewTier(1).requireAvailability();
    List<BaseLaptopData> laptops = pg.getLaptopData();
    for (BaseLaptopData laptop : laptops) {
      if (laptop.maxPrice() != null) {
        assertTrue(laptop.minPrice() <= max, laptop.minPrice() + " > " + max);
      }
    }
  }

  @ParameterizedTest
  @CsvSource({ "1", "2", "3" })
  public void laptopRating(int tier) throws InterruptedException {
    HashMap<Integer, Integer> ratings = new HashMap<>();
    ratings.put(1, 95);
    ratings.put(2, 90);
    ratings.put(3, 80);
    var driver = getDriver();
    driver.get("https://notebooky.heureka.cz/");
    var pg = new LaptopsSearchPage(driver);
    pg.acceptCookies();

    pg.setReviewTier(tier);
    List<BaseLaptopData> laptops = pg.getLaptopData();
    for (BaseLaptopData laptop : laptops) {
      assertTrue(
        laptop.ratings() >= ratings.get(tier),
        laptop.ratings() + " < " + ratings.get(tier)
      );
    }
  }

  @ParameterizedTest
  @CsvSource({ "Apple,Huawei,Lenovo", "MSI,Acer,Apple" })
  public void laptopBrand(String a, String b, String c)
    throws InterruptedException {
    List<String> brands = Arrays.asList(a, b, c);
    var driver = getDriver();
    driver.get("https://notebooky.heureka.cz/");
    var pg = new LaptopsSearchPage(driver);
    pg.acceptCookies();
    pg.selectManufacturers(brands);
    List<BaseLaptopData> laptops = pg.getLaptopData();
    for (BaseLaptopData laptop : laptops) {
      boolean found = false;
      for (String brand : brands) {
        if (laptop.Name().contains(brand)) {
          found = true;
          break;
        }
      }
      assertTrue(
        found,
        "Product name " +
        laptop.Name() +
        " does not contain any of the brands: " +
        brands
      );
    }
  }

  @Test
  public void invalidLaptopBrand() {
    var driver = getDriver();
    driver.get("https://notebooky.heureka.cz/");
    var pg = new LaptopsSearchPage(driver);
    pg.acceptCookies();
    assertThrows(
      IllegalArgumentException.class,
      () ->
        pg.selectManufacturers(
          Arrays.asList("Apple", "Huawei", "Lenovo", "Potato")
        )
    );
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/Pairwise.csv", numLinesToSkip = 1)
  public void complexLaptopSearch(
    int testNumber,
    String brand,
    int minPrice,
    int maxPrice,
    int reviewTier
  )
    throws InterruptedException {
    System.out.println(
      "complexLaptopSearch" +
      testNumber +
      ":\t" +
      brand +
      "\t" +
      minPrice +
      "\t" +
      maxPrice +
      "\t" +
      reviewTier
    );

    var driver = getDriver();
    driver.get("https://notebooky.heureka.cz/");
    var pg = new LaptopsSearchPage(driver);
    pg.acceptCookies();
    pg
      .selectManufacturers(Arrays.asList(brand))
      .setPriceRange(minPrice, maxPrice)
      .setReviewTier(reviewTier);

    List<BaseLaptopData> laptops;
    laptops = pg.getLaptopData();
    if (laptops == null) {
      System.out.println("no results");
      return;
    }
    HashMap<Integer, Integer> ratings = new HashMap<>();
    ratings.put(1, 95);
    ratings.put(2, 90);
    ratings.put(3, 80);

    if (minPrice > maxPrice) {
      int tmp = minPrice;
      minPrice = maxPrice;
      maxPrice = tmp;
    }

    for (BaseLaptopData laptop : laptops) {
      System.out.println(laptop);
      assertTrue(laptop.Name().contains(brand));
      if (laptop.minPrice() != null) {
        assertTrue(laptop.minPrice() >= minPrice);
      }
      if (laptop.maxPrice() != null) {
        assertTrue(laptop.minPrice() <= maxPrice);
      }
      if (laptop.ratings() != null) {
        assertTrue(laptop.ratings() >= ratings.get(reviewTier));
      }
    }
  }
}
