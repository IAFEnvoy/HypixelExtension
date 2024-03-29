package iafenvoy.hypextension.ingame.minigame;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import iafenvoy.hypextension.utils.FileUtil;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GameList {
    public static final GameList INSTANCE = new GameList();
    public static final MiniGame BACK = new MiniGame(" < Back", "", new ArrayList<>());
//    public static final MiniGame LOBBY = new MiniGame("Lobby", "", new ArrayList<>());

    public final List<MiniGame> DATA;

    public GameList() {
        List<MiniGame> temp = new ArrayList<>();
        try {
            InputStream listFile = this.getClass().getResourceAsStream("/hypixel_games.json");
            assert listFile != null;
            String data = FileUtil.readByLines(new InputStreamReader(listFile));
            JsonArray json = new JsonParser().parse(data).getAsJsonArray();
            for (JsonElement o : json)
                temp.add(miniGameJsonDFS(o.getAsJsonObject()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DATA = temp;
    }

    private MiniGame miniGameJsonDFS(JsonObject obj) {
        String key = obj.get("key").getAsString();
        String name = obj.get("name").getAsString();
        if (obj.has("command"))
            key = obj.get("command").getAsString();
        List<MiniGame> games = new ArrayList<>();
        if (obj.has("modes")) {
            JsonArray array = obj.get("modes").getAsJsonArray();
            for (JsonElement o : array)
                games.add(miniGameJsonDFS(o.getAsJsonObject()));
        }
        return new MiniGame(name, key, games);
    }
}
