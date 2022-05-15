package cz.cvut.ts1.seleniumheureka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

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

  @Test
  public void dismissCookies() {
    MainPage page = new MainPage(getDriver());
    assertTrue(page.isThereACookiePopUp()); // need to start with cookie pop-up
    page.acceptCookies();
    assertFalse(page.isThereACookiePopUp());
    LoginPage loginPage = page.goToLoginPage();
    assertFalse(loginPage.isThereACookiePopUp()); // check if it persists
  }
}
