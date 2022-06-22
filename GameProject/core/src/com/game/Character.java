package com.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//untuk method dasar dari player dan monster
public interface Character {
    void takeDamage(int damage);
    public void attack();
    public void render(SpriteBatch batch);
    public boolean isDead();
}
