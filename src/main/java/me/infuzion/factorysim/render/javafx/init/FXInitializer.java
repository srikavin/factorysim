package me.infuzion.fractorio.render.javafx.init;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import me.infuzion.fractorio.input.InputHandler;
import me.infuzion.fractorio.render.Initializer;
import me.infuzion.fractorio.render.javafx.FXRenderer;
import me.infuzion.fractorio.render.Renderer;
import me.infuzion.fractorio.sprite.SpriteLoader;

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
        return null;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Fractorio");

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
