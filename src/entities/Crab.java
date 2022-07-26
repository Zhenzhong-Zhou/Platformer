package entities;

import java.awt.geom.Rectangle2D;

import static main.Game.SCALE;
import static utilities.Constants.Directions.RIGHT;
import static utilities.Constants.EnemyConstants.*;

public class Crab extends Enemy {
    private int attackBoxOffsetX;

    public Crab(float x, float y) {
        super(x, y, CRAB_WIDTH, CRAB_HEIGHT, CRAB);
        initHitbox(22, 19);
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (SCALE * 82), (int) (SCALE * 19));
        attackBoxOffsetX = (int) (SCALE * 30);
    }

    public void update(int[][] levelData, Player player) {
        updateBehavior(levelData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }

    public void updateBehavior(int[][] levelData, Player player) {
        if(firstUpdate) {
            firstUpdateCheck(levelData);
        }

        if(inAir) {
            updateInAirCheck(levelData);
        } else {
            switch(state) {
                case IDLE -> {
                    setEnemyStates(RUN);
                }
                case RUN -> {
                    // Patrol
                    if(canSeePlayer(levelData, player)) {
                        turnTowardPlayer(player);
                        if(isPlayerCloseForAttack(player)) {
                            setEnemyStates(ATTACK);
                        }
                    }
                    patrol(levelData);
                }
                case ATTACK -> {
                    if(animationIndex == 0) {
                        attackChecked = false;
                    }
                    if(animationIndex == 3 && ! attackChecked) {
                        checkPlayerHit(attackBox, player);
                    }
                }
                case HIT -> {

                }
                default -> {
                }
            }
        }
    }

    public int flipX() {
        if(walkDirection == RIGHT) {
            return width;
        } else {
            return 0;
        }
    }

    public int flipW() {
        if(walkDirection == RIGHT) {
            return - 1;
        } else {
            return 1;
        }
    }
}
