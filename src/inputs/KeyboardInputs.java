package inputs;

import main.Scene;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {
    private Scene scene;

    public KeyboardInputs(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W:
                scene.changeYDelta(-5);
                System.out.println("W is pressed!");
                break;
            case KeyEvent.VK_A:
                scene.changeXDelta(-5);
                System.out.println("A is pressed!");
                break;
            case KeyEvent.VK_S:
                scene.changeYDelta(+5);
                System.out.println("S is pressed!");
                break;
            case KeyEvent.VK_D:
                scene.changeXDelta(+5);
                System.out.println("D is pressed!");
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
