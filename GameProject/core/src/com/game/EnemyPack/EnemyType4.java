package com.game.EnemyPack;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.game.Character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.game.PlayerPack.Player;

public class EnemyType4 extends Enemy {

    Vector2 position;
    Vector2 direction;
    int moveSpeed;
    float length, targety, targetx;
    int damage;
    boolean hide = false;
    float time = 0;
    private BatMovement batMovement;
    private Skin skin;
    private Assets assets;


    public EnemyType4(Player target, Array obj) {
        super(target, obj);
        this.damage = 10;
        super.setAttackTime(1000000000);
        this.bulSpeed = 200;
        this.moveSpeed = 100;
        targetx = 0;
        targety = 0;

        setRandomPosition();
        position = new Vector2(this.x, this.y);
        direction = new Vector2();

        assets = new Assets();
        assets.load("batMove.pack");
        assets.manager.finishLoading();

        skin = new Skin();
        skin.addRegions(assets.manager.get("batMove.pack", TextureAtlas.class));
//untuk mengambil frame" gerakan dengan nama dalam pack
        batMovement = new BatMovement(this, skin.getRegion("batMove"));
    }

    @Override
    public void addBullet(){
        if(!hide){
            super.addBullet();
        }
    }

    public float checkLength(float targetx, float targety){
        float checkX = targetx - position.x;
        float checkY = targety - position.y;

        float checkLength = (float) Math.sqrt((checkX*checkX) + (checkY*checkY));
        return checkLength;
    }



    public void move(){
        //cek jika jarak ke target < 10 maka hide = false
        if(length<10) {
            this.hide = false;
            time += Gdx.graphics.getDeltaTime();
            targetx = position.x;
            targety = position.y;
            //jika time sudah mencapai 5, mulai bergerakdan dan hide = true
            if(time>5) {
                do {
                    targetx = MathUtils.random(0, 700);
                    targety = MathUtils.random(60, 500);
                } while (checkLength(targetx, targety)<300);
                time -= 5;
                this.hide = true;
            }
        }

        direction.x = targetx - position.x;
        direction.y = targety - position.y;
        length = (float) Math.sqrt((direction.x * direction.x) + (direction.y * direction.y));

        if (length != 0) {
            direction.x = (direction.x / (length));
            direction.y = (direction.y / (length));
        }

        position.x += direction.x * moveSpeed * Gdx.graphics.getDeltaTime();
        position.y += direction.y * moveSpeed * Gdx.graphics.getDeltaTime();
        this.x = position.x;
        this.y = position.y;
    }

    @Override
    public void takeDamage(int damage){
        if(this.hide){
            damage = 0;
        }
        this.hp -= damage;
    }

    public void render(SpriteBatch batch){
        move();
        if(!this.hide) {
            batMovement.update(batch);
        }
    }

}
