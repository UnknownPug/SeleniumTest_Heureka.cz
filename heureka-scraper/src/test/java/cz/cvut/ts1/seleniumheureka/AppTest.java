package cz.cvut.ts1.seleniumheureka;

import org.junit.jupiter.api.Test;

public class AppTest extends TestCase {

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        new MainPage(getDriver())
                .goToLoginPage()
                .login("login", "password")
                .laptopsPage()
                .laptopInfo()
                .laptopPriceDescriptionOne()
                .backToLaptopPage()
                .laptopPriceDescriptionTwo()
                .backToLaptopPage()
                .openThirdLaptopDescription()
                .addThirdLaptopToUserBasket()
                .goToMainPage()
                .carPage()
                .skodaAuto()
                .skodaAutoDetails()
                .advisoryCarSearch()
                .howToChooseAuto()
                .goBackToMainPage()
                .heurekaProfile()
                .exit();
        System.out.println();
    }
}
