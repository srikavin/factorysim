package me.infuzion.engine.sprite;

public class SpriteIdentifier {
    private final String mod;
    private final String name;
    private final String variation;

    public SpriteIdentifier(String mod, String name) {
        this(mod, name, null);
    }

    public SpriteIdentifier(String mod, String name, String variation) {
        this.mod = mod;
        this.name = name;
        this.variation = variation;
    }

    @Override
    public String toString() {
        return "[" + mod + "/" + name + (variation != null ? "/" + variation : "") + "] ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpriteIdentifier that = (SpriteIdentifier) o;

        return mod.equals(that.mod)
                && name.equals(that.name)
                && (variation != null ? variation.equals(that.variation) : that.variation == null);
    }

    @Override
    public int hashCode() {
        int result = mod.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (variation != null ? variation.hashCode() : 0);
        return result;
    }

    public Sprite getSprite(SpriteLoader loader) {
        if (variation != null) {
            return loader.loadSprite(mod, name, variation);
        }
        return loader.loadSprite(mod, name);
    }
}
