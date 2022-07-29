package entities;

import states.Play;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static main.Game.SCALE;
import static main.Game.TILES_SIZE;
import static utilities.Constants.ANIMATE_SPEED;
import static utilities.Constants.GRAVITY;
import static utilities.Constants.PlayerActions.*;
import static utilities.Constants.PlayerStatusBar.*;
import static utilities.HelpMethods.*;
import static utilities.LoadSave.*;

public class Player extends Entity {
    private final float xDrawOffset = SCALE * 21;
    private final float yDrawOffset = SCALE * 4;
    private final float jumpSpeed = - 2.25f * SCALE;
    private final float fallSpeedAfterCollision = 0.5f * SCALE;
    private final Play play;
    private int tileY = 0;
    private BufferedImage[][] animations;
    private boolean left, right, jump;
    private boolean moving = false, attacking = false;
    private int[][] levelData;
    private BufferedImage statusBarImage;
    private int healthWidth = HP_BAR_WIDTH;
    private int flipX = 0;
    private int flipW = 1;

    public Player(float x, float y, int width, int height, Play play) {
        super(x, y, width, height);
        this.play = play;
        this.state = IDLE;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.walkSpeed = 0.9f * SCALE;
        loadAnimations();
        initHitbox(20, 27);
        initAttackBox();
    }

    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x = x;
        hitbox.y = y;
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (SCALE * 20), (int) (SCALE * 20));
    }

    public void update() {
        updateHealthBar();
        if(currentHealth <= 0) {
            if(state != DEAD) {
                state = DEAD;
                animationTick =0 ;
                animationIndex = 0;
                play.setPlayerDead(true);
            } else if(animationIndex == GetSpriteAmount(DEAD - 1) && animationTick >= ANIMATE_SPEED - 1) {
                play.setGameOver(true);
            } else {
                updateAnimationTick();
            }
            return;
        }
        updateAttackBox();
        updatePosition();
        if(moving) {
            checkPotionTouched();
            checkSpikeTouched();
            tileY = (int) (hitbox.y / TILES_SIZE);
        }
        if(attacking) {
            checkAttack();
        }
        updateAnimationTick();
        setAnimation();
    }

    private void checkSpikeTouched() {
        play.checkSpikeTouched(this);
    }

    private void checkPotionTouched() {
        play.checkPotionTouched(hitbox);
    }

    private void checkAttack() {
        if(attackChecked || animationIndex != 1) {
            return;
        }
        attackChecked = true;
        play.checkEnemyHit(attackBox);
        play.checkObjectHit(attackBox);
    }

    private void updateAttackBox() {
        if(right) {
            attackBox.x = hitbox.x + hitbox.width + (int) (SCALE * 10);
        } else if(left) {
            attackBox.x = hitbox.x - hitbox.width - (int) (SCALE * 10);
        }
        attackBox.y = hitbox.y + (SCALE * 10);
    }

    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * HP_BAR_WIDTH);
    }

    public void render(Graphics graphics, int levelOffset) {
        drawStatusBar(graphics);
        graphics.drawImage(animations[state][animationIndex],
                (int) (hitbox.x - xDrawOffset) - levelOffset + flipX, (int) (hitbox.y - yDrawOffset),
                width * flipW, height, null);
        drawAttackBox(graphics, levelOffset);
        drawHitbox(graphics, levelOffset);
    }

    private void drawStatusBar(Graphics graphics) {
        graphics.drawImage(statusBarImage, STATUS_BAR_X, STATUS_BAR_Y,
                STATUS_BAR_WIDTH, STATUS_BAR_HEIGHT, null);
        graphics.setColor(Color.RED);
        graphics.fillRect(HP_BAR_X_START + STATUS_BAR_X, HP_BAR_Y_START + STATUS_BAR_Y, healthWidth, HP_BAR_HEIGHT);
    }

    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= ANIMATE_SPEED) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteAmount(state)) {
                animationIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    private void setAnimation() {
        int startAnimation = state;

        if(moving) {
            state = RUN;
        } else {
            state = IDLE;
        }

        if(inAir) {
            if(airSpeed < 0) {
                state = JUMP;
            } else {
                state = FALL;
            }
        }

        if(attacking) {
            state = ATTACK;
            if(startAnimation != ATTACK) {
                animationIndex = 1;
                animationTick = 0;
                return;
            }
        }

        if(startAnimation != state) {
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void updatePosition() {
        moving = false;
        if(jump) {
            jump();
        }

        if(! inAir) {
            if((! left && ! right) || (right && left)) return;
        }

        float xSpeed = 0;

        if(left) {
            xSpeed -= walkSpeed;
            flipX = width;
            flipW = - 1;
        }
        if(right) {
            xSpeed += walkSpeed;
            flipX = 0;
            flipW = 1;
        }

        if(! inAir) {
            if(IsEntityOnFloor(hitbox, levelData)) {
                inAir = true;
            }
        }

        if(inAir) {
            if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += GRAVITY;
                updateXPosition(xSpeed);
            } else {
                hitbox.y = GetEntityYPositionUnderRoofOrAboveFloor(hitbox, airSpeed);
                if(airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPosition(xSpeed);
            }
        } else {
            updateXPosition(xSpeed);
        }
        moving = true;
    }

    private void jump() {
        if(inAir) return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPosition(float xSpeed) {
        if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPositionNextToWall(hitbox, xSpeed);
        }
    }

    public void changeHealth(int value) {
        currentHealth += value;
        if(currentHealth <= 0) {
            currentHealth = 0;
            // Game Over
            // gameOver();
        } else if(currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
    }

    public void kill() {
        currentHealth = 0;
    }

    public void changePower(int value) {
        System.out.println("Add power!");
    }

    private void loadAnimations() {
        statusBarImage = GetSpriteAtlas(PLAYER_STATUS_BAR);
        BufferedImage image = GetSpriteAtlas(PLAYER_ATLAS);

        animations = new BufferedImage[7][8];
        for(int j = 0; j < animations.length; j++) {
            for(int i = 0; i < animations[j].length; i++) {
                animations[j][i] = image.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
        if(IsEntityOnFloor(hitbox, levelData)) {
            inAir = true;
        }
    }

    public void resetDirectionBoolean() {
        left = false;
        right = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetAll() {
        resetDirectionBoolean();
        inAir = false;
        attacking = false;
        moving = false;
        state = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;
        attackBox.x = x;
        attackBox.y = y;

        if(IsEntityOnFloor(hitbox, levelData)) {
            inAir = true;
        }
    }

    public int getTileY() {
        return tileY;
    }
}
