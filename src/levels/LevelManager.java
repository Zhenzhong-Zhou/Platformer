package levels;

import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilities.LoadSave.GetSpriteAtlas;
import static utilities.LoadSave.LEVEL_ATLAS;

public class LevelManager {
    private Game game;
    private BufferedImage levelSprite;

    public LevelManager(Game game) {
        this.game = game;
        levelSprite = GetSpriteAtlas(LEVEL_ATLAS);
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(levelSprite,0, 0, null);
    }

    public void update() {

    }
}
