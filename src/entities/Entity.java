package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static main.Game.SCALE;

public abstract class Entity {
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int animationTick, animationIndex;
    protected int state;
    protected float airSpeed;
    protected boolean inAir = false;
    protected int maxHealth;
    protected int currentHealth;
    protected Rectangle2D.Float attackBox;
    protected float walkSpeed = 0.9f * SCALE;
    protected boolean attackChecked;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawAttackBox(Graphics graphics, int xLevelOffset) {
        graphics.setColor(Color.RED);
        graphics.drawRect((int) (attackBox.x - xLevelOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    protected void drawHitbox(Graphics graphics, int xLevelOffset) {
        // For debugging the hitbox
        graphics.setColor(Color.PINK);
        graphics.drawRect((int) hitbox.x - xLevelOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * SCALE), (int) (height * SCALE));
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public int getStates() {
        return state;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }
}
