package com.game.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.AfterDark;

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
    private Texture title;

    public Menu(AfterDark AssetGame){
        this.AssetGame = AssetGame;
        AssetGame.music.setVolume(0.2f);
        AssetGame.music.setLooping(true);
        title = new Texture("title.png");

    }
    @Override
    public void render(float delta) {
        AssetGame.batch.begin();
        AssetGame.batch.draw(AssetGame.bg, 0, 0);
        AssetGame.batch.draw(title, -15, 325, 600, 300);
        AssetGame.batch.end();
        stage.act();
        stage.draw();


        //menentukan mute atau tidak
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
        atlas = new TextureAtlas("button/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("button/uiskin.json"),atlas);
        viewport = new ExtendViewport(600,720);
        stage = new Stage(viewport);
        maintable = new Table();
        soundTable = new Table();
        maintable.setFillParent(true);
        soundTable.setFillParent(true);
        maintable.setY(-150);
        stage.addActor(maintable);
        stage.addActor(soundTable);
        AssetGame.music.play();
        soundTable.setPosition(-260, 290);

        addButton(100, 100, "btn-info", maintable, false).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Here we go!!!");
                AssetGame.soundclick.play();
                dispose();
                AssetGame.setScreen(new InfoScreen(AssetGame));

            }
        });

        //tombol play
        addButton(125, 125, "btn-play", maintable, false).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Here we go!!!");
                AssetGame.soundclick.play();
                dispose();
                AssetGame.setScreen(new LevelMenu(AssetGame));
            }
        });

        //tombol exit
        addButton(100, 100, "btn-exit", maintable, false).addListener(new ClickListener(){
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

        Gdx.input.setInputProcessor(stage);
    }

    //method membuat button
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
