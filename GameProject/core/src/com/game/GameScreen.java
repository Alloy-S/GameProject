package com.game;

import com.badlogic.gdx.audio.Music;
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
import java.util.Iterator;

public class GameScreen  implements Screen{
    final AfterDark game;
    private State state = State.RUN;
    OrthographicCamera camera;
    Array<Enemy> enemies;
    Array<Bullet> bullets;
    long lastDropTime;
    int dropsGathered;
    float shootTime;
    final float shootWaitTime = 0.1f;
    Player player;
    Table maintable;
    Button setting;
    TextureAtlas atlas;
    protected Skin skin;
    private Stage stage;
    Viewport viewport;
    private SmallScreen smallScreen;
    HealthBar helthPlayer;
    public Music music;

    public GameScreen(final AfterDark game) {
        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each
        player = new Player(400-64, 20);
        helthPlayer = new HealthBar(player);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);


        // create the raindrops array and spawn the first raindrop
        enemies = new Array<Enemy>();
        bullets = player.bullets;
        spawnEnemy();


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
    }

    private void spawnEnemy() {
        Enemy enemy = new Enemy(player);
        Enemy enemyv2 = new EnemyType2(player);
        Enemy enemyV3 = new EnemyType3(player);
        Enemy enemyV4 = new EnemyType4(player);

        //enemies.add(enemy);
        //enemies.add(enemyv2);
        enemies.add(enemyV3);
        //enemies.add(enemyV4);
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



        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        game.font.draw(game.batch, "FPS = " + Gdx.graphics.getFramesPerSecond(), 10, 700 );
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
                        game.soundclick.play();
                        pause();
                    }
                });
                game.font.setColor(Color.RED);
                game.font.draw(game.batch, "Enemy Destroyed: " + dropsGathered, 320, 550);

                player.render(game.batch);

                if (player.isDead()) {
                    music.dispose();
                    //game.setScreen(new Menu(game));
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
                System.out.println("*");
//        game.batch.setColor(Color.GREEN);
//        game.batch.draw(blank, 0, 0, Gdx.graphics.getWidth() * 0.01f * player.hp , 5);
                helthPlayer.render();

                if (enemies.isEmpty()) {
                    System.out.println("YESSSSSSSSSSSSSSSSSS");
                }


                //shoting code
                shootTime += delta;
                if ((Gdx.input.isTouched()) && shootTime >= shootWaitTime) {
                    player.attack();
//                    player.addBullet(Gdx.input.getX(), Gdx.graphics.getHeight()- Gdx.input.getY());
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
                break;
        }

        if(smallScreen.getState() == 1){
            smallScreen.setState(0);
            resume();
        } else if (smallScreen.getState() == 2) {
            game.setScreen(new Menu(game));
        }
        game.batch.end();
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

    @Override
    public void hide() {

    }

    public enum State{
        PAUSE,RUN
    }
}
