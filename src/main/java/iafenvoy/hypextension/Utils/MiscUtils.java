package iafenvoy.hypextension.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import iafenvoy.hypextension.Config.Configs;

public class MiscUtils {
  private static final Date DATE = new Date();

  public static String getChatTimestamp() {
    SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss]");
    DATE.setTime(System.currentTimeMillis());
    return sdf.format(DATE);
  }

  public static int getChatBackgroundColor(int colorOrig) {
    int newColor = Configs.INSTANCE.chatBackgroundColor.getIntegerValue();
    return (newColor & 0x00FFFFFF)
        | ((int) (((newColor >>> 24) / 255.0) * ((colorOrig >>> 24) / 255.0) / 0.5 * 255) << 24);
  }
}
