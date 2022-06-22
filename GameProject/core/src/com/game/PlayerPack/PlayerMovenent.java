package com.game.PlayerPack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

@SuppressWarnings("unchecked")
class PlayerMovement {
    private static final int FRAME_COLS = 7; //kesamping
    private static final int FRAME_ROWS = 1; //kebawah

    public static final int LEFT = 0;
    public static final int DOWN = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int stillLEFT = 6;
    public static final int stillDOWN = 5;
    public static final int stillRIGHT = 4;
    public static final int stillUP = 7;
    private int currentAnimation;

    private float stateTime = 0;

    private Animation<TextureRegion> upAnimation;
    private Animation<TextureRegion> downAnimation;
    private Animation<TextureRegion> rightAnimation;
    private Animation<TextureRegion> leftAnimation;
    private Animation<TextureRegion> stillleftAnimation;
    private Animation<TextureRegion> stillRightAnimation;
    private Animation<TextureRegion> stillUpAnimation;
    private Animation<TextureRegion> stillDownAnimation;

    private TextureRegion[] upFrames;
    private TextureRegion[] downFrames;
    private TextureRegion[] rightFrames;
    private TextureRegion[] leftFrames;

    private TextureRegion currentFrame;
    private Animation[] animations;

    private Sound stepSound;
    private int stepIndex = 0;
    private Player player;

    public PlayerMovement(Player player, TextureRegion textureRegionUp, TextureRegion textureRegionDown, TextureRegion textureRegionLeft, TextureRegion textureRegionRight, Sound stepSound) {
        this.player = player;
        this.stepSound = stepSound;

        //still Left
        TextureRegion[][] tmp = textureRegionLeft.split(textureRegionLeft.getRegionWidth() / FRAME_COLS,
                textureRegionLeft.getRegionHeight() / FRAME_ROWS);
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
        tmp = textureRegionRight.split(textureRegionRight.getRegionWidth() / FRAME_COLS,
                textureRegionRight.getRegionHeight() / FRAME_ROWS);
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
        tmp = textureRegionUp.split(textureRegionUp.getRegionWidth() / FRAME_COLS,
                textureRegionUp.getRegionHeight() / FRAME_ROWS);
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
        tmp = textureRegionDown.split(textureRegionDown.getRegionWidth() / FRAME_COLS,
                textureRegionDown.getRegionHeight() / FRAME_ROWS);
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
        tmp = textureRegionUp.split(textureRegionUp.getRegionWidth() / FRAME_COLS,
                textureRegionUp.getRegionHeight() / FRAME_ROWS);
        upFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                upFrames[index++] = tmp[i][j];
            }
        }
        upAnimation = new Animation(0.25f, (Object[]) upFrames);
        upAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // down Animation
        tmp = textureRegionDown.split(textureRegionDown.getRegionWidth() / FRAME_COLS,
                textureRegionDown.getRegionHeight() / FRAME_ROWS);
        downFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                downFrames[index++] = tmp[i][j];
            }
        }
        downAnimation = new Animation(0.25f, (Object[]) downFrames);
        downAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // right Animation
        tmp = textureRegionRight.split(textureRegionRight.getRegionWidth() / FRAME_COLS,
                textureRegionRight.getRegionHeight() / FRAME_ROWS);
        rightFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                rightFrames[index++] = tmp[i][j];
            }
        }
        rightAnimation = new Animation(0.25f, (Object[]) rightFrames);
        rightAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // left Animation
        tmp = textureRegionLeft.split(textureRegionLeft.getRegionWidth() / FRAME_COLS,
                textureRegionLeft.getRegionHeight() / FRAME_ROWS);
        leftFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                leftFrames[index++] = tmp[i][j];
            }
        }
        leftAnimation = new Animation(0.25f, (Object[]) leftFrames);
        leftAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // Array of Animations
        animations = new Animation[8];
        animations[0] = leftAnimation;
        animations[1] = downAnimation;
        animations[2] = rightAnimation;
        animations[3] = upAnimation;
        animations[4] = stillleftAnimation;
        animations[5] = stillDownAnimation;
        animations[6] = stillRightAnimation;
        animations[7] = stillUpAnimation;

        // Initial currentAnimation
        setCurrentAnimation(7);
    }

    public void setCurrentAnimation(int currentAnimation) {
        this.currentAnimation = currentAnimation;
        stateTime = 0;
        stepIndex = 0;
    }

    public int getCurrentAnimation() {
        return currentAnimation;
    }

    public void update(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

//        //untuk mengatur suara step ketika lari agar lbh cepat
//        if(animations[currentAnimation].getKeyFrameIndex(stateTime) != stepIndex){
//            if(animations[currentAnimation].getKeyFrameIndex(stateTime) == 0
//                    || animations[currentAnimation].getKeyFrameIndex(stateTime) == 12) {
//                stepIndex = animations[currentAnimation].getKeyFrameIndex(stateTime);
//                stepSound.play();
//            }
//        }

        currentFrame = (TextureRegion) animations[currentAnimation].getKeyFrame(stateTime, true);

        //batch.begin();
        batch.draw(currentFrame, player.x - 32, player.y - 5);
        //batch.end();
    }
}
