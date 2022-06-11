package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Player extends Rectangle {

    Sprite sprite;
    Texture texture;
//    Vector2 vec;
//    Vector2 position;
    long lastBulletSpawn;
    int hp;
    int damage;
    boolean dead = false;
    int movementSpeed = 300;

    public Array<Bullet> bullets;

    public Player(float x, float y) {
       texture = new Texture(Gdx.files.internal("kaktus.png"));
       sprite = new Sprite(texture);
       bullets = new Array<>();
//       vec = new Vector2();
//       position = new Vector2(800 / 2 - 64 / 2, 20);
        this.x = x;
        this.y = y;
       width = 64;
       height = 64;
        hp = 100;
        damage = 50;
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
        System.out.println("x : " + this.x);
        System.out.println("y : " + this.y);
    }

    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture, x, y);
        batch.end();
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    public void addBullet(float targetx, float targety){
        bullets.add(new Bullet(x, y, targetx, targety));
        lastBulletSpawn = TimeUtils.nanoTime();
    }

    public void removeBullet(Bullet bullet){
        bullets.removeValue(bullet, true);
    }

    public Boolean isDead() {
        if (this.hp <= 0) {
            dead = true;
        }
        return dead;
    }
}
