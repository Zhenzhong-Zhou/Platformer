package levels;

import main.Game;
import states.GameStates;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.Game.TILES_IN_HEIGHT;
import static main.Game.TILES_SIZE;
import static states.GameStates.MENU;
import static utilities.LoadSave.*;

public class LevelManager {
    private final Game game;
    private ArrayList<Level> levels;
    private BufferedImage[] levelSprite;
    private int levelIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    public void loadNextLevel() {
        levelIndex++;
        if(levelIndex >=levels.size()) {
            levelIndex = 0;
            System.out.println("No more levels!");
            GameStates.gameStates = MENU;
        }
        Level newLevel = levels.get(levelIndex);
        game.getPlay().getEnemyManager().loadEnemies(newLevel);
        game.getPlay().getPlayer().loadLevelData(newLevel.getLevelData());
        game.getPlay().setMaxLevelOffsetX(newLevel.getMaxLevelOffsetX());
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = GetAllLevels();
        for(BufferedImage level : allLevels) {
            levels.add(new Level(level));
        }
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

    public void draw(Graphics graphics, int levelOffset) {
        for(int j = 0; j < TILES_IN_HEIGHT; j++) {
            for(int i = 0; i < levels.get(levelIndex).getLevelData()[0].length; i++) {
                int index = levels.get(levelIndex).getSpriteIndex(i, j);
                graphics.drawImage(levelSprite[index], TILES_SIZE * i - levelOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
            }
        }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levels.get(levelIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }
}
