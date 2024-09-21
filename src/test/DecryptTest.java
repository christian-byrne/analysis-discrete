package src.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import src.main.DecryptVIC;
import src.main.VICOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecryptTest {

  private final DecryptVIC decrypt = new DecryptVIC();
  private final VICOperations vic = new VICOperations();

  @Test
  public void testMainOutput1() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out; // Save the original System.out
    System.setOut(new PrintStream(outputStream));

    try {
      // Call the main method
      // MyApp.main(new String[]{});
      // EncryptVIC.main(new String[] { "src/test/data/test_01.txt" });
      DecryptVIC.main(new String[] { "src/test/data/test_03.txt" });

      // Check the captured output
      String expectedOutput = "RUNAWAY";

      assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    } finally {
      // Restore the original System.out
      System.setOut(originalOut);
    }

  }

  @Test
  public void testMainOutput2() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out; // Save the original System.out
    System.setOut(new PrintStream(outputStream));

    try {
      // Call the main method
      // MyApp.main(new String[]{});
      DecryptVIC.main(new String[] { "src/test/data/test_04.txt" });

      // Check the captured output
      String expectedOutput = "SENDMONEY";

      assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    } finally {
      // Restore the original System.out
      System.setOut(originalOut);
    }

  }

}
