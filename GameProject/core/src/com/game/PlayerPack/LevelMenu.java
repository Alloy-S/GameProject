package com.game.PlayerPack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.AfterDark;
import com.game.ScreenLoading;

public class LevelMenu extends ScreenAdapter {
    private Stage stage;
    private Viewport viewport;
    private Table levelTable;
    TextureAtlas atlas;
    protected Skin skin;
    AfterDark AssetGame;
    TextButton button;
    BitmapFont menuFont;
    Texture title;

    public LevelMenu(AfterDark asset) {
        this.AssetGame = asset;
        title = new Texture("title.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(.1f,.1f,.15f,1);
        AssetGame.batch.begin();
        AssetGame.batch.draw(AssetGame.bg, 0, 0);
        AssetGame.batch.draw(title, 40, 470, 500, 250);
        AssetGame.batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void show() {
        atlas = new TextureAtlas("button/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("button/uiskin.json"),atlas);
        viewport = new ExtendViewport(600,720);
        stage = new Stage(viewport);
        levelTable = new Table();
        levelTable.setFillParent(true);
        stage.addActor(levelTable);
        levelTable.setPosition(0, 20);


        addButton("1", 75, 75, "btn-small", levelTable, false).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
                AssetGame.setScreen(new ScreenLoading(AssetGame));
            }
        });

        addButton("2", 75, 75, "btn-small", levelTable, false).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
            }
        });

        addButton("3", 75, 75, "btn-small", levelTable, false).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
            }
        });

        addButton("4", 75, 75, "btn-small", levelTable, false).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
            }
        });

        addButton("5", 75, 75, "btn-small", levelTable, true).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
            }
        });

        addButton("6", 75, 75, "btn-small", levelTable, false).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
            }
        });

        addButton("7", 75, 75, "btn-small", levelTable, false).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
            }
        });

        addButton("8", 75, 75, "btn-small", levelTable, false).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
            }
        });

        addButton("9", 75, 75, "btn-small", levelTable, false).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
            }
        });

        addButton("10", 75, 75, "btn-small", levelTable, false).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private TextButton addButton(String text,float width, float height, String style, Table table, boolean vertikal){
        button = new TextButton(text, skin, style);
        table.add(button).width(width).height(height).padRight(10);
        if (vertikal) {
            table.row();
        }

        return button;
    }
}
