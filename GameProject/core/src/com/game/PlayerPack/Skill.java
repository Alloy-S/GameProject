package com.game.PlayerPack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.game.EnemyPack.Enemy;
import com.game.GameScreen;

public class Skill extends Window {
    private Window levelUpWindow;
    private Array<String> listSkill;
    GameScreen game;


    public Skill(String title, Skin skin, GameScreen gameScreen) {
        super(title, skin);
        this.game = gameScreen;
        create();
    }

    public Skill(String title, Skin skin, String styleName, GameScreen gameScreen) {
        super(title, skin, styleName);
        this.game = gameScreen;
        create();
    }

    public Skill(String title, Window.WindowStyle style, GameScreen gameScreen) {
        super(title, style);
        this.game = gameScreen;
        create();
    }

    private void checkSkill(String skill){
        if(skill.equalsIgnoreCase("6")){
            game.player.setDamage(game.player.getDamage()+10);


        }else if(skill.equalsIgnoreCase("2")){
            game.player.setHp(game.player.getMaxHp());




        }else if(skill.equalsIgnoreCase("22")){
            game.player.setMovementSpeed(game.player.getMovementSpeed()+50);


        }else if(skill.equalsIgnoreCase("9")){
            for (Enemy enemy: game.enemies){
                enemy.setMovementSpeed(enemy.getMovementSpeed()/2);
                enemy.setBulSpeed(enemy.getBulSpeed()/2);
                enemy.setAttackTime(enemy.getAttackTime()*2);
            }

        }else if(skill.equalsIgnoreCase("19")){
            game.player.setInvisTime(4);
        }

    }

    public void create(){
        listSkill = new Array<>();
        listSkill.add("default", "skill-invisible", "skill-spd", "skill-atkSpd");
        listSkill.add("skill-atkdmg", "skill-time");

        this.setPosition();
        this.setResizable(false);

        Button option4 = new Button(getSkin(), listSkill.random());
        Button option5 = new Button(getSkin(), listSkill.random());
        Button option6 = new Button(getSkin(), listSkill.random());

        Array<TextButton> textButtonArray = new Array<>();
        Array<Button> ButtonArray = new Array<>();
        ButtonArray.add(option4, option5);

        for (final Button option: ButtonArray) {
            option.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                        checkSkill(option.getStyle().up.toString());
                    for (Actor actor: game.getStage().getActors()){
                        if(actor instanceof Skill){
                            //actor.remove akan langsung keluar dari loop
                            actor.addAction(Actions.removeActor());
                        }
                    }
                    game.player.levelUp = false;
                    game.resume();
                }
            });
            this.add(option).size(120, 120).padRight(50).padLeft(50);
        }
    }

    public void setPosition(){
        this.setBounds(Gdx.graphics.getWidth()/2 - this.getWidth()/2,
                Gdx.graphics.getHeight()/2 - this.getHeight()/2, 550, 350);
    }
}
