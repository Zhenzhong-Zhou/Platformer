package main;

import javax.swing.*;
import java.awt.*;

public class Scene extends JPanel {
    public Scene() {

    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawRect(10, 10, 200, 100);
        graphics.fillRect(10, 10, 200, 100);
    }
}
