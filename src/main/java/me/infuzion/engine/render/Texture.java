package me.infuzion.engine.render;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Texture {
    private final ByteBuffer imageData;
    private final int width;
    private final int height;

    public Texture(String path) throws IOException {
        PNGDecoder decoder = new PNGDecoder(Texture.class.getClassLoader().getResourceAsStream(path));

        width = decoder.getWidth();
        height = decoder.getHeight();

        imageData = ByteBuffer.allocateDirect(4 * decoder.getHeight() * decoder.getWidth());

        decoder.decode(imageData, 4 * decoder.getWidth(), PNGDecoder.Format.RGBA);
        imageData.flip();
    }

    public ByteBuffer getImageData() {
        return imageData;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
