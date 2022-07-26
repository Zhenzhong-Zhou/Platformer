package entities;

import states.Play;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilities.Constants.EnemyConstants.*;
import static utilities.LoadSave.*;

public class EnemyManager {
    private Play play;
    private BufferedImage[][] crabArray;
    private ArrayList<Crab> crabs = new ArrayList<>();

    public EnemyManager(Play play) {
        this.play = play;
        loadEnemyImages();
        addEnemies();
    }

    private void addEnemies() {
        crabs = GetCrabs();
        System.out.println("Size of Crabs: " + crabs.size());
    }

    public void update() {
        for(Crab crab : crabs) {
            crab.update();
        }
    }

    public void draw(Graphics graphics, int xLevelOffset) {
        drawCrabs(graphics, xLevelOffset);
    }

    private void drawCrabs(Graphics graphics, int xLevelOffset) {
        for(Crab crab : crabs) {
            graphics.drawImage(crabArray[crab.getEnemyStates()][crab.getAnimationIndex()],
                    (int) crab.getHitbox().x * xLevelOffset, (int) crab.getHitbox().y, CRAB_WIDTH, CRAB_HEIGHT,null);
        }
    }

    private void loadEnemyImages() {
        crabArray = new BufferedImage[5][9];
        BufferedImage temp = GetSpriteAtlas(CRAB_ATLAS);

        for(int j=0;j<crabArray.length;j++) {
            for(int i=0;i<crabArray[j].length;i++) {
                crabArray[j][i] = temp.getSubimage(CRAB_DEFAULT_WIDTH*i,CRAB_DEFAULT_HEIGHT*j, CRAB_DEFAULT_WIDTH, CRAB_DEFAULT_HEIGHT);
            }
        }
    }
}