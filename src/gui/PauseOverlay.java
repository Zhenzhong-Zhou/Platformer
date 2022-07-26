package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static main.Game.GAME_WIDTH;
import static main.Game.SCALE;
import static utilities.Constants.GUI.PauseButtons.SOUND_SIZE;
import static utilities.LoadSave.GetSpriteAtlas;
import static utilities.LoadSave.PAUSE_BACKGROUND;

public class PauseOverlay {
    private BufferedImage backgroundImage;
    private int bgX, bgY, bgW, bgH;
    private SoundButton musicButton, sfxButton;

    public PauseOverlay() {
        loadBackground();
        createSoundButtons();
    }

    private void createSoundButtons() {
        int soundX = (int)(SCALE*450);
        int musicY = (int)(SCALE*140);
        int sfxY = (int) (SCALE*186);

        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton= new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    private void loadBackground() {
        backgroundImage = GetSpriteAtlas(PAUSE_BACKGROUND);
        bgW = (int) (backgroundImage.getWidth()*SCALE);
        bgH = (int) (backgroundImage.getHeight()*SCALE);
        bgX = GAME_WIDTH/2 - bgW/2;
        bgY = (int) (SCALE * 25);
    }

    public void update() {

    }

    public void draw(Graphics graphics) {
        // Background
        graphics.drawImage(backgroundImage, bgX, bgY, bgW, bgH, null);

        // Sound Buttons
        musicButton.draw(graphics);
        sfxButton.draw(graphics);
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }
}
