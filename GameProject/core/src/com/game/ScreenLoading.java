package com.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;

public class ScreenLoading extends ScreenAdapter {

    private GameScreen game;
    private AfterDark AssetGame;
    Music music;

    public ScreenLoading(AfterDark AssetGame){

        this.AssetGame = AssetGame;

    }
    @Override
    public void render(float delta) {
        game.render(delta);
    }

    @Override
    public void show() {
        //music.play();
        game = new GameScreen(AssetGame);

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}