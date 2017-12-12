package me.infuzion.factorysim.sprite;

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
    private Frame[] frames;
    private long lastUpdate = 0;
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
            int x = frame.get("x").getAsInt();
            int y = frame.get("y").getAsInt();
            int w = frame.get("w").getAsInt();
            int h = frame.get("h").getAsInt();
            int duration = e.getAsJsonObject().get("duration").getAsInt();
            Frame frameS = new Frame(x, y, w, h, duration, new WritableImage(reader, x, y, w, h));
            frames[i] = frameS;
            i++;
        }
    }

    public Frame getCurrentFrame() {
        return frames[currentFrame];
    }

    public int getCurrentFrameIndex() {
        return currentFrame;
    }

    public void setCurrentFrameIndex(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public Image draw() {
        if (!animated) {
            return image;
        }
        return frames[currentFrame].getImage();
    }

    public void update() {
        if (!animated) {
            return;
        }

        long curTime = System.currentTimeMillis();

        if (currentFrame >= frames.length - 1) {
            currentFrame = 0;
        }

        if (curTime - lastUpdate >= frames[currentFrame].getDuration() * 50) {
            lastUpdate = curTime;
            currentFrame++;
        }
    }

    public SpriteIdentifier getIdentifier() {
        return identifier;
    }
}
