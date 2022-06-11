package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Enemy extends Rectangle {
    private int hp = 100;
    private Boolean dead = false;
    Texture texture;
    Player target;
    private long lastBulletSpawn;
    public Array<Bullet> bullets;

    public Enemy(Player target) {
        if (texture == null) {
            texture = new Texture("monster-icon.png");
        }
        this.target = target;
        width = 48;
        height = 48;
        bullets = new Array<>();

        x = MathUtils.random(60, 752);
        y = MathUtils.random(60, 552);
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

    public Boolean isDead() {
        if (this.hp <= 0) {
            dead = true;
        }
        return dead;
    }

    public long getLastBulletSpawn() {
        return lastBulletSpawn;
    }

    public void addBullet(){
        bullets.add(new Bullet(x, y, target.x, target.y));
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
