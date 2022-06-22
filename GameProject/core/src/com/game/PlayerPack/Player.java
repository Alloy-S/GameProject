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

import java.net.PortUnreachableException;

public class Player extends Rectangle implements Character {

    private boolean newBul = false;
    private boolean doubleAtk = false;
    public long lastBulletSpawn;
    private int hp;
    private int damage;
    private boolean dead = false;
    public int movementSpeed = 300;
    private int xp;
    private int maxhp = 100;

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
    int skillCount;
    float time;
    int invisTime;
    boolean invis = false;
    int maxHp;
    private int level;
    public boolean levelUp = false;
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
       bullets = new Array<>();
       this.x = x;
       this.y = y;
       this.width = 45;
       this.height = 45;
       hp = 100;
       damage = 50;
       this.bulSpeed = 350;
        this.xp = 0;
        this.skillCount = 0;
        this.invisTime = 0;
        this.time = 0;
        this.level = 0;
        this.maxHp = this.hp;

    }

    public void takeDamage(int damage) {
        if(invis){
            damage = 0;
        }
        this.hp -= damage;
    }

    public void setInvisTime(int invisTime) {
        this.invisTime += invisTime;
    }

    public void addSkill(){
        this.skillCount++;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getSkillCount() {
        return skillCount;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public boolean isNewBul() {
        return newBul;
    }

    public void setNewBul() {
        this.newBul = true;
    }

    public void setDoubleAtk() {
        this.doubleAtk = true;
    }

    public void addXp(int xp){
        this.xp += xp;
        if (this.xp >= 100) {
            this.xp -= 100;
            this.level++;
            this.levelUp = true;
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getXp() {
        return xp;
    }

    @Override
    public void attack() {
        addBullet(Gdx.input.getX(), Gdx.graphics.getHeight()- Gdx.input.getY());

    }

    public void levelUp(){
        this.level++;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void render(SpriteBatch batch) {

        if (this.time < this.invisTime){
            invis = true;
            this.time = this.time + Gdx.graphics.getDeltaTime();
            System.out.println(time);
            if (this.time >= invisTime){
                this.invis = false;
                this.time = 0;
                this.invisTime = 0;
            }
        }

        player.update(batch);

        angleRad = (float) (Math.atan2(Gdx.input.getX() - this.x,Gdx.graphics.getHeight()- Gdx.input.getY() - this.y));
        angle = (float) Math.toDegrees(angleRad - Math.PI / 2);
        angle = Math.round(angle) <= 0 ? angle += 360 : angle;
        if (Math.round(angle) == 360)
            angle = 0;
        //System.out.println(angle);

        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            this.x -= movementSpeed * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyJustPressed(Input.Keys.A)) player.setCurrentAnimation(2);
            press = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.x += movementSpeed * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyJustPressed(Input.Keys.D)) player.setCurrentAnimation(0);
            press = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.y -= movementSpeed * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyJustPressed(Input.Keys.S)) player.setCurrentAnimation(1);
            press = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.y += movementSpeed * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) player.setCurrentAnimation(3);
            press = true;
        }

        if (this.x > 600 - 95) this.x = 600 - 95;
        if (this.x < 0) this.x = 0;
        if (this.y > 720 - 95) this.y = 720 - 95;
        if (this.y < 0) this.y = 0;


        if (!press || Gdx.input.isTouched()) {
            if (angle >= 45 && angle < 135) {
                //System.out.println("Down");
                player.setCurrentAnimation(PlayerMovement.stillDOWN);
                stage = 1;
            } else if (angle >= 135 && angle < 225) {
               // System.out.println("LEFT");
                player.setCurrentAnimation(PlayerMovement.stillLEFT);
                stage = 2;
            } else if (angle >= 225 && angle < 315) {
                //System.out.println("up");
                player.setCurrentAnimation(PlayerMovement.stillUP);
                stage = 3;
            } else if (angle >= 315 || angle < 45) {
                //System.out.println("RIGHT");
                player.setCurrentAnimation(PlayerMovement.stillRIGHT);
                stage = 0;
            }
        }
        press = false;
    }

    public void addBullet(float targetx, float targety){
        if (this.doubleAtk) {
            Bullet bullet1 = new Bullet(x - 15, y, targetx - 15, targety);
            Bullet bullet2 = new Bullet(x + 15, y, targetx + 15, targety);
            bullet1.setDamage(damage);
            bullet2.setDamage(damage);
            bullet1.setBulSpeed(this.bulSpeed);
            bullet2.setBulSpeed(this.bulSpeed);
            if (newBul) {
                bullet1.setTexture(new Texture("skill/newBull.png"));
                bullet2.setTexture(new Texture("skill/newBull.png"));
            }
            bullets.add(bullet1, bullet2);
        } else {
            Bullet bullet = new Bullet(x, y, targetx, targety);
            bullet.setDamage(damage);
            bullet.setBulSpeed(this.bulSpeed);
            if (newBul) {
                bullet.setTexture(new Texture("skill/newBull.png"));
            }
            bullets.add(bullet);
        }
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
