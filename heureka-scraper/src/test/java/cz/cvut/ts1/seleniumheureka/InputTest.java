package cz.cvut.ts1.seleniumheureka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

public class InputTest {

  @Test
  public void inputTeir() {
    InputStream sysInBackup = System.in; // backup System.in to restore it later
    ByteArrayInputStream in = new ByteArrayInputStream(
      "hello\n516\n-1\n2\n165165165165165161".getBytes()
    );
    System.setIn(in);

    Integer MaxPrice = App.getReviewTier(in);

    assertEquals(MaxPrice, 2);

    System.setIn(sysInBackup);
  }

  @Test
  public void nullInputTeir() {
    InputStream sysInBackup = System.in; // backup System.in to restore it later
    ByteArrayInputStream in = new ByteArrayInputStream(
      "hello\n516\n\n-1\n2\n165165165165165161".getBytes()
    );
    System.setIn(in);

    Integer MaxPrice = App.getReviewTier(in);

    assertEquals(MaxPrice, null);

    System.setIn(sysInBackup);
  }
}
