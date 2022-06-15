package com.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public interface Character {
    void takeDamage(int damage);
    public void attack(Character target);
    public void render(SpriteBatch batch);
    public boolean isDead();
}
