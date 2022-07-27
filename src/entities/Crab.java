package entities;

import static main.Game.SCALE;
import static utilities.Constants.EnemyConstants.*;

public class Crab extends Enemy {
    public Crab(float x, float y) {
        super(x, y, CRAB_WIDTH, CRAB_HEIGHT, CRAB);
        initHitbox(x, y, (int) (SCALE * 22), (int) (SCALE * 19));
    }

    public void update(int[][] levelData) {
        updateMove(levelData);
        updateAnimationTick();
    }

    public void updateMove(int[][] levelData) {
        if(firstUpdate) {
            firstUpdateCheck(levelData);
        }

        if(inAir) {
            updateInAirCheck(levelData);
        } else {
            switch(enemyStates) {
                case IDLE -> {
                    enemyStates = RUN;
                }
                case RUN -> {
                    // Patrol
                    patrol(levelData);
                }
                default -> {

                }
            }
        }
    }
}
