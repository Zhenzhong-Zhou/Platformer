package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class Scene extends JPanel {
    private final MouseInputs mouseInputs;
    private final Game game;

    public Scene(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;
        setSceneSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setSceneSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("Size: " + GAME_WIDTH + ", " + GAME_HEIGHT);
    }

    public void updateScene() {
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        game.render(graphics);
    }

    public Game getGame() {
        return game;
    }
}
