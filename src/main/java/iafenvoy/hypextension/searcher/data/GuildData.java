package iafenvoy.hypextension.searcher.data;

import com.google.gson.JsonObject;

public class GuildData extends DataBase {
    public GuildData(JsonObject data) {
        super(data.get("guild"));
    }
}
