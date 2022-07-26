package gui;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilities.Constants.GUI.PauseButtons.SOUND_DEFAULT_SIZE;
import static utilities.LoadSave.GetSpriteAtlas;
import static utilities.LoadSave.SOUND_BUTTONS;

public class SoundButton extends PauseButton {
    private BufferedImage[][] soundImages;

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

    }

    public void draw(Graphics graphics) {
        graphics.drawImage(soundImages[0][0], x, y, width, height, null);
    }
}
