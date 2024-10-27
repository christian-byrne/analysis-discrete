import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class FileLoader {
  private static final int TOTAL_ENTRIES = 65536;

  public static int[][] loadLookupTable(String fileName) {
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

  public static Optional<int[]> parseDataFile(String filename) {
    try (Stream<String> lines = Files.lines(Paths.get(filename))) {
      return Optional.of(lines
          .mapToInt(Integer::parseInt)
          .toArray());
    } catch (IOException e) {
      System.out.println("Error reading file: " + e.getMessage());
      return Optional.empty();
    } catch (NumberFormatException e) {
      System.out.println("File contains invalid number format." + e.getMessage());
      return Optional.empty();
    }
  }
}
