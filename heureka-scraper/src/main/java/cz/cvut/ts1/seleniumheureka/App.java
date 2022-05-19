package cz.cvut.ts1.seleniumheureka;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {

  public static void main(String[] args) throws InterruptedException {
    var in = System.in;
    Scanner input = new Scanner(System.in);
    System.out.println(
      "Welcome to the heureka notebook browser (crappy edition)!"
    );
    System.out.print("Please enter your login email to heureka: ");
    String email = input.nextLine();

    System.out.print("Please enter your login password: ");
    String password = input.nextLine();

    Integer minPrice = getMinPrice(in);
    Integer maxPrice;
    if (minPrice == null) {
      maxPrice = getMaxPrice(0, in);
    } else {
      maxPrice = getMaxPrice(minPrice, in);
    }

    Integer reviewTier = getReviewTier(in);

    boolean inStock = getInStock(in);

    System.setProperty(Consts.DRIVER_TYPE, Consts.DRIVER_LOCATIONS);

    WebDriver driver = new ChromeDriver();

    MainPage mainPage = new MainPage(driver);
    mainPage.acceptCookies();
    UserMainPage ump = mainPage.goToLoginPage().login(email, password);

    System.out.println("You are logged in as " + ump.getUserName());

    LaptopsSearchPage lsp = ump.navToLaptopsPage();

    lsp.setPriceRange(minPrice, maxPrice);

    if (reviewTier != null) {
      lsp.setReviewTier(reviewTier);
    }

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

    int numberOfLaptops = howManyLaptopsToShow(laptops.size(), in);

    for (int i = 0; i < numberOfLaptops; i++) {
      System.out.println(laptops.get(i).toString());
    }
    input.close();
  }

  private static int howManyLaptopsToShow(int nOfFoundLaptops, InputStream in) {
    Scanner input = new Scanner(in);
    System.out.print(
      "How many laptops do you want to see? (1-" + nOfFoundLaptops + "): "
    );
    do {
      String numberOfLaptopsString = input.nextLine();
      int numberOfLaptopsInt = Integer.parseInt(numberOfLaptopsString);
      if (numberOfLaptopsInt > 0 && numberOfLaptopsInt <= nOfFoundLaptops) {
        input.close();
        return numberOfLaptopsInt;
      } else {
        System.out.println(
          "Please enter a number between 1 and " + nOfFoundLaptops
        );
      }
    } while (true);
  }

  public static boolean getInStock(InputStream in) {
    Scanner input = new Scanner(in);
    System.out.println("Does the laptop need to be in stock? (y/n)");

    while (true) {
      String answer = input.nextLine();
      if (answer.equals("y")) {
        input.close();
        return true;
      } else if (answer.equals("n")) {
        input.close();
        return false;
      } else {
        System.out.println("Please enter y or n");
      }
    }
  }

  public static Integer getReviewTier(InputStream in) {
    Scanner input = new Scanner(in);
    System.out.println(
      "Please enter the review tier you want to search for (or leave blank if it doesn't matter):"
    );
    System.out.println("1: 95% or higher");
    System.out.println("2: 90% or higher");
    System.out.println("3: 80% or higher");

    do {
      String read = input.nextLine();
      if (read.isEmpty()) {
        input.close();
        return null;
      }
      try {
        int num = Integer.parseInt(read);
        if (num < 1 || num > 3) {
          System.out.println("Please enter either 1, 2 or 3");
        } else {
          input.close();
          return num;
        }
      } catch (NumberFormatException e) {
        System.out.println("Please enter either 1, 2 or 3");
      }
    } while (true);
  }

  public static Integer getMinPrice(InputStream in) {
    Scanner input = new Scanner(in);
    System.out.println(
      "Please enter the minimum price [leave blank if it doesn't matter]:"
    );
    do {
      String min = input.nextLine();
      if (min.isEmpty()) {
        input.close();
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
      input.close();
      return minVal;
    } while (true);
  }

  public static Integer getMaxPrice(int minval, InputStream in) {
    Scanner input = new Scanner(in);
    System.out.println(
      "Please enter the maximum price [leave blank if it doesn't matter]:"
    );
    do {
      String max = input.nextLine();
      if (max.isEmpty()) {
        input.close();
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
      input.close();
      return maxVal;
    } while (true);
  }
}
