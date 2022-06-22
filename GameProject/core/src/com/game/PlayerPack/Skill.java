package com.game.PlayerPack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.game.EnemyPack.Enemy;
import com.game.Screen.GameScreen;

//mengaplikasikan skill tambahan ke permainan jika dipilih serta display kartu skill
public class Skill extends Window {
    private Array<String> listSkill;
    GameScreen game;
    public Sound click;

    public Skill(String title, Skin skin, GameScreen gameScreen) {
        super(title, skin);
        this.game = gameScreen;
        click = Gdx.audio.newSound(Gdx.files.internal("skillUp.wav"));
        create();
    }
    

    //cek skill yang dipilih
    private void checkSkill(Button skill){
        if(skill.getName().equalsIgnoreCase("skill-atkdmg")){
            //cek apakah player sudah pernah mendapat skill atkdmg
            if(game.player.isNewBul()) {
                game.player.setDamage(game.player.getDamage()+10);
            }else{
                game.player.setNewBul();
                game.player.setDamage(90);
            }


        }else if(skill.getName().equalsIgnoreCase("default")){
            game.player.setHp(game.player.getMaxHp());


        }else if(skill.getName().equalsIgnoreCase("skill-speed")){
            game.player.setMovementSpeed(game.player.getMovementSpeed()+50);


        }else if(skill.getName().equalsIgnoreCase("skill-time")){
            //memperlambat enemy dengan cara movementspeed /2, bulletspeed/2, dan attack time*2
            for (Enemy enemy: game.enemies){
                enemy.setMovementSpeed(enemy.getMovementSpeed()/2);
                enemy.setBulSpeed(enemy.getBulSpeed()/2);
                enemy.setAttackTime(enemy.getAttackTime()*2);
            }

        }else if(skill.getName().equalsIgnoreCase("skill-shield")){
            //set waktu invisible untuk player
            game.player.setInvisTime(4);
        } else if(skill.getName().equalsIgnoreCase("skill-upBull")) {
            game.player.setDoubleAtk();
        }

    }

    public void create(){
        listSkill = new Array<>();
        listSkill.add("default", "skill-shield", "skill-spd", "skill-atkSpd");
        listSkill.add("skill-atkdmg", "skill-time", "skill-upbull");

        this.setPosition();
        this.setResizable(false);

        //set nama skill
        String name = listSkill.random();
        Button option1 = new Button(getSkin(), name);
        option1.setName(name);

        name = listSkill.random();
        Button option2 = new Button(getSkin(), name);
        option2.setName(name);

        Array<Button> ButtonArray = new Array<>();
        ButtonArray.add(option1, option2);

        for (final Button option: ButtonArray) {
            option.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    click.play();
                    checkSkill(option);
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
            this.add(option).size(200, 200).padRight(20).padLeft(20);
        }
    }

    public void setPosition(){
        this.setBounds(Gdx.graphics.getWidth()/2 - this.getWidth()/2,
                Gdx.graphics.getHeight()/2 - this.getHeight()/2, 550, 350);
    }
}
