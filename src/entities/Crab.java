package entities;

import static main.Game.SCALE;
import static utilities.Constants.EnemyConstants.*;

public class Crab extends Enemy {
    public Crab(float x, float y) {
        super(x, y, CRAB_WIDTH, CRAB_HEIGHT, CRAB);
        initHitbox(x, y, (int) (SCALE * 22), (int) (SCALE * 19));
    }

    public void update(int[][] levelData, Player player) {
        updateMove(levelData, player);
        updateAnimationTick();
    }

    public void updateMove(int[][] levelData, Player player) {
        if(firstUpdate) {
            firstUpdateCheck(levelData);
        }

        if(inAir) {
            updateInAirCheck(levelData);
        } else {
            switch(enemyStates) {
                case IDLE -> {
                    setEnemyStates(RUN);
                }
                case RUN -> {
                    // Patrol
                    if(canSeePlayer(levelData, player)) {
                        turnTowardPlayer(player);
                    }
                    if(isPlayerCloseForAttack(player)) {
                        setEnemyStates(ATTACK);
                    }
                    patrol(levelData);
                }
                default -> {

                }
            }
        }
    }
}
