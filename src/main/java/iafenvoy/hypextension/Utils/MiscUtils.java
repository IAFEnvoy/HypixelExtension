package iafenvoy.hypextension.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MiscUtils {
  private static final Date DATE = new Date();

  public static String getChatTimestamp() {
    SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss]");
    DATE.setTime(System.currentTimeMillis());
    return sdf.format(DATE);
  }
}
