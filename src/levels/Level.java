package levels;

import entities.Crab;
import objects.Container;
import objects.Potion;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.Game.TILES_IN_WIDTH;
import static main.Game.TILES_SIZE;
import static utilities.HelpMethods.*;

public class Level {
    private final BufferedImage image;
    private int[][] levelData;
    private ArrayList<Crab> crabs;
    private ArrayList<Potion> potions;
    private ArrayList<Container> containers;
    private int levelTileWidth;
    private int maxTilesOffset;
    private int maxLevelOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage image) {
        this.image = image;
        createLevelData();
        createEnemies();
        createPotions();
        createContainers();
        calculateOffsets();
        calculatePlayerSpawn();
    }

    private void createPotions() {
        potions = GetPotions(image);
    }

    private void createContainers() {
        containers = GetContainers(image);
    }

    private void calculatePlayerSpawn() {
        playerSpawn = GetPlayerSpawn(image);
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

    public ArrayList<Potion> getPotions() {
        return potions;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public int getMaxLevelOffsetX() {
        return maxLevelOffsetX;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }
}
