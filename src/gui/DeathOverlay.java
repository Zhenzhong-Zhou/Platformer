package gui;

import states.GameStates;
import states.Play;

import java.awt.*;
import java.awt.event.KeyEvent;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import static states.GameStates.MENU;

public class DeathOverlay {
    private Play play;

    public DeathOverlay(Play play) {
        this.play = play;
    }

    public void draw(Graphics graphics) {
        graphics.setColor(new Color(0, 0, 0, 200));
        graphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        graphics.setColor(Color.white);
        graphics.drawString("Game Over", GAME_WIDTH / 2, 150);
        graphics.drawString("Press esc to enter Main Menu!", GAME_WIDTH / 2, 300);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            play.resetAll();
            GameStates.gameStates = MENU;
        }
    }
}
