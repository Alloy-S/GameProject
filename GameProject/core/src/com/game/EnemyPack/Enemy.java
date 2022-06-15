package com.game.EnemyPack;
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
    private int hp = 100;
    private Boolean dead = false;
    Texture texture;
    //Player target;
    int bulSpeed;
    long attackTime;
    private long lastBulletSpawn;
    public Array<Bullet> bullets;
    SpriteBatch batch;
    int damage;

    public Enemy(com.game.PlayerPack.Player target) {
        if (texture == null) {
            texture = new Texture("monster-icon.png");
        }

        batch = new SpriteBatch();
        this.target = target;
        width = 48;
        height = 48;
        bullets = new Array<>();
        this.damage = 10;
        x = MathUtils.random(60, 752);
        y = MathUtils.random(60, 552);
        this.attackTime = 1000000000;
        this.bulSpeed = 500;
    }

    public int getHp() {
        return hp;
    }

    public void render (SpriteBatch bacth) {
        //bacth.draw();
        bacth.draw(texture, x, y);
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    @Override
    public void attack(Character target) {
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

                if(bullet.position.x<0 || bullet.position.x > 800-16 || bullet.position.y > 600-16
                        || bullet.position.y<0){
                    removeBullet(bullet);
                }
            }
        }
        batch.end();
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

    public void setAttackTime(long attackTime) {
        this.attackTime = attackTime;
    }

    public void setBulSpeed(int bulSpeed) {
        this.bulSpeed = bulSpeed;
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
