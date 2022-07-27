package entities;

import java.awt.geom.Rectangle2D;

import static main.Game.SCALE;
import static main.Game.TILES_SIZE;
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
    protected int tileY;
    protected float attackRange = TILES_SIZE;
    protected int maxHealth;
    protected int currentHealth;
    protected boolean active = true;
    protected boolean attackChecked;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
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
            tileY = (int) (hitbox.y/TILES_SIZE);
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

    protected void turnTowardPlayer(Player player) {
        if(player.hitbox.x> hitbox.x) {
            walkDirection = RIGHT;
        }else {
            walkDirection =LEFT;
        }
    }

    protected boolean canSeePlayer(int[][] levelData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / TILES_SIZE);
        if(playerTileY == tileY) {
            if(isPlayerInRange(player)) {
                if(IsSightClear(levelData, hitbox, player.hitbox, tileY)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackRange * 5;
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackRange;
    }

    protected void setEnemyStates(int enemyStates) {
        this.enemyStates = enemyStates;
        animationTick = 0;
        animationIndex = 0;
    }

    public void hurt(int amount) {
        currentHealth -= amount;
        if(currentHealth<=0){
            setEnemyStates(DEAD);
        }else {
            setEnemyStates(HIT);
        }
    }

    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if(attackBox.intersects(player.hitbox)) {
            player.changeHealth(-GetEnemyDamageCheck(enemyType));
        }
        attackChecked = true;
    }

    protected void updateAnimationTick() {
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteAmount(enemyType, enemyStates)) {
                animationIndex = 0;
                switch(enemyStates) {
                    case ATTACK, HIT -> enemyStates = IDLE;
                    case DEAD -> active = false;
                }
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

    public boolean isActive() {
        return active;
    }

    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y =y;
        firstUpdate = true;
        currentHealth = maxHealth;
        setEnemyStates(IDLE);
        active = true;
        fallSpeed =0;
    }
}
