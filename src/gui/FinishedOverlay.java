package gui;

import states.Play;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static main.Game.GAME_WIDTH;
import static main.Game.SCALE;
import static utilities.Constants.GUI.PauseButtons.UTIL_SIZE;
import static utilities.LoadSave.GetSpriteAtlas;
import static utilities.LoadSave.LEVEL_COMPLETE;

public class FinishedOverlay {
    private Play play;
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
        bgW = (int) (image.getWidth()*SCALE);
        bgH = (int) (image.getHeight()*SCALE);
        bgX = GAME_WIDTH/2 - bgW/2;
        bgY = (int) (75*SCALE);
    }

    public void update() {

    }

    public void draw(Graphics graphics) {
        graphics.drawImage(image, bgX, bgY, bgW, bgH, null);
        nextButton.draw(graphics);
        menuButton.draw(graphics);
    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }
}
