package iafenvoy.hypextension.Functions;

import iafenvoy.hypextension.FastGameMenu.GameList;
import iafenvoy.hypextension.FastGameMenu.MiniGame;
import net.minecraft.client.MinecraftClient;

public class AutoGG {
  private static final MinecraftClient client = MinecraftClient.getInstance();
  private static final String lineString = "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬";
  private static String previousMessage = "";
  private static long prev = 0L;

  private static boolean checkInGamesDFS(String s, MiniGame game) {
    if (game.getModes().size() == 0)
      return s.toLowerCase().replace(" ", "").contains(game.getDisplayName().toLowerCase().replace(" ", ""));
    else
      for (MiniGame g : game.getModes())
        if (checkInGamesDFS(s, g))
          return true;
    return false;
  }

  private static boolean checkInGames(String message) {
    for (MiniGame game : GameList.INSTANCE.DATA)
      if (checkInGamesDFS(message, game))
        return true;
    return false;
  }

  public static void checkMessage(String message) {
    if (message.contains("The game starts in") || message.contains("游戏将在"))
      prev = System.currentTimeMillis();
    if (previousMessage.contains(lineString)) {
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
