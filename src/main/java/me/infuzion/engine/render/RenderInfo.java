package me.infuzion.engine.render;

import me.infuzion.engine.render.model.Mesh;
import me.infuzion.engine.render.model.Scene;
import me.infuzion.engine.render.model.Texture;
import me.infuzion.engine.sprite.SpriteIdentifier;

public class RenderInfo {
    private SpriteIdentifier sprite;
    private boolean usingSharedAnimations;
    private boolean visible;
    private static Mesh mesh;

    static {
        try {
            Scene scene = new Scene("assets/base/furnace.dae");
            mesh = scene.getMeshes()[0];
//            setTexture(new Texture("assets/base/earth.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RenderInfo(SpriteIdentifier sprite, boolean sharedAnimation) {
        this(sprite, sharedAnimation, true);
    }

    public RenderInfo(SpriteIdentifier sprite, boolean sharedAnimation, boolean visible) {
        this.sprite = sprite;
        this.usingSharedAnimations = sharedAnimation;
        this.visible = visible;
        setTexture(new Texture("assets/base/furnace/furnace_on.png"));
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setTexture(Texture texture) {
        mesh.getMaterial().setTexture(texture);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public SpriteIdentifier getSprite() {
        return sprite;
    }

    public void setSprite(SpriteIdentifier sprite) {
        this.sprite = sprite;
    }

    public boolean isUsingSharedAnimations() {
        return usingSharedAnimations;
    }

    public void setUsingSharedAnimations(boolean usingSharedAnimations) {
        this.usingSharedAnimations = usingSharedAnimations;
    }
}
