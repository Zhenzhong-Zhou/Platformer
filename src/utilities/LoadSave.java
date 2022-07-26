package utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    //    public static final String LEVEL_DEFAULT_DATA = "level_one_data.png";
    public static final String LEVEL_DEFAULT_DATA = "level_one_data_long.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String MENU_BACKGROUND_IMAGE = "background_menu.png";
    public static final String PAUSE_BACKGROUND = "pause_background.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String UTIL_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String PLAY_BG_IMAGE = "playing_bg_img.png";
    public static final String BIG_CLOUDS = "big_clouds.png";
    public static final String SMALL_CLOUDS = "small_clouds.png";

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
