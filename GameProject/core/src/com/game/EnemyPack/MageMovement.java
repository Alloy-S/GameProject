package com.game.EnemyPack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

@SuppressWarnings("unchecked")
public class MageMovement {
    //jumlah frame jalan
    private static final int WFRAME_COLS = 10; //kesamping
    private static final int WFRAME_ROWS = 1; //kebawah
    //animasi jalan
    private Animation<TextureRegion> walkAnimation;
    //textureregion jalan
    private TextureRegion[] walkFrames;

    private float stateTime = 0;
    private TextureRegion currentFrame;
    private Animation[] animations;
    private int currentAnimation;
    private Enemy mage;

    public MageMovement(Enemy mage, TextureRegion textureRegionWalk){
        this.mage = mage;
        TextureRegion[][] tmp = textureRegionWalk.split(textureRegionWalk.getRegionWidth() / WFRAME_COLS,
                textureRegionWalk.getRegionHeight() / WFRAME_ROWS);
        walkFrames = new TextureRegion[WFRAME_COLS*WFRAME_ROWS];
        int index = 0;
        for (int i = 0; i < WFRAME_ROWS; i++) {
            for (int j = 0; j < WFRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.075f, (Object[]) walkFrames);
        walkAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // Array of Animations
        animations = new Animation[1];
        animations[0] = walkAnimation;
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
        currentFrame = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);

        batch.draw(currentFrame, mage.x, mage.y);

    }
}
