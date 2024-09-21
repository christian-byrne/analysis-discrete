package src.test;

import org.junit.jupiter.api.Test;
import src.main.StringPermutation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringPermutationTest {

  private final StringPermutation stringPermutation = new StringPermutation();

  // Test digitPermutation normal cases
  @Test
  public void testDigitPermutationNormalCases() {
    assertEquals("4071826395", stringPermutation.digitPermutation("BANANALAND")); // 4071826395
    assertEquals("6704821539", stringPermutation.digitPermutation("STARTEARLY")); // 6704821539
    assertEquals(
        "0123456789",
        stringPermutation.digitPermutation("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
  }
}
