package com.game.EnemyPack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

@SuppressWarnings("unchecked")
public class GoblinMovement {
    //jumlah frame jalan
    private static final int WFRAME_COLS = 8; //kesamping
    private static final int WFRAME_ROWS = 1; //kebawah
    //jumlah frame attack
    private static final int AFRAME_COLS = 3; //kesamping
    private static final int AFRAME_ROWS = 1; //kebawah
    //jumlah frame dead
    private static final int DFRAME_COLS = 5; //kesamping
    private static final int DFRAME_ROWS = 1; //kebawah

    public static final int LEFT = 2;
    public static final int DOWN = 1;
    public static final int RIGHT = 0;
    public static final int UP = 3;

    private float stateTime = 0;
    private Enemy goblin;

    //animasi jalan dan diam
    private Animation<TextureRegion> upAnimation;
    private Animation<TextureRegion> downAnimation;
    private Animation<TextureRegion> rightAnimation;
    private Animation<TextureRegion> leftAnimation;
    private Animation<TextureRegion> stillleftAnimation;
    private Animation<TextureRegion> stillRightAnimation;
    private Animation<TextureRegion> stillUpAnimation;
    private Animation<TextureRegion> stillDownAnimation;

    //animasi attack dan mati
    private Animation<TextureRegion> attackUpAnimation;
    private Animation<TextureRegion> attackDownAnimation;
    private Animation<TextureRegion> attackRightAnimation;
    private Animation<TextureRegion> attackLeftAnimation;
    private Animation<TextureRegion> deadAnimation;

    //textureregion jalan
    private TextureRegion[] upFrames;
    private TextureRegion[] downFrames;
    private TextureRegion[] rightFrames;
    private TextureRegion[] leftFrames;

    //texture region attack
    private TextureRegion[] attackUpFrames;
    private TextureRegion[] attackDownFrames;
    private TextureRegion[] attackRightFrames;
    private TextureRegion[] attackLeftFrames;

    //texture region mati
    private TextureRegion[] deadFrames;

    private TextureRegion currentFrame;
    private Animation[] animations;
    private int currentAnimation;

