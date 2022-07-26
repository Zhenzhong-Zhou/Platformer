package states;

import entities.Player;
import gui.PauseOverlay;
import levels.LevelManager;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import static main.Game.*;
import static utilities.Constants.Environment.*;
import static utilities.LoadSave.*;

public class Play extends State implements StateMethods {
    private Player player;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private boolean paused = false;
    private int xLevelOffset;
    private int leftBorder = (int) (GAME_WIDTH * 0.2);
    private int rightBorder = (int) (GAME_WIDTH * 0.8);
    private int levelTileWidth = GetLevelData()[0].length;
    private int maxTilesOffset = levelTileWidth - TILES_IN_WIDTH;
    private int maxLevelOffsetX = maxTilesOffset * TILES_SIZE;
    private BufferedImage backgroundImage, bigCloudsImage, smallCloudsImage;
    private int[] smallCloudsPosition;
    private Random random = new Random();

    public Play(Game game) {
        super(game);
        initClasses();
        backgroundImage = GetSpriteAtlas(PLAY_BG_IMAGE);
        bigCloudsImage = GetSpriteAtlas(BIG_CLOUDS);
        smallCloudsImage = GetSpriteAtlas(SMALL_CLOUDS);
        smallCloudsPosition = new int[8];
        for(int i= 0 ; i< smallCloudsPosition.length;i++) {
            smallCloudsPosition[i] = (int) (SCALE*90) + random.nextInt((int) (SCALE*100));
        }
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE));
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
    }

    @Override
    public void update() {
        if(! paused) {
            levelManager.update();
            player.update();
            checkCloseToBorder();
        } else {
            pauseOverlay.update();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int difference = playerX - xLevelOffset;

        if(difference > rightBorder) {
            xLevelOffset += difference-rightBorder;
        } else if(difference<leftBorder) {
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

        if(paused) {
            graphics.setColor(new Color(0,0,0,150));
            graphics.fillRect(0,0,GAME_WIDTH, GAME_HEIGHT);
            pauseOverlay.draw(graphics);
        }
    }

    private void drawClouds(Graphics graphics) {
        for(int i =0; i<3; i++) {
            graphics.drawImage(bigCloudsImage,BIG_CLOUDS_WIDTH*i-(int) (xLevelOffset*0.3), (int) (SCALE*204), BIG_CLOUDS_WIDTH, BIG_CLOUDS_HEIGHT,null);
        }

        for(int i =0; i<smallCloudsPosition.length;i++) {
            graphics.drawImage(smallCloudsImage, SMALL_CLOUDS_WIDTH*4*i-(int) (xLevelOffset*0.7), smallCloudsPosition[i], SMALL_CLOUDS_WIDTH, SMALL_CLOUDS_HEIGHT, null);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if(paused) {
            pauseOverlay.mouseDragged(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            player.setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(paused) {
            pauseOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(paused) {
            pauseOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(paused) {
            pauseOverlay.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_A -> {
                player.setLeft(true);
            }
            case KeyEvent.VK_D -> {
                player.setRight(true);
            }
            case KeyEvent.VK_SPACE -> {
                player.setJump(true);
            }
            case KeyEvent.VK_ESCAPE -> {
                paused = ! paused;
            }
            default -> {
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_A -> {
                player.setLeft(false);
            }
            case KeyEvent.VK_D -> {
                player.setRight(false);
            }
            case KeyEvent.VK_SPACE -> {
                player.setJump(false);
            }
            default -> {
            }
        }
    }

    public void resume() {
        paused = false;
    }

    public void windowFocusLost() {
        player.resetDirectionBoolean();
    }

    public Player getPlayer() {
        return player;
    }
}
