package gui;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilities.Constants.GUI.PauseButtons.URM_DEFAULT_SIZE;
import static utilities.Constants.GUI.PauseButtons.URM_SIZE;
import static utilities.LoadSave.GetSpriteAtlas;
import static utilities.LoadSave.URM_BUTTONS;

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
        BufferedImage temp = GetSpriteAtlas(URM_BUTTONS);
        images = new BufferedImage[3];

        for(int i =0; i< images.length;i++) {
            images[i] = temp.getSubimage(URM_DEFAULT_SIZE * i, URM_DEFAULT_SIZE * rowIndex, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
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
        graphics.drawImage(images[index], x, y, URM_SIZE, URM_SIZE, null);
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
