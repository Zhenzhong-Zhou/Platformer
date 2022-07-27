package gui;

import states.GameStates;
import states.Play;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static main.Game.GAME_WIDTH;
import static main.Game.SCALE;
import static states.GameStates.MENU;
import static utilities.Constants.GUI.PauseButtons.UTIL_SIZE;
import static utilities.LoadSave.GetSpriteAtlas;
import static utilities.LoadSave.LEVEL_COMPLETE;

public class FinishedOverlay {
    private final Play play;
    private UtilButtons menuButton, nextButton;
    private BufferedImage image;
    private int bgX, bgY, bgW, bgH;

    public FinishedOverlay(Play play) {
        this.play = play;
        initImage();
        initButtons();
    }

    private void initButtons() {
        int nextX = (int) (445 * SCALE);
        int menuX = (int) (330 * SCALE);
        int y = (int) (195 * SCALE);
        nextButton = new UtilButtons(nextX, y, UTIL_SIZE, UTIL_SIZE, 0);
        menuButton = new UtilButtons(menuX, y, UTIL_SIZE, UTIL_SIZE, 2);
    }

    private void initImage() {
        image = GetSpriteAtlas(LEVEL_COMPLETE);
        bgW = (int) (image.getWidth() * SCALE);
        bgH = (int) (image.getHeight() * SCALE);
        bgX = GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (75 * SCALE);
    }

    public void update() {
        nextButton.update();
        menuButton.update();
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(image, bgX, bgY, bgW, bgH, null);
        nextButton.draw(graphics);
        menuButton.draw(graphics);
    }

    private boolean isSelectedButton(UtilButtons utilButtons, MouseEvent e) {
        return utilButtons.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        nextButton.setMouseHover(false);
        menuButton.setMouseHover(false);

        if(isSelectedButton(nextButton, e)) {
            nextButton.setMouseHover(true);
        } else if(isSelectedButton(menuButton, e)) {
            menuButton.setMouseHover(true);
        }
    }

    public void mousePressed(MouseEvent e) {
        if(isSelectedButton(nextButton, e)) {
            nextButton.setMousePressed(true);
        } else if(isSelectedButton(menuButton, e)) {
            menuButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(isSelectedButton(nextButton, e)) {
            if(nextButton.isMousePressed()) {
                play.loadNextLevel();
            }
        } else if(isSelectedButton(menuButton, e)) {
            if(menuButton.isMousePressed()) {
                play.resetAll();
                GameStates.gameStates = MENU;
            }
        }
        nextButton.restBooleans();
        menuButton.restBooleans();
    }
}
