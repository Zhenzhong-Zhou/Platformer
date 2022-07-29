package utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class LoadSave {
    // Entities
    public static final String PLAYER_ATLAS = "entities/player_sprites.png";
    public static final String PLAYER_STATUS_BAR = "entities/health_power_bar.png";
    public static final String CRAB_ATLAS = "entities/crabby_sprite.png";

    // Environment
    public static final String LEVEL_ATLAS = "environment/outside_sprites.png";
    public static final String PLAY_BG_IMAGE = "environment/playing_bg_img.png";
    public static final String BIG_CLOUDS = "environment/big_clouds.png";
    public static final String SMALL_CLOUDS = "environment/small_clouds.png";

    // GUI
    public static final String MENU_BUTTONS = "gui/menu/button_atlas.png";
    public static final String MENU_BACKGROUND = "gui/menu/menu_background.png";
    public static final String MENU_BACKGROUND_IMAGE = "gui/menu/background_menu.png";
    public static final String OPTIONS_MENU = "gui/menu/options_background.png";
    public static final String PAUSE_BACKGROUND = "gui/pause/pause_background.png";
    public static final String SOUND_BUTTONS = "gui/pause/sound_button.png";
    public static final String UTIL_BUTTONS = "gui/pause/urm_buttons.png";
    public static final String VOLUME_BUTTONS = "gui/pause/volume_buttons.png";
    public static final String LEVEL_COMPLETE = "gui/complete/completed_sprite.png";
    public static final String DEAD_SCREEN = "gui/dead/dead_screen.png";

    // Objects
    public static final String POTIONS_SPRITES = "objects/potions_sprites.png";
    public static final String BARREL_SPRITES = "objects/objects_sprites.png";
    public static final String TRAP_SPRITE = "objects/trap_atlas.png";
    public static final String CANNON_SPRITES = "objects/cannon_atlas.png";

    // Projectiles
    public static final String CANNON_BALL = "projectiles/ball.png";

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

    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/level");
        File file = null;
        try {
            assert url != null;
            file = new File(url.toURI());
        } catch(URISyntaxException e) {
            e.printStackTrace();
        }

        assert file != null;
        File[] files = file.listFiles();
        assert files != null;
        File[] filesSorted = new File[files.length];

        for(int i = 0; i < filesSorted.length; i++) {
            for(File f : files) {
                if(f.getName().equals((i + 1) + ".png")) {
                    filesSorted[i] = f;
                }
            }
        }

        BufferedImage[] images = new BufferedImage[filesSorted.length];
        for(int i = 0; i < images.length; i++) {
            try {
                images[i] = ImageIO.read(filesSorted[i]);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }
}
