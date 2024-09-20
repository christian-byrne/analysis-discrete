package src.main;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Adder {
  public static void main(String[] args) {
    int a = 10;
    int b = 20;
    int sum = a + b;
    System.out.println("Sum of " + a + " and " + b + " is " + sum);
  }

  /**
   * Converts a given string into a deque of characters.
   *
   * @param inputString the string to be converted into a deque
   * @return a deque containing the characters of the input string
   */
  public static Deque<Character> stringToDeque(String inputString) {
    char[] inputCharList = inputString.toCharArray();
    Deque<Character> inputStringDeque = new ArrayDeque<Character>();
    for (char c : inputCharList) {
      inputStringDeque.add(c);
    }
    return inputStringDeque;
  }

  /**
   * Converts a Deque of Characters to a String.
   *
   * This method takes a Deque containing Character elements and concatenates
   * them into a single String. The characters are appended in the order they
   * appear in the Deque.
   *
   * @param inputDeque the Deque of Characters to be converted to a String
   * @return a String representation of the characters in the input Deque
   */
  static String DequeToString(Deque<Character> inputDeque) {
    StringBuilder sb = new StringBuilder();
    for (char c : inputDeque) {
      sb.append(c);
    }
    return sb.toString();
  }

  /**
   * Converts a list of characters to a single string.
   *
   * @param inputList the list of characters to be converted
   * @return a string that concatenates all characters in the input list
   */
  static String ListToString(List<Character> inputList) {
    StringBuilder sb = new StringBuilder();
    for (char c : inputList) {
      sb.append(c);
    }
    return sb.toString();
  }

  /**
   * Performs no-carry addition on two strings of digits. In no-carry addition,
   * when adding
   * two digits that sum to a number greater than 9, only the unit digit is kept,
   * and the carry is discarded.
   * 
   * For example:
   * - 7 ∔ 18 results in 15 (since 7 + 8 = 15, only 5 is kept)
   * - 359 ∔ 163 results in 412 (since 9 + 3 = 12, only 2 is kept; other digits
   * are summed normally)
   * 
   * The method is designed to handle numbers with leading zeroes as well.
   * 
   * @param num1 the first number as a string
   * @param num2 the second number as a string
   * @return the result of no-carry addition as a string
   */
  public static String noCarryAddition(String num1, String num2) {
    if (num1 == null && num2 == null) {
      return null;
    }
    if (num1 == null) {
      return num2;
    }
    if (num2 == null) {
      return num1;
    }
    if (num1.isEmpty() && num2.isEmpty()) {
      return "";
    }
    Deque<Character> aStringDeque = stringToDeque(num1);
    Deque<Character> bStringDeque = stringToDeque(num2);
    Deque<Character> sumStringDeque = new ArrayDeque<Character>();

    while (aStringDeque.size() > 0 || bStringDeque.size() > 0) {
      char aChar = aStringDeque.size() > 0 ? aStringDeque.removeLast() : '0';
      char bChar = bStringDeque.size() > 0 ? bStringDeque.removeLast() : '0';
      int sum = Character.getNumericValue(aChar) + Character.getNumericValue(bChar);
      String sumString = Integer.toString(sum);
      sumStringDeque.addFirst(sumString.charAt(sumString.length() - 1));
    }
    return DequeToString(sumStringDeque);
  }

  /**
   * Performs chain addition on a given number to extend it by appending digits
   * derived from
   * no-carry additions of adjacent digits. This process continues until the
   * resulting number
   * has as many digits as specified by the second argument.
   * 
   * For example:
   * - chainAddition(64, 3) results in 640 (since 6 + 4 = 10, append 0)
   * - chainAddition(762, 8) results in 76238513 (from successive no-carry
   * additions)
   * 
   * If the number has only one digit, assume zero as the preceding digit to start
   * the process.
   * For example, chainAddition(7, 5) produces 77415.
   * 
   * If the second argument is less than the length of the number, return a prefix
   * of that length.
   * For example, chainAddition(54321, 2) returns "54".
   * 
   * @param num        the number as a string
   * @param digitCount the desired length of the resulting number
   * @return the result of chain addition as a string
   */
  public static String chainAddition(String num, int digitCount) {
    // Convert input string to a character list
    ArrayList<Character> charList = new ArrayList<Character>();
    for (char c : num.toCharArray()) {
      charList.add(c);
    }

    // If the size is already equal to targetDigitCount, return inputString
    if (charList.size() == digitCount) {
      return num;
    }

    // If the input is larger than the target, return the prefix up to
    // targetDigitCount
    if (charList.size() > digitCount) {
      return num.substring(0, digitCount);
    }

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

        // Only increment pointers if the right digit is not null. E.g., when chain
        // addition on 7, first "pair" is 07, and second pair is 77 -- the pair
        // selection does not change
        leftPointer++;
        rightPointer++;
      }

      // Perform no-carry addition of the last two digits
      int newDigit = noCarryAddition(Integer.toString(leftDigit), Integer.toString(rightDigit)).charAt(0) - '0';

      // Append the new digit
      charList.add(Character.forDigit(newDigit, 10));
    }

    return ListToString(charList);
  }

}