package states;

import gui.MenuButtons;
import main.Game;

import java.awt.event.MouseEvent;

public class State {
    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public boolean isSelectedButton(MouseEvent e, MenuButtons menuButton) {
        return menuButton.getBounds().contains(e.getX(), e.getY());
    }

    public Game getGame() {
        return game;
    }
}
