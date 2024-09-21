package src.main;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class VICOperations {
  static final private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  static HashMap<String, LinkedList<Integer>> digitMap = new HashMap<String, LinkedList<Integer>>();

  /**
   * Counts the occurrences of a specified character in a given string.
   *
   * @param str the string in which to count the occurrences of the character
   * @param c   the character to count within the string
   * @return the number of times the specified character appears in the string
   */
  private static int countChar(String str, char c) {
    return (int) str.chars().filter(ch -> ch == c).count(); // Stream-based char counting
  }

  /**
   * Removes duplicates from a given string.
   *
   * @param str the input string
   * @return a string without duplicate characters
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

  /**
   * Arranges characters in a string lexicographically.
   *
   * @param str the input string
   * @return a list of characters arranged lexicographically
   */
  private static ArrayList<String> arrangeCharsLexographically(String str) {
    ArrayList<String> result = new ArrayList<>();
    for (char c : str.toCharArray()) {
      result.add(String.valueOf(c));
    }
    result.sort(null); // Sort lexicographically
    return result;
  }

  /**
   * Maps character orderings and stores them in a HashMap.
   *
   * @param str the input string
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

  /**
   * Generates a permutation of the digits 0-9 based on the first 10 characters of
   * a given string.
   *
   * @param str the input string
   * @return a permutation of the digits 0-9 based on the first 10 characters, or
   *         null if the string is too short
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

      // If the character is not in the alphabet, handle accordingly
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

  /**
   * Generates a straddling checkerboard for mapping letters to numeric codes
   * based
   * on a digit permutation and an anagram of eight distinct letters and two
   * spaces.
   *
   * @param digits  a permutation of the digits 0-9
   * @param anagram an anagram of eight distinct letters and two spaces
   * @return an ArrayList of strings representing the letter-to-number mapping, or
   *         null if arguments are invalid
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

  private static ArrayList<String> extractExtraLetters(String anagram) {
    ArrayList<String> extraLetters = new ArrayList<>();
    for (char c : alphabet.toCharArray()) {
      if (anagram.indexOf(c) == -1) {
        extraLetters.add(String.valueOf(c));
      }
    }
    return extraLetters;
  }

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

  /**
   * Converts a given string into a deque of characters.
   *
   * @param inputString the string to be converted into a deque
   * @return a deque containing the characters of the input string
   */
  public static Deque<Character> stringToDeque(String inputString) {
    Deque<Character> deque = new ArrayDeque<>();
    for (char c : inputString.toCharArray()) {
      deque.add(c);
    }
    return deque;
  }

  /**
   * Converts a Deque of Characters to a String.
   *
   * @param inputDeque the Deque of Characters to be converted to a String
   * @return a String representation of the characters in the input Deque
   */
  static String dequeToString(Deque<Character> inputDeque) {
    StringBuilder sb = new StringBuilder();
    inputDeque.forEach(sb::append);
    return sb.toString();
  }

  /**
   * Converts a list of characters to a single string.
   *
   * @param inputList the list of characters to be converted
   * @return a string that concatenates all characters in the input list
   */
  static String listToString(List<Character> inputList) {
    StringBuilder sb = new StringBuilder();
    inputList.forEach(sb::append);
    return sb.toString();
  }

  /**
   * Performs no-carry addition on two strings of digits.
   *
   * @param num1 the first number as a string
   * @param num2 the second number as a string
   * @return the result of no-carry addition as a string
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

  private static int getNextDigit(Deque<Character> deque) {
    return deque.isEmpty() ? 0 : Character.getNumericValue(deque.removeLast());
  }

}
