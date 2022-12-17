package iafenvoy.hypextension.searcher;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import iafenvoy.hypextension.config.Configs;
import iafenvoy.hypextension.searcher.error.ApiError;
import iafenvoy.hypextension.searcher.error.HypixelApiError;
import iafenvoy.hypextension.searcher.error.MojangApiError;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class NetworkUtil {
    //Mojang API : PlayerName->UUID
    public static final String profilesUrl = "https://api.mojang.com/users/profiles/minecraft/";//+playername
    //Hypixel API Base
    public static final String hypixelUrl = "https://api.hypixel.net/";
    //缓存UUID
    private static final HashMap<String, String> uuidMap = new HashMap<>();

    public static String getUrl(String location, String... args) {
        StringBuilder url = new StringBuilder(hypixelUrl);
        url.append(location).append("?").append("key=").append(Configs.INSTANCE.apiKey.getStringValue());
        for (String arg : args) {
            url.append("&");
            url.append(arg);
        }
        return url.toString();
    }

    public static String getUuid(String playerName) throws ApiError {
        try {
            if (uuidMap.containsKey(playerName))
                return uuidMap.get(playerName);
            JsonObject data = downloadJson(profilesUrl + playerName);
            if (data.has("id"))
                uuidMap.put(playerName, data.get("id").getAsString());
            else
                throw new ApiError();
            return uuidMap.get(playerName);
        } catch (Exception e) {
            throw new ApiError(e);
        }
    }

    public static HypixelInfo getInfo(String uuid) throws ApiError {
        JsonObject main = downloadJson(getUrl("player", "uuid=" + uuid));
        JsonObject guild = downloadJson(getUrl("guild", "player=" + uuid));
        JsonObject status = downloadJson(getUrl("status", "uuid=" + uuid));
        return new HypixelInfo(main, guild, status);
    }

    public static JsonObject downloadJson(String url) throws ApiError {
        try {
            StringBuilder json = new StringBuilder();
            URL u = new URL(url);
            URLConnection yc = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), StandardCharsets.UTF_8));
            String input;
            while ((input = in.readLine()) != null)
                json.append(input);
            in.close();
            JsonElement element = new JsonParser().parse(json.toString());
            if (element.isJsonNull())
                throw new MojangApiError("404");
            else
                return (JsonObject) element;
        } catch (Exception e) {
            if (url.contains("mojang")) {
                if (e.getMessage().contains("404"))
                    throw new MojangApiError("404 : Player Not Found").setUrl(url);
                else if (e.getMessage().contains("403"))
                    throw new MojangApiError("403 : Server Busy").setUrl(url);
                else if (e.getMessage().contains("502"))
                    throw new MojangApiError("502 : Server Down").setUrl(url);
            } else if (url.contains("hypixel"))
                if (e.getMessage().contains("404"))
                    throw new HypixelApiError("404 : No Data Can Be Found").setUrl(url);
                else if (e.getMessage().contains("403"))
                    throw new HypixelApiError("403 : Invalid API Key").setUrl(url);
                else if (e.getMessage().contains("429"))
                    throw new HypixelApiError("429 : Key throttle").setUrl(url);
            e.printStackTrace();
            return new JsonObject();
        }
    }
}
