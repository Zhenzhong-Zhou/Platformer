package utilities;

import entities.Crab;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static main.Game.TILES_SIZE;
import static utilities.Constants.EnemyConstants.CRAB;

public class LoadSave {
    // Entities
    public static final String PLAYER_ATLAS = "entities/player_sprites.png";
    public static final String CRAB_ATLAS = "entities/crabby_sprite.png";

    // Environment
    public static final String LEVEL_ATLAS = "environment/outside_sprites.png";
    public static final String PLAY_BG_IMAGE = "environment/playing_bg_img.png";
    public static final String BIG_CLOUDS = "environment/big_clouds.png";
    public static final String SMALL_CLOUDS = "environment/small_clouds.png";

    // Level
    //    public static final String LEVEL_DEFAULT_DATA = "level_one_data.png";
    public static final String LEVEL_DEFAULT_DATA = "level/level_one_data_long.png";

    // GUI
    public static final String MENU_BUTTONS = "gui/menu/button_atlas.png";
    public static final String MENU_BACKGROUND = "gui/menu/menu_background.png";
    public static final String MENU_BACKGROUND_IMAGE = "gui/menu/background_menu.png";
    public static final String PAUSE_BACKGROUND = "gui/pause/pause_background.png";
    public static final String SOUND_BUTTONS = "gui/pause/sound_button.png";
    public static final String UTIL_BUTTONS = "gui/pause/urm_buttons.png";
    public static final String VOLUME_BUTTONS = "gui/pause/volume_buttons.png";

    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage image = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            assert is != null;
            image = ImageIO.read(is);
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert is != null;
                is.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    public static ArrayList<Crab> GetCrabs() {
        BufferedImage image = GetSpriteAtlas(LEVEL_DEFAULT_DATA);
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

    public static int[][] GetLevelData() {
        BufferedImage image = GetSpriteAtlas(LEVEL_DEFAULT_DATA);
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
}
