package main;

import javax.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Window {
    private JFrame jFrame;

    public Window(Scene scene) {
        jFrame = new JFrame();
        jFrame.setSize(400, 400);
        jFrame.setTitle("Platformer");
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.add(scene);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
