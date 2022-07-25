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
                scene.getGame().getPlayer().setUp(true);
            }
            case KeyEvent.VK_A -> {
                scene.getGame().getPlayer().setLeft(true);
            }
            case KeyEvent.VK_S -> {
                scene.getGame().getPlayer().setDown(true);
            }
            case KeyEvent.VK_D -> {
                scene.getGame().getPlayer().setRight(true);
            }
            default -> {
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                scene.getGame().getPlayer().setUp(false);
            }
            case KeyEvent.VK_A -> {
                scene.getGame().getPlayer().setLeft(false);
            }
            case KeyEvent.VK_S -> {
                scene.getGame().getPlayer().setDown(false);
            }
            case KeyEvent.VK_D -> {
                scene.getGame().getPlayer().setRight(false);
            }
            default -> {
            }
        }
    }
}
