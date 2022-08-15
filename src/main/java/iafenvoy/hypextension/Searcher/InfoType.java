package iafenvoy.hypextension.Searcher;

import fi.dy.masa.malilib.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum InfoType {
    None("", "", ""),
    BedWar("BEDWARS", "Bedwars", "Bed Wars", "bedwar", "bw"),
    SkyWar("SKYWARS", "SkyWars", "SkyWars", "skywar", "sw"),
    MurderMystery("MURDER_MYSTERY", "MurderMystery", "Murder Mystery", "murdermystery", "mm"),
    BuildBattle("BUILD_BATTLE", "BuildBattle", "Build Battle", "buildbattle", "bb"),
    Arcade("ARCADE", "Arcade", "Arcade", "arcade", "ar"),
    Duel("DUELS", "Duels", "Duels", "duel", "duels"),
    TNTGames("TNTGAMES", "TNTGames", "TNT Games", "tntgames", "tnt", "tgame", "tg"),
    UHC("UHC", "UHC", "UHC Champions", "uhc"),
    MCGO("MCGO", "MCGO", "Cops and Crims", "mcgo", "cac", "go"),
    MegaWalls("WALLS3", "Walls3", "Mega Walls", "megawall", "megawalls", "mw"),
    Housing("HOUSING", "Housing", "Housing", "house", "housing"),
    Classic("LEGACY", "Legacy", "Classic Games", "classic", "cg", "legacy"),
    Prototype("PROTOTYPE", "Prototype", "Prototype", "phototype", "photo", "pt"),
    Pit("PIT", "Pit", "Pit", "pit"),
    SkyBlock("SKYBLOCK", "SkyBlock", "SkyBlock", "sb", "skyblock"),
    Warlords("BATTLEGROUND", "Battleground", "Warlords", "warlords", "wl", "bg", "battleground"),
    Walls("WALLS", "Walls", "Walls", "wall", "walls"),
    Paintball("PAINTBALL", "Paintball", "Paintball", "paintball", "pb"),
    SkyClash("SKYCLASH", "SkyClash", "SkyClash", "skyclash", "sc"),
    SuperSmash("SUPER_SMASH", "SuperSmash", "Smash Heroes", "smashheroes", "sh"),
    Quake("QUAKECRAFT", "Quake", "Quake", "quake"),
    HungerGames("SURVIVAL_GAMES", "HungerGames", "Blitz Survival Games", "blitz", "hg"),
    VampireZ("VAMPIREZ", "VampireZ", "VampireZ", "VampireZ", "Vampire"),
    Arena("ARENA", "Arena", "Arena", "Arena"),
    Kart("GINGERBREAD", "GingerBread", "Turbo Kart Racers", "ginger", "tkr"),
    CrazyWalls("TRUE_COMBAT", "TrueCombat", "Crazy Walls", "crazywalls", "crazy", "crazywall"),
    SpeedUHC("SPEED_UHC", "SpeedUHC", "Speed UHC", "speeduhc"),
    Replay("REPLAY", "Replay", "Replay", "replay"),
    SMP("SMP", "SMP", "SMP", "smp");

    private final String key, name, name2;
    private final List<String> abbrs = new ArrayList<>();

    InfoType(String key, String name, String name2, String... abbr) {
        this.key = key;
        this.name = name;
        this.name2 = name2;
        for (String string : abbr)
            abbrs.add(string.toLowerCase());
    }

    public static InfoType fromString(String name) {
        name = name.toLowerCase();
        for (InfoType val : InfoType.values())
            if (val.abbrs.contains(name))
                return val;
        return InfoType.None;
    }

    public static String getAllString() {
        StringBuilder output = new StringBuilder();
        for (InfoType val : InfoType.values()) {
            if (val == InfoType.None)
                continue;
            output.append("\n-").append(StringUtils.translate(val.name().toLowerCase())).append(" :");
            for (String key : val.abbrs)
                output.append(" ").append(key);
        }
        return output.toString();
    }

    public String getKey() {
        return this.key;
    }

    public String getDataBaseName() {
        return this.name;
    }

    public String getName() {
        return this.name2;
    }
}
