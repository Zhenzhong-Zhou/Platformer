package utilities;

import static main.Game.*;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {
        if(IsSolid(x, y, levelData)) {
            if(IsSolid(x + width, y + height, levelData)) {
                if(IsSolid(x + width, y, levelData)) {
                    return IsSolid(x, y + height, levelData);
                }
            }
        }
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] levelData) {
        if(x <0 || x>=GAME_WIDTH){
            return false;
        }
        if(y<0|| y>= GAME_HEIGHT) {
            return false;
        }

        float xIndex = x / TILES_SIZE;
        float yIndex = y / TILES_SIZE;

        int value = levelData[(int) yIndex][(int) xIndex];

        return value == 11;
    }
}
