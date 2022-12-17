package iafenvoy.hypextension.ingame.minigame;

import iafenvoy.hypextension.utils.ClientUtil;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.List;

public class MiniGame {
    public static final List<TranslatableText> gameName = new ArrayList<>();
    private final String translateKey;// name in json file
    private final String commamd;// key in json file
    private final List<MiniGame> modes;// mode list in json file

    public MiniGame(String translateKey, String commamd, List<MiniGame> modes) {
        this.translateKey = translateKey;
        this.commamd = commamd.contains("/") ? commamd : "/play " + commamd.toLowerCase();
        this.modes = modes;
        if (!translateKey.equals("Lobby"))
            gameName.add(new TranslatableText(translateKey));
    }

    public static boolean containGameName(String s) {
        s = s.replace(" ", "").toLowerCase();
        for (TranslatableText t : gameName)
            if (s.contains(t.getString().replace(" ", "").toLowerCase()))
                return true;
        return false;
    }

    public String getDisplayName() {
        return new TranslatableText(this.translateKey).getString() + (modes.size() == 0 ? "" : " >");
    }

    public List<MiniGame> getModes() {
        return this.modes;
    }

    public void sendCommand() {
        ClientUtil.sendMessage(this.commamd, true);
    }
}
