package entities;

import static main.Game.SCALE;
import static utilities.Constants.EnemyConstants.*;

public class Crab extends Enemy {
    public Crab(float x, float y) {
        super(x, y, CRAB_WIDTH, CRAB_HEIGHT, CRAB);
        initHitbox(x, y, (int) (SCALE * 22), (int) (SCALE * 19));
    }
}
