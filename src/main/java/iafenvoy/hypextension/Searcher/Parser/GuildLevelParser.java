package iafenvoy.hypextension.Searcher.Parser;

public class GuildLevelParser extends DoubleParser {
    static final int[] guildLevelTables = {100000, 150000, 250000, 500000, 750000, 1000000, 1250000, 1500000, 2000000, 2500000, 2500000, 2500000, 2500000, 2500000, 3000000};

    public double parse(double value) {
        int level = 0;
        for (int i = 0; ; i++) {
            int need = i >= guildLevelTables.length ? guildLevelTables[guildLevelTables.length - 1] : guildLevelTables[i];
            value -= need;
            level++;
            if (value < 0) return level + value / need;
        }
    }
}
