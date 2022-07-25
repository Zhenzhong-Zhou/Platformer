package levels;

import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.Game.*;
import static utilities.LoadSave.*;

public class LevelManager {
    private final Game game;
    private BufferedImage[] levelSprite;
    private final Level levelDefault;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levelDefault = new Level(GetLevelData());
    }

    private void importOutsideSprites() {
        BufferedImage image = GetSpriteAtlas(LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for(int j = 0; j < 4; j++) {
            for(int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = image.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }

    public void draw(Graphics graphics) {
        for(int j = 0; j < TILES_IN_HEIGHT; j++) {
            for(int i = 0; i < TILES_IN_WIDTH; i++) {
                int index = levelDefault.getSpriteIndex(i, j);
                graphics.drawImage(levelSprite[index], TILES_SIZE * i, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
            }
        }
    }

    public void update() {

    }
}
