package objects;

import states.Play;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilities.Constants.GameObject.*;
import static utilities.LoadSave.*;

public class ObjectManager {
    private final Play play;
    private BufferedImage[][] potionImages, containerImages;
    private final ArrayList<Potion> potions;
    private final ArrayList<Container> containers;

    public ObjectManager(Play play) {
        this.play = play;
        loadImages();

        potions = new ArrayList<>();
        potions.add(new Potion(300, 300, RED_POTION));
        potions.add(new Potion(400, 300, BLUE_POTION));

        containers = new ArrayList<>();
        containers.add(new Container(500, 300, BARREL));
        containers.add(new Container(600, 300, BOX));
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
}
