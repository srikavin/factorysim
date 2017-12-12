package me.infuzion.factorysim.render.javafx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import me.infuzion.factorysim.GameWorld;
import me.infuzion.factorysim.Position;
import me.infuzion.factorysim.object.GameObject;
import me.infuzion.factorysim.render.Camera;
import me.infuzion.factorysim.render.RenderInfo;
import me.infuzion.factorysim.render.Renderer;
import me.infuzion.factorysim.sprite.Sprite;
import me.infuzion.factorysim.sprite.SpriteIdentifier;
import me.infuzion.factorysim.sprite.SpriteLoader;
import me.infuzion.factorysim.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FXRenderer implements Renderer {
    private final Canvas canvas;
    private final GraphicsContext context;
    private final SpriteLoader loader;
    private final Map<UUID, Pair<RenderInfo, Sprite>> objectSpriteMap = new HashMap<>();
    private final Map<SpriteIdentifier, Sprite> sharedSprites = new HashMap<>();

    public FXRenderer(Canvas canvas, SpriteLoader loader) {
        this.canvas = canvas;
        this.loader = loader;
        this.context = canvas.getGraphicsContext2D();
    }

    @Override
    public void render(GameWorld world, Camera camera) {
        context.setFill(Color.BLUE);
        context.fillRect(0, 0, canvas.getHeight(), canvas.getWidth());

        double scale = world.getScale();

        for (GameObject e : world.getObjects()) {
            UUID objID = e.getUuid();

            //Load Sprites if not present
            RenderInfo renderInfo = e.getRenderInfo();
            SpriteIdentifier identifier = renderInfo.getSprite();

            if (renderInfo.isUsingSharedAnimations()) {
                sharedSprites.putIfAbsent(identifier, identifier.getSprite(loader));
                objectSpriteMap.putIfAbsent(objID, new Pair<>(e.getRenderInfo(), sharedSprites.get(identifier)));
            } else {
                objectSpriteMap.putIfAbsent(objID, new Pair<>(e.getRenderInfo(), identifier.getSprite(loader)));
            }

            Position position = e.getPosition();
            Sprite sprite = objectSpriteMap.get(objID).getRight();

            if(!sprite.getIdentifier().equals(identifier)){
                objectSpriteMap.put(objID, new Pair<>(renderInfo, sprite.getIdentifier().getSprite(loader)));
            }

            double val = 32 * scale;
            double xOffset = world.getOffSetX() * scale;
            double yOffset = world.getOffSetY() * scale;

            context.drawImage(sprite.draw(), position.getX() * val + xOffset,
                    position.getY() * val + yOffset, 32 * scale, 32 * scale);

        }
        updateSprites();
    }

    private void updateSprites() {
        for (Map.Entry<SpriteIdentifier, Sprite> e : sharedSprites.entrySet()) {
            e.getValue().update();
        }
        for (Map.Entry<UUID, Pair<RenderInfo, Sprite>> e : objectSpriteMap.entrySet()) {
            Pair<RenderInfo, Sprite> pair = e.getValue();
            Sprite spr = pair.getRight();
            RenderInfo renderInfo = pair.getLeft();

            if (!renderInfo.isUsingSharedAnimations()) {
                spr.update();
            }
        }
    }
}
