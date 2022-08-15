package iafenvoy.hypextension.Searcher.Data;

import com.google.gson.JsonObject;

public class GuildData extends DataBase {
    public GuildData(JsonObject data) {
        super(data.get("guild"));
    }

    public void print() {
        for (String key : values.keySet())
            System.out.println(key + " : " + values.get(key));
    }
}
