package com.game.EnemyPack;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.game.Character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.PlayerPack.Player;

public class EnemyType3 extends Enemy {

    Vector2 position;
    Vector2 direction;
    int moveSpeed;
    float length;
    private GoblinMovement goblin;
    private Skin skin;
    private Assets assetsGoblin;
    float angleRad;
    float angle;
    int stage;
    int lastStage;
    int xp;
    int attackStage;
    int attackCount;
    float attackTime;


    public EnemyType3(Player target, Array obj) {
        super(target, obj);
        super.setHp(100);
        super.setDamage(20);
        this.height = 16;
        this.width = 16;
        this.moveSpeed = 150;
        setRandomPosition();

        position = new Vector2(this.x, this.y);
        direction = new Vector2();
        assetsGoblin = new Assets();
        assetsGoblin.load("goblinMovement.pack");
        assetsGoblin.manager.finishLoading();
        skin = new Skin();
        skin.addRegions(assetsGoblin.manager.get("goblinMovement.pack", TextureAtlas.class));
        //untuk mengambil frame" gerakan dengan nama dalam pack
        goblin = new GoblinMovement(this, skin.getRegion("up"), skin.getRegion("down"), skin.getRegion("right"), skin.getRegion("left"), skin.getRegion("attackup"), skin.getRegion("attackdown"), skin.getRegion("attackright"), skin.getRegion("attackleft"));

    }

    public void chase(float targetx, float targety) {
        direction.x = targetx - position.x;
        direction.y = targety - position.y;
        length = (float) Math.sqrt((direction.x * direction.x) + (direction.y * direction.y));

        if (length != 0) {
            direction.x = (direction.x / (length));
            direction.y = (direction.y / (length));
        }

        position.x += direction.x * moveSpeed * Gdx.graphics.getDeltaTime();
        position.y += direction.y * moveSpeed * Gdx.graphics.getDeltaTime();
        this.x = position.x;
        this.y = position.y;
    }

    @Override
    public void render(SpriteBatch batch) {

        angleRad = (float) (Math.atan2(((Player) target).x - this.x, ((Player) target).y - this.y));
        angle = (float) Math.toDegrees(angleRad - Math.PI / 2);
        angle = Math.round(angle) <= 0 ? angle += 360 : angle;
       //System.out.println("angle : " + angle);
        if (Math.round(angle) == 360)
            angle = 0;

            if (angle >= 45 && angle < 135) {
                //System.out.println("Down");
                stage = 1;
                attackStage = 9;
            } else if (angle >= 135 && angle < 225) {
                //System.out.println("LEFT");
                stage = 2;
                attackStage = 10;
            } else if (angle >= 225 && angle < 315) {
                //System.out.println("up");
                stage = 3;
                attackStage = 11;
            } else if (angle >= 315 || angle < 45) {
                //System.out.println("RIGHT");
                stage = 0;
                attackStage = 8;
            }

            if (stage != lastStage) {
                goblin.setCurrentAnimation(stage);
                lastStage = stage;
            }
        goblin.update(batch);


    }

    public void setXp(int xp){
        this.xp = xp;
    }


    @Override
    public void attack() {
        if (this.overlaps((Rectangle) target)) {
            this.setXp(0);
            attackTime += Gdx.graphics.getDeltaTime();
            attackCount++;
            if (!(attackCount  >= 3)) {
                goblin.setCurrentAnimation(attackStage);
                target.takeDamage(this.getDamage());
            }
            if (attackTime >= 0.75f)
                attackCount = 0;
                //this.setHp(0);
        } else {
            chase(((Player) target).x, ((Player) target).y);
        }
    }
}