package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Menu extends ScreenAdapter {
    private Stage stage;
    private Viewport viewport;
    private Table maintable;
    TextureAtlas atlas;
    protected Skin skin;
    AfterDark AssetGame;
    Texture bg;
    int mute;
    Button button;

    public Menu(AfterDark AssetGame){
        this.AssetGame = AssetGame;
        bg = new Texture("forest3.png");
        AssetGame.music.setVolume(0.2f);
        AssetGame.music.setLooping(true);

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(.1f,.1f,.15f,1);

        AssetGame.batch.begin();
        AssetGame.batch.draw(bg, 0, 0);
        AssetGame.batch.end();
        stage.act();
        stage.draw();
        if (mute == 0) {
            button.setDisabled(false);
            AssetGame.music.play();
        } else {
            button.setDisabled(true);
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
        maintable.setFillParent(true);
        maintable.setY(-100);
        stage.addActor(maintable);
        AssetGame.music.play();

        //tombol play
        addButton(100, 100, "default").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Here we go!!!");
                AssetGame.soundclick.play();
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new ScreenLoading(AssetGame));

            }
        });

        //tombol exit
        addButton(75, 75, "btn-exit").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("SAYONARA");
                AssetGame.soundclick.play();
                Gdx.app.exit();
            }
        });
        button = new Button(skin, "btn-music");
        maintable.add(button).width(75).height(75).padBottom(10).padRight(10);

        button.addListener(new ClickListener() {
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


        button.setDisabled(mute == 1);
        Gdx.input.setInputProcessor(stage);
    }
    private Button addButton(float width, float height, String style){
        button = new Button(skin, style);
        maintable.add(button).width(width).height(height).padBottom(10).padRight(10);
        //maintable.row();
        return button;
    }

    @Override
    public void dispose() {

    }
}
