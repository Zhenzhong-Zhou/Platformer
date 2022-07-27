package inputs;

import main.Scene;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static states.GameStates.gameStates;

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
        switch(gameStates) {
            case MENU -> scene.getGame().getMenu().keyPressed(e);
            case PLAY -> scene.getGame().getPlay().keyPressed(e);
            default -> {
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(gameStates) {
            case MENU -> scene.getGame().getMenu().keyReleased(e);
            case PLAY -> scene.getGame().getPlay().keyReleased(e);
            default -> {
            }
        }
    }
}
