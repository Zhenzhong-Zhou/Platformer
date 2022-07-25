package main;

public class Game implements Runnable {
    private final int FPS_SET = 120;
    private final Window window;
    private final Scene scene;
    private Thread thread;

    public Game() {
        scene = new Scene();
        window = new Window(scene);
        scene.requestFocus();
        start();
    }

    private void start() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();

        int frames = 0;
        long lastCheck = System.currentTimeMillis();
        // noinspection InfiniteLoopStatement
        while(true) {
            now = System.nanoTime();
            if(now - lastFrame >= timePerFrame) {
                scene.repaint();
                lastFrame = now;
                frames++;
            }

            if(System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }
}
