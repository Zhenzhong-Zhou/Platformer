package main;

import entities.Player;
import levels.LevelManager;

import java.awt.*;

public class Game implements Runnable {
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private final Window window;
    private final Scene scene;
    private Thread thread;
    private Player player;
    private LevelManager levelManager;

    public Game() {
        initClasses();

        scene = new Scene(this);
        window = new Window(scene);
        scene.setFocusable(true);
        scene.requestFocus();

        start();
    }

    private void initClasses() {
        levelManager = new LevelManager(this);
        player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE));
//        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
    }

    private void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void update() {
        player.update();
        levelManager.update();
    }

    public void render(Graphics graphics) {
        levelManager.draw(graphics);
        player.render(graphics);
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

    public void windowFocusLost() {
        player.resetDirectionBoolean();
    }

    public Player getPlayer() {
        return player;
    }
}
