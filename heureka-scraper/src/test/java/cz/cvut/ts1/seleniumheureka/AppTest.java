package cz.cvut.ts1.seleniumheureka;

import org.junit.jupiter.api.Test;

public class AppTest extends TestCase {

  @Test
  public void shouldAnswerWithTrue() {
    new MainPage(getDriver())
      .acceptCookies()
      .goToLoginPage()
      .login("login", "password") // Enter your login and password before start testing
      .navToLaptopsPage()
      .laptopInfo()
      .laptopPriceDescriptionOne()
      .backToLaptopPage()
      .laptopPriceDescriptionTwo()
      .backToLaptopPage()
      .openThirdLaptopDescription()
      .addThirdLaptopToUserBasket()
      .goToMainPage()
      .navToCarPage()
      .skodaAutoDetails()
      .advisoryCarSearch()
      .navToHowToChooseAuto()
      .goBackToMainPage()
      .navToHeurekaProfile()
      .logOut();
    System.out.println(); //Select this line and run debug to see the testing process
  }
}
