package states;

import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static main.Game.GAME_WIDTH;
import static states.GameStates.PLAY;
import static states.GameStates.gameStates;

public class Menu extends State implements StateMethods{
    public Menu(Game game) {
        super(game);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.PINK);
        graphics.drawString("MENU", GAME_WIDTH/2, 200);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            gameStates = PLAY;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
