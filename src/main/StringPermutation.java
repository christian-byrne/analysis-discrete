
package src.main;

import java.util.HashMap;
import java.util.LinkedList;

public class StringPermutation {
  public static void main(String[] args) {
    // Test the digitPermutation method
    System.out.println("Expected: 4071826395");
    System.out.println(digitPermutation("BANANALAND")); // 4071826395
    System.out.println("Expected: 6704821539");
    System.out.println(digitPermutation("STARTEARLY")); // 6704821539
  }

  static final private String alphabet = "abcdefghijklmnopqrstuvwxyz";

  // Use a hashmap mapping characters to a linked list of integers, because we
  // will be accessing by key only (O(1) for hashmap) and on each access we will
  // be removing the first element (O(1) for linked list)
  static HashMap<String, LinkedList<Integer>> digitMap = new HashMap<String, LinkedList<Integer>>();

  /**
   * Counts the occurrences of a specified character in a given string.
   *
   * @param str the string in which to count the occurrences of the character
   * @param c   the character to count within the string
   * @return the number of times the specified character appears in the string
   */
  private static int countChar(String str, char c) {
    int count = 0;
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == c) {
        count++;
      }
    }
    return count;
  }

  private static void mapLetterOrderings(String str) {
    // Iterate through the alphabet
    int counter = 0;
    for (int i = 0; i < 26; i++) {

      // Get the number of times the current character appears in the string
      int digitCount = countChar(str, alphabet.charAt(i));

      // Create a linked list of integers from counter to counter + digitCount
      LinkedList<Integer> list = new LinkedList<Integer>();
      for (int j = 0; j < digitCount; j++) {
        list.add(counter);
        counter++;
      }

      // Add the linked list to the hashmap, mapping it to the current character
      digitMap.put(String.valueOf(alphabet.charAt(i)), list);

    }
  }

  /**
   * Generates a permutation of the digits 0-9 based on the first 10 characters of
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
   * 
   * @param str the input string
   * @return a permutation of the digits 0-9 based on the first 10 characters, or
   *         null if the string
   *         is too short
   */
  public static String digitPermutation(String str) {
    // Return null if the string is too short
    if (str.length() < 10) {
      return null;
    }

    // Clear the digit map
    digitMap.clear();

    // Convert to lowercase to ignore case
    String lowercaseStr = str.toLowerCase();

    // Truncate the string to the first 10 characters
    if (lowercaseStr.length() > 10) {
      lowercaseStr = lowercaseStr.substring(0, 10);
    }

    // Map the letter orderings
    mapLetterOrderings(lowercaseStr);

    // Construct the result string
    StringBuilder result = new StringBuilder();

    // Iterate through the original string
    for (int i = 0; i < str.length(); i++) {
      String key = String.valueOf(lowercaseStr.charAt(i));

      // Raise an exception if the character is not in the alphabet
      if (!digitMap.containsKey(key)) {
        throw new IllegalArgumentException(
            "Character not in alphabet, but it should have been mapped  by the previous loop");
      }

      // Append first element of the current character's linked list
      result.append(digitMap.get(key).removeFirst());

    }

    return result.toString();
  }
}
