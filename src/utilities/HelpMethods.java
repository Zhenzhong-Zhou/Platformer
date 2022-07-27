package utilities;

import java.awt.geom.Rectangle2D;

import static main.Game.GAME_HEIGHT;
import static main.Game.TILES_SIZE;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {
        if (!IsSolid(x, y, levelData)) {
            if (!IsSolid(x + width, y + height, levelData)) {
                if (!IsSolid(x + width, y, levelData)) {
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

        return IsTileSolid((int) xIndex,(int) yIndex, levelData);
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
        return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, levelData);
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] levelData) {
        for(int i =0; i< xEnd - xStart;i++) {
            if(IsTileSolid(xStart+i, y, levelData)) {
                return false;
            }
            if(!IsTileSolid(xStart+i,y+1 , levelData)) {
                return false;
            }
        }
        return true;
    }

    public static boolean IsSightClear(int[][] levelData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int tileY) {
        int firstTileX = (int) (firstHitbox.x / TILES_SIZE);
        int secondTileX = (int) (secondHitbox.x / TILES_SIZE);

        if(firstTileX > secondTileX) {
            return IsAllTilesWalkable(secondTileX, firstTileX,tileY,levelData);
        }else {
            return IsAllTilesWalkable(firstTileX, secondTileX,tileY,levelData);
        }
    }
}
