package me.infuzion.fractorio;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.ScrollEvent;
import me.infuzion.fractorio.object.GameObject;
import me.infuzion.fractorio.sprite.SpriteLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameWorld {
    private final SpriteLoader loader;
    private List<GameObject> objects = new ArrayList<>();
    private Map<Position, GameObject> gameObjectMap = new HashMap<>();
    private double scale = 2.0;
    private double offSetX = 0;
    private double offSetY = 0;

    public GameWorld(SpriteLoader loader) {

        this.loader = loader;
    }

    public double getOffSetX() {
        return offSetX;
    }

    public double getOffSetY() {
        return offSetY;
    }

    public double getScale() {
        return scale;
    }

    public void init(Canvas canvas) {
        canvas.addEventHandler(ScrollEvent.SCROLL, event -> {
            double dY = event.getDeltaY();
            if (dY > 0) {
                zoomIn();
            } else {
                zoomOut();
            }
        });
    }

    public void zoomIn() {
        scale += .1;
    }

    public void zoomOut() {
        scale -= .1;
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

    public void panLeft() {
        offSetX += 2 * scale;
    }

    public void panRight() {
        offSetX -= 2 * scale;
    }

    public void panUp() {
        offSetY += 2 * scale;
    }

    public void panDown() {
        offSetY -= 2 * scale;
    }
}
