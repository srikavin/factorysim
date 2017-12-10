package me.infuzion.fractorio.render;

import me.infuzion.fractorio.sprite.SpriteIdentifier;

public class RenderInfo {
    private SpriteIdentifier sprite;
    private boolean usingSharedAnimations;

    public RenderInfo(SpriteIdentifier sprite, boolean sharedAnimation) {
        this.sprite = sprite;
        this.usingSharedAnimations = sharedAnimation;
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
