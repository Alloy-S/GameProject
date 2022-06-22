package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import javax.xml.stream.events.StartDocument;
import java.nio.file.attribute.UserPrincipal;

public class Bullet extends Rectangle {
    public int bulSpeed = 500;
    private static Texture texture;
    public Vector2 direction;
    public Vector2 position;
    private int damage;
    private boolean doubleAtk = false;

    public boolean remove = false;

    public void setBulSpeed(int bulSpeed) {
        this.bulSpeed = bulSpeed;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }



    public Bullet (float x, float y, float targetx, float targety) {
        if (texture == null) {
            texture = new Texture("bomb.png");
        }

        width = 16;
        height = 16;
        position = new Vector2(x, y);
        direction = new Vector2();

        damage = 20;

        direction.x = targetx - position.x;
        direction.y = targety - position.y;
        float length = (float) Math.sqrt((direction.x * direction.x) + (direction.y * direction.y));

        if (length != 0) {
            direction.x = direction.x / length;
            direction.y = direction.y / length;
        }
    }

    public void render (SpriteBatch bacth) {
        position.x += direction.x * bulSpeed * Gdx.graphics.getDeltaTime();
        position.y += direction.y * bulSpeed * Gdx.graphics.getDeltaTime();
        this.x = position.x;
        this.y = position.y;
        bacth.draw(texture, position.x, position.y, 24, 24);
    }

    public int getDamage() {
        return damage;
    }

    public static void setTexture(Texture texture) {
        Bullet.texture = texture;
    }
}
