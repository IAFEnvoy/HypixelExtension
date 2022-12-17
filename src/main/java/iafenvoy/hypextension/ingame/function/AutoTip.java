package iafenvoy.hypextension.ingame.function;

import iafenvoy.hypextension.config.Configs;
import iafenvoy.hypextension.utils.ClientUtil;

import java.util.Timer;
import java.util.TimerTask;

public class AutoTip {
    public static void start() {
        new Timer("Auto Tip").schedule(new TimerTask() {
            @Override
            public void run() {
                if (Configs.INSTANCE.autoTip.getBooleanValue())
                    try {
                        ClientUtil.sendMessage("/tip all", true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }, 0, 60000);
    }

}
