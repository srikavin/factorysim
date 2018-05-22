package me.infuzion.engine.render.model;

import me.infuzion.engine.render.lwjgl.util.LWJGLUtil;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;

import java.nio.ByteBuffer;

public class Scene {
    private Mesh[] meshes;

    public Scene(String fileName) {
        this(LWJGLUtil.ioResourceToByteBuffer(fileName, 1024));
    }

    public Scene(ByteBuffer modelContents) {
        this(Assimp.aiImportFileFromMemory(modelContents, 0, ""));
    }

    public Scene(AIScene scene) {
        System.out.println(Assimp.aiGetErrorString());
        meshes = new Mesh[scene.mNumMeshes()];
        for (int i = 0; i < scene.mNumMeshes(); i++) {
            AIMesh a = AIMesh.create(scene.mMeshes().get(i));
            meshes[i] = Mesh.create(a);
        }
    }

    public Mesh[] getMeshes() {
        return meshes;
    }

    public void render() {
        for (Mesh e : meshes) {
            e.render();
        }
    }


}
