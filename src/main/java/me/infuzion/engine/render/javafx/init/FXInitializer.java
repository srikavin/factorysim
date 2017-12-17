package me.infuzion.engine.render.javafx.init;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import me.infuzion.engine.input.FXInputHandler;
import me.infuzion.engine.input.InputHandler;
import me.infuzion.engine.render.Initializer;
import me.infuzion.engine.render.Renderer;
import me.infuzion.engine.render.javafx.FXRenderer;
import me.infuzion.engine.sprite.SpriteLoader;

public class FXInitializer extends Application implements Initializer {
    private static volatile GraphicsContext context;

    public Renderer initRenderer(SpriteLoader loader) {
        new Thread(() ->
                Application.launch(getClass(), new String[]{})
        ).start();
        while (context == null){
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new FXRenderer(context.getCanvas(), loader);
    }

    @Override
    public InputHandler initInput() {
        return new FXInputHandler(context.getCanvas());
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Factory Simulator");

        Canvas canvas = new Canvas(512, 512);
        canvas.setFocusTraversable(true);

        Group root = new Group();
        Scene scene = new Scene(root);
        root.getChildren().add(canvas);

        stage.setScene(scene);
        stage.show();

        context = canvas.getGraphicsContext2D();

        stage.widthProperty().addListener(((observable, oldValue, newValue) -> canvas.setWidth((Double) newValue)));
        stage.heightProperty().addListener(((observable, oldValue, newValue) -> canvas.setHeight((Double) newValue)));
    }

    public GraphicsContext getContext() {
        return context;
    }
}
