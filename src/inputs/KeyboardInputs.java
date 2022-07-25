package inputs;

import main.Scene;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utilities.Constants.Directions.*;

public class KeyboardInputs implements KeyListener {
    private final Scene scene;

    public KeyboardInputs(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                scene.setDirection(UP);
            }
            case KeyEvent.VK_A -> {
                scene.setDirection(LEFT);
            }
            case KeyEvent.VK_S -> {
                scene.setDirection(DOWN);
            }
            case KeyEvent.VK_D -> {
                scene.setDirection(RIGHT);
            }
            default -> {
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D -> scene.setMoving(false);
            default -> {
            }
        }
    }
}
