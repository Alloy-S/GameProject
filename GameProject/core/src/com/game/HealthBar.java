package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.game.PlayerPack.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//set display bar health dan xp
public class HealthBar {
    
    private final float WIDTH = 100;
    private final float HEIGHT = 15;
    private final float OFFSET_X = 30;
    private final float OFFSET_Y = 12;
    Texture bar;
    Player player;
    SpriteBatch batch;
    private int maxHp;
    public BitmapFont fontSmall;
    public BitmapFont fontMedium;

    public HealthBar(Player player) {
        this.player = player;
        bar = new Texture("blank.png");
        batch = new SpriteBatch();
        this.maxHp = player.getHp();
        fontSmall = new BitmapFont(Gdx.files.internal("roboto-small.fnt"));
        fontMedium = new BitmapFont(Gdx.files.internal("roboto-medium.fnt"));
        fontSmall.setColor(Color.BLACK);
        fontMedium.setColor(Color.BLACK);
    }

    public void render() {

        batch.begin();
        batch.setColor(Color.RED);
        batch.draw(bar, player.x - OFFSET_X, player.y - OFFSET_Y, WIDTH, HEIGHT);
        batch.setColor(Color.GREEN);
        batch.draw(bar, player.x - OFFSET_X, player.y - OFFSET_Y, WIDTH * player.getHp() * 0.01f, HEIGHT);
        //font.draw(batch, player.getHp() + "/" + player.getMaxHp(), player.x, player.y + 2);
        GlyphLayout text = new GlyphLayout(fontSmall, player.getHp() + "/" + player.getMaxHp());
        fontSmall.draw(batch, text, player.x, player.y);
        batch.end();
    }

    public void renderXp() {
        batch.begin();
        batch.setColor(Color.RED);
        batch.draw(bar, 100, 655, 400, 30);
        batch.setColor(Color.GREEN);
        batch.draw(bar, 100, 655, 400 * player.getXp() * 0.01f, 30);
        fontMedium.draw(batch, player.getXp() + "/100" , 275, 678);
        batch.end();
    }
}
