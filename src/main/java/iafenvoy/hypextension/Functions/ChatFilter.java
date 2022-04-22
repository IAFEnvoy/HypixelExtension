package iafenvoy.hypextension.Functions;

import java.util.regex.Pattern;

public class ChatFilter {
  private static final Pattern noemal, team, guild, party, shout, spectator;
  static {
    noemal = Pattern.compile("(?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    team = Pattern.compile("\\.get(TEAM) (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    guild = Pattern.compile("Guild > (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    party = Pattern.compile("Party > (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    shout = Pattern.compile("\\.get(SHOUT) (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    spectator = Pattern.compile("\\.get(SPECTATOR) (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
  }

  public static void checkMessage(String message) {

  }
}
