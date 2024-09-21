package src.main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;

public class StringPermutation {
  public static void main(String[] args) {

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
    str = str.toLowerCase();

    // Truncate the string to the first 10 characters
    if (str.length() > 10) {
      str = str.substring(0, 10);
    }

    // Map the letter orderings
    mapLetterOrderings(str);

    // Construct the result string
    StringBuilder result = new StringBuilder();

    // Iterate through the original string
    for (int i = 0; i < str.length(); i++) {
      String key = String.valueOf(str.charAt(i));

      // Raise an exception if the character is not in the alphabet
      if (!digitMap.containsKey(key)) {
        if (key.equals(" ")) {
          // Append whitespace if whitespace is found
          result.append(" ");
          continue;
        }
        throw new IllegalArgumentException(
            "Character not in alphabet");
      }

      // Append first element of the current character's linked list
      result.append(digitMap.get(key).removeFirst());
    }
    return result.toString();
  }

  /**
   * Generates a straddling checkerboard for mapping letters to numeric codes
   * based on a digit
   * permutation (first argument) and an anagram of eight distinct letters and two
   * spaces (second argument).
   * The method returns an ArrayList of strings, where each string represents the
   * numeric assignment
   * of a letter in alphabetical order.
   * 
   * For example:
   * - Given the digit permutation "4071826395" and the anagram "a tin shoe", the
   * straddling checkerboard
   * is created with two rows: the first containing the anagram's characters and
   * the second and third
   * rows containing the remaining letters of the alphabet (except for the two
   * letters assigned to the spaces).
   * 
   * Example board:
   * 
   * 4 0 7 1 8 2 6 3 9 5
   * A T I N S H O E
   * 0 B C D F G J K L M P
   * 2 Q R U V W X Y Z
   * 
   * The method assigns numbers to each letter by concatenating the row's label
   * (if it exists) with the column
   * label. For instance:
   * - 'F' is assigned "01" (row 0, column 1),
   * - 'Z' is assigned "23" (row 2, column 3),
   * - 'S' is assigned "6" (since it appears in the first row without a row
   * label).
   * 
   * The return value is an ArrayList of strings where the assignments are
   * arranged in alphabetical order.
   * 
   * For example, with the checkerboard above, the method returns:
   * ["4", "04", "00", "07", "5", "01", "08", ..., "22", "26", "23"]
   * 
   * If either argument is invalid (the first is not a valid permutation of digits
   * 0-9, or the second
   * is not a valid anagram), the method returns null.
   * 
   * @param digits  a permutation of the digits 0-9
   * @param anagram an anagram of eight distinct letters and two spaces
   * @return an ArrayList of strings representing the letter-to-number mapping, or
   *         null if arguments are invalid
   */
  public static ArrayList<String> straddlingCheckerboard(String digits, String anagram) {
    // Return null if the digits string is not 10 characters long
    if (digits.length() != 10) {
      return null;
    }

    // Return null if the anagram string is not 10 characters long
    if (anagram.length() != 10) {
      return null;
    }

    // more efficient is this is a hashmap
    ArrayList<String> extraLetters = new ArrayList<String>();

    ArrayList<String> result = new ArrayList<String>();

    for (int i = 0; i < 26; i++) {
      char currentChar = alphabet.charAt(i);
      if (anagram.indexOf(currentChar) == -1) {
        extraLetters.add(String.valueOf(alphabet.charAt(i)));
      }
    }

    // Get index of first and second whitespace in anagram
    int firstSpaceIndex = anagram.indexOf(" ");
    int secondSpaceIndex = anagram.indexOf(" ", firstSpaceIndex + 1);

    // Return null if either space is not found
    if (firstSpaceIndex == -1 || secondSpaceIndex == -1) {
      return null;
    }

    // Row labels are the characters in digits string at the index of the spaces
    String[] rowLabels = {
        String.valueOf(digits.charAt(firstSpaceIndex)),
        String.valueOf(digits.charAt(secondSpaceIndex))
    };

    for (int i = 0; i < 26; i++) {
      char currentChar = alphabet.charAt(i);

      // Check if the character is in the anagram
      if (anagram.indexOf(currentChar) != -1) {
        int anagramIndex = anagram.indexOf(currentChar);
        String columnLabel = digits.substring(anagramIndex, anagramIndex + 1);
        result.add(columnLabel);
      } else {
        int index = extraLetters.indexOf(String.valueOf(currentChar));

        // Determine if first or second row and then add the row label
        int rowIndex = index / 10;
        String rowLabel = rowLabels[rowIndex];

        int columnIndex = index % 10;
        String columnLabel = digits.substring(columnIndex, columnIndex + 1);
        result.add(rowLabel + columnLabel);
      }

    }
    return result;
  }
}
