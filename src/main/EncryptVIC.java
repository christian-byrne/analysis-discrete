/*
 * Author: Christian Byrne
 * Course: CSc 345 — Analysis of Discrete Structures
 * Assignment: Program #1: The VIC (VIC InComplete) Cipher
 * Instructor: McCann
 * TAs: Rubin Yang, Lucas Almeida, Hamad Ayaz, Sohan Bhakta, CJ Chen, Hyungji Kim, Hamlet Taraz
 * Due Date: September 19th, 2024
 *
 * This program performs encryption using the VIC cipher. It reads data from 
 * a file, processes the input message and encryption parameters, and outputs 
 * the encrypted message. The encryption involves a series of transformations 
 * on the data using no-carry addition, digit permutation, and a straddling 
 * checkerboard encoding.
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
 * Class: EncryptVIC
 * Author: Christian Byrne
 * Dependencies: VICData (handles reading the VIC cipher data)
 * Purpose: This class performs encryption using the VIC cipher. It orchestrates 
 * the process of reading the data, formatting the message, and applying the 
 * encryption transformations.
 */
public class EncryptVIC {

  public static void main(String[] args) {
    /*
     * Method: main
     * Purpose: This method starts the program, ensures that a file name is provided
     * as an argument, reads data from the file, processes it, and displays the
     * final
     * encrypted message.
     * Pre-condition: The program should be invoked with at least one command-line
     * argument specifying the file name.
     * Post-condition: If a valid file is provided, an encrypted message is
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
    VICData vicData = VICData.readVICData(args[0], true);

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

  /*
   * Method: encryptMessage
   * Purpose: This method handles the complete process of encrypting the message
   * using the VIC cipher. It performs various steps including no-carry addition,
   * digit permutation, and message encoding.
   * Pre-condition: vicData contains valid input data for encryption.
   * Post-condition: A fully encrypted message is returned.
   * Parameters:
   * - vicData: A VICData object containing the input values such as agentID and
   * date.
   * - phrase: The uppercase phrase used for digit permutation.
   * - anagram: The uppercase anagram used for the straddling checkerboard.
   * - message: The formatted message that is to be encrypted.
   * Returns: A string representing the final encrypted message.
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

  /*
   * Method: addIdToDate
   * Purpose: This method adds the agent's ID to the first five digits of the date
   * using no-carry addition.
   * Pre-condition: The id and date strings must be at least 5 characters long.
   * Post-condition: Returns a string resulting from the no-carry addition of the
   * id
   * and the first five digits of the date.
   * Parameters:
   * - id: A string representing the agent's ID.
   * - date: A string representing the date (at least 5 digits).
   * Returns: A string representing the result of the no-carry addition, or null
   * if
   * id or date is too short.
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
   * Purpose: This method truncates the input date to the first five digits if it
   * exceeds 5 digits.
   * Pre-condition: The input string must represent a valid date with at least 5
   * digits.
   * Post-condition: Returns a string representing the first 5 digits of the date.
   * Parameters:
   * - date: A string representing the input date.
   * Returns: A string with the first five digits of the input date.
   */
  private static String truncateToFiveDigits(String date) {
    return date.length() > 5 ? date.substring(0, 5) : date;
  }

  /*
   * Method: encodeMessage
   * Purpose: Encodes the message using the straddling checkerboard technique.
   * Each letter in the message is replaced by its corresponding code from
   * the checkerboard.
   * Pre-condition: The message contains only letters and non-letter characters,
   * and the checkerboard has mappings for each letter.
   * Post-condition: Returns the encoded message.
   * Parameters:
   * - message: A string representing the message to encode.
   * - checkerboard: An ArrayList of strings representing the straddling
   * checkerboard
   * used for encoding.
   * Returns: A string representing the encoded message.
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

  /*
   * Method: insertIdIntoMessage
   * Purpose: This method inserts the agent's ID into the encoded message at a
   * position determined by the last digit of the date.
   * Pre-condition: The encoded message, ID, and date are valid strings.
   * Post-condition: Returns the encoded message with the ID inserted at the
   * correct
   * position, or appended at the end if the position exceeds the message length.
   * Parameters:
   * - encodedMessage: The encoded message string.
   * - id: The agent's ID string.
   * - date: The date string used to determine the insertion position.
   * Returns: The final message with the ID inserted.
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

  /*
   * Method: formatMessage
   * Purpose: This method formats the input message by removing all non-letter
   * characters and converting the remaining letters to uppercase.
   * Pre-condition: The input message is a valid string containing letters and
   * non-letter characters.
   * Post-condition: Returns a string with only uppercase letters.
   * Parameters:
   * - message: A string representing the input message.
   * Returns: A string containing only uppercase letters from the input message.
   */
  private static String formatMessage(String message) {
    return message.replaceAll("[^a-zA-Z]", "").toUpperCase();
  }
}
