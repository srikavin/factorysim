package me.infuzion.engine.render.lwjgl;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformations {
    private final static float FIELD_OF_VIEW = (float) Math.toRadians(110);

    //Cache instances to reduce allocations per frame
    private final Matrix4f projection = new Matrix4f();
    private final Matrix4f worldMatrix = new Matrix4f();

    public Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
        worldMatrix.translation(offset)
                .rotateX((float) Math.toRadians(rotation.x))
                .rotateY((float) Math.toRadians(rotation.y))
                .rotateZ((float) Math.toRadians(rotation.z))
                .scale(scale);
        return worldMatrix;
    }

    public Matrix4f getProjectionUniform(float width, float height) {
        projection.setPerspective(FIELD_OF_VIEW, width / height, 0.1f, 1000.0f);
        return projection;
    }
}
