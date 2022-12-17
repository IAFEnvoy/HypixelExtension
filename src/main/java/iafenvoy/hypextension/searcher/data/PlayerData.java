package iafenvoy.hypextension.searcher.data;

import com.google.gson.JsonObject;

public class PlayerData extends DataBase {
    public PlayerData(JsonObject data) {
        super(data.get("player"));
    }
}
