
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncryptTest {

  private final EncryptVIC encrypt = new EncryptVIC();
  private final VICOperations vic = new VICOperations();

  @Test
  public void testMainOutput1() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out; // Save the original System.out
    System.setOut(new PrintStream(outputStream));

    try {
      // Call the main method
      // MyApp.main(new String[]{});
      EncryptVIC.main(new String[] { "src/test/data/test_01.txt" });

      // Check the captured output
      String expectedOutput = "3532423085721239";

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
      EncryptVIC.main(new String[] { "src/test/data/test_02.txt" });

      // Check the captured output
      String expectedOutput = "67835384730038700";

      assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    } finally {
      // Restore the original System.out
      System.setOut(originalOut);
    }

  }

  @Test
  public void testAddIdToDate() {
    assertEquals("22712", encrypt.addIdToDate("85721", "47091"));
    assertEquals("18053", encrypt.addIdToDate("73003", "45050"));
  }

  @Test
  public void testEncodeMessage() {
    ArrayList<String> checkerboard = vic.straddlingCheckerboard("1528069374", "HEAT IS ON");
    assertEquals("35324230239", encrypt.encodeMessage("RUNAWAY", checkerboard));

    ArrayList<String> checkerboard2 = vic.straddlingCheckerboard("1354670298", "a rose tin");
    assertEquals("678353848700", encrypt.encodeMessage("SENDMONEY", checkerboard2));
  }

  @Test
  public void testInsertIdIntoMessage() {
    assertEquals("3532423085721239", encrypt.insertIdIntoMessage("35324230239", "85721", "470918"));
    assertEquals("67835384730038700", encrypt.insertIdIntoMessage("678353848700", "73003", "450508"));
  }

}
