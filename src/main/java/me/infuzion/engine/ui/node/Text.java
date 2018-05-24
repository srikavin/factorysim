package me.infuzion.engine.ui.node;

import me.infuzion.engine.ui.UIManager;
import me.infuzion.engine.ui.UINode;
import org.lwjgl.nanovg.NVGColor;

import static org.lwjgl.nanovg.NanoVG.*;

public class Text extends UINode {
    private int font;
    private NVGColor color;

    public Text(UIManager manager) {
        font = manager.getFont("assets/fonts/a.ttf", "a");
        color = NVGColor.create();
    }

    @Override
    public void draw(UIManager manager, int x, int y, double scale) {
        long vg = manager.getVgContext();
        nvgFontSize(vg, 40.0f);
        nvgFontFaceId(vg, font);
        nvgFillColor(vg, nvgRGBA((byte) 0x23, (byte) 0xa1, (byte) 0xf1, (byte) 200, color));
        nvgText(vg, 45, 45, "Testing ");
    }
}
