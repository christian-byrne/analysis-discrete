
/**
 * File: Prog4.java
 * Author: Christian Byrne
 * Course: CSc 345 — Analysis of Discrete Structures
 * Assignment: Program #4: They did the MASH...
 * Instructor: Dr. McCann
 * TAs: Rubin Yang, Lucas Almeida, Hamad Ayaz, Sohan Bhakta, CJ Chen, Hyungji Kim, Hamlet Taraz
 * Due Date: November 26, 2024
 * Desc: A Java program that computes a message digest using the MASH algorithm. The program
 *       takes two prime numbers and a message as input, and outputs the message digest in
 *       binary and decimal form. The MASH algorithm is a cryptographic hash function that uses
 *       modular arithmetic and bitwise operations to compute the digest.
 * Compilation: javac Prog4.java
 * Execution: java Prog4 <primenum1> <primenum2> <message>
 * Usage: Replace <primenum1> and <primenum2> with prime numbers, and <message> with a message.
 *       The message must be 2-6 characters long and contain only letters, digits, and punctuation.
 * Example: java Prog4 17 19 "Hello!"
 * Features Not Implemented: None
 * Java Version: 7
 */

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * The Prog4 class computes a message digest using the MASH algorithm.
 * 
 * Details:
 * - The program takes two prime numbers and a message as input.
 * - The message must be 2-6 characters long and contain only letters, digits,
 * and punctuation.
 * - The program outputs the message digest in binary and decimal form.
 * - The MASH algorithm uses modular arithmetic and bitwise operations to
 * compute the digest.
 * 
 * Constants:
 * - FILL_HB: The fill hex byte value (1111).
 * - PREPEND_HB: The prepend hex byte value (1010).
 * - ASCII_BITS: The number of bits all ASCII characters are encoded to (7).
 * - EXPONENT: The exponent value used in the MASH algorithm (257).
 *
 * @dependencies BigInteger
 * @version 1.0
 * @since 2024-11-26
 * @author Christian Byrne
 */
public class Prog4 {
  private static final String FILL_HB = "1111";
  private static final String PREPEND_HB = "1010";
  private static final int ASCII_BITS = 7;
  private static final int EXPONENT = 257;

  public static void main(String[] args) {
    validateArgs(args);
    String inputStringBinary = stringToBinaryString(args[2]);

    // Assert input length is 7*chars when converted to binary string
    if (inputStringBinary.length() != ASCII_BITS * args[2].length())
      throw new AssertionError(
          "Binary string is not 7n-bit string - Ensure message has ASCII-encodeable inputs.");

    // Step 1: Compute M = pq, and determine m, the number of bits in M
    BigInteger primeP = new BigInteger(args[0]); // p (first prime input)
    BigInteger primeQ = new BigInteger(args[1]); // q (second prime input)
    BigInteger productOfPrimes = primeP.multiply(primeQ); // M = pq
    int lengthPrimeProduct = productOfPrimes.bitLength(); // m = bits in M

    // Step 2: Determine n (digest size), largest multiple of 16 ≤ m.
    int digestLen = largestMultipleOf16LessThan(lengthPrimeProduct); // n = largest multiple of 16 ≤ m

    // Step 3: Set H0 = 0, set A to four 1–bits and n−4 0–bits
    String digestBinary = binaryZerosString(digestLen); // H_0 = 0
    String hashBase = FILL_HB + binaryZerosString(digestLen - 4); // A = 1111 + 0's

    // Step 4: Append 0–bits to right of x to nearest multiple of n/2
    int lengthBinaryInput = inputStringBinary.length(); // b = length of x
    int halfDigestLength = digestLen / 2; // n/2 = half the digest size
    int extendedLength = nearestMultiple(lengthBinaryInput, halfDigestLength); // x = nearest multiple of n/2
    int padCount = extendedLength - lengthBinaryInput; // number of zeros to pad
    inputStringBinary = padMessage(inputStringBinary, padCount); // x = x + 0's

    // Step 5 & 7: Divide x into n/2-bit blocks. Insert 1111 beetween nybbles
    ArrayList<String> blocks = binaryStrToBlocks(inputStringBinary, halfDigestLength); // T = blocks[]

    // Step 6: Create an n 2–bit block xt+1 that is the binary representation of b
    String lengthMsgBinString = "0".repeat(halfDigestLength - numBits(lengthBinaryInput))
        + Integer.toBinaryString(lengthBinaryInput); // L in binary

    // Step 8: Insert 1010 before each nybble in block yt+1
    blocks.add(insertStrBetweenNybbles(lengthMsgBinString, PREPEND_HB)); // t_i+1

    // Step 9: For each block y1 through y_t+1:
    for (String block : blocks) {
      String fValue = calculateF(block, hashBase, digestBinary, productOfPrimes); // (a) F_i=((H_i−1 ⊕ y_i) ∨ A)^257 % M
      String gValue = calculateG(fValue, digestLen); // (b) G_i=the rightmost n bits of F_i0
      digestBinary = calculateH(gValue, digestBinary, digestLen); // (c) H_i=G_i ⊕ H_i−1
    }

    String resultDecimal = new BigInteger(digestBinary, 2).toString(); // Convert digest to decimal
    assert digestBinary.length() == digestLen; // Assert final result is n-bit string

    // Output the final digest in binary and decimal
    System.out.println("Binary: " + digestBinary + "\nDecimal: " + resultDecimal);
  }

