package inputs;

import main.Scene;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static states.GameStates.gameState;

public class MouseInputs implements MouseListener, MouseMotionListener {
    private final Scene scene;

    public MouseInputs(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch(gameState) {
            case MENU -> {
                scene.getGame().getMenu().mouseClicked(e);
            }
            case PLAY -> {
                scene.getGame().getPlay().mouseClicked(e);
            }
            default -> {

            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

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

    }
}
