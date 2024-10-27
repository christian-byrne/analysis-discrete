import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class LoadLookupTable {
  private static final int TOTAL_ENTRIES = 8192;

  public static int[][] toArray(String fileName) {
    int[][] lookupTable = new int[TOTAL_ENTRIES][3];

    try (DataInputStream dis = new DataInputStream(new FileInputStream(fileName))) {
      // Populate the array directly
      for (int i = 0; i < TOTAL_ENTRIES; i++) {
        lookupTable[i][0] = dis.readInt();
        lookupTable[i][1] = dis.readInt();
        lookupTable[i][2] = dis.readInt();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return lookupTable;
  }
}
