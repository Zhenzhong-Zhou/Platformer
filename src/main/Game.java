package main;

public class Game {
    private Window window;
    private Scene scene;

    public Game() {
        scene = new Scene();
        window = new Window(scene);
        scene.requestFocus();
    }
}
