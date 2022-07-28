package objects;

import entities.Player;
import levels.Level;
import states.Play;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.Game.TILES_SIZE;
import static utilities.Constants.GameObject.*;
import static utilities.HelpMethods.CanCannonSeePlayer;
import static utilities.LoadSave.*;

public class ObjectManager {
    private final Play play;
    private BufferedImage[][] potionImages, containerImages;
    private BufferedImage spikeImage;
    private BufferedImage[] cannonImages;
    private ArrayList<Potion> potions;
    private ArrayList<Container> containers;
    private ArrayList<Spike> spikes;
    private ArrayList<Cannon> cannons;

    public ObjectManager(Play play) {
        this.play = play;
        loadImages();
    }

    public void checkedSpikesTouched(Player player) {
        for(Spike spike : spikes) {
            if(spike.getHitbox().intersects(player.getHitbox())) {
                player.kill();
            }
        }
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
        cannons = newLevel.getCannons();
    }

    private void loadImages() {
        BufferedImage potionSprite = GetSpriteAtlas(POTIONS_SPRITES);
        potionImages = new BufferedImage[2][7];
        for(int j = 0; j < potionImages.length; j++) {
            for(int i = 0; i < potionImages[j].length; i++) {
                potionImages[j][i] = potionSprite.getSubimage(i * POTION_DEFAULT_WIDTH, j * POTION_DEFAULT_HEIGHT,
                        POTION_DEFAULT_WIDTH, POTION_DEFAULT_HEIGHT);
            }
        }

        BufferedImage containerSprite = GetSpriteAtlas(BARREL_SPRITES);
        containerImages = new BufferedImage[2][8];
        for(int j = 0; j < containerImages.length; j++) {
            for(int i = 0; i < containerImages[j].length; i++) {
                containerImages[j][i] = containerSprite.getSubimage(i * CONTAINER_DEFAULT_WIDTH,
                        j * CONTAINER_DEFAULT_HEIGHT, CONTAINER_DEFAULT_WIDTH, CONTAINER_DEFAULT_HEIGHT);
            }
        }

        spikeImage = GetSpriteAtlas(TRAP_SPRITE);

        cannonImages = new BufferedImage[7];
        BufferedImage cannonSprites = GetSpriteAtlas(CANNON_SPRITES);
        for(int i=0; i<cannonImages.length;i++) {
            cannonImages[i] = cannonSprites.getSubimage(i*CANNON_DEFAULT_WIDTH, 0, CANNON_DEFAULT_WIDTH, CANNON_DEFAULT_HEIGHT);
        }
    }

    public void update(int[][] levelData, Player player) {
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

       updateCannon(levelData, player);
    }

    private boolean isPlayerInRange(Cannon cannon, Player player) {
        int absValue = (int) Math.abs(player.getHitbox().x - cannon.getHitbox().x);
        return absValue <= TILES_SIZE * 5;
    }

    private boolean isPlayerInFrontOfCannon(Cannon cannon, Player player) {
        if(cannon.getObjectType() == CANNON_LEFT) {
            return cannon.getHitbox().x > player.getHitbox().x;
        } else return cannon.getHitbox().x < player.getHitbox().x;
    }

    private void updateCannon(int[][] levelData, Player player) {
        for(Cannon cannon : cannons) {
            if(!cannon.doAnimation) {
                if(cannon.getTileY() == player.getTileY()) {
                    if(isPlayerInRange(cannon, player)){
                        if(isPlayerInFrontOfCannon(cannon, player)) {
                            if(CanCannonSeePlayer(levelData, player.getHitbox(), cannon.getHitbox(), cannon.getTileY())) {
                                cannonFire(cannon);
                            }
                        }
                    }
                }
            }
            cannon.update();
        }
    }

    private void cannonFire(Cannon cannon) {
        cannon.setAnimation(true);
    }

    public void draw(Graphics graphics, int xLevelOffset) {
        drawPotions(graphics, xLevelOffset);
        drawContainers(graphics, xLevelOffset);
        drawTraps(graphics, xLevelOffset);
        drawCannons(graphics, xLevelOffset);
    }

    private void drawCannons(Graphics graphics, int xLevelOffset) {
        for(Cannon cannon : cannons) {
            int x = (int) (cannon.getHitbox().x - xLevelOffset);
            int width = CANNON_WIDTH;
            if(cannon.getObjectType() == CANNON_RIGHT) {
                x+=width;
                width*=-1;
            }
            graphics.drawImage(cannonImages[cannon.getAnimationIndex()], x, (int) (cannon.getHitbox().y ),
                    width, CANNON_HEIGHT, null);
        }
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

        for(Cannon cannon : cannons) {
            cannon.reset();
        }
    }
}
