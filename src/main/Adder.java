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

  public static Deque<Character> stringToDeque(String inputString) {
    char[] inputCharList = inputString.toCharArray();
    Deque<Character> inputStringDeque = new ArrayDeque<Character>();
    for (char c : inputCharList) {
      inputStringDeque.add(c);
    }
    return inputStringDeque;
  }

  static String DequeToString(Deque<Character> inputDeque) {
    StringBuilder sb = new StringBuilder();
    for (char c : inputDeque) {
      sb.append(c);
    }
    return sb.toString();
  }

  static String ListToString(List<Character> inputList) {
    StringBuilder sb = new StringBuilder();
    for (char c : inputList) {
      sb.append(c);
    }
    return sb.toString();
  }

  public static String noCarryAddition(String a, String b) {
    if (a == null && b == null) {
      return null;
    }
    if (a == null) {
      return b;
    }
    if (b == null) {
      return a;
    }
    if (a.isEmpty() && b.isEmpty()) {
      return "";
    }
    Deque<Character> aStringDeque = stringToDeque(a);
    Deque<Character> bStringDeque = stringToDeque(b);
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

  public static String chainAddition(String inputString, int targetDigitCount) {
    // Convert input string to a character list
    ArrayList<Character> charList = new ArrayList<Character>();
    for (char c : inputString.toCharArray()) {
      charList.add(c);
    }

    // If the size is already equal to targetDigitCount, return inputString
    if (charList.size() == targetDigitCount) {
      return inputString;
    }

    // If the input is larger than the target, return the prefix up to
    // targetDigitCount
    if (charList.size() > targetDigitCount) {
      return inputString.substring(0, targetDigitCount);
    }

    // Extend the number by appending digits based on no-carry addition
    int leftPointer = 0;
    int rightPointer = 1;
    while (charList.size() < targetDigitCount) {
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