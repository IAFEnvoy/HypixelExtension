package iafenvoy.hypextension.Searcher;

import iafenvoy.hypextension.Searcher.Data.DataBase;
import iafenvoy.hypextension.Searcher.Data.GuildData;
import iafenvoy.hypextension.Searcher.Data.PlayerData;
import iafenvoy.hypextension.Searcher.Data.StatusData;
import iafenvoy.hypextension.Searcher.Error.JsonKeyNotFoundException;
import iafenvoy.hypextension.Searcher.Parser.*;
import iafenvoy.hypextension.Utils.ColorUtil;
import iafenvoy.hypextension.Utils.MathUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HypixelDataParser {
    public static List<String> getRoot(PlayerData data) {
        InfoBuilder builder = new InfoBuilder(data);
        builder.addDouble("Level", "networkExp", 2, new NetworkLevelParser()).addEnter();
        builder.addInt("Karma", "karma").addEnter();
        builder.addInt("Achievement Point", "achievementPoints").addEnter();
        builder.addInt("Complete Quest", "achievements.general_quest_master").addEnter();
        builder.addInt("Complete Challenge", "achievements.general_challenger").addEnter();
        builder.addString("Language", "userLanguage", new TextParser()).addEnter();
        builder.addTime("First Login", "firstLogin").addEnter();
        builder.addTime("Last Login", "lastLogin").addEnter();
        builder.addTime("Last Logout", "lastLogout").addEnter();
        return builder.toStringList();
    }

    public static List<String> getGuild(GuildData data, String playerUuid) {
        if (data.isEmpty())
            return Collections.singletonList("Guild : No Guild");
        InfoBuilder builder = new InfoBuilder(data);
        builder.addString("Guild", "name").addEnter();
        builder.addDouble("Level", "exp", 2, new GuildLevelParser()).addEnter();
        builder.addInt("Members", "members.length").addEnter();
        try {
            int playerId = -1, rankId = -1;
            int totalMember = data.getValue("members.length").getAsInt();
            int totalRank = data.getValue("ranks.length").getAsInt();
            for (int i = 0; i < totalMember; i++)
                if (data.getValue("members." + i + ".uuid").getAsString().equals(playerUuid)) {
                    playerId = i;
                    break;
                }
            if (playerId == -1) return builder.toStringList();
            for (int i = 0; i < totalRank; i++)
                if (data.getValue("ranks." + i + ".name").getAsString().equals(data.getValue("members." + playerId + ".rank").getAsString())) {
                    rankId = i;
                    break;
                }
            builder.addTime("Join Time", "members." + playerId + ".joined").addEnter();
            builder.addStr("Rank : ");
            if (rankId == -1) {
                builder.addStr("Guild Master(");
                builder.addStr(ColorUtil.getColorByName(data.getValue("tagColor").getAsString()));
                builder.addStr("[GM]§f)");
            } else {
                builder.addStr(data.getValue("ranks." + rankId + ".name").getAsString());
                builder.addStr("(");
                builder.addStr(ColorUtil.getColorByName(data.getValue("tagColor").getAsString()));
                builder.addStr("[");
                builder.addStr(data.getValue("ranks." + rankId + ".tag").getAsString());
                builder.addStr("]§f)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toStringList();
    }

    public static List<String> getStatus(StatusData data) {
        if (!data.getValue("online").getAsBoolean())
            return Collections.singletonList("Status : Offline");
        InfoBuilder builder = new InfoBuilder(data);
        builder.addStr("Status : Online").addEnter();
        builder.addString("Game Type", "gameType", new TextParser()).addEnter();
        builder.addString("Mode", "mode", new TextParser()).addEnter();
        builder.addString("Map", "map").addEnter();
        return builder.toStringList();
    }

    public static List<String> getBedwar(PlayerData data) {
        InfoBuilder builder = new InfoBuilder(data);
        builder.addString("Level", "achievements.bedwars_level").addSep().addInt("Coins", "stats.Bedwars.coins").addEnter();
        builder.addInt("Winstreak", "stats.Bedwars.winstreak").addEnter();
        builder.addInt("Bed Destroy", "stats.Bedwars.beds_broken_bedwars").addSep().addInt("Bed Lost", "stats.Bedwars.beds_lost_bedwars").addEnter();
        builder.addRatio("Win", "Loss", "W/L", "stats.Bedwars.wins_bedwars", "stats.Bedwars.losses_bedwars", 2).addEnter();
        builder.addRatio("Kill", "Death", "K/D", "stats.Bedwars.kills_bedwars", "stats.Bedwars.deaths_bedwars", 2).addEnter();
        builder.addRatio("Final Kill", "Final Death", "FKDR", "stats.Bedwars.final_kills_bedwars", "stats.Bedwars.final_deaths_bedwars", 2).addEnter();
        builder.addInt("Iron", "stats.Bedwars.iron_resources_collected_bedwars").addSep();
        builder.addInt("Gold", "stats.Bedwars.gold_resources_collected_bedwars").addEnter();
        builder.addInt("Diamond", "stats.Bedwars.diamond_resources_collected_bedwars").addSep();
        builder.addInt("Emerald", "stats.Bedwars.emerald_resources_collected_bedwars").addEnter();
        return builder.toStringList();
    }

    public static List<String> getSkyWars(PlayerData data) {
        InfoBuilder builder = new InfoBuilder(data);
        builder.addString("Level", "stats.SkyWars.levelFormatted").addStr("§f").addSep().addInt("Soul", "stats.SkyWars.souls").addEnter();
        builder.addInt("Coins", "stats.SkyWars.coins").addSep().addInt("Assists", "stats.SkyWars.assists").addEnter();
        builder.addRatio("Kills", "Deaths", "K/D", "stats.SkyWars.kills", "stats.SkyWars.deaths", 2).addEnter();
        builder.addRatio("Wins", "Losses", "W/L", "stats.SkyWars.wins", "stats.SkyWars.losses", 2).addEnter();
        return builder.toStringList();
    }

    public static List<String> getMurderMystery(PlayerData data) {
        InfoBuilder builder = new InfoBuilder(data);
        builder.addInt("Coins", "stats.MurderMystery.coins").addSep().addInt("Gold Collected", "stats.MurderMystery.coins_pickedup").addEnter();
        builder.addInt("Murder Chance", "stats.MurderMystery.murderer_chance").addStr("%").addSep().addInt("Detective Chance", "stats.MurderMystery.detective_chance").addStr("%").addEnter();
        builder.addInt("Wins", "stats.MurderMystery.wins").addSep().addPercent("Win Rate", "stats.MurderMystery.wins", "stats.MurderMystery.games").addEnter();
        builder.addInt("Kills", "stats.MurderMystery.kills").addSep().addInt("Deaths", "stats.MurderMystery.deaths").addEnter();
        builder.addInt("Knife Kills", "stats.MurderMystery.knife_kills").addSep().addInt("Bow Kills", "stats.MurderMystery.bow_kills").addEnter();
        builder.addInt("Kills As Murderer", "stats.MurderMystery.kills_as_murderer").addSep().addInt("Heroes", "stats.MurderMystery.was_hero").addEnter();
        builder.addInt("Kills As Infected", "stats.MurderMystery.kills_as_infected").addSep().addInt("Kills As Survivor", "stats.MurderMystery.kills_as_survivor").addEnter();
        builder.addInt("Longest Time Survive", "stats.MurderMystery.longest_time_as_survivor_seconds").addStr("s").addEnter();
        builder.addInt("Alpha Chance", "stats.MurderMystery.alpha_chance").addStr("%");
        return builder.toStringList();
    }

    public static List<String> getDuel(PlayerData data) {
        InfoBuilder builder = new InfoBuilder(data);
        builder.addInt("Coins", "stats.Duels.coins").addSep().addInt("Ping Preference", "stats.Duels.pingPreference").addStr("ms").addEnter();
        builder.addRatio("Win", "Loss", "W/L", "stats.Duels.wins", "stats.Duels.losses", 2).addEnter();
        builder.addInt("Best Winstreak", "stats.Duels.best_all_modes_winstreak").addSep().addInt("Current Winstreak", "stats.Duels.current_winstreak").addEnter();
        builder.addRatio("Kills", "Deaths", "K/D", "stats.Duels.kills", "stats.Duels.deaths", 2).addEnter();
        return builder.toStringList();
    }

    public static List<String> getUhc(PlayerData data) {
        InfoBuilder builder = new InfoBuilder(data);
        builder.addInt("Score", "stats.UHC.score").addSep().addInt("Coins", "stats.UHC.coins").addSep().addInt("Wins", "stats.UHC.wins").addEnter();
        builder.addRatio("Kills", "Deaths", "K/D", "stats.UHC.kills", "stats.UHC.deaths", 2).addEnter();
        return builder.toStringList();
    }

    public static List<String> getMegaWall(PlayerData data) {
        InfoBuilder builder = new InfoBuilder(data);
        builder.addInt("Coins", "stats.Walls3.Coins").addSep().addDouble("Wither Damage", "stats.Walls3.wither_damage", 2).addEnter();
        builder.addString("Chosen Class", "stats.Walls3.chosen_class", new TextParser()).addEnter();
        builder.addRatio("Wins", "Losses", "W/L", "stats.Walls3.wins", "stats.Walls3.losses", 2).addEnter();
        builder.addInt("Kills", "stats.Walls3.kills").addSep().addInt("Deaths", "stats.Walls3.deaths").addEnter();
        builder.addRatio("K/D", "stats.Walls3.kills", "stats.Walls3.deaths", 2).addSep().addInt("Assists", "stats.Walls3.assists").addEnter();
        builder.addInt("Final kills", "stats.Walls3.final_kills").addSep().addInt("Final deaths", "stats.Walls3.final_deaths").addEnter();
        builder.addRatio("FKDR", "stats.Walls3.final_kills", "stats.Walls3.final_deaths", 2).addSep().addInt("Final Assists", "stats.Walls3.final_assists").addEnter();
        return builder.toStringList();
    }

    private static class InfoBuilder {
        private static final String UNDEFINED = "undefined";
        private StringBuilder builder = new StringBuilder();
        private final DataBase json;
        private final List<String> data = new ArrayList<>();

        public InfoBuilder(DataBase json) {
            this.json = json;
        }

        public InfoBuilder addInt(String title, String jsonKey) {
            builder.append(title).append(" : ");
            try {
                if (json.hasValue(jsonKey))
                    builder.append(json.getValue(jsonKey).getAsInt());
                else
                    throw new JsonKeyNotFoundException();
            } catch (Exception e) {
                builder.append(0);
            }
            return this;
        }

        public InfoBuilder addDouble(String title, String jsonKey, int digit, DoubleParser parser) {
            builder.append(title).append(" : ");
            try {
                if (json.hasValue(jsonKey))
                    builder.append(MathUtil.round(parser.parse(json.getValue(jsonKey).getAsDouble()), digit));
                else
                    throw new JsonKeyNotFoundException();
            } catch (Exception e) {
                builder.append(0.0);
            }
            return this;
        }

        public InfoBuilder addString(String title, String jsonKey) {
            return this.addString(title, jsonKey, new StringParser());
        }

        public InfoBuilder addString(String title, String jsonKey, StringParser parser) {
            builder.append(title).append(" : ");
            try {
                if (json.hasValue(jsonKey))
                    builder.append(parser.parse(json.getValue(jsonKey).getAsString()));
                else
                    throw new JsonKeyNotFoundException();
            } catch (Exception e) {
                builder.append(UNDEFINED);
            }
            return this;
        }

        public InfoBuilder addTime(String title, String jsonKey) {
            builder.append(title).append(" : ");
            try {
                if (json.hasValue(jsonKey))
                    builder.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(json.getValue(jsonKey).getAsLong())));
                else
                    throw new JsonKeyNotFoundException();
            } catch (Exception e) {
                builder.append(UNDEFINED);
            }
            return this;
        }

        public InfoBuilder addRatio(String title1, String title2, String title3, String key1, String key2, int digit) {
            this.addInt(title1, key1).addSep().addInt(title2, key2).addSep();
            return this.addRatio(title3, key1, key2, digit);
        }

        public InfoBuilder addRatio(String title, String key1, String key2, int digit) {
            builder.append(title).append(" : ");
            try {
                double v1 = json.getValue(key1).getAsDouble();
                double v2 = json.getValue(key2).getAsDouble();
                builder.append(MathUtil.round(v1 / v2, digit));
            } catch (Exception e) {
                builder.append(0.0);
            }
            return this;
        }

        public InfoBuilder addStr(String str) {
            builder.append(str);
            return this;
        }

        public InfoBuilder addSep() {
            builder.append(" | ");
            return this;
        }

        public void addEnter() {
            data.add(builder.toString());
            builder = new StringBuilder();
        }

        public InfoBuilder addPercent(String title, String partKey, String allKey) {
            builder.append(title).append(" : ");
            try {
                if (json.hasValue(partKey) && json.hasValue(allKey))
                    builder.append(MathUtil.round(100 * json.getValue(partKey).getAsDouble() / json.getValue(allKey).getAsDouble(), 2)).append("%");
                else
                    throw new JsonKeyNotFoundException();
            } catch (Exception e) {
                builder.append("0.00%");
            }
            return this;
        }

        public List<String> toStringList() {
            return this.data;
        }

        public InfoBuilder addDouble(String title, String jsonKey, int digit) {
            return this.addDouble(title, jsonKey, digit, new DoubleParser());
        }
    }
}
