package states;

import gui.MenuButton;
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
    private final MenuButton[] buttons = new MenuButton[3];
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
        buttons[0] = new MenuButton(GAME_WIDTH / 2, (int) (SCALE * 150), 0, PLAY);
        buttons[1] = new MenuButton(GAME_WIDTH / 2, (int) (SCALE * 220), 1, OPTIONS);
        buttons[2] = new MenuButton(GAME_WIDTH / 2, (int) (SCALE * 290), 2, QUIT);
    }

    @Override
    public void update() {
        for(MenuButton menuButton : buttons) {
            menuButton.update();
        }
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(backgroundImage, menuX, menuY, menuWidth, menuHeight, null);
        for(MenuButton menuButton : buttons) {
            menuButton.draw(graphics);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButton menuButton : buttons) {
            if(isSelectedButton(e, menuButton)) {
                menuButton.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButton menuButton : buttons) {
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
        for(MenuButton menuButton : buttons) {
            menuButton.resetBooleans();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton menuButton : buttons) {
            menuButton.setMouseHover(false);
        }

        for(MenuButton menuButton : buttons) {
            if(isSelectedButton(e, menuButton)) {
                menuButton.setMouseHover(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            gameStates = PLAY;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
