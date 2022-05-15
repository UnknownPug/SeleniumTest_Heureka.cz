package cz.cvut.ts1.seleniumheureka;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
