package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static main.Game.GAME_WIDTH;
import static main.Game.SCALE;
import static utilities.LoadSave.GetSpriteAtlas;
import static utilities.LoadSave.PAUSE_BACKGROUND;

public class PauseOverlay {
    private BufferedImage backgroundImage;
    private int bgX, bgY, bgW, bgH;

    public PauseOverlay() {
        loadBackground();
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
        graphics.drawImage(backgroundImage, bgX, bgY, bgW, bgH, null);
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
