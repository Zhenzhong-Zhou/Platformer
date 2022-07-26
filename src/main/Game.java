package main;

import states.Menu;
import states.Play;

import java.awt.*;

import static states.GameStates.PLAY;
import static states.GameStates.gameStates;

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
    private Menu menu;
    private Play play;

    public Game() {
        initClasses();

        scene = new Scene(this);
        window = new Window(scene);
        scene.setFocusable(true);
        scene.requestFocus();

        start();
    }

    private void initClasses() {
        menu = new Menu(this);
        play = new Play(this);
    }

    private void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void update() {
        switch(gameStates) {
            case MENU -> {
                menu.update();
            }
            case PLAY -> {
                play.update();
            }
            case OPTIONS, QUIT -> {
                System.exit(0);
            }
            default -> {

            }
        }
    }

    public void render(Graphics graphics) {
        switch(gameStates) {
            case MENU -> {
                menu.draw(graphics);
            }
            case PLAY -> {
                play.draw(graphics);
            }
            default -> {

            }
        }
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
        if(gameStates == PLAY) {
            play.getPlayer().resetDirectionBoolean();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public Play getPlay() {
        return play;
    }
}
