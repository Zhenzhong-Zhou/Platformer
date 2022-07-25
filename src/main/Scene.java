package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilities.Constants.Directions.*;
import static utilities.Constants.PlayerActions.*;

public class Scene extends JPanel {
    private final MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage image;
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean moving = false;

    public Scene() {
        mouseInputs = new MouseInputs(this);
        importImage();
        loadAnimations();
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
        } finally {
            try {
                assert is != null;
                is.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadAnimations() {
        animations = new BufferedImage[9][6];
        for(int j = 0; j < animations.length; j++) {
            for(int i = 0; i < animations[j].length; i++) {
                animations[j][i] = image.getSubimage(i*64, j*40, 64, 40);
            }
        }
    }

    private void setSceneSize() {
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
    }

    public void setDirection(int direction) {
        this.playerDirection = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
            }
        }
    }

    private void setAnimation() {
        if(moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }

    private void updatePosition() {
        if(moving) {
            switch(playerDirection) {
                case LEFT -> xDelta -= 5;
                case UP -> yDelta -= 5;
                case RIGHT -> xDelta += 5;
                case DOWN -> yDelta += 5;
                default -> {
                }
            }
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        updateAnimationTick();
        setAnimation();
        updatePosition();

        graphics.drawImage(animations[playerAction][animationIndex], (int) xDelta, (int) yDelta, 256, 160, null);
    }
}
