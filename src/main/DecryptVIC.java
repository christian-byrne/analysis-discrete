package src.main;

import java.util.ArrayList;

public class DecryptVIC {

  private static final VICOperations vicOperations = new VICOperations();

  /**
   * Decodes a message encoded with the VIC algorithm.
   * 
   * @param encodedMessage the message to decode (all digits)
   * @param date           the date in the format YYMMDD
   * @param phrase         the phrase from which the first 10 letters are used
   * @param anagram        the 10-character anagram of 8 unique letters and 2
   *                       spaces
   * @return the decoded message
   */
  public static String decodeMessage(String encodedMessage, String date, String phrase, String anagram) {
    // Step 1: Extract the agent ID from the message
    String agentID = extractAgentID(encodedMessage, date);
    encodedMessage = removeAgentID(encodedMessage, date);

    // Step 2: Recreate the same straddling checkerboard
    String result1 = addIdToDate(agentID, date);
    String result2 = vicOperations.chainAddition(result1, 10);
    String result3 = vicOperations.digitPermutation(phrase.toUpperCase().substring(0, 10));
    String result4 = vicOperations.noCarryAddition(result2, result3);
    String result5 = vicOperations.digitPermutation(result4);
    ArrayList<String> checkerboard = vicOperations.straddlingCheckerboard(result5, anagram.toUpperCase());

    // Step 3: Decode the message using the straddling checkerboard
    return decodeUsingCheckerboard(encodedMessage, checkerboard);
  }

  /**
   * Extracts the agent ID from the encoded message.
   * 
   * @param encodedMessage the encoded message
   * @param date           the date in the format YYMMDD (used to find the
   *                       position)
   * @return the extracted agent ID
   */
  private static String extractAgentID(String encodedMessage, String date) {
    int position = Character.getNumericValue(date.charAt(date.length() - 1));
    return encodedMessage.substring(position, position + 5); // Extract the 5-digit agent ID
  }

  /**
   * Removes the agent ID from the encoded message.
   * 
   * @param encodedMessage the encoded message
   * @param date           the date in the format YYMMDD (used to find the
   *                       position)
   * @return the encoded message without the agent ID
   */
  private static String removeAgentID(String encodedMessage, String date) {
    int position = Character.getNumericValue(date.charAt(date.length() - 1));
    return encodedMessage.substring(0, position) + encodedMessage.substring(position + 5);
  }

  /**
   * Decodes the encoded message using the straddling checkerboard.
   * 
   * @param encodedMessage the encoded message (digits)
   * @param checkerboard   the straddling checkerboard for digit-to-letter mapping
   * @return the decoded message
   */
  private static String decodeUsingCheckerboard(String encodedMessage, ArrayList<String> checkerboard) {
    StringBuilder decodedMessage = new StringBuilder();
    int i = 0;

    while (i < encodedMessage.length()) {
      char digit = encodedMessage.charAt(i);

      if (digit == '0' || digit == '3') {
        // If digit is 0 or 3, the next two digits represent a letter
        String digitPair = encodedMessage.substring(i, i + 2);
        int letterIndex = checkerboard.indexOf(digitPair);
        decodedMessage.append((char) ('A' + letterIndex));
        i += 2; // Move forward by two digits
      } else {
        // Single digit represents a letter
        int letterIndex = checkerboard.indexOf(String.valueOf(digit));
        decodedMessage.append((char) ('A' + letterIndex));
        i++; // Move forward by one digit
      }
    }

    return decodedMessage.toString();
  }

  /**
   * Adds the ID to the first five digits of the date using no-carry addition.
   * 
   * @param id   the ID string
   * @param date the date string (first five digits)
   * @return the result of the no-carry addition
   */
  public static String addIdToDate(String id, String date) {
    if (id.length() < 5 || date.length() < 5) {
      return null; // Ensure both ID and date are at least 5 characters long
    }

    date = truncateToFiveDigits(date); // Ensure date is exactly 5 digits
    return vicOperations.noCarryAddition(id, date);
  }

  /**
   * Truncates the input date to the first five digits if necessary.
   *
   * @param date the input date string
   * @return a string with the first five digits of the date
   */
  private static String truncateToFiveDigits(String date) {
    return date.length() > 5 ? date.substring(0, 5) : date;
  }

  public static void main(String[] args) {
    // Example usage with provided inputs
    String encodedMessage = "8150520956920365"; // Message to decode
    String date = "251221"; // Date in YYMMDD format
    String phrase = "The quick brown fox"; // Phrase for first 10 letters
    String anagram = "A TIN SHOE"; // 10-character anagram with 2 spaces

    String decodedMessage = decodeMessage(encodedMessage, date, phrase, anagram);
    System.out.println("Decoded Message: " + decodedMessage);
  }

}
