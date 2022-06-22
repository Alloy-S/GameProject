package com.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.AfterDark;

public class InfoScreen extends ScreenAdapter {
    private Stage stage;
    private Viewport viewport;
    TextureAtlas atlas;
    protected Skin skin;
    AfterDark AssetGame;
    Texture title;

    public InfoScreen(AfterDark assetGame) {
        AssetGame = assetGame;
        this.title = new Texture("nama-kelompok.png");
    }

    @Override
    public void render(float delta) {
        AssetGame.batch.begin();
        AssetGame.batch.draw(AssetGame.bg, 0, 0);
        AssetGame.batch.draw(title, 115, 120, 370, 550);
        AssetGame.batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void show() {
        atlas = new TextureAtlas("button/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("button/uiskin.json"), atlas);
        viewport = new ExtendViewport(600, 720);
        stage = new Stage(viewport);

        Button homeButton = new Button(skin, "btn-home");
        homeButton.setHeight(90);
        homeButton.setWidth(90);
        homeButton.setPosition(250, 20);
        stage.addActor(homeButton);

        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
                dispose();
                AssetGame.setScreen(new Menu(AssetGame));

            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        title.dispose();
        skin.dispose();
        atlas.dispose();
    }
}
