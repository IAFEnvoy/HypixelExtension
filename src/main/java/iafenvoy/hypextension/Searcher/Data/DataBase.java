package iafenvoy.hypextension.Searcher.Data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.HashMap;
import java.util.Map;

public class DataBase {
    protected final HashMap<String, JsonElement> values;

    public DataBase(JsonElement json) {
        this.values = new HashMap<>();
        if (!json.isJsonNull())
            this.jsonDFS(json, "");
    }

    public DataBase(HashMap<String, JsonElement> values) {
        this.values = values;
    }

    private void jsonDFS(JsonElement json, String previous) {
        String pre = previous.isEmpty() ? "" : previous + ".";
        if (json.isJsonArray()) {
            JsonArray array = json.getAsJsonArray();
            for (int i = 0; i < array.size(); i++)
                jsonDFS(array.get(i), pre + i);
            this.values.put(pre + "length", new JsonPrimitive(array.size()));
        } else if (json.isJsonObject()) {
            JsonObject obj = json.getAsJsonObject();
            for (Map.Entry<String, JsonElement> key : obj.entrySet())
                jsonDFS(key.getValue(), pre + key.getKey());
        } else
            this.values.put(previous, json);
    }

    public JsonElement getValue(String key) {
        return this.values.get(key);
    }

    public boolean hasValue(String key) {
        return this.values.containsKey(key);
    }

    public DataBase copy(String prefix) {
        final HashMap<String, JsonElement> map = new HashMap<>();
        for (String key : values.keySet())
            if (key.startsWith(prefix + "."))
                map.put(key.substring(key.length() + 1), values.get(key));
        return new DataBase(map);
    }

    public boolean isEmpty() {
        return this.values.isEmpty();
    }
}
