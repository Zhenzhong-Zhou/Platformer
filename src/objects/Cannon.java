package objects;

import static main.Game.SCALE;
import static main.Game.TILES_SIZE;

public class Cannon extends GameObject{
    private int tileY;
    public Cannon(int x, int y, int objectType) {
        super(x, y, objectType);
        tileY = y / TILES_SIZE;
        initHitbox(40, 26);
        hitbox.x -= (int) (4*SCALE);
        hitbox.y += (int) (6*SCALE);
    }

    public void update() {
        if(doAnimation) {
            updateAnimationTick();
        }
    }

    public int getTileY() {
        return tileY;
    }
}
