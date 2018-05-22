package me.infuzion.engine.ui;

import me.infuzion.engine.render.lwjgl.Window;
import me.infuzion.engine.render.lwjgl.util.LWJGLUtil;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.nanovg.NanoVG.nvgCreateFontMem;
import static org.lwjgl.nanovg.NanoVGGL3.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class UIManager {
    private final Window window;
    /**
     * Maintains references of the fonts to prevent garbage collection.
     * If it gets garbage collected, NanoVG will crash in native code, as it expects
     * the buffer to be maintained.
     */
    private final Map<Integer, ByteBuffer> fontBufferMap;
    private long vgContext;

    public UIManager(Window window) {
        this.window = window;
        vgContext = nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES);

        if (vgContext == NULL) {
            throw new RuntimeException("Could not initialize UI System using NanoVG!");
        }
        fontBufferMap = new HashMap<>();
    }

    public long getVgContext() {
        return vgContext;
    }

    public int getFont(String resourcePath, String fontName) {
        ByteBuffer buffer = LWJGLUtil.ioResourceToByteBuffer(resourcePath, 1024);
        int fontID = nvgCreateFontMem(vgContext, fontName, buffer, 1);

        if (fontID == -1) {
            throw new RuntimeException("Font at " + resourcePath + " could not be loaded!");
        }

        fontBufferMap.put(fontID, buffer);

        return fontID;
    }

    public void destroyFont(int id) {
        fontBufferMap.remove(id);
    }

    public void restoreScreenState() {
        window.restoreState();
    }
}
