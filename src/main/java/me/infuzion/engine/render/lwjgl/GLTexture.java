package me.infuzion.engine.render.lwjgl;

import me.infuzion.engine.render.model.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class GLTexture {
    private final Texture texture;
    private int textureId;

    public GLTexture(Texture texture) {
        this.texture = texture;
        textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
//        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA,
//                texture.getWidth(), texture.getHeight(),
//                0, GL_RGBA, GL_UNSIGNED_BYTE,
//                texture());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public int getTextureId() {
        return textureId;
    }

    public void cleanup() {
        glDeleteTextures(textureId);
    }

    public Texture getTexture() {
        return texture;
    }
}
