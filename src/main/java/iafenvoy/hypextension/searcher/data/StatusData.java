package iafenvoy.hypextension.searcher.data;

import com.google.gson.JsonObject;

public class StatusData extends DataBase {
    public StatusData(JsonObject json) {
        super(json.get("session"));
    }
}