    public GoblinMovement(Enemy goblin, TextureRegion textureRegionUp, TextureRegion textureRegionDown, TextureRegion textureRegionLeft, TextureRegion textureRegionRight, TextureRegion textureRegionAttUp, TextureRegion textureRegionAttDown, TextureRegion textureRegionAttLeft, TextureRegion textureRegionAttRight){
        this.goblin = goblin;
        //still Left
        TextureRegion[][] tmp = textureRegionLeft.split(textureRegionLeft.getRegionWidth() / WFRAME_COLS,
                textureRegionLeft.getRegionHeight() / WFRAME_ROWS);
        leftFrames = new TextureRegion[1];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                leftFrames[index++] = tmp[i][j];
            }
        }
        stillleftAnimation = new Animation(0.25f, (Object[]) leftFrames);
        stillleftAnimation.setPlayMode(Animation.PlayMode.LOOP);

        //still right
        tmp = textureRegionRight.split(textureRegionRight.getRegionWidth() / WFRAME_COLS,
                textureRegionRight.getRegionHeight() / WFRAME_ROWS);
        rightFrames = new TextureRegion[1];
        index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                rightFrames[index++] = tmp[i][j];
            }
        }
        stillRightAnimation = new Animation(0.25f, (Object[]) rightFrames);
        stillRightAnimation.setPlayMode(Animation.PlayMode.LOOP);

        //still up
        tmp = textureRegionUp.split(textureRegionUp.getRegionWidth() / WFRAME_COLS,
                textureRegionUp.getRegionHeight() / WFRAME_ROWS);
        upFrames = new TextureRegion[1];
        index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                upFrames[index++] = tmp[i][j];
            }
        }
        stillUpAnimation = new Animation(0.25f, (Object[]) upFrames);
        stillUpAnimation.setPlayMode(Animation.PlayMode.LOOP);

        //still down
        tmp = textureRegionDown.split(textureRegionDown.getRegionWidth() / WFRAME_COLS,
                textureRegionDown.getRegionHeight() / WFRAME_ROWS);
        downFrames = new TextureRegion[1];
        index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                downFrames[index++] = tmp[i][j];
            }
        }
        stillDownAnimation = new Animation(0.25f, (Object[]) downFrames);
        stillDownAnimation.setPlayMode(Animation.PlayMode.LOOP);


        // up Animation
        tmp = textureRegionUp.split(textureRegionUp.getRegionWidth() / WFRAME_COLS,
                textureRegionUp.getRegionHeight() / WFRAME_ROWS);
        upFrames = new TextureRegion[WFRAME_COLS * WFRAME_ROWS];
        index = 0;
        for (int i = 0; i < WFRAME_ROWS; i++) {
            for (int j = 0; j < WFRAME_COLS; j++) {
                upFrames[index++] = tmp[i][j];
            }
        }
        upAnimation = new Animation(0.25f, (Object[]) upFrames);
        upAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // down Animation
        tmp = textureRegionDown.split(textureRegionDown.getRegionWidth() / WFRAME_COLS,
                textureRegionDown.getRegionHeight() / WFRAME_ROWS);
        downFrames = new TextureRegion[WFRAME_COLS * WFRAME_ROWS];
        index = 0;
        for (int i = 0; i < WFRAME_ROWS; i++) {
            for (int j = 0; j < WFRAME_COLS; j++) {
                downFrames[index++] = tmp[i][j];
            }
        }
        downAnimation = new Animation(0.25f, (Object[]) downFrames);
        downAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // right Animation
        tmp = textureRegionRight.split(textureRegionRight.getRegionWidth() / WFRAME_COLS,
                textureRegionRight.getRegionHeight() / WFRAME_ROWS);
        rightFrames = new TextureRegion[WFRAME_COLS * WFRAME_ROWS];
        index = 0;
        for (int i = 0; i < WFRAME_ROWS; i++) {
            for (int j = 0; j < WFRAME_COLS; j++) {
                rightFrames[index++] = tmp[i][j];
            }
        }
        rightAnimation = new Animation(0.25f, (Object[]) rightFrames);
        rightAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // left Animation
        tmp = textureRegionLeft.split(textureRegionLeft.getRegionWidth() / WFRAME_COLS,
                textureRegionLeft.getRegionHeight() / WFRAME_ROWS);
        leftFrames = new TextureRegion[WFRAME_COLS * WFRAME_ROWS];
        index = 0;
        for (int i = 0; i < WFRAME_ROWS; i++) {
            for (int j = 0; j < WFRAME_COLS; j++) {
                leftFrames[index++] = tmp[i][j];
            }
        }
        leftAnimation = new Animation(0.25f, (Object[]) leftFrames);
        leftAnimation.setPlayMode(Animation.PlayMode.LOOP);

        //attack up
        tmp = textureRegionAttUp.split(textureRegionAttUp.getRegionWidth() / AFRAME_COLS,
                textureRegionAttUp.getRegionHeight() / AFRAME_ROWS);
        attackUpFrames = new TextureRegion[AFRAME_COLS * AFRAME_ROWS];
        index = 0;
        for (int i = 0; i < AFRAME_ROWS; i++) {
            for (int j = 0; j < AFRAME_COLS; j++) {
                attackUpFrames[index++] = tmp[i][j];
            }
        }
        attackUpAnimation = new Animation(0.25f, (Object[]) attackUpFrames);
        attackUpAnimation.setPlayMode(Animation.PlayMode.LOOP);

        //attack down
        tmp = textureRegionAttDown.split(textureRegionAttDown.getRegionWidth() / AFRAME_COLS,
                textureRegionAttDown.getRegionHeight() / AFRAME_ROWS);
        attackDownFrames = new TextureRegion[AFRAME_COLS * AFRAME_ROWS];
        index = 0;
        for (int i = 0; i < AFRAME_ROWS; i++) {
            for (int j = 0; j < AFRAME_COLS; j++) {
                attackDownFrames[index++] = tmp[i][j];
            }
        }
        attackDownAnimation = new Animation(0.25f, (Object[]) attackDownFrames);
        attackDownAnimation.setPlayMode(Animation.PlayMode.LOOP);

        //attack right
        tmp = textureRegionAttRight.split(textureRegionAttRight.getRegionWidth() / AFRAME_COLS,
                textureRegionAttRight.getRegionHeight() / AFRAME_ROWS);
        attackRightFrames = new TextureRegion[AFRAME_COLS * AFRAME_ROWS];
        index = 0;
        for (int i = 0; i < AFRAME_ROWS; i++) {
            for (int j = 0; j < AFRAME_COLS; j++) {
                attackRightFrames[index++] = tmp[i][j];
            }
        }
        attackRightAnimation = new Animation(0.25f, (Object[]) attackRightFrames);
        attackRightAnimation.setPlayMode(Animation.PlayMode.LOOP);

        //attack left
        tmp = textureRegionAttLeft.split(textureRegionAttLeft.getRegionWidth() / AFRAME_COLS,
                textureRegionAttLeft.getRegionHeight() / AFRAME_ROWS);
        attackLeftFrames = new TextureRegion[AFRAME_COLS * AFRAME_ROWS];
        index = 0;
        for (int i = 0; i < AFRAME_ROWS; i++) {
            for (int j = 0; j < AFRAME_COLS; j++) {
                attackLeftFrames[index++] = tmp[i][j];
            }
        }
        attackLeftAnimation = new Animation(0.25f, (Object[]) attackLeftFrames);
        attackLeftAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // Array of Animations
        animations = new Animation[12];
        animations[0] = leftAnimation;
        animations[1] = downAnimation;
        animations[2] = rightAnimation;
        animations[3] = upAnimation;
        animations[4] = stillleftAnimation;
        animations[5] = stillDownAnimation;
        animations[6] = stillRightAnimation;
        animations[7] = stillUpAnimation;
        animations[8] = attackLeftAnimation;
        animations[9] = attackDownAnimation;
        animations[10] = attackRightAnimation;
        animations[11] = attackUpAnimation;

        // Initial currentAnimation
        setCurrentAnimation(7);
    }
    public void setCurrentAnimation(int currentAnimation) {
        this.currentAnimation = currentAnimation;
        stateTime = 0;
    }
    public int getCurrentAnimation() {
        return currentAnimation;
    }
    public void update(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = (TextureRegion) animations[currentAnimation].getKeyFrame(stateTime, true);

        batch.draw(currentFrame, goblin.x - 16, goblin.y);
    }
}
