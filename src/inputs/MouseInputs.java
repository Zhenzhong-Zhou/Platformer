package inputs;

import main.Scene;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static states.GameStates.gameStates;

public class MouseInputs implements MouseListener, MouseMotionListener {
    private final Scene scene;

    public MouseInputs(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch(gameStates) {
            case PLAY -> {
                scene.getGame().getPlay().mouseClicked(e);
            }
            default -> {

            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch(gameStates) {
            case MENU -> {
                scene.getGame().getMenu().mousePressed(e);
            }
            case PLAY -> {
                scene.getGame().getPlay().mousePressed(e);
            }
            default -> {

            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch(gameStates) {
            case MENU -> {
                scene.getGame().getMenu().mouseReleased(e);
            }
            case PLAY -> {
                scene.getGame().getPlay().mouseReleased(e);
            }
            default -> {

            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch(gameStates) {
            case MENU -> {
                scene.getGame().getMenu().mouseMoved(e);
            }
            case PLAY -> {
                scene.getGame().getPlay().mouseMoved(e);
            }
            default -> {

            }
        }
    }
}
