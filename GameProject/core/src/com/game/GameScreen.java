package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {
    final AfterDark game;
    private State state = State.RUN;
    Texture dropImage;
    Texture bucketImage;
    OrthographicCamera camera;
    ArrayList<Bullet> bullets;
    Array<Enemy> enemies;
    long lastDropTime;
    int dropsGathered;
    float shootTime;
    final float shootWaitTime = 0.5f;
    Player player;
    Table maintable;
    ImageButton setting;
    TextureAtlas atlas;
    protected Skin skin;
    private Stage stage;
    Viewport viewport;
    Texture settingImage;
    private SmallScreen smallScreen;
    Texture blank;
    HealthBar helthPlayer;




    public GameScreen(final AfterDark game) {
        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each
        player = new Player(400-64, 20);
        blank = new Texture("blank.png");
        helthPlayer = new HealthBar(player);



        // load the drop sound effect and the rain background "music"

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);


        // create the raindrops array and spawn the first raindrop
        bullets = new ArrayList<Bullet>();
        enemies = new Array<Enemy>();
        //player.addBullet(400, 600);
        spawnEnemy();

        atlas = new TextureAtlas("uiskin.atlas");
        skin = new Skin(Gdx.files.internal("uiskin.json"),atlas);
        settingImage = new Texture(Gdx.files.internal("setting.png"));
        viewport = new ExtendViewport(800,600);
        stage = new Stage(viewport);
        maintable = new Table();
        maintable.setFillParent(true);
        setting = new ImageButton(skin);
        setting.getStyle().imageUp  = new TextureRegionDrawable(new TextureRegion(settingImage));
        setting.getStyle().imageDown  = new TextureRegionDrawable(new TextureRegion(settingImage));
        maintable.add(setting).width(16).height(16).padBottom(20);
        stage.addActor(maintable);
        maintable.setPosition(392,284);
        smallScreen = new SmallScreen(game);
        smallScreen.show();
    }



    private void spawnEnemy() {
        Enemy enemy = new Enemy(player);
        enemy.x = MathUtils.random(100, 800 - 100);
        enemy.y = MathUtils.random(100, 480 - 100);
        enemies.add(enemy);
        lastDropTime = TimeUtils.millis();
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

        //player.update(delta);
        player.update(delta);
        player.draw(game.batch);

        if (player.isDead()) {
            game.setScreen(new Menu(game));
        }

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        stage.act();
        stage.draw();
        Gdx.input.setInputProcessor(stage);
        switch(state){
            case PAUSE:
                smallScreen.render(60);

                break;
            case RUN:
                setting.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        pause();
                    }
                });

                break;
        }
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        for (Bullet bullet: player.bullets) {
            bullet.render(game.batch);
            bullet.update(delta);
        }

        for (Enemy enemy : enemies) {
            enemy.render(game.batch);

            if (enemy.bullets.isEmpty()){
                if (TimeUtils.nanoTime() - enemy.getLastBulletSpawn() > 1000000000) enemy.addBullet();
            }else {
                for (Bullet bullet : enemy.bullets) {
                    bullet.render(game.batch);
                    bullet.update(Gdx.graphics.getDeltaTime());
                    if (TimeUtils.nanoTime() - enemy.getLastBulletSpawn() > 1000000000) enemy.addBullet();

                    if (bullet.overlaps(player)) {
                        player.takeDamage(bullet.getDamage());
                        enemy.removeBullet(bullet);
                    }

                    if(bullet.position.x<0 || bullet.position.x > 800-16 || bullet.position.y > 600-16
                            || bullet.position.y<0){
                        enemy.removeBullet(bullet);
                    }
                }
            }
        }
        System.out.println("*");
//        game.batch.setColor(Color.GREEN);
//        game.batch.draw(blank, 0, 0, Gdx.graphics.getWidth() * 0.01f * player.hp , 5);
        helthPlayer.render();
        game.batch.end();

        if(smallScreen.getState() == 1){
            smallScreen.setState(0);
            resume();
        }

        //shoting code
        shootTime += delta;
        if ((Gdx.input.isTouched()) && shootTime >= shootWaitTime) {
            player.addBullet(Gdx.input.getX(), Gdx.graphics.getHeight()- Gdx.input.getY());
            //bullets.add(new Bullet(player.x - 64, player.y + 20, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()));
            shootTime = 0;
        }

        if (TimeUtils.millis() - lastDropTime > 4000) {
            spawnEnemy();
        }
//
//
//        // move the raindrops, remove any that are beneath the bottom edge of
//        // the screen or that hit the bucket. In the later case we increase the
//        // value our drops counter and add a sound effect.
        Iterator<Enemy> iterEn = enemies.iterator();

		while (iterEn.hasNext()) {
            Enemy enemy = iterEn.next();

            game.batch.begin();
            game.font.draw(game.batch, "HP = " + enemy.getHp(), enemy.x, enemy.y);
            game.batch.end();

            if (enemy.isDead()) {
                iterEn.remove();
                dropsGathered++;
            }
            Iterator<Bullet> iterBull = player.bullets.iterator();
            while (iterBull.hasNext()) {
                Bullet bullet = iterBull.next();

                //enemy terkena bullet maka take damage
                if (bullet.overlaps(enemy)) {
                    enemy.takeDamage(player.damage);
                    iterBull.remove();
                }
            }
		}
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
    public void hide() {
    }


    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
    }
    public void pause(){
        this.state = State.PAUSE;
    }
    public void resume(){
        this.state = State.RUN;
    }
    public enum State{
        PAUSE,RUN
    }
}
