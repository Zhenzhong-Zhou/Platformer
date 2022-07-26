package entities;

import static utilities.Constants.EnemyConstants.GetSpriteAmount;

public abstract class Enemy extends Entity {
    private int animationIndex;
    private int enemyStates;
    private final int enemyType;
    private int animationTick;
    private final int animationSpeed = 25;

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
        // Patrol
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
