/*
 * Author: Christian Byrne
 * Course: CSc 345 — Analysis of Discrete Structures
 * Assignment: Program #1: The VIC (VIC InComplete) Cipher
 * Instructor: McCann
 * TAs: Rubin Yang, Lucas Almeida, Hamad Ayaz, Sohan Bhakta, CJ Chen, Hyungji Kim, Hamlet Taraz
 * Due Date: September 19th, 2024
 *
 * This program decodes messages that were encrypted using the VIC cipher. It reads 
 * data from a file, processes the encoded message and decryption parameters, and 
 * outputs the decoded message. The decryption involves reversing transformations 
 * such as no-carry addition, digit permutation, and straddling checkerboard encoding.
 *
 * Language/Version: Java 6
 * Compilation: No special compilation details required.
 * Input: The program reads a file specified as the first command-line argument 
 * that contains the VIC cipher input data.
 *
 * Known Bugs: None.
 * Features Not Implemented: Handling of different date formats.
 */

import java.util.ArrayList;

/*
 * Class: DecryptVIC
 * Author: Christian Byrne
 * Dependencies: VICData (handles reading the VIC cipher data)
 * Purpose: This class decodes messages using the VIC cipher. It orchestrates 
 * the process of reading the data, extracting the agent ID, recreating the 
 * checkerboard, and decoding the message.
 */
public class DecryptVIC {

  private static final VICOperations vicOperations = new VICOperations();

  /*
   * Method: decodeMessage
   * Purpose: This method decodes a message that was encoded using the VIC cipher.
   * It involves recreating the same transformations used during encryption,
   * and decoding the message using the straddling checkerboard.
   * Pre-condition: encodedMessage is a valid encrypted string, date is in the
   * format YYMMDD, phrase contains at least 10 letters, and anagram is a valid
   * 10-character string.
   * Post-condition: Returns the fully decoded message.
   * Parameters:
   * - encodedMessage: The message to decode (all digits).
   * - date: The date string in the format YYMMDD.
   * - phrase: The phrase from which the first 10 letters are used.
   * - anagram: The 10-character anagram used for creating the checkerboard.
   * Returns: The decoded message.
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

  /*
   * Method: extractAgentID
   * Purpose: This method extracts the agent ID from the encoded message based
   * on the last digit of the date.
   * Pre-condition: encodedMessage is a valid string, and date is in the format
   * YYMMDD.
   * Post-condition: Returns the extracted agent ID.
   * Parameters:
   * - encodedMessage: The encoded message string.
   * - date: The date string in the format YYMMDD, used to determine the position.
   * Returns: A string representing the agent ID.
   */
  private static String extractAgentID(String encodedMessage, String date) {
    int position = Character.getNumericValue(date.charAt(date.length() - 1));
    return encodedMessage.substring(position, position + 5); // Extract the 5-digit agent ID
  }

  /*
   * Method: removeAgentID
   * Purpose: This method removes the agent ID from the encoded message.
   * Pre-condition: encodedMessage is a valid string, and date is in the format
   * YYMMDD.
   * Post-condition: Returns the encoded message with the agent ID removed.
   * Parameters:
   * - encodedMessage: The encoded message string.
   * - date: The date string in the format YYMMDD, used to determine the position.
   * Returns: A string representing the encoded message without the agent ID.
   */
  private static String removeAgentID(String encodedMessage, String date) {
    int position = Character.getNumericValue(date.charAt(date.length() - 1));
    return encodedMessage.substring(0, position) + encodedMessage.substring(position + 5);
  }

  /*
   * Method: decodeUsingCheckerboard
   * Purpose: This method decodes the encoded message using the straddling
   * checkerboard.
   * Pre-condition: encodedMessage contains only digits, and checkerboard is
   * a valid mapping from digits to letters.
   * Post-condition: Returns the fully decoded message.
   * Parameters:
   * - encodedMessage: A string representing the encoded message.
   * - checkerboard: An ArrayList representing the straddling checkerboard
   * for digit-to-letter mapping.
   * Returns: A string representing the decoded message.
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

  /*
   * Method: addIdToDate
   * Purpose: This method adds the agent's ID to the first five digits of the
   * date using no-carry addition.
   * Pre-condition: id and date must both be at least 5 characters long.
   * Post-condition: Returns the result of the no-carry addition.
   * Parameters:
   * - id: A string representing the agent's ID.
   * - date: A string representing the first five digits of the date.
   * Returns: A string representing the result of the no-carry addition.
   */
  public static String addIdToDate(String id, String date) {
    if (id.length() < 5 || date.length() < 5) {
      return null; // Ensure both ID and date are at least 5 characters long
    }

    date = truncateToFiveDigits(date); // Ensure date is exactly 5 digits
    return vicOperations.noCarryAddition(id, date);
  }

  /*
   * Method: truncateToFiveDigits
   * Purpose: This method truncates the input date to the first five digits if
   * it exceeds 5 digits.
   * Pre-condition: date is a valid string representing the date.
   * Post-condition: Returns a string with exactly 5 digits.
   * Parameters:
   * - date: A string representing the input date.
   * Returns: A string with the first five digits of the input date.
   */
  private static String truncateToFiveDigits(String date) {
    return date.length() > 5 ? date.substring(0, 5) : date;
  }

  public static void main(String[] args) {
    /*
     * Method: main
     * Purpose: This method starts the program, ensures that a file name is
     * provided as an argument, reads data from the file, processes it, and
     * displays the final decrypted message.
     * Pre-condition: The program should be invoked with at least one command-line
     * argument specifying the file name.
     * Post-condition: If a valid file is provided, a decrypted message is
     * displayed.
     * Parameters:
     * - args: Array of command-line arguments. The first element should be the
     * name of the file.
     */
    // Ensure a file name is provided as the first argument
    if (args.length < 1) {
      System.out.println("Please provide a file name as the first argument.");
      return;
    }

    // Read and process VIC data from the file
    VICData vicData = VICData.readVICData(args[0], false);

    // Prepare and format input data
    String phrase = vicData.phrase.toUpperCase();
    String anagram = vicData.anagram.toUpperCase();

    // Perform decryption steps
    String decryptedMessage = decodeMessage(vicData.message, vicData.date, phrase, anagram);

    // Display the final decrypted message
    System.out.println(decryptedMessage);
  }
}
