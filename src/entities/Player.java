package entities;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.Game.SCALE;
import static utilities.Constants.PlayerActions.*;
import static utilities.HelpMethods.*;
import static utilities.LoadSave.GetSpriteAtlas;
import static utilities.LoadSave.PLAYER_ATLAS;

public class Player extends Entity {
    private final float playerSpeed = 2.0f;
    private final float xDrawOffset = SCALE * 21;
    private final float yDrawOffset = SCALE * 4;
    private final float gravity = 0.04f * SCALE;
    private final float jumpSpeed = - 2.25f * SCALE;
    private final float fallSpeedAfterCollision = 0.5f * SCALE;
    private int animationTick;
    private int animationIndex;
    private final int animationSpeed = 25;
    private BufferedImage[][] animations;
    private int playerAction = IDLE;
    private boolean left, up, right, down, jump;
    private boolean moving = false, attacking = false;
    private int[][] levelData;
    // Jump / Gravity
    private float airSpeed = 0.0f;
    private boolean inAir = false;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, SCALE * 20, SCALE * 27);
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics graphics) {
        graphics.drawImage(animations[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
        drawHitbox(graphics);
    }

    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
            }
        }
    }

    private void setAnimation() {
        int startAnimation = playerAction;

        if(moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if(inAir) {
            if(airSpeed < 0) {
                playerAction = JUMP;
            } else {
                playerAction = FALLING;
            }
        }

        if(attacking) {
            playerAction = ATTACK;
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
        if(! left && ! right && ! inAir) return;

        float xSpeed = 0;

        if(left) {
            xSpeed -= playerSpeed;
        }
        if(right) {
            xSpeed += playerSpeed;
        }

        if(! inAir) {
            if(! IsEntityOnFloor(hitbox, levelData)) {
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

    private void loadAnimations() {
        BufferedImage image = GetSpriteAtlas(PLAYER_ATLAS);

        animations = new BufferedImage[9][6];
        for(int j = 0; j < animations.length; j++) {
            for(int i = 0; i < animations[j].length; i++) {
                animations[j][i] = image.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
        if(! IsEntityOnFloor(hitbox, levelData)) {
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

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