  /**
   * calculateF
   * Calculate the F value for the current block.
   *
   * F = ((H ⊕ block) ∨ A)^257 % M where ⊕ is the bitwise XOR operator, ∨ is the
   * bitwise OR operator, ^ is the exponentiation operator, and 257 is the
   * exponent in decimal.
   *
   * @param block         the current block
   * @param hashBase      the hash base value
   * @param currentDigest the current digest value
   * @param mValue        the modulus value
   * @return the F value
   */
  private static String calculateF(String block, String hashBase, String currentDigest, BigInteger mValue) {
    // Convert numbers to BigInteger for bit manipulation
    BigInteger blockInt = new BigInteger(block, 2);
    BigInteger hashInt = new BigInteger(hashBase, 2);
    BigInteger digestInt = new BigInteger(currentDigest, 2);

    // Calculate H ⊕ block
    BigInteger xorDigestBlock = digestInt.xor(blockInt);

    // Calculate H_XOR_block ∨ A
    BigInteger hashXorBlockOrResult = xorDigestBlock.or(hashInt);

    // Raise to 257 modulus M
    BigInteger fValue = hashXorBlockOrResult.modPow(BigInteger.valueOf(EXPONENT), mValue);

    return fValue.toString(2);
  }

  /**
   * calculateH
   * Calculate the H value for the current block.
   *
   * H = G ⊕ H where ⊕ is the bitwise XOR operator.
   *
   * @param gBlock        the G block value
   * @param currentDigest the current digest value
   * @param correctLength the correct length of the digest
   * @return the H value
   */
  private static String calculateH(String gBlock, String currentDigest, int correctLength) {
    int originalLength = currentDigest.length(); // Original length of the digest

    // Convert numbers to BigInteger for bit manipulation
    BigInteger gBlockInt = new BigInteger(gBlock, 2); // G_i
    BigInteger previousHBlockInt = new BigInteger(currentDigest, 2); // H_i-1

    // Calculate H ⊕ block (bitwise XOR)
    BigInteger hashXorBlock = previousHBlockInt.xor(gBlockInt);

    // Pad with zeros to correct length
    String newDigestBlock = hashXorBlock.toString(2); // Convert to binary string
    if (hashXorBlock.bitLength() < originalLength)
      newDigestBlock = "0".repeat(originalLength - hashXorBlock.bitLength()) + newDigestBlock;

    // Truncate to correct length if necessary
    if (newDigestBlock.length() != correctLength)
      newDigestBlock = newDigestBlock.substring(newDigestBlock.length() - correctLength);

    return newDigestBlock;
  }

  /**
   * calculateG
   * Calculate the G value for the current block.
   *
   * G = the rightmost n bits of F where n is the digest size.
   *
   * @param fValue     the F value
   * @param digestSize the size of the digest
   * @return the G value
   */
  private static String calculateG(String fValue, int digestSize) {
    if (fValue.length() < digestSize)
      return fValue; // the F value is less than the digest size, so return the whole value

    // Return the rightmost n bits of the F value
    return fValue.substring(fValue.length() - digestSize);
  }

  /**
   * insertStrBetweenNybbles
   * Insert a string between each nybble in a block.
   * 
   * A nybble is a 4-bit chunk. The inserted string goes before each nybble,
   * including the first one.
   *
   * @param block        the block to insert the string into
   * @param insertString the string to insert between each nybble
   * @return the block with the string inserted between each nybble
   */
  private static String insertStrBetweenNybbles(String block, String insertString) {
    StringBuilder newBlock = new StringBuilder();

    // Process each nibble (4 bits at a time)
    for (int i = 0; i < block.length(); i += 4) {
      // Extract the nibble
      String nibble = block.substring(i, Math.min(i + 4, block.length()));

      // Prepend the insertion string to the nibble
      newBlock.append(insertString).append(nibble);
    }
    return newBlock.toString();
  }

  /**
   * binaryStrToBlocks
   * Convert a binary string into blocks of a specified size.
   *
   * @param binaryStr the binary string to convert
   * @param blockSize the size of each block
   * @return a list of blocks
   */
  private static ArrayList<String> binaryStrToBlocks(String binaryStr, int blockSize) {
    int i = 0;
    ArrayList<String> blocks = new ArrayList<>(); // List of blocks
    while (i < binaryStr.length()) {
      // Extract a block of the specified size
      String block = binaryStr.substring(i, Math.min(i + blockSize, binaryStr.length()));

      // Insert FILL_HB between each nybble
      blocks.add(insertStrBetweenNybbles(block, FILL_HB));

      i += blockSize;
    }
    return blocks;
  }

