package entities;

import static main.Game.SCALE;
import static utilities.Constants.Directions.LEFT;
import static utilities.Constants.Directions.RIGHT;
import static utilities.Constants.EnemyConstants.*;
import static utilities.HelpMethods.*;

public abstract class Enemy extends Entity {
    protected final int enemyType;
    protected final int animationSpeed = 25;
    protected int animationIndex;
    protected int enemyStates;
    protected int animationTick;
    protected boolean firstUpdate = true;
    protected boolean inAir;
    protected float fallSpeed;
    protected final float gravity = SCALE * 0.04f;
    protected int walkDirection = LEFT;
    protected final float walkSpeed = 0.35f * SCALE;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    protected void firstUpdateCheck(int[][] levelData) {
        if(IsEntityOnFloor(hitbox, levelData)) {
            inAir = true;
        }
        firstUpdate = false;
    }

    protected void updateInAirCheck(int[][] levelData) {
        if(CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, levelData)) {
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = GetEntityYPositionUnderRoofOrAboveFloor(hitbox, fallSpeed);
        }
    }

    protected void patrol(int[][] levelData) {
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

    protected void updateAnimationTick() {
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteAmount(enemyType, enemyStates)) {
                animationIndex = 0;
            }
        }
    }

    protected void changeWalkDirection() {
        if(walkDirection == LEFT) {
            walkDirection = RIGHT;
        } else {
            walkDirection = LEFT;
        }
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyStates() {
        return enemyStates;
    }
}
