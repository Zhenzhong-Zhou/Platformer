package utilities;

import entities.Crab;
import objects.Container;
import objects.Potion;
import objects.Spike;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.Game.GAME_HEIGHT;
import static main.Game.TILES_SIZE;
import static utilities.Constants.EnemyConstants.CRAB;
import static utilities.Constants.GameObject.*;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {
        if(! IsSolid(x, y, levelData)) {
            if(! IsSolid(x + width, y + height, levelData)) {
                if(! IsSolid(x + width, y, levelData)) {
                    return ! IsSolid(x, y + height, levelData);
                }
            }
        }
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] levelData) {
        int maxWidth = levelData[0].length * TILES_SIZE;
        if(x < 0 || x >= maxWidth) {
            return false;
        }
        if(y < 0 || y >= GAME_HEIGHT) {
            return false;
        }

        float xIndex = x / TILES_SIZE;
        float yIndex = y / TILES_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, levelData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] levelData) {
        int value = levelData[yTile][xTile];
        return value != 11;
    }

    public static float GetEntityXPositionNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / TILES_SIZE);
        if(xSpeed > 0) {
            // Right
            int tileXPosition = currentTile * TILES_SIZE;
            int xOffset = (int) (TILES_SIZE - hitbox.width);
            return tileXPosition + xOffset - 1;
        } else {
            // Left
            return currentTile * TILES_SIZE;
        }
    }

    public static float GetEntityYPositionUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / TILES_SIZE);
        if(airSpeed > 0) {
            // Falling
            int tileYPosition = currentTile * TILES_SIZE;
            int yOffset = (int) (TILES_SIZE - hitbox.height);
            return tileYPosition + yOffset - 1;
        } else {
            // Jumping
            return currentTile * TILES_SIZE;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] levelData) {
        // Check the pixel below bottom left and bottom right
        if(IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelData)) {
            return ! IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData);
        }
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] levelData) {
        if(xSpeed > 0) {
            return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, levelData);
        } else {
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, levelData);
        }
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] levelData) {
        for(int i = 0; i < xEnd - xStart; i++) {
            if(IsTileSolid(xStart + i, y, levelData)) {
                return false;
            }
            if(! IsTileSolid(xStart + i, y + 1, levelData)) {
                return false;
            }
        }
        return true;
    }

    public static boolean IsSightClear(int[][] levelData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int tileY) {
        int firstTileX = (int) (firstHitbox.x / TILES_SIZE);
        int secondTileX = (int) (secondHitbox.x / TILES_SIZE);

        if(firstTileX > secondTileX) {
            return IsAllTilesWalkable(secondTileX, firstTileX, tileY, levelData);
        } else {
            return IsAllTilesWalkable(firstTileX, secondTileX, tileY, levelData);
        }
    }

    public static int[][] GetLevelData(BufferedImage image) {
        int[][] levelData = new int[image.getHeight()][image.getWidth()];
        for(int j = 0; j < image.getHeight(); j++) {
            for(int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getRed();
                if(value >= 48) {
                    value = 0;
                }
                levelData[j][i] = value;
            }
        }
        return levelData;
    }

    public static ArrayList<Crab> GetCrabs(BufferedImage image) {
        ArrayList<Crab> crabArrayList = new ArrayList<>();
        for(int j = 0; j < image.getHeight(); j++) {
            for(int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if(value == CRAB) {
                    crabArrayList.add(new Crab(TILES_SIZE * i, TILES_SIZE * j));
                }
            }
        }
        return crabArrayList;
    }

    public static ArrayList<Potion> GetPotions(BufferedImage image) {
        ArrayList<Potion> potionArrayList = new ArrayList<>();
        for(int j = 0; j < image.getHeight(); j++) {
            for(int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getBlue();
                if(value == RED_POTION || value == BLUE_POTION) {
                    potionArrayList.add(new Potion(TILES_SIZE * i, TILES_SIZE * j, value));
                }
            }
        }
        return potionArrayList;
    }

    public static ArrayList<Container> GetContainers(BufferedImage image) {
        ArrayList<Container> containerArrayList = new ArrayList<>();
        for(int j = 0; j < image.getHeight(); j++) {
            for(int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getBlue();
                if(value == BARREL || value == BOX) {
                    containerArrayList.add(new Container(TILES_SIZE * i, TILES_SIZE * j, value));
                }
            }
        }
        return containerArrayList;
    }

    public static ArrayList<Spike> GetSpikes(BufferedImage image) {
        ArrayList<Spike> spikeArrayList = new ArrayList<>();
        for(int j = 0; j < image.getHeight(); j++) {
            for(int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getBlue();
                if(value == SPIKE) {
                    spikeArrayList.add(new Spike(TILES_SIZE * i, TILES_SIZE * j, SPIKE));
                }
            }
        }
        return spikeArrayList;
    }

    public static Point GetPlayerSpawn(BufferedImage image) {
        for(int j = 0; j < image.getHeight(); j++) {
            for(int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if(value == 100) {
                    return new Point(i * TILES_SIZE, j * TILES_SIZE);
                }
            }
        }
        return new Point(TILES_SIZE, TILES_SIZE);
    }
}
