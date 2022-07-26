package entities;

import levels.Level;
import states.Play;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilities.Constants.EnemyConstants.*;
import static utilities.LoadSave.CRAB_ATLAS;
import static utilities.LoadSave.GetSpriteAtlas;

public class EnemyManager {
    private final Play play;
    private BufferedImage[][] crabArray;
    private ArrayList<Crab> crabs = new ArrayList<>();

    public EnemyManager(Play play) {
        this.play = play;
        loadEnemyImages();
    }

    public void loadEnemies(Level level) {
        crabs = level.getCrabs();
        System.out.println("Size of Crabs: " + crabs.size());
    }

    public void update(int[][] levelData, Player player) {
        boolean isAnyActive = false;
        for(Crab crab : crabs) {
            if(crab.isActive()) {
                crab.update(levelData, player);
                isAnyActive = true;
            }
        }
        if(! isAnyActive) {
            play.setLevelFinished(true);
        }
    }

    public void draw(Graphics graphics, int xLevelOffset) {
        drawCrabs(graphics, xLevelOffset);
    }

    private void drawCrabs(Graphics graphics, int xLevelOffset) {
        for(Crab crab : crabs) {
            if(crab.isActive()) {
                graphics.drawImage(crabArray[crab.getStates()][crab.getAnimationIndex()],
                        (int) crab.getHitbox().x - xLevelOffset - CRAB_DRAW_OFFSET_X + crab.flipX(),
                        (int) crab.getHitbox().y - CRAB_DRAW_OFFSET_Y, CRAB_WIDTH * crab.flipW(), CRAB_HEIGHT, null);
                crab.drawHitbox(graphics, xLevelOffset);
                crab.drawAttackBox(graphics, xLevelOffset);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for(Crab crab : crabs) {
            if(crab.isActive()) {
                if(attackBox.intersects(crab.getHitbox())) {
                    crab.hurt(10);
                }
            }
        }
    }

    private void loadEnemyImages() {
        crabArray = new BufferedImage[5][9];
        BufferedImage temp = GetSpriteAtlas(CRAB_ATLAS);

        for(int j = 0; j < crabArray.length; j++) {
            for(int i = 0; i < crabArray[j].length; i++) {
                crabArray[j][i] = temp.getSubimage(CRAB_DEFAULT_WIDTH * i, CRAB_DEFAULT_HEIGHT * j, CRAB_DEFAULT_WIDTH, CRAB_DEFAULT_HEIGHT);
            }
        }
    }

    public void resetAllEnemies() {
        for(Crab crab : crabs) {
            crab.resetEnemy();
        }
    }
}
