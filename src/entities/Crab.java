package entities;

import static utilities.Constants.EnemyConstants.*;

public class Crab extends Enemy{
    public Crab(float x, float y) {
        super(x, y, CRAB_WIDTH, CRAB_HEIGHT, CRABBY);
    }
}
