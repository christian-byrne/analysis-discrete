package src.test;

import org.junit.jupiter.api.Test;
import src.main.StringPermutation;
import java.util.Random;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringPermutationTest {
  /**
   * DESCRIPTION of what StringPermutation.digitPermutation does.
   * 
   * * Generates a permutation of the digits 0-9 based on the first 10 characters
   * of
   * a given string.
   * Each unique character is assigned a digit in the order of its appearance in
   * the alphabet, and
   * this assignment is applied to the string in left-to-right order.
   * 
   * For example:
   * - Given "BANANALAND", the permutation generated is "4071826395".
   * - The earliest character ('A') appears four times and is assigned digits 0,
   * 1, 2, and 3.
   * - The next character ('B') is assigned 4, 'D' is assigned 5, and so on,
   * producing the final
   * result "4071826395".
   * 
   * If the input string has fewer than 10 characters, the method returns null.
   * 
   * For example, "STARTEARLY" generates the permutation "6704821539".
   */

  private final StringPermutation stringPermutation = new StringPermutation();

  // Test digitPermutation normal cases
  @Test
  public void testDigitPermutationNormalCases() {
    assertEquals("4071826395", stringPermutation.digitPermutation("BANANALAND")); // 4071826395
    assertEquals("6704821539", stringPermutation.digitPermutation("STARTEARLY")); // 6704821539
    assertEquals("0123456789", stringPermutation.digitPermutation("ABCDEFGHIJKL"));
  }

  @Test
  public void testDigitPermutationEdgeCases() {
    assertEquals("0123456789", stringPermutation.digitPermutation("AAAAAAAAAA")); // All the same character
    assertEquals(null, stringPermutation.digitPermutation("")); // Empty string
    assertEquals(null, stringPermutation.digitPermutation("A")); // Single character
    assertEquals(null, stringPermutation.digitPermutation("   ")); // Whitespace
  }

  @Test
  public void testDigitPermutationMixedCases() {
    assertEquals("0123456789", stringPermutation.digitPermutation("AABBBCCCDD")); // Mixed characters
    assertEquals("0123456789", stringPermutation.digitPermutation("AABBBCCCDD")); // Mixed characters
    assertEquals("0123456789", stringPermutation.digitPermutation("AABBBCCCDD")); // Mixed characters
  }

  @Test
  public void testDigitPermutationGroupedLetters() {
    assertEquals("4560123789", stringPermutation.digitPermutation("CCCAAAADDD"));
    assertEquals("7894560123", stringPermutation.digitPermutation("DDDBBBAAAA"));
    assertEquals("0123789456", stringPermutation.digitPermutation("AAAACCCBBB"));
  }

  @Test
  public void testDigitPermutationAllDistinctCharacters() {
    assertEquals("0123456789", stringPermutation.digitPermutation("ABCDEFGHIJK"));
    // Now shuffle the order (the expected result should essentially be a digits
    // indicating the lexographical order of the characters)
    // Wherever A is, the same index should have 0
    // Wherever B is, the same index should have 1
    // And so on
    // A: 0
    // B: 1
    // C: 2
    // D: 3
    // E: 4
    // F: 5
    // G: 6
    // H: 7
    // I: 8
    // J: 9
    assertEquals("0481592673", stringPermutation.digitPermutation("AEIBFJCGHD"));
  }

  // Helper function that generates a random permutation of digits 0-9 and maps
  // them to A-J
  public void testRandomShufflings(StringPermutation stringPermutation) {
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
      assertEquals(expected, stringPermutation.digitPermutation(input), "Failed on input: " + input);
    }
  }

  // JUnit 5 test case to invoke the helper function
  @Test
  public void testRandomShufflings100Times() {
    StringPermutation stringPermutation = new StringPermutation(); // Replace with your class
    testRandomShufflings(stringPermutation);
  }

  @Test
  public void testStraddlingCheckerboardNormalCases() {
    String[] expected = { "4", "04", "00", "07", "5", "01", "08", "3", "1", "02", "06", "03", "09", "8", "9", "05",
        "24", "20", "6", "7", "27", "21", "28", "22", "26", "23" };
    assertEquals(
        // Convert to ArrayList for comparison
        new ArrayList<String>(List.of(expected)),
        stringPermutation.straddlingCheckerboard("4071826395", "a tin shoe"));
  }
}
