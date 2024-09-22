/*
 * Author: Christian Byrne
 * Course: CSc 345 — Analysis of Discrete Structures
 * Assignment: Program #1: The VIC (VIC InComplete) Cipher
 * Instructor: McCann
 * TAs: Rubin Yang, Lucas Almeida, Hamad Ayaz, Sohan Bhakta, CJ Chen, Hyungji Kim, Hamlet Taraz
 * Due Date: September 19th, 2024
 *
 * This class implements the operations used in the VIC cipher for both encryption 
 * and decryption processes. It contains methods for digit permutations, 
 * straddling checkerboard generation, and no-carry addition, among others.
 *
 * Language/Version: Java 6
 * Compilation: No special compilation details required.
 *
 * Known Bugs: None.
 * Features Not Implemented: Handling of different date formats.
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/*
 * Class: VICOperations
 * Author: Christian Byrne
 * Dependencies: None
 * Purpose: This class provides various utility operations for encoding and decoding 
 * messages using the VIC cipher.
 */
public class VICOperations {
  static final private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  static HashMap<String, LinkedList<Integer>> digitMap = new HashMap<>();

  /*
   * Method: countChar
   * Purpose: Counts the occurrences of a specified character in a given string.
   * Pre-condition: str is a valid string, and c is a character.
   * Post-condition: Returns the count of the specified character in the string.
   * Parameters:
   * - str: A string in which to count occurrences of the character.
   * - c: The character to count within the string.
   * Returns: The number of times the specified character appears in the string.
   */
  private static int countChar(String str, char c) {
    return (int) str.chars().filter(ch -> ch == c).count(); // Stream-based char counting
  }

