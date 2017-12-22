package me.infuzion.engine.render;

import me.infuzion.engine.sprite.SpriteIdentifier;

public class RenderInfo {
    private SpriteIdentifier sprite;
    private boolean usingSharedAnimations;
    private boolean visible;

    public RenderInfo(SpriteIdentifier sprite, boolean sharedAnimation) {
        this(sprite, sharedAnimation, true);
    }

    public RenderInfo(SpriteIdentifier sprite, boolean sharedAnimation, boolean visible) {
        this.sprite = sprite;
        this.usingSharedAnimations = sharedAnimation;
        this.visible = visible;
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
