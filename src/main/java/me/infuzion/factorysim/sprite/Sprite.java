package me.infuzion.fractorio.sprite;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Sprite {
    private final Image image;
    private final SpriteIdentifier identifier;
    private final boolean animated;
    private long lastUpdate = 0;
    private Frame[] frames;
    private int currentFrame = 0;

    public Sprite(Image image, SpriteIdentifier identifier) {
        this(image, identifier, null);
    }

    public Sprite(Image image, SpriteIdentifier identifier, String json) {
        this.image = image;
        this.identifier = identifier;

        if (json == null) {
            animated = false;
            return;
        }


        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(json).getAsJsonObject();
        JsonArray array = object.getAsJsonArray("frames");

        animated = array.size() > 1;

        frames = new Frame[array.size()];
        PixelReader reader = image.getPixelReader();

        int i = 0;
        for (JsonElement e : array) {
            JsonObject frame = e.getAsJsonObject().getAsJsonObject("frame");
            Frame frameS = new Frame();
            frameS.x = frame.get("x").getAsInt();
            frameS.y = frame.get("y").getAsInt();
            frameS.w = frame.get("w").getAsInt();
            frameS.h = frame.get("h").getAsInt();
            frameS.duration = e.getAsJsonObject().get("duration").getAsInt();
            frames[i] = frameS;
            i++;
        }
        for (Frame e : frames) {
            e.image = new WritableImage(reader, e.x, e.y, e.w, e.h);
        }
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public Image draw() {
        if (!animated) {
            return image;
        }
        return frames[currentFrame].image;
    }

    public void update() {
        if(!animated){
            return;
        }

        long curTime = System.currentTimeMillis();

        if (currentFrame >= frames.length - 1) {
            currentFrame = 0;
        }

        if (curTime - lastUpdate >= frames[currentFrame].duration * 50) {
            lastUpdate = curTime;
            currentFrame++;
        }
    }

    public SpriteIdentifier getIdentifier() {
        return identifier;
    }

    static class Frame {
        int x;
        int y;
        int w;
        int h;
        int duration;
        Image image;
    }
}
