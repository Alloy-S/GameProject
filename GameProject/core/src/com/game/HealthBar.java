package com.game;

import com.game.PlayerPack.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HealthBar {

    private final float WIDTH = 100;
    private final float HEIGHT = 15;
    Texture bar;
    Player player;
    //Enemy enemy;
    SpriteBatch batch;
    BitmapFont font;
    private int maxHp;

    public HealthBar(Player player) {
        this.player = player;
        bar = new Texture("blank.png");
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.maxHp = player.getHp();
    }

    public void render() {

        batch.begin();
        batch.setColor(Color.RED);
        batch.draw(bar, player.x, player.y, WIDTH, HEIGHT);
        batch.setColor(Color.GREEN);
        batch.draw(bar, player.x, player.y, WIDTH * player.getHp() * 0.01f, HEIGHT);
        font.draw(batch, player.getHp() + "/" + player.getMaxHp(), player.x + 25, player.y + 13);
        batch.end();
    }
}
