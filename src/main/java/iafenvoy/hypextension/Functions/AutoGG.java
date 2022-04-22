package iafenvoy.hypextension.Functions;

import iafenvoy.hypextension.FastGameMenu.MiniGame;
import net.minecraft.client.MinecraftClient;

public class AutoGG {
  private static final MinecraftClient client = MinecraftClient.getInstance();
  private static final String lineString = "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬";
  private static String previousMessage = "";
  private static long prev = 0L;

  public static void checkMessage(String message) {
    if (message.contains("The game starts in"))
      prev = System.currentTimeMillis();
    if (previousMessage.contains(lineString)) {
      System.out.println("[Hypixel Extension] Get Line String !");
      if (prev != 0L) {
        long now = System.currentTimeMillis();
        if (now - prev <= 1500L) {
          prev = 0L;
          previousMessage = message;
          return;
        }
        prev = 0L;
      }
      if (MiniGame.containGameName(message))
        client.player.sendChatMessage("gg");
    }
    previousMessage = message;
  }
  /*
   * private static final List<String> endingStrings = Arrays.asList(
   * "1st Killer -",
   * "1st Place -",
   * "Winner",
   * "- Damage Dealt -",
   * "Winning Team -",
   * "1st -",
   * "Winning Team:",
   * "won the game!",
   * "Top Seeker:",
   * "1st Place:",
   * "Last team standing!",
   * "Winner #1",
   * "Top Survivors",
   * "Winners",
   * "Most Kills");
   */
}
