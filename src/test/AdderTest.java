package src.test;

import org.junit.jupiter.api.Test;
import src.main.Adder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdderTest {

  private final Adder adder = new Adder();

  // Test noCarryAddition normal cases
  @Test
  public void testNoCarryAdditionNormalCases() {
    assertEquals("15", adder.noCarryAddition("7", "18")); // 7 ∔ 18 = 15
    assertEquals("412", adder.noCarryAddition("359", "163")); // 359 ∔ 163 = 412
  }

  // Test noCarryAddition edge cases
  @Test
  public void testNoCarryAdditionEdgeCases() {
    assertEquals("888", adder.noCarryAddition("999", "999")); // Overflow carry discarded
    assertEquals("000", adder.noCarryAddition("000", "000")); // Leading zeroes
  }

  // Test chainAddition normal cases
  @Test
  public void testChainAdditionNormalCases() {
    assertEquals("640", adder.chainAddition("64", 3)); // chain 64 to 640
    assertEquals("76238513", adder.chainAddition("762", 8)); // chain 762 to 76238513
    assertEquals("77415", adder.chainAddition("7", 5)); // chain 7 to 77415
  }

  // Test chainAddition with large numbers
  @Test
  public void testChainAdditionLargeNumbers() {
    assertEquals("98767533", adder.chainAddition("9876", 8)); // Correct result based on the chain addition process
  }

  // Test chainAddition minimal extension
  @Test
  public void testChainAdditionMinimalExtension() {
    assertEquals("123", adder.chainAddition("12", 3)); // Simple extension
  }

  // Test chainAddition with leading zeros
  @Test
  public void testChainAdditionWithLeadingZeros() {
    assertEquals("001201", adder.chainAddition("0012", 6)); // Handle leading zeroes
  }

  // Test chainAddition based on no-carry addition
  @Test
  public void testChainAdditionNoCarry() {
    assertEquals("421463", adder.chainAddition("4214", 6)); // Extend with no-carry addition
  }

}
