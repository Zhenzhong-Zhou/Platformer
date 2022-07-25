package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Scene extends JPanel {
    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private float xDir = 1f, yDir = 0.03f;
    private int frames = 0;
    private long lastCheck = 0;
    private Color color =new Color(150,20,90);
    private Random random;

    public Scene() {
        random = new Random();
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void changeXDelta(int value) {
        this.xDelta += value;
    }

    public void changeYDelta(int value) {
        this.yDelta += value;
    }

    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        updateRectangle();
        graphics.setColor(color);
        graphics.fillRect((int) xDelta, (int) yDelta, 200, 100);
        frames++;
        if(System.currentTimeMillis() - lastCheck >= 1000) {
            lastCheck = System.currentTimeMillis();
            System.out.println("FPS: " + frames);
            frames = 0;
        }
    }

    private void updateRectangle() {
        xDelta+=xDir;
        if(xDelta > 400 || xDelta < 0) {
            xDir*=-1;
            color = getRandColor();
        }
        yDelta+=yDir;
        if(yDelta > 400 || yDelta < 0) {
            yDir*=-1;
            color = getRandColor();
        }
    }

    private Color getRandColor() {
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Color(r, g,b);
    }
}
