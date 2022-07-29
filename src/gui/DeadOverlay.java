package gui;

import states.GameStates;
import states.Play;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static main.Game.*;
import static states.GameStates.MENU;
import static utilities.Constants.GUI.PauseButtons.UTIL_SIZE;
import static utilities.LoadSave.DEAD_SCREEN;
import static utilities.LoadSave.GetSpriteAtlas;

public class DeadOverlay {
    private final Play play;
    private BufferedImage deadScreen;
    private int imageX, imageY,imageW,imageH;
    private UtilButtons menu, replay;

    public DeadOverlay(Play play) {
        this.play = play;
        createDeadScreenImage();
        createButtons();
    }

    private void createButtons() {
        int menuX = (int) (335 * SCALE);
        int replayX = (int) (440 * SCALE);
        int y = (int) (195 * SCALE);
        menu = new UtilButtons(menuX, y, UTIL_SIZE, UTIL_SIZE, 2);
        replay = new UtilButtons(replayX, y, UTIL_SIZE, UTIL_SIZE, 1);
    }

    private void createDeadScreenImage() {
        deadScreen = GetSpriteAtlas(DEAD_SCREEN);
        imageW = (int) (deadScreen.getWidth() * SCALE);
        imageH = (int) (deadScreen.getHeight() * SCALE);
        imageX = GAME_WIDTH / 2 - imageW / 2;
        imageY = (int) (100 * SCALE);
    }

    public void draw(Graphics graphics) {
        graphics.setColor(new Color(0, 0, 0, 200));
        graphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        graphics.drawImage(deadScreen, imageX, imageY,imageW,imageH, null);
        menu.draw(graphics);
        replay.draw(graphics);
    }

    public void update() {
        menu.update();
        replay.update();
    }

    private boolean isSelectedButton(UtilButtons utilButtons, MouseEvent e) {
        return utilButtons.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        menu.setMouseHover(false);
        replay.setMouseHover(false);

        if(isSelectedButton(menu, e)) {
            menu.setMouseHover(true);
        } else if(isSelectedButton(replay, e)) {
            replay.setMouseHover(true);
        }
    }

    public void mousePressed(MouseEvent e) {
        if(isSelectedButton(menu, e)) {
            menu.setMousePressed(true);
        } else if(isSelectedButton(replay, e)) {
            replay.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(isSelectedButton(menu, e)) {
            if(menu.isMousePressed()) {
                play.resetAll();
                GameStates.gameStates = MENU;
                play.setGameStates(MENU);
            }
        } else if(isSelectedButton(replay, e)) {
            if(replay.isMousePressed()) {
                play.resetAll();
                play.getGame().getAudioController().setLevelSound(play.getLevelManager().getLevelIndex());
            }
        }
        menu.restBooleans();
        replay.restBooleans();
    }
}
