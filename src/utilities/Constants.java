package utilities;

import static main.Game.SCALE;

public class Constants {
    public static class EnemyConstants {
        public static final int CRAB = 0;

        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int HIT = 2;
        public static final int ATTACK = 3;
        public static final int DEAD = 4;

        public static final int CRAB_DEFAULT_WIDTH = 72;
        public static final int CRAB_DEFAULT_HEIGHT = 32;
        public static final int CRAB_WIDTH = (int) (CRAB_DEFAULT_WIDTH * SCALE);
        public static final int CRAB_HEIGHT = (int) (CRAB_DEFAULT_HEIGHT * SCALE);

        public static int GetSpriteAmount(int enemy_type, int enemy_state) {
            switch(enemy_type) {
                case CRAB -> {
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
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerActions {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int ATTACK = 6;
        public static final int ATTACK_JUMP_1 = 7;
        public static final int ATTACK_JUMP_2 = 8;

        public static int GetSpriteAmount(int player_action) {
            return switch(player_action) {
                case RUNNING -> 6;
                case IDLE -> 5;
                case HIT -> 4;
                case JUMP, ATTACK, ATTACK_JUMP_1, ATTACK_JUMP_2 -> 3;
                case GROUND -> 2;
                default -> 1;
            };
        }
    }
}
