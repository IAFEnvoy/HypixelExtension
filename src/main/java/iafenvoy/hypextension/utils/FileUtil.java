package iafenvoy.hypextension.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {
    public static String readByLines(InputStreamReader stream) throws IOException {
        BufferedReader in = new BufferedReader(stream);
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null)
            buffer.append(line).append("\n");
        return buffer.toString();
    }
}
