package me.infuzion.factorysim.sprite;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

public class Frame {
    private final Image image;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final int duration;

    Frame(int x, int y, int width, int height, int duration, Image image) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.duration = duration;
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDuration() {
        return duration;
    }

    public Image getImage() {
        return image;
    }

    public ByteBuffer getRawImage() {
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
        image.getPixelReader().getPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(), buffer, width * 4);
        return buffer;
    }

    public int getY() {
        return y;
    }
}
