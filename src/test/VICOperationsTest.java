package src.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import src.main.VICOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VICOperationsTest {

  private final VICOperations vic = new VICOperations();

  // Test noCarryAddition normal cases
  @Test
  public void testNoCarryAdditionNormalCases() {
    assertEquals("15", vic.noCarryAddition("7", "18")); // 7 ∔ 18 = 15
    assertEquals("412", vic.noCarryAddition("359", "163")); // 359 ∔ 163 = 412
    assertEquals("22712", vic.noCarryAddition("85721", "47091")); // 85721 ∔ 47091 = 22712
    assertEquals("2527058253", vic.noCarryAddition("2271249836", "0356819427"));
    assertEquals("1343450275", vic.noCarryAddition("1805398582", "0548162793"));
  }

  // Test noCarryAddition edge cases
  @Test
  public void testNoCarryAdditionEdgeCases() {
    assertEquals("888", vic.noCarryAddition("999", "999")); // Overflow carry discarded
    assertEquals("000", vic.noCarryAddition("000", "000")); // Leading zeroes
  }

  // Test chainAddition normal cases
  @Test
  public void testChainAdditionNormalCases() {
    assertEquals("640", vic.chainAddition("64", 3)); // chain 64 to 640
    assertEquals("76238513", vic.chainAddition("762", 8)); // chain 762 to 76238513
    assertEquals("77415", vic.chainAddition("7", 5)); // chain 7 to 77415
    assertEquals("2271249836", vic.chainAddition("22712", 10));
    assertEquals("1805398582", vic.chainAddition("18053", 10));
  }

  // Test chainAddition with large numbers
  @Test
  public void testChainAdditionLargeNumbers() {
    assertEquals("98767533", vic.chainAddition("9876", 8)); // Correct result based on the chain addition process
  }

  // Test chainAddition minimal extension
  @Test
  public void testChainAdditionMinimalExtension() {
    assertEquals("123", vic.chainAddition("12", 3)); // Simple extension
  }

  // Test chainAddition with leading zeros
  @Test
  public void testChainAdditionWithLeadingZeros() {
    assertEquals("001201", vic.chainAddition("0012", 6)); // Handle leading zeroes
  }

  // Test chainAddition based on no-carry addition
  @Test
  public void testChainAdditionNoCarry() {
    assertEquals("421463", vic.chainAddition("4214", 6)); // Extend with no-carry addition
  }

  // Test digitPermutation normal cases
  @Test
  public void testDigitPermutationNormalCases() {
    assertEquals("4071826395", vic.digitPermutation("BANANALAND")); // 4071826395
    assertEquals("6704821539", vic.digitPermutation("STARTEARLY")); // 6704821539
    assertEquals("0123456789", vic.digitPermutation("ABCDEFGHIJKL"));
    assertEquals("1528069374", vic.digitPermutation("2527058253"));
    assertEquals("1354670298", vic.digitPermutation("1343450275"));
  }

  @Test
  public void testDigitPermutationEdgeCases() {
    assertEquals("0123456789", vic.digitPermutation("AAAAAAAAAA")); // All the same character
    assertEquals(null, vic.digitPermutation("")); // Empty string
    assertEquals(null, vic.digitPermutation("A")); // Single character
    assertEquals(null, vic.digitPermutation("   ")); // Whitespace
  }

  @Test
  public void testDigitPermutationMixedCases() {
    assertEquals("0123456789", vic.digitPermutation("AABBBCCCDD")); // Mixed characters
    assertEquals("0123456789", vic.digitPermutation("AABBBCCCDD")); // Mixed characters
    assertEquals("0123456789", vic.digitPermutation("AABBBCCCDD")); // Mixed characters
  }

  @Test
  public void testDigitPermutationGroupedLetters() {
    assertEquals("4560123789", vic.digitPermutation("CCCAAAADDD"));
    assertEquals("7894560123", vic.digitPermutation("DDDBBBAAAA"));
    assertEquals("0123789456", vic.digitPermutation("AAAACCCBBB"));
  }

  @Test
  public void testDigitPermutationAllDistinctCharacters() {
    assertEquals("0123456789", vic.digitPermutation("ABCDEFGHIJK"));
    assertEquals("0481592673", vic.digitPermutation("AEIBFJCGHD"));
  }

  // Helper function that generates a random permutation of digits 0-9 and maps
  // them to A-J
  public void testRandomShufflings(VICOperations vic) {
    // Create a map for the digit-to-letter correspondence
    String alphabet = "ABCDEFGHIJ";
    Random random = new Random();

    // Run 100 tests
    for (int i = 0; i < 100; i++) {
      // Generate a list of digits 0-9
      List<Integer> digits = new ArrayList<>();
      for (int j = 0; j < 10; j++) {
        digits.add(j);
      }

      // Shuffle the digits randomly
      Collections.shuffle(digits, random);

      // Create the corresponding string based on the shuffled digits
      StringBuilder inputString = new StringBuilder();
      StringBuilder expectedString = new StringBuilder();

      for (int digit : digits) {
        inputString.append(alphabet.charAt(digit)); // Map digits to A-J
        expectedString.append(digit); // Keep the same order of digits for the expected result
      }

      // Convert input and expected strings to proper format
      String input = inputString.toString();
      String expected = expectedString.toString();

      // Run the assert to ensure the digitPermutation function works
      assertEquals(expected, vic.digitPermutation(input), "Failed on input: " + input);
    }
  }

  // JUnit 5 test case to invoke the helper function
  @Test
  public void testRandomShufflings100Times() {
    testRandomShufflings(vic);
  }

  @Test
  public void testStraddlingCheckerboardNormalCases() {
    String[] expected = { "4", "04", "00", "07", "5", "01", "08", "3", "1", "02", "06", "03", "09", "8", "9", "05",
        "24", "20", "6", "7", "27", "21", "28", "22", "26", "23" };
    assertEquals(
        // Convert to ArrayList for comparison
        new ArrayList<String>(List.of(expected)),
        vic.straddlingCheckerboard("4071826395", "a tin shoe"));
  }

  @Test
  public void testCreateDigitPermutationFromPhrase() {
    assertEquals("0356819427", vic.digitPermutation("CHOOSETHER"));
    assertEquals("0548162793", vic.digitPermutation("DONTDODRUG"));
  }

}
