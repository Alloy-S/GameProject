package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
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
import com.game.PlayerPack.LevelMenu;

public class Menu extends ScreenAdapter {
    private Stage stage;
    private Viewport viewport;
    private Table maintable;
    private Table soundTable;
    TextureAtlas atlas;
    protected Skin skin;
    AfterDark AssetGame;
    int mute;
    Button button;
    Button soundButton;
    Button musicButton;
    BitmapFont menuFont;
    private Texture title;

    public Menu(AfterDark AssetGame){
        this.AssetGame = AssetGame;
        //bg = new Texture("forest3.png");
        AssetGame.music.setVolume(0.2f);
        AssetGame.music.setLooping(true);
        menuFont = new BitmapFont(Gdx.files.internal("snap ITC.fnt"));
        title = new Texture("title.png");

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(.1f,.1f,.15f,1);
//        menuFont.setColor(Color.RED);
        AssetGame.batch.begin();
        AssetGame.batch.draw(AssetGame.bg, 0, 0);
        AssetGame.batch.draw(title, -15, 310, 600, 300);
//        GlyphLayout menuLayout = new GlyphLayout(menuFont, "Welcome to the jungle");
//        menuFont.draw(AssetGame.batch, menuLayout, 100, 500);
        AssetGame.batch.end();
        stage.act();
        stage.draw();


        if (mute == 0) {

            musicButton.setDisabled(false);
            AssetGame.music.play();
        } else {
            musicButton.setDisabled(true);
            AssetGame.music.pause();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void show(){
        atlas = new TextureAtlas("button/btn-skin.atlas");
        skin = new Skin(Gdx.files.internal("button/btn-skin.json"),atlas);
        viewport = new ExtendViewport(600,720);
        stage = new Stage(viewport);
        maintable = new Table();
        soundTable = new Table();
        maintable.setFillParent(true);
        soundTable.setFillParent(true);
        maintable.setY(-100);
        stage.addActor(maintable);
        stage.addActor(soundTable);
        AssetGame.music.play();
        soundTable.setPosition(-260, 290);

        //tombol play
        addButton(100, 100, "default", maintable, false).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Here we go!!!");
                AssetGame.soundclick.play();
                dispose();
                AssetGame.setScreen(new LevelMenu(AssetGame));
//                    ((Game) Gdx.app.getApplicationListener()).setScreen(new ScreenLoading(AssetGame));

            }
        });

        //tombol exit
        addButton(75, 75, "btn-exit", maintable, false).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("SAYONARA");
                AssetGame.soundclick.play();
                Gdx.app.exit();
            }
        });


        soundButton = new Button(skin, "btn-sound");
        soundTable.row();
        soundTable.add(soundButton).width(50).height(50).padBottom(10);
        musicButton = new Button(skin, "btn-music");
        soundTable.row();
        soundTable.add(musicButton).width(50).height(50);

        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
            }
        });

        musicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
                if (mute == 0) {
                    mute = 1;
                } else {
                    mute = 0;
                }
            }
        });


//        maintable.add(button).width(75).height(75).padBottom(10).padRight(10);
//
//        button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                AssetGame.soundclick.play();
//
//                if (mute == 0) {
//                    mute = 1;
//                } else {
//                    mute = 0;
//                }
//
//            }
//        });

        Gdx.input.setInputProcessor(stage);
    }
    private Button addButton(float width, float height, String style, Table table, boolean vertikal){
        button = new Button(skin, style);
        table.add(button).width(width).height(height).padBottom(10).padRight(10);
        if (vertikal) {
            table.row();
        }

        return button;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
