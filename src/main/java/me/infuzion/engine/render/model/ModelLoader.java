package me.infuzion.engine.render.model;

import me.infuzion.engine.render.lwjgl.util.LWJGLUtil;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;

public class ModelLoader {
    public ModelLoader() {
        AIScene scene = Assimp.aiImportFileFromMemory(LWJGLUtil.ioResourceToByteBuffer("assets/base/furnace.dae", 1024), 0, "");
        System.out.println(Assimp.aiGetErrorString());
        System.out.println(scene.mNumMeshes());

    }

    public static void main(String[] args) {
        new ModelLoader();
    }
}
