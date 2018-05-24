package me.infuzion.engine.render;

public class Camera {
    private double offSetZ = 5;
    private final static double yRot = Math.toRadians(25);
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
        offSetZ += .1 * scale;
    }

    public void zoomOut() {
        offSetZ -= .1 * scale;
    }

    public void panLeft() {
        offSetX += .5 * scale;
    }

    public void panRight() {
        offSetX -= .5 * scale;
    }

    public void panUp() {
        offSetY += .5 * scale;
    }

    public void panDown() {
        offSetY -= .5 * scale;
    }

    public double getOffSetZ() {
        return offSetZ;
    }
}
