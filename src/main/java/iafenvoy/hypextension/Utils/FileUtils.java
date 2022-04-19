package iafenvoy.hypextension.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {
  public static String readByLines(InputStreamReader stream) throws IOException {
    BufferedReader in = new BufferedReader(stream);
    StringBuffer buffer = new StringBuffer();
    String line = "";
    while ((line = in.readLine()) != null)
      buffer.append(line).append("\n");
    String input = buffer.toString();
    return input;
  }
}
