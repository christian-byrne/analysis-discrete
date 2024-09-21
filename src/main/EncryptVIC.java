package src.main;

import java.util.ArrayList;

public class EncryptVIC {

  public static void main(String[] args) {
    // Ensure a file name is provided as the first argument
    if (args.length < 1) {
      System.out.println("Please provide a file name as the first argument.");
      return;
    }

    // Read and process VIC data from the file
    VICData vicData = VICData.readVICData(args[0]);

    // Prepare and format input data
    String phrase = vicData.phrase.toUpperCase();
    String anagram = vicData.anagram.toUpperCase();
    String message = formatMessage(vicData.message);

    // Perform encryption steps
    String encryptedMessage = encryptMessage(vicData, phrase, anagram, message);

    // Display the final encrypted message
    System.out.println(encryptedMessage);
  }

  private static final VICOperations vicOperations = new VICOperations();

  /**
   * Centralized method to handle the encryption steps and produce the final
   * encrypted message.
   *
   * @param vicData the data object containing input values
   * @param phrase  the uppercase phrase used in digit permutation
   * @param anagram the uppercase anagram used in the checkerboard
   * @param message the formatted message to be encrypted
   * @return the final encrypted message
   */
  private static String encryptMessage(VICData vicData, String phrase, String anagram, String message) {
    String result1 = addIdToDate(vicData.agentID, vicData.date);
    String result2 = vicOperations.chainAddition(result1, 10);
    String result3 = vicOperations.digitPermutation(phrase);
    String result4 = vicOperations.noCarryAddition(result2, result3);
    String result5 = vicOperations.digitPermutation(result4);
    ArrayList<String> result6 = vicOperations.straddlingCheckerboard(result5, anagram);
    String encodedMessage = encodeMessage(message, result6);
    return insertIdIntoMessage(encodedMessage, vicData.agentID, vicData.date);
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

  /**
   * Encodes the given message using the straddling checkerboard.
   * 
   * @param message      the message to encode
   * @param checkerboard the straddling checkerboard
   * @return the encoded message
   */
  public static String encodeMessage(String message, ArrayList<String> checkerboard) {
    StringBuilder encodedMessage = new StringBuilder();

    for (char c : message.toCharArray()) {
      if (Character.isLetter(c)) {
        // Get the index of the letter in the alphabet and append corresponding number
        int index = c - 'A';
        encodedMessage.append(checkerboard.get(index));
      } else {
        // Append non-letter characters as is
        encodedMessage.append(c);
      }
    }
    return encodedMessage.toString();
  }

  /**
   * Inserts the ID into the encoded message based on the last digit of the date.
   * 
   * @param encodedMessage the encoded message from step 7
   * @param id             the ID string
   * @param date           the date string (for determining insertion point)
   * @return the final message with the ID inserted
   */
  public static String insertIdIntoMessage(String encodedMessage, String id, String date) {
    int position = Character.getNumericValue(date.charAt(date.length() - 1));

    // If the position is greater than the message length, append the ID at the end
    if (position > encodedMessage.length()) {
      return encodedMessage + id;
    }

    // Insert the ID at the determined position
    return encodedMessage.substring(0, position) + id + encodedMessage.substring(position);
  }

  /**
   * Formats the input message by removing non-letter characters and converting to
   * uppercase.
   * 
   * @param message the input message
   * @return a formatted message with only uppercase letters
   */
  private static String formatMessage(String message) {
    return message.replaceAll("[^a-zA-Z]", "").toUpperCase();
  }
}
