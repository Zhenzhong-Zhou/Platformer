package main;

public class Game implements Runnable {
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
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

    public void update() {
        scene.updateScene();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaUpdates = 0;
        double deltaFrames = 0;

        // noinspection InfiniteLoopStatement
        while(true) {
            long currentTime = System.nanoTime();
            deltaUpdates += (currentTime - previousTime) / timePerUpdate;
            deltaFrames += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            if(deltaUpdates >= 1) {
                update();
                updates++;
                deltaUpdates--;
            }

            if(deltaFrames >= 1) {
                scene.repaint();
                frames++;
                deltaFrames--;
            }

            if(System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
}
