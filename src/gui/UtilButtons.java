package gui;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilities.Constants.GUI.PauseButtons.UTIL_DEFAULT_SIZE;
import static utilities.Constants.GUI.PauseButtons.UTIL_SIZE;
import static utilities.LoadSave.GetSpriteAtlas;
import static utilities.LoadSave.UTIL_BUTTONS;

public class UtilButtons extends PauseButtons {
    private BufferedImage[] images;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;

    public UtilButtons(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImages();
    }

    private void loadImages() {
        BufferedImage temp = GetSpriteAtlas(UTIL_BUTTONS);
        images = new BufferedImage[3];

        for(int i =0; i< images.length;i++) {
            images[i] = temp.getSubimage(UTIL_DEFAULT_SIZE * i, UTIL_DEFAULT_SIZE * rowIndex, UTIL_DEFAULT_SIZE, UTIL_DEFAULT_SIZE);
        }
    }

    public void update() {
        index = 0;
        if(mouseOver) {
            index = 1;
        }
        if(mousePressed) {
            index = 2;
        }
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(images[index], x, y, UTIL_SIZE, UTIL_SIZE, null);
    }

    public void restBooleans() {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
