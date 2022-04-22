package iafenvoy.hypextension.FastGameMenu;

import java.util.ArrayList;
import java.util.List;

import iafenvoy.hypextension.Utils.ClientUtil;
import net.minecraft.text.TranslatableText;

public class MiniGame {
  public static final List<TranslatableText> gameName = new ArrayList<>();
  private final String translateKey;// name in json file
  private final String commamd;// key in json file
  private final List<MiniGame> modes;// mode list in json file

  public MiniGame(String translateKey, String commamd, List<MiniGame> modes) {
    this.translateKey = translateKey;
    this.commamd = commamd.contains("/") ? commamd : "/play " + commamd.toLowerCase();
    this.modes = modes;
    gameName.add(new TranslatableText(translateKey));
  }

  public String getTranslateKey() {
    return this.translateKey;
  }

  public String getDisplayName() {
    return new TranslatableText(this.translateKey).getString();
  }

  public String getCommamd() {
    return this.commamd;
  }

  public List<MiniGame> getModes() {
    return this.modes;
  }

  public String toString() {
    String ret = "{" + this.translateKey + "," + this.commamd + ",[";
    for (MiniGame m : this.modes)
      ret += m.toString() + ",";
    ret += "]}";
    return ret;
  }

  public void sendCommand() {
    ClientUtil.sendMessage(this.commamd, true);
  }

  public static boolean containGameName(String s) {
    s = s.replace(" ", "").toLowerCase();
    for (TranslatableText t : gameName)
      if (s.contains(t.getString().replace(" ", "").toLowerCase()))
        return true;
    return false;
  }
}