  /*
   * Method: removeDuplicates
   * Purpose: Removes duplicate characters from a given string.
   * Pre-condition: str is a valid string.
   * Post-condition: Returns a string without duplicate characters.
   * Parameters:
   * - str: The input string.
   * Returns: A string without duplicate characters.
   */
  private static String removeDuplicates(String str) {
    StringBuilder sb = new StringBuilder();
    for (char c : str.toCharArray()) {
      if (sb.indexOf(String.valueOf(c)) == -1) {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  /*
   * Method: arrangeCharsLexographically
   * Purpose: Arranges characters in a string lexicographically.
   * Pre-condition: str is a valid string.
   * Post-condition: Returns a list of characters arranged lexicographically.
   * Parameters:
   * - str: The input string.
   * Returns: A list of characters arranged lexicographically.
   */
  private static ArrayList<String> arrangeCharsLexographically(String str) {
    ArrayList<String> result = new ArrayList<>();
    for (char c : str.toCharArray()) {
      result.add(String.valueOf(c));
    }
    result.sort(null); // Sort lexicographically
    return result;
  }

  /*
   * Method: mapCharOrderings
   * Purpose: Maps character orderings and stores them in a HashMap.
   * Pre-condition: str is a valid string.
   * Post-condition: Populates digitMap with characters and their positions.
   * Parameters:
   * - str: The input string.
   */
  private static void mapCharOrderings(String str) {
    String distinctChars = removeDuplicates(str);
    ArrayList<String> orderedChars = arrangeCharsLexographically(distinctChars);
    int counter = 0;

    for (String charStr : orderedChars) {
      int digitCount = countChar(str, charStr.charAt(0));
      LinkedList<Integer> list = new LinkedList<>();

      for (int j = 0; j < digitCount; j++) {
        list.add(counter++);
      }
      digitMap.put(charStr, list);
    }
  }

  /*
   * Method: digitPermutation
   * Purpose: Generates a permutation of the digits 0-9 based on the first 10
   * characters of a given string.
   * Pre-condition: str is a valid string with at least 10 characters.
   * Post-condition: Returns a permutation of the digits 0-9.
   * Parameters:
   * - str: The input string.
   * Returns: A string representing the permutation of the digits 0-9.
   */
  public static String digitPermutation(String str) {
    if (str.length() < 10)
      return null; // Ensure minimum length of 10

    digitMap.clear(); // Reset digit map
    str = str.toUpperCase().substring(0, 10); // Ensure uppercase and trim to 10 characters

    mapCharOrderings(str); // Map character orderings

    StringBuilder result = new StringBuilder();

    for (char c : str.toCharArray()) {
      String key = String.valueOf(c);

      if (!digitMap.containsKey(key)) {
        if (key.equals(" ")) {
          result.append(" "); // Handle whitespace
        } else {
          throw new IllegalArgumentException("Character not in alphabet");
        }
      } else {
        result.append(digitMap.get(key).removeFirst()); // Append first element from linked list
      }
    }

    return result.toString();
  }

  /*
   * Method: straddlingCheckerboard
   * Purpose: Generates a straddling checkerboard for mapping letters to numeric
   * codes based on a digit permutation and an anagram.
   * Pre-condition: digits is a permutation of the digits 0-9, anagram is a valid
   * 10-character string.
   * Post-condition: Returns an ArrayList of strings representing the
   * letter-to-number mapping.
   * Parameters:
   * - digits: A permutation of the digits 0-9.
   * - anagram: An anagram of eight distinct letters and two spaces.
   * Returns: An ArrayList representing the letter-to-number mapping.
   */
  public static ArrayList<String> straddlingCheckerboard(String digits, String anagram) {
    if (digits.length() != 10 || anagram.length() != 10)
      return null; // Validate inputs

    anagram = anagram.toUpperCase(); // Convert to uppercase
    ArrayList<String> extraLetters = extractExtraLetters(anagram);
    ArrayList<String> result = new ArrayList<>();

    int firstSpaceIndex = anagram.indexOf(" ");
    int secondSpaceIndex = anagram.indexOf(" ", firstSpaceIndex + 1);

    if (firstSpaceIndex == -1 || secondSpaceIndex == -1)
      return null; // Validate spaces

    String[] rowLabels = {
        String.valueOf(digits.charAt(firstSpaceIndex)),
        String.valueOf(digits.charAt(secondSpaceIndex))
    };

    result = mapStraddlingCheckerboard(anagram, digits, extraLetters, rowLabels);

    return result;
  }

  /*
   * Method: extractExtraLetters
   * Purpose: Extracts letters from the alphabet that are not in the anagram.
   * Pre-condition: anagram is a valid string.
   * Post-condition: Returns a list of characters not present in the anagram.
   * Parameters:
   * - anagram: The input anagram.
   * Returns: An ArrayList containing the letters not in the anagram.
   */
  private static ArrayList<String> extractExtraLetters(String anagram) {
    ArrayList<String> extraLetters = new ArrayList<>();
    for (char c : alphabet.toCharArray()) {
      if (anagram.indexOf(c) == -1) {
        extraLetters.add(String.valueOf(c));
      }
    }
    return extraLetters;
  }

  /*
   * Method: mapStraddlingCheckerboard
   * Purpose: Maps the characters in the straddling checkerboard.
   * Pre-condition: anagram and digits are valid strings, extraLetters and
   * rowLabels are valid arrays.
   * Post-condition: Returns the mapped straddling checkerboard.
   * Parameters:
   * - anagram: The input anagram.
   * - digits: A permutation of the digits 0-9.
   * - extraLetters: A list of characters not in the anagram.
   * - rowLabels: Row labels used for the straddling checkerboard.
   * Returns: An ArrayList representing the mapped straddling checkerboard.
   */
  private static ArrayList<String> mapStraddlingCheckerboard(String anagram, String digits,
      ArrayList<String> extraLetters, String[] rowLabels) {
    ArrayList<String> result = new ArrayList<>();

    for (char currentChar : alphabet.toCharArray()) {
      if (anagram.indexOf(currentChar) != -1) {
        int anagramIndex = anagram.indexOf(currentChar);
        result.add(digits.substring(anagramIndex, anagramIndex + 1));
      } else {
        int index = extraLetters.indexOf(String.valueOf(currentChar));
        int rowIndex = index / 10;
        int columnIndex = index % 10;
        result.add(rowLabels[rowIndex] + digits.charAt(columnIndex));
      }
    }

    return result;
  }

  /*
   * Method: stringToDeque
   * Purpose: Converts a given string into a deque of characters.
   * Pre-condition: inputString is a valid string.
   * Post-condition: Returns a deque containing the characters of the input
   * string.
   * Parameters:
   * - inputString: The string to convert to a deque.
   * Returns: A deque containing the characters of the input string.
   */
  public static Deque<Character> stringToDeque(String inputString) {
    Deque<Character> deque = new ArrayDeque<>();
    for (char c : inputString.toCharArray()) {
      deque.add(c);
    }
    return deque;
  }

  /*
   * Method: dequeToString
   * Purpose: Converts a deque of characters into a string.
   * Pre-condition: inputDeque is a valid deque of characters.
   * Post-condition: Returns a string containing the characters in the deque.
   * Parameters:
   * - inputDeque: A deque of characters.
   * Returns: A string representation of the characters in the deque.
   */
  static String dequeToString(Deque<Character> inputDeque) {
    StringBuilder sb = new StringBuilder();
    inputDeque.forEach(sb::append);
    return sb.toString();
  }

  /*
   * Method: listToString
   * Purpose: Converts a list of characters to a string.
   * Pre-condition: inputList is a valid list of characters.
   * Post-condition: Returns a string containing the characters from the list.
   * Parameters:
   * - inputList: A list of characters.
   * Returns: A string representation of the characters in the list.
   */
  static String listToString(List<Character> inputList) {
    StringBuilder sb = new StringBuilder();
    inputList.forEach(sb::append);
    return sb.toString();
  }

  /*
   * Method: noCarryAddition
   * Purpose: Performs no-carry addition on two strings of digits.
   * Pre-condition: num1 and num2 are valid strings of digits.
   * Post-condition: Returns the result of the no-carry addition.
   * Parameters:
   * - num1: The first number as a string.
   * - num2: The second number as a string.
   * Returns: A string representing the result of no-carry addition.
   */
  public static String noCarryAddition(String num1, String num2) {
    if (num1 == null)
      return num2 == null ? null : num2;
    if (num2 == null)
      return num1;
    if (num1.isEmpty() && num2.isEmpty())
      return "";

    Deque<Character> aDeque = stringToDeque(num1);
    Deque<Character> bDeque = stringToDeque(num2);
    Deque<Character> sumDeque = new ArrayDeque<>();

    while (!aDeque.isEmpty() || !bDeque.isEmpty()) {
      int sum = getNextDigit(aDeque) + getNextDigit(bDeque);
      sumDeque.addFirst(Character.forDigit(sum % 10, 10));
    }

    return dequeToString(sumDeque);
  }

  /*
   * Method: chainAddition
   * Purpose: Extends a given string of digits using chain addition to reach a
   * specified length.
   * Pre-condition: num is a valid string of digits, and digitCount is a positive
   * integer.
   * Post-condition: Returns the extended string of digits.
   * Parameters:
   * - num: The input string of digits.
   * - digitCount: The target length for the string.
   * Returns: The extended string of digits.
   */
  public static String chainAddition(String num, int digitCount) {
    // Convert input string to a character list
    ArrayList<Character> charList = new ArrayList<>();
    for (char c : num.toCharArray()) {
      charList.add(c);
    }

    // If the size is already equal to targetDigitCount, return input string
    if (charList.size() == digitCount)
      return num;
    // If the input is larger than the target, return the prefix up to
    // targetDigitCount
    if (charList.size() > digitCount)
      return num.substring(0, digitCount);

    // Extend the number by appending digits based on no-carry addition
    int leftPointer = 0;
    int rightPointer = 1;

    while (charList.size() < digitCount) {
      int leftDigit;
      int rightDigit;

      // Handle cases for single or empty inputs
      if (leftPointer >= charList.size()) {
        leftDigit = 0;
      } else {
        leftDigit = Character.getNumericValue(charList.get(leftPointer));
      }

      if (rightPointer >= charList.size()) {
        rightDigit = 0;
      } else {
        rightDigit = Character.getNumericValue(charList.get(rightPointer));

        // Only increment pointers if the right digit is not null.
        leftPointer++;
        rightPointer++;
      }

      // Perform no-carry addition of the last two digits
      int newDigit = noCarryAddition(Integer.toString(leftDigit), Integer.toString(rightDigit)).charAt(0) - '0';

      // Append the new digit
      charList.add(Character.forDigit(newDigit, 10));
    }

    return listToString(charList);
  }

  /*
   * Method: getNextDigit
   * Purpose: Retrieves the next digit from a deque.
   * Pre-condition: deque is a valid deque of characters.
   * Post-condition: Returns the next digit from the deque.
   * Parameters:
   * - deque: The input deque of characters.
   * Returns: The next digit as an integer.
   */
  private static int getNextDigit(Deque<Character> deque) {
    return deque.isEmpty() ? 0 : Character.getNumericValue(deque.removeLast());
  }
}
