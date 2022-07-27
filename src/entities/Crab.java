package entities;

import static main.Game.SCALE;
import static utilities.Constants.Directions.LEFT;
import static utilities.Constants.EnemyConstants.*;
import static utilities.HelpMethods.*;

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
            if(!IsEntityOnFloor(hitbox, levelData)) {
                inAir = true;
            }
            firstUpdate = false;
        }

        if(inAir) {
            if(CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += fallSpeed;
                fallSpeed += gravity;
            } else {
                inAir = false;
                hitbox.y = GetEntityYPositionUnderRoofOrAboveFloor(hitbox, fallSpeed);
            }
        } else {
            // Patrol
            switch(enemyStates) {
                case IDLE -> {
                    enemyStates = RUN;
                }
                case RUN -> {
                    float xSpeed = 0;

                    if(walkDirection == LEFT) {
                        xSpeed = - walkSpeed;
                    } else {
                        xSpeed = walkSpeed;
                    }

                    if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                        if(IsFloor(hitbox, xSpeed, levelData)) {
                            hitbox.x += xSpeed;
                            return;
                        }
                    }
                    changeWalkDirection();
                }
                default -> {

                }
            }
        }
    }
}
