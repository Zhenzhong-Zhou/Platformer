package utilities;

import static main.Game.SCALE;

public class Constants {
    public static final float GRAVITY = 0.04f * SCALE;
    public static final float ANIMATE_SPEED = 25;

    public static class AudioPlayer {
        public static final int MENU_1 = 0;
        public static final int LEVEL_1 = 1;
        public static final int LEVEL_2 = 2;

        public static final int DIE = 0;
        public static final int JUMP_SOUND_EFFECT = 1;
        public static final int GAME_OVER = 2;
        public static final int LVL_COMPLETED = 3;
        public static final int ATTACK_ONE = 4;
        public static final int ATTACK_TWO = 5;
        public static final int ATTACK_THREE = 6;
    }

    public static class Projectiles {
        public static final int CANNON_BALL_DEFAULT_WIDTH = 15;
        public static final int CANNON_BALL_DEFAULT_HEIGHT = 15;
        public static final int CANNON_BALL_WIDTH = (int) (CANNON_BALL_DEFAULT_WIDTH * SCALE);
        public static final int CANNON_BALL_HEIGHT = (int) (CANNON_BALL_DEFAULT_HEIGHT * SCALE);

        public static final float SPEED = 0.75f * SCALE;
    }

    public static class GameObject {
        // Object Types
        public static final int RED_POTION = 0;
        public static final int BLUE_POTION = 1;
        public static final int BARREL = 2;
        public static final int BOX = 3;
        public static final int SPIKE = 4;
        public static final int CANNON_LEFT = 5;
        public static final int CANNON_RIGHT = 6;

        // Potion Value
        public static final int RED_POTION_VALUE = 15;
        public static final int BLUE_POTION_VALUE = 10;

        // Potion Sizes
        public static final int POTION_DEFAULT_WIDTH = 12;
        public static final int POTION_DEFAULT_HEIGHT = 16;
        public static final int POTION_WIDTH = (int) (POTION_DEFAULT_WIDTH * SCALE);
        public static final int POTION_HEIGHT = (int) (POTION_DEFAULT_HEIGHT * SCALE);

        // Container Sizes
        public static final int CONTAINER_DEFAULT_WIDTH = 40;
        public static final int CONTAINER_DEFAULT_HEIGHT = 30;
        public static final int CONTAINER_WIDTH = (int) (CONTAINER_DEFAULT_WIDTH * SCALE);
        public static final int CONTAINER_HEIGHT = (int) (CONTAINER_DEFAULT_HEIGHT * SCALE);

        // Spike Sizes
        public static final int SPIKE_DEFAULT_WIDTH = 32;
        public static final int SPIKE_DEFAULT_HEIGHT = 32;
        public static final int SPIKE_WIDTH = (int) (SPIKE_DEFAULT_WIDTH * SCALE);
        public static final int SPIKE_HEIGHT = (int) (SPIKE_DEFAULT_HEIGHT * SCALE);

        // Cannon Sizes
        public static final int CANNON_DEFAULT_WIDTH = 40;
        public static final int CANNON_DEFAULT_HEIGHT = 26;
        public static final int CANNON_WIDTH = (int) (CANNON_DEFAULT_WIDTH * SCALE);
        public static final int CANNON_HEIGHT = (int) (CANNON_DEFAULT_HEIGHT * SCALE);

        public static int GetSpriteAmount(int object_type) {
            switch(object_type) {
                case RED_POTION, BLUE_POTION, CANNON_LEFT, CANNON_RIGHT -> {
                    return 7;
                }
                case BARREL, BOX -> {
                    return 8;
                }
                default -> {
                }
            }
            return 1;
        }
    }

    public static class PlayerStatusBar {
        public static final int STATUS_BAR_DEFAULT_WIDTH = 192;
        public static final int STATUS_BAR_DEFAULT_HEIGHT = 58;
        public static final int STATUS_BAR_WIDTH = (int) (STATUS_BAR_DEFAULT_WIDTH * SCALE);
        public static final int STATUS_BAR_HEIGHT = (int) (STATUS_BAR_DEFAULT_HEIGHT * SCALE);

        public static final int STATUS_BAR_DEFAULT_X = 10;
        public static final int STATUS_BAR_DEFAULT_Y = 10;
        public static final int STATUS_BAR_X = (int) (STATUS_BAR_DEFAULT_X * SCALE);
        public static final int STATUS_BAR_Y = (int) (STATUS_BAR_DEFAULT_Y * SCALE);

