package iafenvoy.hypextension.searcher;

import com.mojang.brigadier.arguments.StringArgumentType;
import fi.dy.masa.malilib.gui.GuiBase;
import iafenvoy.hypextension.config.Configs;
import iafenvoy.hypextension.gui.SearcherGUI;
import iafenvoy.hypextension.searcher.error.ApiError;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

public class Commands {
    public static void register() {
        ClientCommandManager.DISPATCHER.register(literal("hypixel")
                .then(argument("playername", StringArgumentType.word())
                        .executes(context -> {
                            if (Configs.INSTANCE.apiKey.getStringValue().equals(""))
                                context.getSource().sendFeedback(new LiteralText("You need to input api key in setting page!"));
                            else
                                new Thread(() -> {
                                    context.getSource().sendFeedback(new LiteralText("Downloading data, please wait..."));
                                    String playername = StringArgumentType.getString(context, "playername");
                                    try {
                                        HypixelInfo info = NetworkUtil.getInfo(NetworkUtil.getUuid(playername));
                                        GuiBase gui = new SearcherGUI(info);
                                        gui.init();
                                        MinecraftClient.getInstance().openScreen(gui);
                                    } catch (ApiError e) {
                                        context.getSource().sendError(new LiteralText("An error occurred while loading data!"));
                                        context.getSource().sendError(new LiteralText("Request URL: " + e.getUrl()));
                                        context.getSource().sendError(new LiteralText(e.toString()));
                                        e.printStackTrace();
                                    }
                                }).start();
                            return 0;
                        })));
    }
}
