package me.infuzion.factorysim;

import me.infuzion.engine.input.InputHandler;
import me.infuzion.engine.input.KeyInput;
import me.infuzion.engine.render.Camera;
import me.infuzion.engine.world.GameWorld;

import java.util.List;
import java.util.Set;

public class KeyHandler {
    private final GameWorld world;
    private Camera camera;
    private List<InputHandler> inputs;

    public KeyHandler(GameWorld world, Camera camera, List<InputHandler> inputs) {
        this.world = world;
        this.camera = camera;
        this.inputs = inputs;
    }

    public void tick() {
        for (InputHandler input : inputs) {
            Set<KeyInput> current = input.getKeyEvents();

            for (KeyInput e : current) {
                System.out.println("handling " + e);
                switch (e) {
                    case QUIT:
                        System.exit(0);
                        break;
                    case MOVE_LEFT:
                        camera.panLeft();
                        break;
                    case MOVE_RIGHT:
                        camera.panRight();
                        break;
                    case MOVE_UP:
                        camera.panUp();
                        break;
                    case MOVE_DOWN:
                        camera.panDown();
                        break;
                    case ZOOM_IN:
                        camera.zoomIn();
                        break;
                    case ZOOM_OUT:
                        camera.zoomOut();
                        break;
                }
            }
        }
    }
}
