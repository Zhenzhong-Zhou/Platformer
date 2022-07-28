package objects;

import static main.Game.SCALE;

public class Potion extends GameObject {
    private float hoverOffset;
    private int maxHoverOffset, hoverDirection = 1;
    public Potion(int x, int y, int objectType) {
        super(x, y, objectType);
        doAnimation = true;
        initHitbox(7, 14);
        xDrawOffset = (int) (3 * SCALE);
        yDrawOffset = (int) (2 * SCALE);

        maxHoverOffset = (int) (10*SCALE);
    }

    public void update() {
        updateAnimationTick();
        updateHover();
    }

    private void updateHover() {
        hoverOffset += (0.065f * SCALE*hoverDirection);

        if(hoverOffset>=maxHoverOffset) {
            hoverDirection = -1;
        }else if(hoverOffset<0){
            hoverDirection =1;
        }
        hitbox.y = y+hoverOffset;
    }
}
