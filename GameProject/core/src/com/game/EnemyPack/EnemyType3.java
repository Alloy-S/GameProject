package com.game.EnemyPack;

import com.game.Character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.PlayerPack.Player;

public class EnemyType3 extends Enemy {

    Vector2 position;
    Vector2 direction;
    int moveSpeed;
    float length;

    public EnemyType3(Character target) {
        super((Player) target);
        super.setHp(100);
        super.setDamage(25);
        this.height = 16;
        this.width = 16;
        this.moveSpeed = 30;
        setRandomPosition();

        position = new Vector2(this.x, this.y);
        direction = new Vector2();
    }

    public void chase(float targetx, float targety) {
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
    public void render(SpriteBatch batch) {
        chase(((Player) target).x, ((Player) target).y);
        super.render(batch);
    }

    @Override
    public void attack() {
        if (this.overlaps((Rectangle) target)) {
            this.setHp(0);
            target.takeDamage(this.getDamage());
        }
    }
}