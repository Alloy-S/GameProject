package com.game.EnemyPack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

@SuppressWarnings("unchecked")
public class BeeMovement {
    //jumlah frame tetap
    private static final int WFRAME_COLS = 3; //kesamping
    private static final int WFRAME_ROWS = 1; //kebawah
    //animasi tetap
    private Animation<TextureRegion> stillAnimation;
    //textureregion tetap
    private TextureRegion[] stillFrames;

    private float stateTime = 0;
    private TextureRegion currentFrame;
    private Animation[] animations;
    private int currentAnimation;
    Enemy bee;

    public BeeMovement(Enemy bee, TextureRegion textureRegionStill){
        this.bee = bee;
        TextureRegion[][] tmp = textureRegionStill.split(textureRegionStill.getRegionWidth() / WFRAME_COLS,
                textureRegionStill.getRegionHeight() / WFRAME_ROWS);
        stillFrames = new TextureRegion[WFRAME_COLS*WFRAME_ROWS];
        int index = 0;
        for (int i = 0; i < WFRAME_ROWS; i++) {
            for (int j = 0; j < WFRAME_COLS; j++) {
                stillFrames[index++] = tmp[i][j];
            }
        }
        stillAnimation = new Animation(0.14f, (Object[]) stillFrames);
        stillAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // Array of Animations
        animations = new Animation[1];
        animations[0] = stillAnimation;
    }


    public void setCurrentAnimation(int currentAnimation) {
        this.currentAnimation = currentAnimation;
        stateTime = 0 ;
    }

    public int getCurrentAnimation() {
        return currentAnimation;
    }

    public void update(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = (TextureRegion) stillAnimation.getKeyFrame(stateTime, true);

        batch.draw(currentFrame, bee.x, bee.y);
    }
}
