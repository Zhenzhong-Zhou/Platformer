package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Scene extends JPanel {
    private final MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage image, subImage;

    public Scene() {
        mouseInputs = new MouseInputs(this);
        importImage();
        setSceneSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void importImage() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");
        try {
            assert is != null;
            image = ImageIO.read(is);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void setSceneSize() {
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
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
        subImage = image.getSubimage(64, 8*40, 64, 40);
//        graphics.drawImage(image.getSubimage(0, 0, 64, 40), 0, 0, null);
        graphics.drawImage(subImage, (int) xDelta, (int) yDelta, 128, 80, null);
    }
}
