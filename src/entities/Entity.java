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
    protected boolean inAir=false;
    protected int maxHealth;
    protected int currentHealth;
    protected Rectangle2D.Float attackBox;
    protected float walkSpeed = 0.9f * SCALE;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawHitbox(Graphics graphics, int xLevelOffset) {
        // For debugging the hitbox
        graphics.setColor(Color.PINK);
        graphics.drawRect((int) hitbox.x - xLevelOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    protected void initHitbox(float x, float y, int width, int height) {    // int width, int height
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public int getStates() {
        return state;
    }
}
