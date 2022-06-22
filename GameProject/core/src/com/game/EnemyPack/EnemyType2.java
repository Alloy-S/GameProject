package com.game.EnemyPack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.game.EnemyPack.Enemy;
import com.game.PlayerPack.Player;
import com.game.Bullet;

public class EnemyType2 extends Enemy {
    int moveSpeed;
    float maxY;
    float minY;
    int distance = 100;
    int mode;
    private MageMovement mage;
    private Assets assetMage;
    private Skin skin;

    public EnemyType2(Player target, Array obj) {
        super(target, obj);
        /*
        bulspeed adalah kecepatan peluru
        attack time adalah banyaknya serangan perdetik
         */
        super.setBulSpeed(250);
        super.setAttackTime(900000000);
        moveSpeed = 80;
        setRandomPosition();
        checkY();

        maxY = this.y + distance;
        minY = this.y - distance;

        assetMage = new Assets();
        assetMage.load("mageMovement.pack");
        assetMage.manager.finishLoading();
        skin = new Skin();
        skin.addRegions(assetMage.manager.get("mageMovement.pack", TextureAtlas.class));
        mage = new MageMovement(this, skin.getRegion("mageMove"));
    }

    //mengecek tempat spawn agar tidak melebihi window
    public void checkY(){
        if(mode == 0) {
            if (this.y + 100 > 720-getHeight()) {
                super.setY(this.y - 100);
            }
            if (this.y - 100 < getHeight()) {
                super.setY(this.y + 100);
            }
        }

    }

    @Override
    public void render(SpriteBatch batch){
        moveY();
        mage.update(batch);
    }

    public void moveY(){
        this.y += moveSpeed * Gdx.graphics.getDeltaTime();
        if (this.y > maxY) {
            this.y = maxY;
            moveSpeed *= -1;
        }
        if(this.y < minY){
            this.y = minY;
            this.moveSpeed *=-1;
        }

    }
}
