package entities;

public abstract class Enemy extends Entity{
    private int animationIndex, enemyStates, enemyType;
    private int animationTick, animationSpeed = 25;

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
            if(animationIndex >= 9999) {
                animationIndex = 0;
            }
        }
    }

    public void update() {
        updateAnimationTick();
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
