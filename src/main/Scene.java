package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

public class Scene extends JPanel {
    private final MouseInputs mouseInputs;

    public Scene() {
        mouseInputs = new MouseInputs(this);
        setSceneSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setSceneSize() {
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
    }

    public void updateScene() {
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
    }
}
