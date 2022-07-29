package states;

import audio.AudioController;
import gui.MenuButtons;
import main.Game;

import java.awt.event.MouseEvent;

import static utilities.Constants.AudioPlayer.MENU_1;

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

    public void setGameStates(GameStates gameStates) {
        switch(gameStates) {
            case MENU -> game.getAudioController().playSound(MENU_1);
            case PLAY -> game.getAudioController().setLevelSound(game.getPlay().getLevelManager().getLevelIndex());
        }
        GameStates.gameStates = gameStates;
    }
}
