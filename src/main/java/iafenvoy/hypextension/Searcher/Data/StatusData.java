package iafenvoy.hypextension.Searcher.Data;

import com.google.gson.JsonObject;

public class StatusData extends DataBase {
    public StatusData(JsonObject json) {
        super(json.get("session"));
    }
}
