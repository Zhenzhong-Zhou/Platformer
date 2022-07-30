package gui;

import main.Game;

import java.awt.*;
import java.awt.event.MouseEvent;

import static main.Game.SCALE;
import static utilities.Constants.GUI.PauseButtons.*;

public class AudioOptions {
    private SoundButtons musicButton, sfxButton;
    private VolumeButtons volumeButtons;
    private final Game game;

    public AudioOptions(Game game) {
        this.game = game;
        createSoundButtons();
        createVolumeButtons();
    }

    private void createSoundButtons() {
        int soundX = (int) (SCALE * 450);
        int musicY = (int) (SCALE * 140);
        int sfxY = (int) (SCALE * 186);

        musicButton = new SoundButtons(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButtons(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    private void createVolumeButtons() {
        int vX = (int) (SCALE * 309);
        int vY = (int) (SCALE * 278);
        volumeButtons = new VolumeButtons(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    public void draw(Graphics graphics) {
        // Sound Buttons
        musicButton.draw(graphics);
        sfxButton.draw(graphics);

        // Volume Buttons
        volumeButtons.draw(graphics);
    }

    public void update() {
        // Sound Buttons
        musicButton.update();
        sfxButton.update();

        // Volume Buttons
        volumeButtons.update();
    }

    private boolean isSelectedButton(MouseEvent e, PauseButtons pauseButton) {
        return pauseButton.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseDragged(MouseEvent e) {
        if(volumeButtons.isMousePressed()) {
            float valueBefore = volumeButtons.getFloatValue();
            volumeButtons.changeX(e.getX());
            float valueAfter = volumeButtons.getFloatValue();
            if(valueBefore != valueAfter) {
                game.getAudioController().setVolume(valueAfter);
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        if(isSelectedButton(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if(isSelectedButton(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        } else if(isSelectedButton(e, volumeButtons)) {
            volumeButtons.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(isSelectedButton(e, musicButton)) {
            if(musicButton.isMousePressed()) {
                musicButton.setMuted(musicButton.isMuted());
                game.getAudioController().toggleSoundMute();
            }
        } else if(isSelectedButton(e, sfxButton)) {
            if(sfxButton.isMousePressed()) {
                sfxButton.setMuted(sfxButton.isMuted());
                game.getAudioController().toggleEffectMute();
            }
        }

        musicButton.resetBooleans();
        sfxButton.resetBooleans();
        volumeButtons.restBooleans();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseHover(false);
        sfxButton.setMouseHover(false);
        volumeButtons.setMouseHover(false);

        if(isSelectedButton(e, musicButton)) {
            musicButton.setMouseHover(true);
        } else if(isSelectedButton(e, sfxButton)) {
            sfxButton.setMouseHover(true);
        } else if(isSelectedButton(e, volumeButtons)) {
            volumeButtons.setMouseHover(true);
        }
    }
}
