package cz.cvut.ts1.seleniumheureka.scenariotest;

import cz.cvut.ts1.seleniumheureka.MainPage;
import cz.cvut.ts1.seleniumheureka.TestCase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

public class AppTwoScenarioTest extends TestCase {
    @ParameterizedTest
    @CsvSource("rogger115@baanr.com,123456")
    public void shouldAnswerWithTrue(String login, String password) {
        var pg = new MainPage(getDriver());
        pg.acceptCookies();
        pg
                .goToLoginPage()
                .login(login, password)
                .navToLaptopsPage()
                .setReviewTier(2)
                .setPriceRange(30000, 100000)
                .selectManufacturers(List.of("Apple"))
                .laptopInfo()
                .openThirdLaptopDescription()
                .addThirdLaptopToUserBasket()
                .goToMainPage()
                .navToHeurekaProfile()
                .logOut();
        System.out.println();
    }
}
