package me.infuzion.fractorio.util;

public class Pair<K, V> {
    private final K left;
    private final V right;

    public Pair(K left, V right) {
        this.left = left;
        this.right = right;
    }

    public V getRight() {
        return right;
    }

    public K getLeft() {
        return left;
    }
}
