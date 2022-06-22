package com.game.EnemyPack;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.game.Character;
import com.game.Bullet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.game.PlayerPack.Player;

public class Enemy extends Rectangle implements Character{
    Character target;
    public int hp = 100;
    private Boolean dead = false;
    Texture texture;
    int bulSpeed;
    long attackTime;
    int damage;
    public long lastBulletSpawn;
    public Array<Bullet> bullets;
    SpriteBatch batch;
    BitmapFont font;
    private int xp;
    private int movementSpeed;
    private Assets assetBee;
    private BeeMovement beeMovement;
    Skin skin;
    Array<Rectangle> obj;


    public Enemy(com.game.PlayerPack.Player target, Array<Rectangle> obj) {
        if (texture == null) {
            texture = new Texture("monster-icon.png");
        }
        this.obj = obj;
        font = new BitmapFont();
        batch = new SpriteBatch();
        this.target = target;
        width = 48;
        height = 48;
        bullets = new Array<>();
        this.damage = 10;
        x = MathUtils.random(60, 652);
        y = MathUtils.random(60, 552);
        this.attackTime = 1000000000;
        this.bulSpeed = 500;
        this.xp = 25;
        setRandomPosition();

        assetBee = new Assets();
        assetBee.load("beeMove.pack");
        assetBee.manager.finishLoading();

        skin = new Skin();
        skin.addRegions(assetBee.manager.get("beeMove.pack", TextureAtlas.class));

        beeMovement = new BeeMovement(this, skin.getRegion("beeMove"));
    }

    public int getHp() {
        return hp;
    }

    public int getXp() {
        return xp;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void render (SpriteBatch batch) {
        //bacth.draw();
        beeMovement.update(batch);
        displayHp(batch);
    }

    public void displayHp(SpriteBatch batch) {
        font.draw(batch, "HP = " + this.hp, this.x, this.y);
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    @Override
    public void attack() {
        batch.begin();
        if (bullets.isEmpty()){
            if (TimeUtils.nanoTime() - lastBulletSpawn > attackTime) {
                addBullet();
            }
        }else {

                for (Bullet bullet : bullets) {
                    bullet.render(batch);
                    if (TimeUtils.nanoTime() - lastBulletSpawn > attackTime) addBullet();

                    if (bullet.overlaps((Rectangle) target)) {
                        target.takeDamage(this.damage);
                        removeBullet(bullet);
                    }
                    for (Rectangle wall: obj) {
                        if (bullet.overlaps(wall)) {
                            removeBullet(bullet);
                        }
                    }
                    if (bullet.position.x < 0 || bullet.position.x > 600 - 16 || bullet.position.y > 720 - 16
                            || bullet.position.y < 0) {
                        removeBullet(bullet);
                    }
                }
            }

        batch.end();
    }

    public void setRandomPosition() {
        this.x = MathUtils.random(100, 600 - 100);
        this.y = MathUtils.random(100, 700 - 100);
    }

    public boolean isDead() {
        if (this.hp <= 0) {
            dead = true;
        }
        return dead;
    }

    public long getLastBulletSpawn() {
        return lastBulletSpawn;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAttackTime(long attackTime) {
        this.attackTime = attackTime;
    }

    public void setBulSpeed(int bulSpeed) {
        this.bulSpeed = bulSpeed;
    }

    public int getBulSpeed() {
        return bulSpeed;
    }

    public long getAttackTime() {
        return attackTime;
    }

    public void addBullet(){
        Bullet bullet = new Bullet(x, y, ((Player)target).x, ((Player)target).y);
        bullet.setDamage(getDamage());
        bullet.setBulSpeed(this.bulSpeed);
        bullets.add(bullet);
        lastBulletSpawn = TimeUtils.nanoTime();
    }

    public Texture getImg() {
        return texture;
    }

    @Override
    public float getX() {
        return super.getX();
    }

    @Override
    public float getY() {
        return super.getY();
    }

    public void removeBullet(Bullet bullet){
        bullets.removeValue(bullet, true);
    }
}
