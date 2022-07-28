package objects;

import levels.Level;
import states.Play;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilities.Constants.GameObject.*;
import static utilities.LoadSave.*;

public class ObjectManager {
    private final Play play;
    private BufferedImage[][] potionImages, containerImages;
    private BufferedImage spikeImage;
    private ArrayList<Potion> potions;
    private ArrayList<Container> containers;
    private ArrayList<Spike> spikes;

    public ObjectManager(Play play) {
        this.play = play;
        loadImages();
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for(Potion potion : potions) {
            if(potion.isActive()) {
                if(hitbox.intersects(potion.getHitbox())) {
                    potion.setActive(false);
                    applyEffectToPlayer(potion);
                }
            }
        }
    }

    public void applyEffectToPlayer(Potion potion) {
        if(potion.getObjectType() == RED_POTION) {
            play.getPlayer().changeHealth(RED_POTION_VALUE);
        } else {
            play.getPlayer().changePower(BLUE_POTION_VALUE);
        }
    }

    public void checkObjectHit(Rectangle2D.Float attackBox) {
        for(Container container : containers) {
            if(container.isActive() && !container.doAnimation) {
                if(container.getHitbox().intersects(attackBox)) {
                    container.setAnimation(true);
                    int type = 0;
                    if(container.getObjectType() == BARREL) {
                        type = 1;
                    }
                    potions.add(new Potion((int) (container.getHitbox().x + container.getHitbox().width/2),
                            (int) (container.getHitbox().y - container.getHitbox().height/4), type));
                    return;
                }
            }
        }
    }

    public void loadObjects(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
        spikes = newLevel.getSpikes();
    }

    private void loadImages() {
        BufferedImage potionSprite = GetSpriteAtlas(POTIONS_SPRITES);
        potionImages = new BufferedImage[2][7];
        for(int j = 0; j < potionImages.length; j++) {
            for(int i = 0; i < potionImages[j].length; i++) {
                potionImages[j][i] = potionSprite.getSubimage(i * 12, j * 16, 12, 16);
            }
        }

        BufferedImage containerSprite = GetSpriteAtlas(BARREL_SPRITES);
        containerImages = new BufferedImage[2][8];
        for(int j = 0; j < containerImages.length; j++) {
            for(int i = 0; i < containerImages[j].length; i++) {
                containerImages[j][i] = containerSprite.getSubimage(i * 40, j * 30, 40, 30);
            }
        }

        spikeImage = GetSpriteAtlas(TRAP_SPRITE);
    }

    public void update() {
        for(Potion potion : potions) {
            if(potion.isActive()) {
                potion.update();
            }
        }

        for(Container container : containers) {
            if(container.isActive()) {
                container.update();
            }
        }
    }

    public void draw(Graphics graphics, int xLevelOffset) {
        drawPotions(graphics, xLevelOffset);
        drawContainers(graphics, xLevelOffset);
        drawTraps(graphics, xLevelOffset);
    }

    private void drawTraps(Graphics graphics, int xLevelOffset) {
        for(Spike spike : spikes) {
            graphics.drawImage(spikeImage,
                    (int) (spike.getHitbox().x - xLevelOffset), (int) (spike.getHitbox().y - spike.getYDrawOffset()),
                    SPIKE_WIDTH, SPIKE_HEIGHT, null);
        }
    }

    private void drawPotions(Graphics graphics, int xLevelOffset) {
        for(Potion potion : potions) {
            if(potion.isActive()) {
                int type = 0;
                if(potion.getObjectType() == RED_POTION) {
                    type = 1;
                }
                graphics.drawImage(potionImages[type][potion.getAnimationIndex()],
                        (int) (potion.getHitbox().x - potion.getXDrawOffset() - xLevelOffset),
                        (int) (potion.getHitbox().y - potion.getYDrawOffset()),
                        POTION_WIDTH, POTION_HEIGHT, null);
            }
        }
    }

    private void drawContainers(Graphics graphics, int xLevelOffset) {
        for(Container container : containers) {
            if(container.isActive()) {
                int type = 0;
                if(container.getObjectType() == BARREL) {
                    type = 1;
                }
                graphics.drawImage(containerImages[type][container.getAnimationIndex()],
                        (int) (container.getHitbox().x - container.getXDrawOffset() - xLevelOffset),
                        (int) (container.getHitbox().y - container.getYDrawOffset()),
                        CONTAINER_WIDTH, CONTAINER_HEIGHT, null);
            }
        }
    }

    public void resetAllObjects() {
        loadObjects(play.getLevelManager().getCurrentLevel());
        for(Potion potion : potions) {
            potion.reset();
        }
        for(Container container : containers) {
            container.reset();
        }
    }
}
