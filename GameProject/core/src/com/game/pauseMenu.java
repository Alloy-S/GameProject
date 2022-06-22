package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.game.PlayerPack.Skill;

public class pauseMenu extends Window {
    GameScreen game;

    public pauseMenu(String title, Skin skin, String styleName, GameScreen game) {
        super(title, skin, styleName);
        this.game = game;
        create();

    }

    public void create() {
        this.setPosition();
        this.setResizable(false);

        Button resume = new Button(getSkin(), "btn-play");
        Button home = new Button(getSkin(), "btn-home");

        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.resume();
            }
        });


        home.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.exit = true;
            }
        });
        this.add(resume).size(100, 100);
        this.add(home).size(100, 100);


    }

    public void setPosition(){
        this.setBounds(Gdx.graphics.getWidth()/2 - this.getWidth()/2,
                Gdx.graphics.getHeight()/2 - this.getHeight()/2, 550, 350);
    }
}
