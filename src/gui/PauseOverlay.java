package gui;

import states.Play;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static main.Game.GAME_WIDTH;
import static main.Game.SCALE;
import static states.GameStates.MENU;
import static states.GameStates.gameStates;
import static utilities.Constants.GUI.PauseButtons.*;
import static utilities.LoadSave.GetSpriteAtlas;
import static utilities.LoadSave.PAUSE_BACKGROUND;

public class PauseOverlay {
    private final Play play;
    private BufferedImage backgroundImage;
    private int bgX, bgY, bgW, bgH;
    private AudioOptions audioOptions;
    private UtilButtons resumeButton, replayButton, menuButton;

    public PauseOverlay(Play play) {
        this.play = play;
        loadBackground();
        audioOptions = play.getGame().getAudioOptions();
        createUtilButtons();
    }

    private void createUtilButtons() {
        int resumeX = (int) (SCALE * 462);
        int replayX = (int) (SCALE * 387);
        int menuX = (int) (SCALE * 313);
        int bY = (int) (SCALE * 325);

        resumeButton = new UtilButtons(resumeX, bY, UTIL_SIZE, UTIL_SIZE, 0);
        replayButton = new UtilButtons(replayX, bY, UTIL_SIZE, UTIL_SIZE, 1);
        menuButton = new UtilButtons(menuX, bY, UTIL_SIZE, UTIL_SIZE, 2);
    }

    private void loadBackground() {
        backgroundImage = GetSpriteAtlas(PAUSE_BACKGROUND);
        bgW = (int) (backgroundImage.getWidth() * SCALE);
        bgH = (int) (backgroundImage.getHeight() * SCALE);
        bgX = GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (SCALE * 25);
    }

    public void update() {
        // Util Buttons
        resumeButton.update();
        replayButton.update();
        menuButton.update();

        // Audio Buttons
        audioOptions.update();
    }

    public void draw(Graphics graphics) {
        // Background
        graphics.drawImage(backgroundImage, bgX, bgY, bgW, bgH, null);

        // UtilButtons
        resumeButton.draw(graphics);
        replayButton.draw(graphics);
        menuButton.draw(graphics);

        // Audio Buttons
        audioOptions.draw(graphics);
    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    public void mousePressed(MouseEvent e) {
        if(isSelectedButton(e, resumeButton)) {
            resumeButton.setMousePressed(true);
        } else if(isSelectedButton(e, replayButton)) {
            replayButton.setMousePressed(true);
        } else if(isSelectedButton(e, menuButton)) {
            menuButton.setMousePressed(true);
        } else {
            audioOptions.mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(isSelectedButton(e, resumeButton)) {
            if(resumeButton.isMousePressed()) {
                play.resume();
            }
        } else if(isSelectedButton(e, replayButton)) {
            if(replayButton.isMousePressed()) {
                play.resetAll();
                play.resume();
            }
        } else if(isSelectedButton(e, menuButton)) {
            if(menuButton.isMousePressed()) {
                gameStates = MENU;
                play.resume();
            }
        } else {
            audioOptions.mouseReleased(e);
        }

        resumeButton.restBooleans();
        replayButton.restBooleans();
        menuButton.restBooleans();
    }

    public void mouseMoved(MouseEvent e) {
        resumeButton.setMouseHover(false);
        replayButton.setMouseHover(false);
        menuButton.setMouseHover(false);

        if(isSelectedButton(e, resumeButton)) {
            resumeButton.setMouseHover(true);
        } else if(isSelectedButton(e, replayButton)) {
            replayButton.setMouseHover(true);
        } else if(isSelectedButton(e, menuButton)) {
            menuButton.setMouseHover(true);
        } else {
            audioOptions.mouseMoved(e);
        }
    }

    private boolean isSelectedButton(MouseEvent e, PauseButtons pauseButton) {
        return pauseButton.getBounds().contains(e.getX(), e.getY());
    }
}
