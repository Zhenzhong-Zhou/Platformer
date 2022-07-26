package gui;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilities.Constants.GUI.PauseButtons.*;
import static utilities.LoadSave.GetSpriteAtlas;
import static utilities.LoadSave.VOLUME_BUTTONS;

public class VolumeButtons extends PauseButtons {
    private final int minX;
    private final int maxX;
    private BufferedImage[] images;
    private BufferedImage slider;
    private int index = 0;
    private boolean mouseHover, mousePressed;
    private int buttonX;
    private float floatValue = 0f;

    public VolumeButtons(int x, int y, int width, int height) {
        super(x + width / 2, y, VOLUME_WIDTH, height);
        bounds.x -= VOLUME_WIDTH / 2;
        this.x = x;
        this.width = width;
        buttonX = x + width / 2;
        minX = x + VOLUME_WIDTH / 2;
        maxX = x + width - VOLUME_WIDTH / 2;
        loadImages();
    }

    private void loadImages() {
        BufferedImage temp = GetSpriteAtlas(VOLUME_BUTTONS);
        images = new BufferedImage[3];
        for(int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(VOLUME_DEFAULT_WIDTH * i, 0, VOLUME_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
        }

        slider = temp.getSubimage(VOLUME_DEFAULT_WIDTH * 3, 0, SLIDER_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
    }

    public void update() {
        index = 0;
        if(mouseHover) {
            index = 1;
        }
        if(mousePressed) {
            index = 2;
        }
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(slider, x, y, width, height, null);
        graphics.drawImage(images[index], buttonX - VOLUME_WIDTH / 2, y, VOLUME_WIDTH, height, null);
    }

    public void changeX(int x) {
        if(x < minX) {
            buttonX = minX;
        } else buttonX = Math.min(x, maxX);
        updateFloatValue();
        bounds.x = buttonX - VOLUME_WIDTH / 2;
    }

    private void updateFloatValue() {
        float range = maxX - minX;
        float value = buttonX - minX;
        floatValue = value / range;
    }

    public void restBooleans() {
        mouseHover = false;
        mousePressed = false;
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

    public float getFloatValue() {
        return floatValue;
    }
}
