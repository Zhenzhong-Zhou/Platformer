package entities;

import states.Play;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static main.Game.SCALE;
import static utilities.Constants.PlayerActions.*;
import static utilities.Constants.PlayerStatusBar.*;
import static utilities.HelpMethods.*;
import static utilities.LoadSave.*;

public class Player extends Entity {
    private final float playerSpeed = 1.0f * SCALE;
    private final float xDrawOffset = SCALE * 21;
    private final float yDrawOffset = SCALE * 4;
    private final float gravity = 0.04f * SCALE;
    private final float jumpSpeed = - 2.25f * SCALE;
    private final float fallSpeedAfterCollision = 0.5f * SCALE;
    private final int animationSpeed = 25;
    private final int maxHealth = 100;
    private final Play play;
    private int animationTick;
    private int animationIndex;
    private BufferedImage[][] animations;
    private int playerAction = IDLE;
    private boolean left, up, right, down, jump;
    private boolean moving = false, attacking = false;
    private int[][] levelData;
    private float airSpeed = 0.0f;
    private boolean inAir = false;
    private BufferedImage statusBarImage;
    private int currentHealth = maxHealth;
    private int healthWidth = HP_BAR_WIDTH;
    private Rectangle2D.Float attackBox;
    private int flipX = 0;
    private int flipW = 1;
    private boolean attackChecked;

    public Player(float x, float y, int width, int height, Play play) {
        super(x, y, width, height);
        this.play = play;
        loadAnimations();
        initHitbox(x, y, (int) (SCALE * 20), (int) (SCALE * 27)); // (int) (SCALE * 20)
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (SCALE * 20), (int) (SCALE * 20));
    }

    public void update() {
        updateHealthBar();
        if(currentHealth <= 0) {
            play.setGameOver(true);
            return;
        }
        updateAttackBox();
        updatePosition();
        if(attacking) {
            checkAttack();
        }
        updateAnimationTick();
        setAnimation();
    }

    private void checkAttack() {
        if(attackChecked || animationIndex != 1) {
            return;
        }
        attackChecked = true;
        play.checkEnemyHit(attackBox);
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
        graphics.drawImage(animations[playerAction][animationIndex],
                (int) (hitbox.x - xDrawOffset) - levelOffset + flipX, (int) (hitbox.y - yDrawOffset),
                width * flipW, height, null);
        drawAttackBox(graphics, levelOffset);
        drawHitbox(graphics, levelOffset);
    }

    private void drawAttackBox(Graphics graphics, int levelOffset) {
        graphics.setColor(Color.BLACK);
        graphics.drawRect((int) attackBox.x - levelOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    private void drawStatusBar(Graphics graphics) {
        graphics.drawImage(statusBarImage, STATUS_BAR_X, STATUS_BAR_Y,
                STATUS_BAR_WIDTH, STATUS_BAR_HEIGHT, null);
        graphics.setColor(Color.RED);
        graphics.fillRect(HP_BAR_X_START + STATUS_BAR_X, HP_BAR_Y_START + STATUS_BAR_Y, healthWidth, HP_BAR_HEIGHT);
    }

    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    private void setAnimation() {
        int startAnimation = playerAction;

        if(moving) {
            playerAction = RUN;
        } else {
            playerAction = IDLE;
        }

        if(inAir) {
            if(airSpeed < 0) {
                playerAction = JUMP;
            } else {
                playerAction = FALL;
            }
        }

        if(attacking) {
            playerAction = ATTACK;
            if(startAnimation != ATTACK) {
                animationIndex = 1;
                animationTick = 0;
                return;
            }
        }

        if(startAnimation != playerAction) {
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
            xSpeed -= playerSpeed;
            flipX = width;
            flipW = - 1;
        }
        if(right) {
            xSpeed += playerSpeed;
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
                airSpeed += gravity;
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
        up = false;
        down = false;
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
        playerAction = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;
        attackBox.x = x;
        attackBox.y = y;

        if(IsEntityOnFloor(hitbox, levelData)) {
            inAir = true;
        }
    }
}
