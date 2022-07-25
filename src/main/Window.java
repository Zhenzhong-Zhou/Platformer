package main;

import javax.swing.*;

public class Window extends JFrame{

    public Window(Scene scene) {
        setTitle("Platformer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(scene);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
