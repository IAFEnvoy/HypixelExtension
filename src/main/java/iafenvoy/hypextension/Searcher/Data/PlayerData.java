package iafenvoy.hypextension.Searcher.Data;

import com.google.gson.JsonObject;

public class PlayerData extends DataBase {
    public PlayerData(JsonObject data) {
        super(data.get("player"));
    }

    public void print() {
        for (String key : values.keySet())
            System.out.println(key + " : " + values.get(key));
    }
}
