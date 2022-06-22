package com.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AfterDark extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public Music music;
	public Sound soundclick;
	public Texture bg;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(); // use libGDX's default Arial font
		soundclick = Gdx.audio.newSound(Gdx.files.internal("button-click.wav"));
		music = Gdx.audio.newMusic(Gdx.files.internal("bgm.wav"));
		bg = new Texture("forest3.png");

		this.setScreen(new Menu(this));
	}

	@Override
	public void render() {
		super.render();
	}



	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}
