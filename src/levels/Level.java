package levels;

import entities.Crab;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.Game.TILES_IN_WIDTH;
import static main.Game.TILES_SIZE;
import static utilities.HelpMethods.GetCrabs;
import static utilities.HelpMethods.GetLevelData;

public class Level {
    private BufferedImage image;
    private int[][] levelData;
    private ArrayList<Crab> crabs;
    private int levelTileWidth;
    private int maxTilesOffset;
    private int maxLevelOffsetX;

    public Level(BufferedImage image) {
        this.image = image;
        createLevelData();
        createEnemies();
        calculateOffsets();
    }

    private void calculateOffsets() {
        levelTileWidth = image.getWidth();
        maxTilesOffset = levelTileWidth - TILES_IN_WIDTH;
        maxLevelOffsetX = TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {
        crabs = GetCrabs(image);
    }

    private void createLevelData() {
        levelData = GetLevelData(image);
    }

    public int getSpriteIndex(int x, int y) {
        return levelData[y][x];
    }

    public int[][] getLevelData() {
        return levelData;
    }

    public ArrayList<Crab> getCrabs() {
        return crabs;
    }

    public int getMaxLevelOffsetX() {
        return maxLevelOffsetX;
    }
}
