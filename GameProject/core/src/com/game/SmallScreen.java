package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

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

public class SmallScreen extends ScreenAdapter {
    private Stage stage;
    private Viewport viewport;
    private Table maintable;
    TextureAtlas atlas;
    protected Skin skin;
    private int state = 0;
    private AfterDark game;
    public SmallScreen(AfterDark game){
        this.game = game;
    }

    @Override
    public void render(float delta) {


        //game.batch.draw(game.bg, 0, 0);

        stage.act();
        stage.draw();
        Gdx.input.setInputProcessor(stage);

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
        maintable.setFillParent(true);
        stage.addActor(maintable);

        TextButton resume = new TextButton("Resume", skin, "default");

        maintable.add(resume).width(200).height(100).padBottom(20).row();
        addButton(50, 50, "default").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.soundclick.play();
                state = 1;
            }
        });
        addButton(50, 50, "btn-home").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.soundclick.play();
                state = 2;
            }
        });
    }
    private Button addButton(float width, float height, String style){
        Button button = new Button(skin, style);
        maintable.add(button).width(width).height(height).padBottom(10);
        maintable.row();
        return button;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
