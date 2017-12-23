package me.infuzion.factorysim;

import me.infuzion.engine.input.InputHandler;
import me.infuzion.engine.render.Camera;
import me.infuzion.engine.render.Renderer;
import me.infuzion.engine.render.lwjgl.init.LWJGLInitializer;
import me.infuzion.engine.sprite.SpriteLoader;
import me.infuzion.engine.world.GameWorld;
import me.infuzion.engine.world.Position;
import me.infuzion.factorysim.object.Furnace;
import me.infuzion.factorysim.object.conveyor.Conveyor;

import java.util.Arrays;

public class FactorySim {

    public static void main(String[] args) {
        SpriteLoader spriteLoader = new SpriteLoader();

        LWJGLInitializer init = new LWJGLInitializer();
        Renderer renderer = init.initRenderer(spriteLoader);
        InputHandler inputHandler = init.initInput();
        GameWorld world = new GameWorld(spriteLoader);

        Camera camera = new Camera();

        KeyHandler handler = new KeyHandler(world, camera, Arrays.asList(inputHandler));

        for (int i = 0; i < 20; i++) {
            Conveyor a = new Conveyor();
            a.setPosition(Position.positionFor(0, i));
            world.addGameObject(a);
        }

        Furnace f = new Furnace();
        f.setPosition(Position.positionFor(12, 5));
        world.addGameObject(f);

        long lastUpdate = 0;
        long lastSecond = 0;
        int counterFPS = 0;
        int counterUPS = 0;

        while (true) {
            long now = System.currentTimeMillis();
            renderer.render(world, camera);
            if (now - lastUpdate >= 40) {
                lastUpdate = now;
                handler.tick();
                inputHandler.tick();
                world.tick();
                counterUPS++;
            }
            counterFPS++;

            if (now - lastSecond >= 1000) {
                lastSecond = now;
                System.out.println("FPS: " + counterFPS + " UPS: " + counterUPS);
                counterFPS = 0;
                counterUPS = 0;
            }
            if (counterFPS > 100) {
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
