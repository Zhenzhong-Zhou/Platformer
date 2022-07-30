package states;

import gui.AudioOptions;
import gui.PauseButtons;
import gui.UtilButtons;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static main.Game.*;
import static states.GameStates.MENU;
import static utilities.Constants.GUI.PauseButtons.UTIL_SIZE;
import static utilities.LoadSave.*;

public class Settings extends State implements StateMethods {
    private final AudioOptions audioOptions;
    private BufferedImage backgroundImage, optionsBackgroundImage;
    private int bgX, bgY, bgW, bgH;
    private UtilButtons menu;

    public Settings(Game game) {
        super(game);
        loadImages();
        loadButton();
        audioOptions = game.getAudioOptions();
    }

    private void loadImages() {
        backgroundImage = GetSpriteAtlas(MENU_BACKGROUND_IMAGE);
        optionsBackgroundImage = GetSpriteAtlas(OPTIONS_MENU);

        bgW = (int) (optionsBackgroundImage.getWidth() * SCALE);
        bgH = (int) (optionsBackgroundImage.getHeight() * SCALE);
        bgX = GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (33 * SCALE);
    }

    private void loadButton() {
        int menuX = (int) (387 * Game.SCALE);
        int menuY = (int) (325 * Game.SCALE);

        menu = new UtilButtons(menuX, menuY, UTIL_SIZE, UTIL_SIZE, 2);
    }

    @Override
    public void update() {
        menu.update();
        audioOptions.update();
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(backgroundImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        graphics.drawImage(optionsBackgroundImage, bgX, bgY, bgW, bgH, null);

        menu.draw(graphics);
        audioOptions.draw(graphics);
    }

    private boolean isSelectedButton(MouseEvent e, PauseButtons pauseButton) {
        return pauseButton.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(isSelectedButton(e, menu)) {
            menu.setMousePressed(true);
        } else {
            audioOptions.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(isSelectedButton(e, menu)) {
            if(menu.isMousePressed()) {
                GameStates.gameStates = MENU;
            }
        } else {
            audioOptions.mouseReleased(e);
        }

        menu.restBooleans();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menu.setMouseHover(false);

        if(isSelectedButton(e, menu)) {
            menu.setMouseHover(true);
        } else {
            audioOptions.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            GameStates.gameStates = MENU;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
