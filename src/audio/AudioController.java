package audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import static utilities.Constants.AudioPlayer.*;

public class AudioController {
    private Clip[] sounds, effects;
    private int currentSoundId;
    private float volume = 1f;
    private boolean soundMute, effectMute;
    private Random random = new Random();

    public AudioController() {
        loadSounds();
        loadEffects();
        playSound(MENU_1);
    }

    public void loadSounds() {
        String[] soundNames = {"menu", "level1", "level2"};
        sounds = new Clip[soundNames.length];
        for(int i=0; i<sounds.length;i++) {
            sounds[i] = getClip(soundNames[i]);
        }
    }

    public void loadEffects() {
        String[] effectNames = {"die", "jump", "gameover", "lvlcompleted", "attack1", "attack2", "attack3"};
        effects = new Clip[effectNames.length];
        for (int i = 0; i < effects.length; i++) {
            effects[i] = getClip(effectNames[i]);
        }

        updateEffectsVolume();
    }

    public void setVolume(float volume) {
        this.volume = volume;
        updateSoundsVolume();
        updateEffectsVolume();
    }

    public void stopSound() {
        if(sounds[currentSoundId].isActive()) {
            sounds[currentSoundId].stop();
        }
    }

    public void setLevelSound(int levelIndex) {
        if(levelIndex%2==0) {
            playSound(LEVEL_1);
        }else {
            playSound(LEVEL_2);
        }
    }

    public void levelCompleted() {
        stopSound();
        playEffect(LVL_COMPLETED);
    }

    public void playAttackSound() {
        int start = 4;
        start += random.nextInt(3);
        playEffect(start);
    }

    public void playSound(int sound) {
        stopSound();

        currentSoundId = sound;
        updateSoundsVolume();
        sounds[currentSoundId].setMicrosecondPosition(0);
        sounds[currentSoundId].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playEffect(int effect) {
        effects[effect].setMicrosecondPosition(0);
        effects[effect].start();
    }

    public void  toggleSoundMute() {
        this.soundMute = !soundMute;
        for(Clip clip : sounds) {
            BooleanControl booleanControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(soundMute);
        }
    }

    public void toggleEffectMute() {
        this.effectMute = !effectMute;
        for(Clip clip : effects) {
            BooleanControl booleanControl =(BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }
        if(!effectMute) {
            playEffect(JUMP);
        }
    }

    private void updateSoundsVolume() {
        FloatControl gainControl = (FloatControl) sounds[currentSoundId].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }

    private void updateEffectsVolume() {
        for(Clip clip : effects) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

    private Clip getClip(String filename) {
        URL url = getClass().getResource("/audio/" + filename + ".wav");
        AudioInputStream audio;

        try {
            assert url != null;
            audio = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            return clip;
        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}
