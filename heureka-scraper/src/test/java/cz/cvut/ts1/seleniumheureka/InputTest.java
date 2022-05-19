package cz.cvut.ts1.seleniumheureka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class InputTest {

  @Test
  public void inputTeir() {
    ByteArrayInputStream in = new ByteArrayInputStream(
      "hello\n516\n-1\n2\n165165165165165161".getBytes()
    );
    System.setIn(in);

    Integer MaxPrice = App.getReviewTier(in);

    assertEquals(MaxPrice, 2);
  }

  @Test
  public void nullInputTeir() {
    ByteArrayInputStream in = new ByteArrayInputStream(
      "hello\n4\n516\n\n-1\n2\n165165165165165161".getBytes()
    );
    System.setIn(in);

    Integer MaxPrice = App.getReviewTier(in);

    assertEquals(MaxPrice, null);
  }

  @Test
  public void inStockTrue() {
    ByteArrayInputStream in = new ByteArrayInputStream(
      "hello\nnn\nno\n516\n\n-1\ny\n165165165165165161".getBytes()
    );

    boolean inStock = App.getInStock(in);

    assertEquals(inStock, true);
  }

  @Test
  public void inStockFalse() {
    ByteArrayInputStream in = new ByteArrayInputStream(
      "hello\nnn\nno\n516\n\n-1\nn\n165165165165165161".getBytes()
    );

    boolean inStock = App.getInStock(in);

    assertEquals(inStock, false);
  }

  @Test
  public void minPrice() {
    int rand = 195161;
    ByteArrayInputStream in = new ByteArrayInputStream(
      (
        "hello\nnn\nno\n156a516\n-1\n" + rand + "\n165165165165165161"
      ).getBytes()
    );

    Integer res = App.getMinPrice(in);

    assertEquals(rand, res);
  }

  @Test
  public void minPriceNull() {
    int rand = 195161;
    ByteArrayInputStream in = new ByteArrayInputStream(
      (
        "hello\nnn\nno\n156a516\n\n-1\n" + rand + "\n165165165165165161"
      ).getBytes()
    );

    Integer res = App.getMinPrice(in);

    assertEquals(null, res);
  }
}
