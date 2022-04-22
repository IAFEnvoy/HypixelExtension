package iafenvoy.hypextension.Functions;

import java.util.regex.Pattern;

import iafenvoy.hypextension.Config.Configs;

public class ChatFilter {
  private static final Pattern normal, team, guild, party, shout, spectator;
  static {
    normal = Pattern.compile("(?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    team = Pattern.compile("\\.get(TEAM) (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    guild = Pattern.compile("Guild > (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    party = Pattern.compile("Party > (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    shout = Pattern.compile("\\.get(SHOUT) (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    spectator = Pattern.compile("\\.get(SPECTATOR) (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
  }

  public static boolean checkMessage(String message) {
    if (Configs.blockNormal.getBooleanValue())
      if (normal.matcher(message).matches())
        return false;
    if (Configs.blockTeam.getBooleanValue())
      if (team.matcher(message).matches())
        return false;
    if (Configs.blockGuild.getBooleanValue())
      if (guild.matcher(message).matches())
        return false;
    if (Configs.blockParty.getBooleanValue())
      if (party.matcher(message).matches())
        return false;
    if (Configs.blockShout.getBooleanValue())
      if (shout.matcher(message).matches())
        return false;
    if (Configs.blockSpectator.getBooleanValue())
      if (spectator.matcher(message).matches())
        return false;
    return true;
  }
}
