package me.infuzion.engine.world;

import me.infuzion.engine.sprite.SpriteLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameWorld {
    private final SpriteLoader loader;
    private List<GameObject> objects = new ArrayList<>();
    private Map<Position, GameObject> gameObjectMap = new HashMap<>();

    public GameWorld(SpriteLoader loader) {

        this.loader = loader;
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public GameObject getObjectAt(Position position) {
        return gameObjectMap.get(position);
    }

    public void addGameObject(GameObject object) {
        objects.add(object);
        gameObjectMap.put(object.getPosition(), object);
        object.init(loader);
    }

    public void tick() {
        for (GameObject e : getObjects()) {
            e.tick(this);
        }
    }
}