  /**
   * padMessage
   * Pad the input string with zeros.
   *
   * @param inputString the input string
   * @param padCount    the number of zeros to pad
   * @return the padded string
   */
  private static String padMessage(String inputString, int padCount) {
    // Return the input string if the pad count is less than or equal to 0
    if (padCount <= 0)
      return inputString;

    // Pad the input string with zeros
    return inputString + "0".repeat(padCount);
  }

  /**
   * nearestMultiple
   * Find the nearest multiple of a base number.
   *
   * @param value        the value to find the nearest multiple for
   * @param multipleBase the base number
   * @return the nearest multiple
   */
  private static int nearestMultiple(int value, int multipleBase) {
    // Return the value if the multiple base is less than or equal to 0
    if (multipleBase <= 0 || value % multipleBase == 0)
      return value;

    // Find the nearest multiple of the base
    return (value + multipleBase - 1) / multipleBase * multipleBase;
  }

  /**
   * binaryZerosString
   * Create a string of zeros.
   *
   * @param zeroCount the number of zeros to create
   * @return the string of zeros
   */
  private static String binaryZerosString(int zeroCount) {
    // Return an empty string if the count is less than or equal to 0
    if (zeroCount <= 0)
      return "";

    return "0".repeat(zeroCount);
  }

  /**
   * largestMultipleOf16LessThan
   * Find the largest multiple of 16 less than a threshold.
   *
   * @param threshold the threshold value
   * @return the largest multiple of 16 less than the threshold
   */
  private static int largestMultipleOf16LessThan(int threshold) {
    // Special case when p and q are very small values -> extend to 16
    if (threshold <= 16)
      return 16;

    // Find the largest multiple of 16 less than the threshold
    return threshold - (threshold % 16);
  }

  /**
   * numBits
   * Find the number of bits in a number.
   *
   * @param number the number to find the bits for
   * @return the number of bits
   */
  private static int numBits(int number) {
    return BigInteger.valueOf(number).bitLength();
  }

  /**
   * stringToBinaryString
   * Convert a string to a binary string.
   *
   * @param inputString the input string
   * @return the binary string
   */
  private static String stringToBinaryString(String inputString) {
    StringBuilder binary = new StringBuilder(); // Binary string builder

    for (char ch : inputString.toCharArray()) {
      // Convert each character to a 7-bit binary string
      binary.append(String.format("%7s", Integer.toBinaryString(ch)).replace(' ', '0'));
    }
    return binary.toString();
  }

  /**
   * isPunctuationSymbol
   * Check if a character is a punctuation symbol. Includes all characters that
   * are not letters, digits, or whitespace.
   *
   * @param symbol the character to check
   * @return true if the character is a punctuation symbol, false otherwise
   */
  private static final boolean isPunctuationSymbol(char symbol) {
    return !Character.isLetterOrDigit(symbol)
        && !Character.isWhitespace(symbol)
        && !Character.isISOControl(symbol);
  }

  /**
   * validateArgs
   * Validate the command line arguments according to the requirements for the
   * input fields of the program.
   *
   * @param args the command line arguments
   */
  private static final void validateArgs(String[] args) {
    // Validate the number of arguments
    if (args.length != 3) {
      System.out.println("Usage: java Prog4 <primenum> <primenum> <message>");
      System.exit(1);
    }

    // Validate the prime numbers
    if (!isPrime(args[0]) || !isPrime(args[1])) {
      System.out.println("Error: Both numbers must be prime integers.");
      System.exit(1);
    }

    // Validate the message
    if (!isValidMessage(args[2])) {
      System.out.println(
          "Error: Message must be 2-6 chars containing only letters, digits, and punctuation symbols.");
      System.exit(1);
    }
  }

  /**
   * isValidMessage
   * Check if a message is valid.
   * 
   * A message is valid if it is 2-6 characters long and contains only letters,
   * digits, and punctuation symbols.
   *
   * @param message the message to check
   * @return true if the message is valid, false otherwise
   */
  private static final boolean isValidMessage(String message) {
    // Check if the message is null, empty, or not within the length range
    if (message == null || message.isEmpty() || message.length() > 6 || message.length() < 2)
      return false;

    for (char ch : message.toCharArray()) {
      // Check if the character is a control character, letter, digit, or punctuation
      if (Character.isISOControl(ch) || (!Character.isLetterOrDigit(ch) && !isPunctuationSymbol(ch)))
        return false;
    }

    return true;
  }

  /**
   * isPrime
   * Check if a string is a prime number.
   *
   * @param number the string to check
   * @return true if the string is a prime number, false otherwise
   */
  private static boolean isPrime(String number) {
    if (number == null || number.isEmpty())
      return false;

    try {
      BigInteger n = new BigInteger(number);
      // Check if the number is less than 2 or not a probable prime
      if (n.compareTo(BigInteger.valueOf(2)) < 0 || !n.isProbablePrime(10))
        return false;
    } catch (NumberFormatException | ArithmeticException e) {
      return false;
    }

    return true;
  }

}
