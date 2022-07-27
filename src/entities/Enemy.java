package entities;

import java.awt.geom.Rectangle2D;

import static main.Game.SCALE;
import static main.Game.TILES_SIZE;
import static utilities.Constants.ANIMATE_SPEED;
import static utilities.Constants.Directions.LEFT;
import static utilities.Constants.Directions.RIGHT;
import static utilities.Constants.EnemyConstants.*;
import static utilities.Constants.GRAVITY;
import static utilities.HelpMethods.*;

public abstract class Enemy extends Entity {
    protected final int enemyType;
    protected boolean firstUpdate = true;
    protected int walkDirection = LEFT;
    protected int tileY;
    protected float attackRange = TILES_SIZE;
    protected boolean active = true;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
        walkSpeed = 0.35f * SCALE;
    }

    protected void firstUpdateCheck(int[][] levelData) {
        if(IsEntityOnFloor(hitbox, levelData)) {
            inAir = true;
        }
        firstUpdate = false;
    }

    protected void updateInAirCheck(int[][] levelData) {
        if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        } else {
            inAir = false;
            hitbox.y = GetEntityYPositionUnderRoofOrAboveFloor(hitbox, airSpeed);
            tileY = (int) (hitbox.y / TILES_SIZE);
        }
    }

    protected void patrol(int[][] levelData) {
        float xSpeed;

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
        if(player.hitbox.x > hitbox.x) {
            walkDirection = RIGHT;
        } else {
            walkDirection = LEFT;
        }
    }

    protected boolean canSeePlayer(int[][] levelData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / TILES_SIZE);
        if(playerTileY == tileY) {
            if(isPlayerInRange(player)) {
                return IsSightClear(levelData, hitbox, player.hitbox, tileY);
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

    public void hurt(int amount) {
        currentHealth -= amount;
        if(currentHealth <= 0) {
            setEnemyStates(DEAD);
        } else {
            setEnemyStates(HIT);
        }
    }

    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if(attackBox.intersects(player.hitbox)) {
            player.changeHealth(- GetEnemyDamageCheck(enemyType));
        }
        attackChecked = true;
    }

    protected void updateAnimationTick() {
        animationTick++;
        if(animationTick >= ANIMATE_SPEED) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteAmount(enemyType, state)) {
                animationIndex = 0;
                switch(state) {
                    case ATTACK, HIT -> state = IDLE;
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

    protected void setEnemyStates(int enemyStates) {
        this.state = enemyStates;
        animationTick = 0;
        animationIndex = 0;
    }

    public boolean isActive() {
        return active;
    }

    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        setEnemyStates(IDLE);
        active = true;
        airSpeed = 0;
    }
}
