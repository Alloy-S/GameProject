package com.game.PlayerPack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.game.PlayerPack.AssetsPlayer;
import com.game.PlayerPack.PlayerMovement;
import com.game.Character;
import com.game.Bullet;

public class Player extends Rectangle implements Character {

    Texture texture;
    public long lastBulletSpawn;
    public int hp;
    public int damage;
    private boolean dead = false;
    public int movementSpeed = 300;

    ApplicationAdapter applicationAdapter;
    SpriteBatch batch;
    private Skin skin;
    public static int WIDTH = 854;
    public static int HEIGHT = 480;
    private AssetsPlayer assets;
    private PlayerMovement player;
    float angleRad;
    float angle;
    boolean press;
    public boolean keyPressed;
    int stage;
    int bulSpeed;

    public Array<Bullet> bullets;

    public Player(float x, float y) {
        batch = new SpriteBatch();
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);

        assets = new AssetsPlayer();
        assets.load();
        assets.manager.finishLoading();

        skin = new Skin();
        skin.addRegions(assets.manager.get("Movement.pack", TextureAtlas.class));

        stage = 5;

        player = new PlayerMovement(this, skin.getRegion("up"), skin.getRegion("down"), skin.getRegion("right"), skin.getRegion("left"), assets.manager.get("stepSound.wav", Sound.class));
       texture = new Texture(Gdx.files.internal("kaktus.png"));
       bullets = new Array<>();
       this.x = x;
       this.y = y;
       this.width = 64;
       this.height = 64;
       hp = 100;
       damage = 50;
       this.bulSpeed = 350;

    }

    public void update(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.A)) this.x -= movementSpeed * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.D)) this.x += movementSpeed * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.S)) this.y -= movementSpeed * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.W)) this.y += movementSpeed * Gdx.graphics.getDeltaTime();

        if (this.x > 800 - 64) this.x = 800 - 64;
        if (this.x < -64) this.x = -64;
        if (this.y > 600 - 64) this.y = 600 - 64;
        if (this.y < -64) this.y = -64;
//        System.out.println("x : " + this.x);
//        System.out.println("y : " + this.y);
    }

    public void draw(SpriteBatch batch) {

        batch.begin();
        batch.draw(texture, x, y);
        batch.end();
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    @Override
    public void attack(Character target) {

    }

    @Override
    public void render(SpriteBatch batch) {

        player.update(batch);

        angleRad = (float) (Math.atan2(Gdx.input.getX() - this.x,Gdx.graphics.getHeight()- Gdx.input.getY() - this.y));
        angle = (float) Math.toDegrees(angleRad - Math.PI / 2);
        angle = Math.round(angle) <= 0 ? angle += 360 : angle;
        if (Math.round(angle) == 360)
            angle = 0;
        System.out.println(angle);

        if (Gdx.input.isKeyPressed(Input.Keys.A)) this.x -= movementSpeed * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.D)) this.x += movementSpeed * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.S)) this.y -= movementSpeed * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) this.y += movementSpeed * Gdx.graphics.getDeltaTime();

        if (this.x > 800 - 95) this.x = 800 - 95;
        if (this.x < 0) this.x = 0;
        if (this.y > 600 - 95) this.y = 600 - 95;
        if (this.y < 0) this.y = 0;

        if ((Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D))) {
            press = true;
            if ((Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.D))) {
                player.setCurrentAnimation(stage);
            }
        } else {
            press = false;
        }

        if (!press) {
            if (angle >= 45 && angle < 135) {
                System.out.println("Down");
                player.setCurrentAnimation(PlayerMovement.stillDOWN);
                stage = 1;
            } else if (angle >= 135 && angle < 225) {
                System.out.println("LEFT");
                player.setCurrentAnimation(PlayerMovement.stillLEFT);
                stage = 2;
            } else if (angle >= 225 && angle < 315) {
                System.out.println("up");
                player.setCurrentAnimation(PlayerMovement.stillUP);
                stage = 3;
            } else if (angle >= 315 || angle < 45) {
                System.out.println("RIGHT");
                player.setCurrentAnimation(PlayerMovement.stillRIGHT);
                stage = 0;
            }
        }
    }

    public void addBullet(float targetx, float targety){
        Bullet bullet = new Bullet(x, y, targetx, targety);
        bullet.setDamage(damage);
        bullet.setBulSpeed(this.bulSpeed);
        bullets.add(bullet);
        lastBulletSpawn = TimeUtils.nanoTime();
    }

    public void removeBullet(Bullet bullet){
        bullets.removeValue(bullet, true);
    }

    @Override
    public boolean isDead() {
        if (this.hp <= 0) {
            dead = true;
            this.hp = 0;
        }
        return dead;
    }
}
