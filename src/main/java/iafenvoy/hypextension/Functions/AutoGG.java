package iafenvoy.hypextension.Functions;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;

public class AutoGG {
  private static final MinecraftClient client = MinecraftClient.getInstance();
  private static final String lineString1 = "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬";
  private static final String lineString2 = "????????????????????????????????????????????????????????????????";
  private static final List<String> games = new ArrayList<>();
  private static String previousMessage = "";
  private static long prev = 0L;

  public static void Init() {
    games.add("MURDER MYSTERY");
    games.add("密室杀手");
    games.add("BEDWAR");
    games.add("起床战争");
  }

  private static boolean checkInGames(String message) {
    for (String game : games)
      if (message.contains(game))
        return true;
    return false;
  }

  public static void checkMessage(String message) {
    if (message.contains("The game starts in") || message.contains("游戏将在"))
      prev = System.currentTimeMillis();
    if (previousMessage.contains(lineString1) || previousMessage.contains(lineString2)) {
      if (prev != 0L) {
        long now = System.currentTimeMillis();
        if (now - prev <= 1500L) {
          prev = 0L;
          previousMessage = message;
          return;
        }
        prev = 0L;
      }
      if (checkInGames(message))
        client.player.sendChatMessage("gg");
    }
    previousMessage = message;
  }
}
