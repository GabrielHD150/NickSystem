package net.latinplay.nicksystem.Utils.Managers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.latinplay.nicksystem.Main;
import net.latinplay.nicksystem.Skins.SkinData;
import net.latinplay.nicksystem.Utils.Calls.Callback;
import net.latinplay.nicksystem.Utils.RandomUtils;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SkinManager {

    private Main plugin;

    public SkinManager(Main plugin) {
        this.plugin = plugin;
    }

    public void getSkin(String playerName, String skinID, Callback<SkinData> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            JsonObject jsonresponse = getJsonResponse("https://api.mineskin.org/get/id/" + skinID);
            if (jsonresponse != null && !jsonresponse.has("error")) {
                JsonObject textureProperty = jsonresponse.getAsJsonObject("data").getAsJsonObject("texture");
                String value = textureProperty.get("value").getAsString();
                String signature = textureProperty.get("signature").getAsString();

                SkinData skinData = new SkinData(playerName, value, signature);
                callback.done(skinData);
            }
        });
    }

    public void getSkin(String playerName, Callback<SkinData> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            List<String> ids = Main.getMain().getConfigUtils().getConfig(plugin, "Settings").getStringList("SkinIDs");
            int skinID = Integer.valueOf(ids.get(ThreadLocalRandom.current().nextInt(ids.size())));
            JsonObject jsonresponse = getJsonResponse("https://api.mineskin.org/get/id/" + skinID);
            if (jsonresponse != null && !jsonresponse.has("error")) {
                JsonObject textureProperty = jsonresponse.getAsJsonObject("data").getAsJsonObject("texture");
                String value = textureProperty.get("value").getAsString();
                String signature = textureProperty.get("signature").getAsString();

                SkinData skinData = new SkinData(playerName, value, signature);
                callback.done(skinData);
            }
        });
    }

    public void generateRandom(Callback<SkinData> callback) {
        getSkin(RandomUtils.generateName(), callback);
    }

    public void generateNormal(String playerName, Callback<SkinData> callback) {
        getSkin(playerName, callback);
    }

    private JsonObject getJsonResponse(String url) {
        URL ipAdress;
        JsonObject rootobj = null;
        try {
            ipAdress = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(ipAdress.openStream()));
            String jsonresponse = in.readLine();
            JsonParser jsonParser = new JsonParser();
            JsonElement root = jsonParser.parse(jsonresponse);
            rootobj = root.getAsJsonObject();
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return rootobj;
    }

}
