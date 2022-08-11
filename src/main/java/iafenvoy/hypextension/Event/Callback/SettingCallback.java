package iafenvoy.hypextension.Event.Callback;

import net.minecraft.client.MinecraftClient;

public class SettingCallback implements Callback {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void onValueChanged(boolean value) {
        new Thread(() -> {
            while (client == null)
                ;
            while (client.options == null)
                ;
            client.options.gamma = value ? 16.0F : 1.0F;
        }).start();
    }
}
