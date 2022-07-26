package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static main.Game.GAME_WIDTH;
import static main.Game.SCALE;
import static utilities.Constants.GUI.PauseButtons.SOUND_SIZE;
import static utilities.Constants.GUI.PauseButtons.URM_SIZE;
import static utilities.LoadSave.GetSpriteAtlas;
import static utilities.LoadSave.PAUSE_BACKGROUND;

public class PauseOverlay {
    private BufferedImage backgroundImage;
    private int bgX, bgY, bgW, bgH;
    private SoundButtons musicButton, sfxButton;
    private UtilButtons resumeButton, replayButton, menuButton;

    public PauseOverlay() {
        loadBackground();
        createSoundButtons();
        createUtilButtons();
    }

    private void createUtilButtons() {
        int resumeX = (int) (SCALE * 462);
        int replayX = (int) (SCALE * 387);
        int menuX = (int) (SCALE * 313);
        int bY = (int) (SCALE * 325);

        resumeButton = new UtilButtons(resumeX, bY, URM_SIZE, URM_SIZE, 0);
        replayButton = new UtilButtons(replayX, bY, URM_SIZE, URM_SIZE, 1);
        menuButton = new UtilButtons(menuX, bY, URM_SIZE, URM_SIZE, 2);
    }

    private void createSoundButtons() {
        int soundX = (int) (SCALE * 450);
        int musicY = (int) (SCALE * 140);
        int sfxY = (int) (SCALE * 186);

        musicButton = new SoundButtons(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButtons(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    private void loadBackground() {
        backgroundImage = GetSpriteAtlas(PAUSE_BACKGROUND);
        bgW = (int) (backgroundImage.getWidth() * SCALE);
        bgH = (int) (backgroundImage.getHeight() * SCALE);
        bgX = GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (SCALE * 25);
    }

    public void update() {
        musicButton.update();
        sfxButton.update();
        resumeButton.update();
        replayButton.update();
        menuButton.update();
    }

    public void draw(Graphics graphics) {
        // Background
        graphics.drawImage(backgroundImage, bgX, bgY, bgW, bgH, null);

        // Sound Buttons
        musicButton.draw(graphics);
        sfxButton.draw(graphics);

        // UtilButtons
        resumeButton.draw(graphics);
        replayButton.draw(graphics);
        menuButton.draw(graphics);
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if(isSelectedButton(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if(isSelectedButton(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(isSelectedButton(e, musicButton)) {
            if(musicButton.isMousePressed()) {
                musicButton.setMuted(musicButton.isMuted());
            }
        } else if(isSelectedButton(e, sfxButton)) {
            if(sfxButton.isMousePressed()) {
                sfxButton.setMuted(sfxButton.isMuted());
            }
        }

        musicButton.resetBooleans();
        sfxButton.resetBooleans();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseHover(false);
        sfxButton.setMouseHover(false);

        if(isSelectedButton(e, musicButton)) {
            musicButton.setMouseHover(true);
        } else if(isSelectedButton(e, sfxButton)) {
            sfxButton.setMouseHover(true);
        }
    }

    private boolean isSelectedButton(MouseEvent e, PauseButtons pauseButton) {
        return pauseButton.getBounds().contains(e.getX(), e.getY());
    }
}
