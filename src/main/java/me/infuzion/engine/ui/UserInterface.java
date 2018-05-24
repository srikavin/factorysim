package me.infuzion.engine.ui;

public class UserInterface {
    private final UIManager manager;
    private UINode root;

    public UserInterface(UIManager manager, UINode root) {
        this.manager = manager;
        this.root = root;
    }

    public void draw() {
        root.draw(manager, 0, 0, 1);
    }
}
