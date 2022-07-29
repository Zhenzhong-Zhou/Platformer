package objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static main.Game.SCALE;
import static utilities.Constants.ANIMATE_SPEED;
import static utilities.Constants.GameObject.*;

public class GameObject {
    protected int x, y, objectType;
    protected Rectangle2D.Float hitbox;
    protected boolean doAnimation, active = true;
    protected int animationTick, animationIndex;
    protected int xDrawOffset, yDrawOffset;

    public GameObject(int x, int y, int objectType) {
        this.x = x;
        this.y = y;
        this.objectType = objectType;
    }

    protected void updateAnimationTick() {
        animationTick++;
        if(animationTick >= ANIMATE_SPEED) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteAmount(objectType)) {
                animationIndex = 0;
                if(objectType == BARREL || objectType == BOX) {
                    doAnimation = false;
                    active = false;
                } else if(objectType == CANNON_LEFT || objectType == CANNON_RIGHT) {
                    doAnimation = false;
                }
            }
        }
    }

    public void reset() {
        animationIndex = 0;
        animationTick = 0;
        active = true;

        doAnimation = objectType != BARREL && objectType != BOX && objectType != CANNON_LEFT && objectType != CANNON_RIGHT;
    }

    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * SCALE), (int) (height * SCALE));
    }

    public void drawHitbox(Graphics graphics, int xLevelOffset) {
        // For debugging the hitbox
        graphics.setColor(Color.BLACK);
        graphics.drawRect((int) hitbox.x - xLevelOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    public int getObjectType() {
        return objectType;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }

    public int getXDrawOffset() {
        return xDrawOffset;
    }

    public int getYDrawOffset() {
        return yDrawOffset;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getAnimationTick() {
        return animationTick;
    }
}
