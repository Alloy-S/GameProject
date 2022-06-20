package com.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.game.EnemyPack.Enemy;
import com.game.EnemyPack.EnemyType2;
import com.game.EnemyPack.EnemyType3;
import com.game.EnemyPack.EnemyType4;
import com.game.PlayerPack.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.PlayerPack.Skill;

import java.util.Iterator;

public class GameScreen  implements Screen{
    final AfterDark game;
    private State state = State.RUN;
    OrthographicCamera camera;
    public Array<Enemy> enemies;
    Array<Bullet> bullets;
    long lastDropTime;
    int dropsGathered;
    float shootTime;
    final float shootWaitTime = 0.1f;
    public Player player;
    Table maintable;
    Button setting;
    TextureAtlas atlas;
    TextureAtlas atlasSkill;
    protected Skin skin;
    Skin windowSkin;
    private Stage stage;
    Viewport viewport;
    private SmallScreen smallScreen;
    HealthBar helthPlayer;
    public Music music;
    boolean levelUp = false;
    private Skill skill;
    Texture bg;


    public GameScreen(final AfterDark game) {
        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each
        player = new Player(400-64, 20);
        helthPlayer = new HealthBar(player);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 720);


        // create the raindrops array and spawn the first raindrop
        enemies = new Array<Enemy>();
        bullets = player.bullets;
        spawnEnemy();

        bg = new Texture("forest3.png");
        atlas = new TextureAtlas("button/btn-skin.atlas");
        skin = new Skin(Gdx.files.internal("button/btn-skin.json"),atlas);
        viewport = new ExtendViewport(600,720);
        stage = new Stage(viewport);
        maintable = new Table();
        maintable.setFillParent(true);
        setting = new Button(skin, "btn-setting");
        maintable.add(setting).width(50).height(50).padTop(20).padRight(20);
        stage.addActor(maintable);
        maintable.setPosition(275,340);
        smallScreen = new SmallScreen(game);
        smallScreen.show();

        atlasSkill = new TextureAtlas(Gdx.files.internal("skill/window.atlas"));
        windowSkin = new Skin(Gdx.files.internal("skill/window.json"), atlasSkill);
    }

    private void spawnEnemy() {
        Enemy enemy = new Enemy(player);
        Enemy enemyv2 = new EnemyType2(player);
        Enemy enemyV3 = new EnemyType3(player);
        Enemy enemyV4 = new EnemyType4(player);

        enemies.add(enemy);
        enemies.add(enemyv2);
        enemies.add(enemyV3);
        enemies.add(enemyV4);
        lastDropTime = TimeUtils.millis();
    }

    public void powerUp(){
        if (player.getLevel()>player.getSkillCount()) {
            skill = new Skill("Level UP", windowSkin, this);
            stage.addActor(skill);
            player.addSkill();
            skill.setPosition();
        }
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(254, 254, 254, 1);

        // tell the camera to update its matrices.
        camera.update();



        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);



        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        game.batch.draw(bg, 0, 0);
        game.font.draw(game.batch, "FPS = " + Gdx.graphics.getFramesPerSecond(), 10, 700 );
        stage.act();
        stage.draw();
        Gdx.input.setInputProcessor(stage);


        switch(state){
            case LEVELUP:
                powerUp();
                break;
            case PAUSE:
                smallScreen.render(60);
                break;
            case RUN:
                setting.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.soundclick.play();
                        pause();
                    }
                });

                game.font.setColor(Color.RED);
                game.font.draw(game.batch, "Enemy Destroyed: " + dropsGathered, 320, 700);

                player.render(game.batch);
                helthPlayer.render();
                game.font.draw(game.batch, "XP = " + player.getXp() + player.levelUp, 320, 600);

                if (player.isDead()) {
                    //game.setScreen(new Menu(game));
                }

                if (player.levelUp) {
                    levelUp();
                }

                if (enemies.isEmpty()) {
                    System.out.println("YESSSSSSSSSSSSSSSSSS");
                    spawnEnemy();
                }

                if (!player.bullets.isEmpty()) {
                    for (Bullet bullet: player.bullets) {
                        bullet.render(game.batch);
                    }
                }


                for (Enemy enemy : enemies) {
                    enemy.render(game.batch);
                    enemy.attack();
                }

                //shoting code
                shootTime += delta;
                if ((Gdx.input.isTouched()) && shootTime >= shootWaitTime) {
                    player.attack();
                    shootTime = 0;
                }

                if (TimeUtils.millis() - lastDropTime > 4000) {
                    //spawnEnemy();
                }

                Iterator<Enemy> iterEn = enemies.iterator();

                while (iterEn.hasNext()) {
                    Enemy enemy = iterEn.next();

                    if (enemy.isDead()) {
                        iterEn.remove();
                        dropsGathered++;
                        player.addXp(enemy.getXp());
                    }
                    Iterator<Bullet> iterBull = player.bullets.iterator();
                    while (iterBull.hasNext()) {
                        Bullet bullet = iterBull.next();

                        //enemy terkena bullet maka take damage
                        if (bullet.overlaps(enemy)) {
                            enemy.takeDamage(player.getDamage());
                            iterBull.remove();
                        }
                    }
                }
                break;
        }

        System.out.println(smallScreen.getState());
        if(smallScreen.getState() == 1){
            smallScreen.setState(0);
            if (player.levelUp) {
                System.out.println("levelup");
                levelUp();
            } else {
                resume();
            }
        } else if (smallScreen.getState() == 2) {
            game.setScreen(new Menu(game));
        }
        game.batch.end();
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown

    }

    @Override
    public void dispose() {
        music.dispose();
    }

    public void pause(){
        this.state = State.PAUSE;
    }

    public void resume(){
        this.state = State.RUN;
    }

    public void levelUp(){
        this.state = State.LEVELUP;
    }

    @Override
    public void hide() {

    }

    public enum State{
        PAUSE,RUN,LEVELUP
    }


}
