package cz.cvut.ts1.seleniumheureka;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {

  public static void main(String[] args) throws InterruptedException {
    System.out.println(
      "Welcome to the heureka notebook browser (crappy edition)!"
    );
    System.out.print("Please enter your login email to heureka: ");
    String email = System.console().readLine();

    System.out.print("Please enter your login password: ");
    String password = System.console().readLine();

    System.setProperty(Consts.DRIVER_TYPE, Consts.DRIVER_LOCATIONS);

    WebDriver driver = new ChromeDriver();

    MainPage mainPage = new MainPage(driver);
    mainPage.acceptCookies();
    UserMainPage ump = mainPage.goToLoginPage().login(email, password);

    System.out.println("You are logged in as " + ump.getUserName());

    LaptopsSearchPage lsp = ump.navToLaptopsPage();

    Integer minPrice = getMinPrice();
    Integer maxPrice = getMaxPrice(minPrice);

    lsp.setPriceRange(minPrice, maxPrice);

    Integer reviewTier = getReviewTier();
    if (reviewTier != null) {
      lsp.setReviewTier(reviewTier);
    }

    boolean inStock = getInStock();
    if (inStock) {
      lsp.requireAvailability();
    }
    List<BaseLaptopData> laptops = new ArrayList<>();

    try {
      laptops = lsp.getLaptopData();
      System.out.println("Found " + laptops.size() + " laptops:");
    } catch (Exception e) {
      System.out.println("No laptops found.");
      System.exit(0);
    }

    int numberOfLaptops = howManyLaptopsToShow(laptops.size());

    for (int i = 0; i < numberOfLaptops; i++) {
      System.out.println(laptops.get(i).toString());
    }
  }

  private static int howManyLaptopsToShow(int nOfFoundLaptops) {
    System.out.print(
      "How many laptops do you want to see? (1-" + nOfFoundLaptops + "): "
    );
    do {
      String numberOfLaptopsString = System.console().readLine();
      int numberOfLaptopsInt = Integer.parseInt(numberOfLaptopsString);
      if (numberOfLaptopsInt > 0 && numberOfLaptopsInt <= nOfFoundLaptops) {
        return numberOfLaptopsInt;
      } else {
        System.out.println(
          "Please enter a number between 1 and " + nOfFoundLaptops
        );
      }
    } while (true);
  }

  private static boolean getInStock() {
    System.out.println("Does the laptop need to be in stock? (y/n)");

    while (true) {
      String answer = System.console().readLine();
      if (answer.equals("y")) {
        return true;
      } else if (answer.equals("n")) {
        return false;
      } else {
        System.out.println("Please enter y or n");
      }
    }
  }

  private static Integer getReviewTier() {
    System.out.println(
      "Please enter the review tier you want to search for (or leave blank if it doesn't matter):"
    );
    System.out.println("1: 95% or higher");
    System.out.println("2: 90% or higher");
    System.out.println("3: 80% or higher");

    do {
      String read = System.console().readLine();
      if (read.isEmpty()) {
        return null;
      }
      try {
        int num = Integer.parseInt(read);
        if (num < 1 || num > 3) {
          System.out.println("Please enter either 1, 2 or 3");
        } else {
          return num;
        }
      } catch (NumberFormatException e) {
        System.out.println("Please enter either 1, 2 or 3");
      }
    } while (true);
  }

  private static Integer getMinPrice() {
    System.out.println(
      "Please enter the minimum price [leave blank if it doesn't matter]:"
    );
    do {
      String min = System.console().readLine();
      if (min.isEmpty()) {
        return null;
      }
      int minVal;
      try {
        minVal = Integer.parseInt(min);
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid number!");
        continue;
      }
      if (minVal < 0) {
        System.out.println("Please enter a positive number!");
        continue;
      }
      return minVal;
    } while (true);
  }

  private static Integer getMaxPrice(int minval) {
    System.out.println(
      "Please enter the maximum price [leave blank if it doesn't matter]:"
    );
    do {
      String max = System.console().readLine();
      if (max.isEmpty()) {
        return null;
      }
      int maxVal;
      try {
        maxVal = Integer.parseInt(max);
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid number!");
        continue;
      }
      if (maxVal < minval) {
        System.out.println(
          "Please enter a number greater than " + minval + "!"
        );
        continue;
      }
      return maxVal;
    } while (true);
  }
}
