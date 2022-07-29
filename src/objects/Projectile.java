package objects;

import java.awt.geom.Rectangle2D;

import static main.Game.SCALE;
import static utilities.Constants.Projectiles.*;

public class Projectile {
    private final Rectangle2D.Float hitbox;
    private final int direction;
    private boolean active = true;

    public Projectile(int x, int y, int direction) {
        int XOffset = (int) (- 3 * SCALE);
        int YOffset = (int) (5 * SCALE);

        if(direction == 1) {
            XOffset = (int) (29 * SCALE);
        }
        this.direction = direction;
        hitbox = new Rectangle2D.Float(x + XOffset, y + YOffset, CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT);
    }

    public void updatePosition() {
        hitbox.x += direction * SPEED;
    }

    public void setPosition(int x, int y) {
        hitbox.x = x;
        hitbox.y = y;
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
}
