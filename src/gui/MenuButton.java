package gui;

import states.GameStates;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilities.Constants.GUI.Buttons.*;
import static utilities.LoadSave.GetSpriteAtlas;
import static utilities.LoadSave.MENU_BUTTONS;

public class MenuButton {
    private int xPosition, yPosition, rowIndex, index;
    private int xOffsetCenter = BUTTON_WIDTH / 2;
    private GameStates gameStates;
    private BufferedImage[] images;
    private boolean mouseHover, mousePressed;
    private Rectangle bounds;

    public MenuButton(int xPosition, int yPosition, int rowIndex, GameStates gameStates) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.rowIndex = rowIndex;
        this.gameStates = gameStates;

        loadImages();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPosition - xOffsetCenter, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    private void loadImages() {
        images = new BufferedImage[3];
        BufferedImage temp = GetSpriteAtlas(MENU_BUTTONS);

        for(int i=0;i<images.length;i++){
            images[i] = temp.getSubimage(BUTTON_DEFAULT_WIDTH * i, BUTTON_DEFAULT_HEIGHT * rowIndex, BUTTON_DEFAULT_WIDTH, BUTTON_DEFAULT_HEIGHT);
        }
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(images[index], xPosition-xOffsetCenter, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT, null);
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


    public Rectangle getBounds() {
        return bounds;
    }

    public void setGameStates() {
        GameStates.gameStates = gameStates;
    }

    public void resetBooleans() {
        mouseHover = false;
        mousePressed= false;
    }
}
