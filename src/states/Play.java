package states;

import entities.EnemyManager;
import entities.Player;
import gui.DeathOverlay;
import gui.FinishedOverlay;
import gui.PauseOverlay;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import static main.Game.*;
import static utilities.Constants.Environment.*;
import static utilities.LoadSave.*;

public class Play extends State implements StateMethods {
    private final int leftBorder = (int) (GAME_WIDTH * 0.2);
    private final int rightBorder = (int) (GAME_WIDTH * 0.8);
    private final BufferedImage backgroundImage;
    private final BufferedImage bigCloudsImage;
    private final BufferedImage smallCloudsImage;
    private final int[] smallCloudsPosition;
    private final Random random = new Random();
    private int maxLevelOffsetX;
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private PauseOverlay pauseOverlay;
    private DeathOverlay deathOverlay;
    private FinishedOverlay finishedOverlay;
    private boolean paused = false;
    private int xLevelOffset;
    private boolean gameOver;
    private boolean finishedLevel;

    public Play(Game game) {
        super(game);
        initClasses();
        backgroundImage = GetSpriteAtlas(PLAY_BG_IMAGE);
        bigCloudsImage = GetSpriteAtlas(BIG_CLOUDS);
        smallCloudsImage = GetSpriteAtlas(SMALL_CLOUDS);
        smallCloudsPosition = new int[8];
        for(int i = 0; i < smallCloudsPosition.length; i++) {
            smallCloudsPosition[i] = (int) (SCALE * 90) + random.nextInt((int) (SCALE * 100));
        }
        calculateOffsets();
        loadStartLevel();
    }

    public void loadNextLevel() {
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    private void calculateOffsets() {
        maxLevelOffsetX = levelManager.getCurrentLevel().getMaxLevelOffsetX();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);

        player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE), this);
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        pauseOverlay = new PauseOverlay(this);
        deathOverlay = new DeathOverlay(this);
        finishedOverlay = new FinishedOverlay(this);
    }

    @Override
    public void update() {
        if(paused) {
            pauseOverlay.update();
        } else if(finishedLevel) {
            finishedOverlay.update();
        } else if(! gameOver) {
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            objectManager.update();
            checkCloseToBorder();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int difference = playerX - xLevelOffset;

        if(difference > rightBorder) {
            xLevelOffset += difference - rightBorder;
        } else if(difference < leftBorder) {
            xLevelOffset += difference - leftBorder;
        }

        if(xLevelOffset > maxLevelOffsetX) {
            xLevelOffset = maxLevelOffsetX;
        } else if(xLevelOffset < 0) {
            xLevelOffset = 0;
        }
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(backgroundImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        drawClouds(graphics);
        levelManager.draw(graphics, xLevelOffset);
        player.render(graphics, xLevelOffset);
        enemyManager.draw(graphics, xLevelOffset);
        objectManager.draw(graphics, xLevelOffset);

        if(paused) {
            graphics.setColor(new Color(0, 0, 0, 150));
            graphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            pauseOverlay.draw(graphics);
        } else if(gameOver) {
            deathOverlay.draw(graphics);
        } else if(finishedLevel) {
            finishedOverlay.draw(graphics);
        }
    }

    private void drawClouds(Graphics graphics) {
        for(int i = 0; i < 3; i++) {
            graphics.drawImage(bigCloudsImage, BIG_CLOUDS_WIDTH * i - (int) (xLevelOffset * 0.3),
                    (int) (SCALE * 204), BIG_CLOUDS_WIDTH, BIG_CLOUDS_HEIGHT, null);
        }

        for(int i = 0; i < smallCloudsPosition.length; i++) {
            graphics.drawImage(smallCloudsImage, SMALL_CLOUDS_WIDTH * 4 * i - (int) (xLevelOffset * 0.7),
                    smallCloudsPosition[i], SMALL_CLOUDS_WIDTH, SMALL_CLOUDS_HEIGHT, null);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if(! gameOver) {
            if(paused) {
                pauseOverlay.mouseDragged(e);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(! gameOver) {
            if(e.getButton() == MouseEvent.BUTTON1) {
                player.setAttacking(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(! gameOver) {
            if(paused) {
                pauseOverlay.mousePressed(e);
            } else if(finishedLevel) {
                finishedOverlay.mousePressed(e);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(! gameOver) {
            if(paused) {
                pauseOverlay.mouseReleased(e);
            } else if(finishedLevel) {
                finishedOverlay.mouseReleased(e);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(! gameOver) {
            if(paused) {
                pauseOverlay.mouseMoved(e);
            } else if(finishedLevel) {
                finishedOverlay.mouseMoved(e);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver) {
            deathOverlay.keyPressed(e);
        } else {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_A -> player.setLeft(true);
                case KeyEvent.VK_D -> player.setRight(true);
                case KeyEvent.VK_SPACE -> player.setJump(true);
                case KeyEvent.VK_ESCAPE -> paused = ! paused;
                default -> {
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(! gameOver) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_A -> player.setLeft(false);
                case KeyEvent.VK_D -> player.setRight(false);
                case KeyEvent.VK_SPACE -> player.setJump(false);
                default -> {
                }
            }
        }
    }

    public void resetAll() {
        gameOver = false;
        paused = false;
        finishedLevel = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void checkPotionTouched(Rectangle2D.Float hitbox) {
        objectManager.checkObjectTouched(hitbox);
    }

    public void checkObjectHit(Rectangle2D.Float attackBox) {
        objectManager.checkObjectHit(attackBox);
    }

    public void checkSpikeTouched(Player player) {
        objectManager.checkedSpikesTouched(player);
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setLevelFinished(boolean levelFinished) {
        this.finishedLevel = levelFinished;
    }

    public void setMaxLevelOffsetX(int maxLevelOffsetX) {
        this.maxLevelOffsetX = maxLevelOffsetX;
    }

    public void resume() {
        paused = false;
    }

    public Player getPlayer() {
        return player;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }
}
