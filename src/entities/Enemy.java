package entities;

import static main.Game.SCALE;
import static utilities.Constants.Directions.LEFT;
import static utilities.Constants.Directions.RIGHT;
import static utilities.Constants.EnemyConstants.*;
import static utilities.HelpMethods.*;

public abstract class Enemy extends Entity {
    private final int enemyType;
    private final int animationSpeed = 25;
    private int animationIndex;
    private int enemyStates;
    private int animationTick;
    private boolean firstUpdate = true;
    private boolean inAir;
    private float fallSpeed;
    private final float gravity = SCALE * 0.04f;
    private int walkDirection = LEFT;
    private final float walkSpeed = 0.35f * SCALE;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteAmount(enemyType, enemyStates)) {
                animationIndex = 0;
            }
        }
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

    private void changeWalkDirection() {
        if(walkDirection == LEFT) {
            walkDirection = RIGHT;
        } else {
            walkDirection = LEFT;
        }
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public void setAnimationIndex(int animationIndex) {
        this.animationIndex = animationIndex;
    }

    public int getEnemyStates() {
        return enemyStates;
    }

    public void setEnemyStates(int enemyStates) {
        this.enemyStates = enemyStates;
    }
}
