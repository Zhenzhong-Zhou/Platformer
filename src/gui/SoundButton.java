package gui;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilities.Constants.GUI.PauseButtons.SOUND_DEFAULT_SIZE;
import static utilities.LoadSave.GetSpriteAtlas;
import static utilities.LoadSave.SOUND_BUTTONS;

public class SoundButton extends PauseButton {
    private BufferedImage[][] soundImages;
    private boolean mouseHover, mousePressed;
    private boolean muted;
    private int rowIndex, columnIndex;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadSoundImages();
    }

    private void loadSoundImages() {
        BufferedImage temp = GetSpriteAtlas(SOUND_BUTTONS);
        soundImages = new BufferedImage[2][3];

        for(int j =0; j<soundImages.length;j++) {
            for(int i=0; i<soundImages[j].length; i++) {
                soundImages[j][i] = temp.getSubimage(SOUND_DEFAULT_SIZE*i, SOUND_DEFAULT_SIZE*j, SOUND_DEFAULT_SIZE, SOUND_DEFAULT_SIZE);
            }
        }
    }

    public void update() {
        if(muted) {
            rowIndex = 1;
        } else {
            rowIndex = 0;
        }

        columnIndex = 0;
        if(mouseHover) {
            columnIndex = 1;
        }
        if(mousePressed) {
            columnIndex = 2;
        }
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(soundImages[rowIndex][columnIndex], x, y, width, height, null);
    }

    public boolean isMouseHover() {
        return mouseHover;
    }

    public void setMouseHover(boolean mouseHover) {
        this.mouseHover = mouseHover;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
