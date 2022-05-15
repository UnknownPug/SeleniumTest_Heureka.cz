package cz.cvut.ts1.seleniumheureka;

import org.junit.jupiter.api.Test;

public class AppTest extends TestCase {

  @Test
  public void shouldAnswerWithTrue() {
    new MainPage(getDriver())
      //.acceptCookies()
      .goToLoginPage()
      .login("gvarph006+ts1_heureka@gmail.com", "cZs%f*Hmh&RZqKw2FPMH")
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
      .selectSkodaAuto()
      .skodaAutoDetails()
      .advisoryCarSearch()
      .navToHowToChooseAuto()
      .goBackToMainPage()
      .navToHeurekaProfile()
      .logOut();
    System.out.println();
  }
}
