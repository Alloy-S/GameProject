package com.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.AfterDark;

//untuk menu pause dan back to main menu
public class SmallScreen extends ScreenAdapter {
    private Stage stage;
    private Viewport viewport;
    private Table maintable;
    TextureAtlas atlas;
    protected Skin skin;
    private int state = 0;
    private AfterDark game;
    Texture texture;
    public SmallScreen(AfterDark game){
        this.game = game;
    }

    @Override
    public void render(float delta) {
//        game.batch.draw(texture, 0, 0);
        stage.act();
        stage.draw();
        Gdx.input.setInputProcessor(stage);
        //game.batch.draw(game.bg, 0, 0);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void show(){
        atlas = new TextureAtlas("button/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("button/uiskin.json"),atlas);
        texture = new Texture("forest3.png");
        viewport = new ExtendViewport(600,720);
        stage = new Stage(viewport);
        maintable = new Table();
        maintable.setFillParent(true);

        stage.addActor(maintable);

        addButton(95, 95, "btn-play").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.soundclick.play();
                state = 1;
            }
        });
        addButton(110, 110, "btn-home").addListener(new ClickListener(){
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
