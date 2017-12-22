package me.infuzion.engine.render;

public class Camera {
    private double scale = 2.0;
    private double offSetX = 0;
    private double offSetY = 0;

    public double getOffSetX() {
        return offSetX;
    }

    public double getOffSetY() {
        return offSetY;
    }

    public double getScale() {
        return scale;
    }

    public void zoomIn() {
        scale += .1;
    }

    public void zoomOut() {
        scale -= .1;
    }

    public void panLeft() {
        offSetX += 2 * scale;
    }

    public void panRight() {
        offSetX -= 2 * scale;
    }

    public void panUp() {
        offSetY += 2 * scale;
    }

    public void panDown() {
        offSetY -= 2 * scale;
    }
}
