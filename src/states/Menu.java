package states;

import gui.MenuButtons;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static main.Game.GAME_WIDTH;
import static main.Game.SCALE;
import static states.GameStates.*;
import static utilities.LoadSave.GetSpriteAtlas;
import static utilities.LoadSave.MENU_BACKGROUND;

public class Menu extends State implements StateMethods {
    private final MenuButtons[] buttons = new MenuButtons[3];
    private BufferedImage backgroundImage;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
    }

    private void loadBackground() {
        backgroundImage = GetSpriteAtlas(MENU_BACKGROUND);
        menuWidth = (int) (backgroundImage.getWidth() * SCALE);
        menuHeight = (int) (backgroundImage.getHeight() * SCALE);
        menuX = GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (SCALE * 45);
    }

    private void loadButtons() {
        buttons[0] = new MenuButtons(GAME_WIDTH / 2, (int) (SCALE * 150), 0, PLAY);
        buttons[1] = new MenuButtons(GAME_WIDTH / 2, (int) (SCALE * 220), 1, OPTIONS);
        buttons[2] = new MenuButtons(GAME_WIDTH / 2, (int) (SCALE * 290), 2, QUIT);
    }

    @Override
    public void update() {
        for(MenuButtons menuButton : buttons) {
            menuButton.update();
        }
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(backgroundImage, menuX, menuY, menuWidth, menuHeight, null);
        for(MenuButtons menuButton : buttons) {
            menuButton.draw(graphics);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButtons menuButton : buttons) {
            if(isSelectedButton(e, menuButton)) {
                menuButton.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButtons menuButton : buttons) {
            if(isSelectedButton(e, menuButton)) {
                if(menuButton.isMousePressed()) {
                    menuButton.setGameStates();
                    break;
                }
            }
        }
        restButtons();
    }

    private void restButtons() {
        for(MenuButtons menuButton : buttons) {
            menuButton.resetBooleans();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButtons menuButton : buttons) {
            menuButton.setMouseHover(false);
        }

        for(MenuButtons menuButton : buttons) {
            if(isSelectedButton(e, menuButton)) {
                menuButton.setMouseHover(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
