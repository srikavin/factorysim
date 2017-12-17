package me.infuzion.engine.sprite;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import javafx.scene.image.Image;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteLoader {
    private static final Gson gson = new Gson();
    private static final JsonParser parser = new JsonParser();
    private final Map<String, SpriteInfo> cacheMap = new HashMap<>();

    public SpriteLoader() {

    }

    public Sprite loadSprite(String modName, String name) {
        return loadSprite(modName, name, null);
    }

    public Sprite loadSprite(String modName, String name, String variation) {
        String filePath = "assets/" + modName + "/" + name + "/" + name + (variation != null ? "_" + variation : "");

        SpriteInfo info;

        if (cacheMap.containsKey(filePath)) {
            info = cacheMap.get(filePath);
        } else {
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream(filePath + ".png");
            InputStream json = this.getClass().getClassLoader().getResourceAsStream(filePath + ".json");
            System.out.println("Loaded: " + filePath + ".json");
            String jsonString;
            try {
                jsonString = IOUtils.toString(json, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            info = new SpriteInfo();
            info.image = new Image(stream);
            info.jsonString = jsonString;
            cacheMap.put(filePath, info);
        }

        return new Sprite(info.image, new SpriteIdentifier(modName, name, variation), info.jsonString);
    }

    public void loadSprites(String json) {
        JsonObject object = parser.parse(json).getAsJsonObject();
        if (object.has("sprites") && object.has("name")) {
            JsonArray array = object.getAsJsonArray("sprites");
            String name = object.get("name").getAsString();
            List<String> locations = gson.fromJson(array, new TypeToken<List<String>>() {
            }.getType());
            for (String e : locations) {
                loadSprite(name, e);
            }
        }
    }

    class SpriteInfo {
        String jsonString;
        Image image;
    }
}
