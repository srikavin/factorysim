package me.infuzion.factorysim;

import me.infuzion.factorysim.input.InputHandler;
import me.infuzion.factorysim.object.base.Furnace;
import me.infuzion.factorysim.object.base.conveyor.Conveyor;
import me.infuzion.factorysim.render.Camera;
import me.infuzion.factorysim.render.Renderer;
import me.infuzion.factorysim.render.lwjgl.init.LWJGLInitializer;
import me.infuzion.factorysim.sprite.SpriteLoader;

import java.util.Arrays;

public class FactorySim {

    public static void main(String[] args) {
        SpriteLoader spriteLoader = new SpriteLoader();

//        FXInitializer initializer = new FXInitializer();
        LWJGLInitializer init = new LWJGLInitializer();
//        init.initRenderer(spriteLoader);
//        Renderer r2 = initializer.initRenderer(spriteLoader);
        Renderer renderer = init.initRenderer(spriteLoader);//initializer.initRenderer(spriteLoader);
        InputHandler inputHandler = init.initInput();
//        InputHandler inputHandler2 = initializer.initInput();
        GameWorld world = new GameWorld(spriteLoader);

//        GraphicsContext context = initializer.getContext();

        Camera camera = new Camera();

//        world.init(context.getCanvas());
        KeyHandler handler = new KeyHandler(world, Arrays.asList(inputHandler));

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
//            Platform.runLater(() ->
//                    r2.render(world, camera));
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
