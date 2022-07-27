package entities;

import static main.Game.SCALE;
import static utilities.Constants.Directions.LEFT;
import static utilities.Constants.Directions.RIGHT;
import static utilities.Constants.EnemyConstants.*;

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