        public static final int HP_BAR_DEFAULT_WIDTH = 150;
        public static final int HP_BAR_DEFAULT_HEIGHT = 4;
        public static final int HP_BAR_WIDTH = (int) (HP_BAR_DEFAULT_WIDTH * SCALE);
        public static final int HP_BAR_HEIGHT = (int) (HP_BAR_DEFAULT_HEIGHT * SCALE);

        public static final int HP_BAR_DEFAULT_X_START = 34;
        public static final int HP_BAR_DEFAULT_Y_START = 14;
        public static final int HP_BAR_X_START = (int) (HP_BAR_DEFAULT_X_START * SCALE);
        public static final int HP_BAR_Y_START = (int) (HP_BAR_DEFAULT_Y_START * SCALE);
    }

    public static class EnemyConstants {
        // Enemy Types
        public static final int CRAB = 0;

        // Crab States
        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        // Crab Sizes
        public static final int CRAB_DEFAULT_WIDTH = 72;
        public static final int CRAB_DEFAULT_HEIGHT = 32;
        public static final int CRAB_WIDTH = (int) (CRAB_DEFAULT_WIDTH * SCALE);
        public static final int CRAB_HEIGHT = (int) (CRAB_DEFAULT_HEIGHT * SCALE);

        // Crab Hitbox Sizes
        public static final int CRAB_DRAW_OFFSET_X = (int) (26 * SCALE);
        public static final int CRAB_DRAW_OFFSET_Y = (int) (9 * SCALE);

        public static int GetSpriteAmount(int enemy_type, int enemy_state) {
            if(enemy_type == CRAB) {
                switch(enemy_state) {
                    case IDLE -> {
                        return 9;
                    }
                    case RUN -> {
                        return 6;
                    }
                    case ATTACK -> {
                        return 7;
                    }
                    case HIT -> {
                        return 4;
                    }
                    case DEAD -> {
                        return 5;
                    }
                }
            }
            return 0;
        }

        public static int GetMaxHealth(int enemy_type) {
            if(enemy_type == CRAB) {
                return 10;
            }
            return 1;
        }

        public static int GetEnemyDamageCheck(int enemy_type) {
            if(enemy_type == CRAB) {
                return 15;
            }
            return 0;
        }
    }

    public static class Environment {
        // Big Clouds
        public static final int BIG_CLOUDS_DEFAULT_WIDTH = 448;
        public static final int BIG_CLOUDS_DEFAULT_HEIGHT = 101;
        public static final int BIG_CLOUDS_WIDTH = (int) (BIG_CLOUDS_DEFAULT_WIDTH * SCALE);
        public static final int BIG_CLOUDS_HEIGHT = (int) (BIG_CLOUDS_DEFAULT_HEIGHT * SCALE);
        // Small Clouds
        public static final int SMALL_CLOUDS_DEFAULT_WIDTH = 74;
        public static final int SMALL_CLOUDS_DEFAULT_HEIGHT = 24;
        public static final int SMALL_CLOUDS_WIDTH = (int) (SMALL_CLOUDS_DEFAULT_WIDTH * SCALE);
        public static final int SMALL_CLOUDS_HEIGHT = (int) (SMALL_CLOUDS_DEFAULT_HEIGHT * SCALE);
    }

    public static class GUI {
        public static class Buttons {
            public static final int BUTTON_DEFAULT_WIDTH = 140;
            public static final int BUTTON_DEFAULT_HEIGHT = 56;
            public static final int BUTTON_WIDTH = (int) (BUTTON_DEFAULT_WIDTH * SCALE);
            public static final int BUTTON_HEIGHT = (int) (BUTTON_DEFAULT_HEIGHT * SCALE);
        }

        public static class PauseButtons {
            // Sound Buttons
            public static final int SOUND_DEFAULT_SIZE = 42;
            public static final int SOUND_SIZE = (int) (SOUND_DEFAULT_SIZE * SCALE);
            // Util Buttons
            public static final int UTIL_DEFAULT_SIZE = 56;
            public static final int UTIL_SIZE = (int) (UTIL_DEFAULT_SIZE * SCALE);
            // Volume Buttons
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * SCALE);
            // Volume Sliders
            public static final int SLIDER_DEFAULT_WIDTH = 215;
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * SCALE);
        }
    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int RIGHT = 2;
    }

    public static class PlayerActions {
        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int JUMP = 2;
        public static final int FALL = 3;
        public static final int ATTACK = 4;
        public static final int HIT = 5;
        public static final int DEAD = 6;

        public static int GetSpriteAmount(int player_action) {
            return switch(player_action) {
                case RUN -> 6;
                case IDLE -> 5;
                case HIT -> 4;
                case JUMP, ATTACK -> 3;
                case DEAD -> 8;
                default -> 1;
            };
        }
    }
}
