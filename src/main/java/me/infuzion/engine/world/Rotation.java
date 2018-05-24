package me.infuzion.engine.world;

import org.joml.Vector3f;

public class Rotation {
    public Vector3f vector;

    public Rotation() {
        vector = new Vector3f(0, 0, 0);
    }

    public Rotation(float x, float y, float z) {
        vector = new Vector3f(x, y, z);
    }

    public Rotation(double x, double y, double z) {
        vector = new Vector3f((float) x, (float) y, (float) z);
    }

    public Rotation(Vector3f vector) {
        this.vector = vector;
    }

    public Vector3f asVector() {
        return vector;
    }

    public void set(double x, double y, double z) {
        vector.set((float) x, (float) y, (float) z);
    }

    public double getX() {
        return vector.x();
    }

    public void setX(double x) {
        vector.x = (float) x;
    }

    public double getY() {
        return vector.y();
    }

    public void setY(double y) {
        vector.y = (float) y;
    }

    public double getZ() {
        return vector.z();
    }

    public void setZ(double z) {
        vector.z = (float) z;
    }
}
