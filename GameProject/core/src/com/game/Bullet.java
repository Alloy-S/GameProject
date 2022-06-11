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
    public static int attackSpeed = 500;
    private static Texture texture;
    Vector2 direction;
    Vector2 position;
    Sprite sprite;
    private int damage;
    private final float SET_MOUSE_LIMIT = 150;

    public boolean remove = false;
    public Bullet (float x, float y, float targetx, float targety) {
//        this.x = x;
//        this.y = y;
        if (texture == null) {
            texture = new Texture("bomb.png");
            sprite = new Sprite(texture);
        }

        width = 16;
        height = 16;
        position = new Vector2(x, y);
        direction = new Vector2();

        damage = 20;
//        if (targetx > position.x + SET_MOUSE_LIMIT) {
//            targetx = position.x + SET_MOUSE_LIMIT;
//        } else if (targetx < position.x - SET_MOUSE_LIMIT) {
//            targetx = position.x - SET_MOUSE_LIMIT;
//        }
//
//        if (targety > position.y + SET_MOUSE_LIMIT) {
//            targety = position.y + SET_MOUSE_LIMIT;
//        } else if (targety < position.y - SET_MOUSE_LIMIT) {
//            targety = position.y - SET_MOUSE_LIMIT;
//        }

        //vec.set(targetx - position.x, (targety - position.y));

        direction.x = targetx - position.x;
        direction.y = targety - position.y;
        float length = (float) Math.sqrt((direction.x * direction.x) + (direction.y * direction.y));

        if (length != 0) {
            direction.x = direction.x / length;
            direction.y = direction.y / length;
        }
    }

    public void update(float deltaTime) {
//        y += speed * deltaTime;
        if (position.x > 800) {
            remove = true;
        }
        if (position.y > 600) {
            remove = true;
        }
        position.x += direction.x * attackSpeed * deltaTime;
        position.y += direction.y * attackSpeed * deltaTime;
        this.x = position.x;
        this.y = position.y;
    }

    public void render (SpriteBatch bacth) {
        bacth.draw(texture, position.x, position.y);
    }



    public int getDamage() {
        return damage;
    }
}
