package iafenvoy.hypextension.Searcher;

import com.google.gson.JsonObject;
import iafenvoy.hypextension.Searcher.Data.GuildData;
import iafenvoy.hypextension.Searcher.Data.PlayerData;
import iafenvoy.hypextension.Searcher.Data.StatusData;
import iafenvoy.hypextension.Searcher.Error.ApiError;
import iafenvoy.hypextension.Utils.ColorUtil;

import java.util.Objects;

public class HypixelInfo {
    private final String playerName;
    private final PlayerData main;
    private final GuildData guild;
    private final StatusData status;

    public HypixelInfo(JsonObject main, JsonObject guild, JsonObject status) throws ApiError {
        if (!main.has("player") || !main.get("player").isJsonObject() || !main.get("player").getAsJsonObject().has("displayname"))
            throw new ApiError();
        if (guild.size() == 0 || status.size() == 0)
            throw new ApiError();
        this.playerName = main.get("player").getAsJsonObject().get("displayname").getAsString();
        this.main = new PlayerData(main);
        this.guild = new GuildData(guild);
        this.status = new StatusData(status);
    }

    public PlayerData getMain() {
        return main;
    }

    public GuildData getGuild() {
        return guild;
    }

    public StatusData getStatus() {
        return status;
    }

    public String getNameWithRank() {
        return this.getRank() + playerName + " " + this.getGuildTag() + "§f";
    }

    private String getRank() {
        String plus, rank, newPackageRank;
        try {
            plus = this.main.getValue("rankPlusColor").getAsString();
            plus = ColorUtil.getColorByName(plus);
        } catch (Exception e) {
            plus = "§c";//默认红色
        }
        try {
            rank = this.main.getValue("rank").getAsString();
            switch (rank) {
                case "YOUTUBER":
                    return "§c[§fYT§c]";
                case "ADMIN":
                    return "§4[ADMIN]";
                case "MODERATOR":
                    return "§2[MOD]";
                case "HELPER":
                    return "§9[HELP]";
            }
        } catch (Exception ignored) {
        }
        try {
            newPackageRank = this.main.getValue("newPackageRank").getAsString();
            if (Objects.equals(newPackageRank, "MVP_PLUS")) {
                if (!this.main.hasValue("monthlyPackageRank") || Objects.equals(this.main.getValue("monthlyPackageRank").getAsString(), "NONE") || !this.main.hasValue("monthlyPackageRank"))
                    return "§b[MVP" + plus + "+§b]";
                else return "§6[MVP" + plus + "++§6]";
            } else if (newPackageRank.equals("MVP")) return "§b[MVP]";
            else if (newPackageRank.equals("VIP_PLUS")) return "§a[VIP§6+§a]";
            else if (newPackageRank.equals("VIP")) return "§a[VIP]";
            else return "§7";
        } catch (Exception e) {
            return "§7";
        }
    }

    private String getGuildTag() {
        if (this.guild.hasValue("tagColor") && this.guild.hasValue("tag"))
            try {
                String color = this.guild.getValue("tagColor").getAsString();
                String tag = this.guild.getValue("tag").getAsString();
                return ColorUtil.getColorByName(color) + "[" + tag + "]";
            } catch (Exception e) {
                return "";
            }
        return "";
    }
}
