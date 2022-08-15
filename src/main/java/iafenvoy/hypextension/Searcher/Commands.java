package iafenvoy.hypextension.Searcher;

import com.mojang.brigadier.arguments.StringArgumentType;
import iafenvoy.hypextension.GUI.SearcherGUI;
import iafenvoy.hypextension.Searcher.Error.ApiError;
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
                            new Thread(() -> {
                                context.getSource().sendFeedback(new LiteralText("Downloading data, please wait..."));
                                String playername = StringArgumentType.getString(context, "playername");
                                try {
                                    HypixelInfo info = NetworkUtil.getInfo(NetworkUtil.getUuid(playername));
                                    MinecraftClient.getInstance().openScreen(new SearcherGUI(info));
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
