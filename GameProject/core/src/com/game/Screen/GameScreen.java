package com.game.Screen;


import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Timer;
import com.game.*;
import com.game.EnemyPack.Enemy;
import com.game.EnemyPack.EnemyType2;
import com.game.EnemyPack.EnemyType3;
import com.game.EnemyPack.EnemyType4;
import com.game.Screen.LevelMenu;
import com.game.PlayerPack.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

//untuk platform menjalankan game (player, map, enemy dll)
public class GameScreen  implements Screen{
    final AfterDark game;
    private TiledMap map;
    private State state = State.RUN;
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer mapRenderer;
    public Array<Enemy> enemies;
    Array<Bullet> bullets;
    Array<Rectangle> objectCollide;
    float shootTime;
    final float shootWaitTime = 0.1f;
    public Player player;
    Table maintable;
    Button setting;
    TextureAtlas atlas;
    protected Skin skin;
    private Stage stage;
    Viewport viewport;
    private SmallScreen smallScreen;
    HealthBar helthPlayer;
    public Music music;
    private Skill skill;
    float Xprev;
    float Yprev;
    boolean move = true;
    public boolean exit;
    private MapLayer layer;
    public float delay;
    public int stageGame;
    public Sound diedSound;
    public Sound playerDied;
    public Sound winSound;


    public GameScreen(final AfterDark game, String fileMapName) {
        this.game = game;

        player = new Player(300, 100);
        helthPlayer = new HealthBar(player);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 720);
        map = new TmxMapLoader().load(fileMapName);
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        enemies = new Array<Enemy>();
        bullets = player.bullets;

        winSound = Gdx.audio.newSound(Gdx.files.internal("win.wav"));
        playerDied = Gdx.audio.newSound(Gdx.files.internal("player-died.wav"));
        diedSound = Gdx.audio.newSound(Gdx.files.internal("died.wav"));
        atlas = new TextureAtlas("button/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("button/uiskin.json"),atlas);
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

        layer = map.getLayers().get("obj layer");
        objectCollide = new Array<Rectangle>();

        //check object collision (tembok) pada map
        for (MapObject object : layer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;

                if ("blocked".equals(rectangleObject.getName())) {
                    Rectangle rectangle = rectangleObject.getRectangle();
                    objectCollide.add(rectangle);
                }
            }
        }
        //spawn emeny pertama kali
//        spawnEnemy();
    }

    private void spawnEnemy() {
        Enemy enemy = new Enemy(player, objectCollide);
        enemies.add(enemy);
        Enemy enemyv2 = new EnemyType2(player, objectCollide);
        enemies.add(enemyv2);
        Enemy enemyV3 = new EnemyType3(player, objectCollide);
        enemies.add(enemyV3);
        Enemy enemyV4 = new EnemyType4(player, objectCollide);
        enemies.add(enemyV4);
    }

    public void powerUp(){
        if (player.getLevel() > player.getSkillCount()) {
            skill = new Skill("", skin, this);
            stage.addActor(skill);
            player.addSkill();
            skill.setPosition();
        }
    }

    @Override
    public void render(float delta) {
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();

        game.batch.setProjectionMatrix(camera.combined);

        stage.act();
        stage.draw();
        Gdx.input.setInputProcessor(stage);
        game.batch.begin();

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

                helthPlayer.renderXp();

                //untuk deteksi player menabrak objek (tembok)
                for (int i = 0; i < objectCollide.size; i++) {
                    if (player.overlaps(objectCollide.get(i))) {
                        move = false;

                        player.setX(Xprev);
                        player.setY(Yprev);

                    }
                }


                player.render(game.batch);
                helthPlayer.render();
                if (move == true) {
                    Xprev = player.getX();
                    Yprev = player.getY();
                }
                move = true;

                //jika player mati maka kembali ke menu pemilihan level
                if (player.isDead()) {
                    playerDied.play();
                    game.setScreen(new LevelMenu(game));
                }

                if (player.levelUp) {
                    levelUp();
                }



                //untuk spawn enemy
                if (enemies.isEmpty()) {
                    delay += Gdx.graphics.getDeltaTime();
                    if (delay >= 1.5f) {
                        stageGame++;
                        if (stageGame == 3) {
                            winSound.play();
                            dispose();
                            game.setScreen(new LevelMenu(game));
                        }
                        delay = 0;
                        spawnEnemy();
                    }
                }

                //render player bullet
                if (!player.bullets.isEmpty()) {
                    for (Bullet bullet : player.bullets) {
                        bullet.render(game.batch);
                    }
                }

                //deteksi player bullet menabrak tembok
                if (!player.bullets.isEmpty()) {
                    for (Rectangle wall : objectCollide) {
                        for (Bullet bullet : player.bullets) {
                            if (bullet.overlaps(wall)) {
                                player.removeBullet(bullet);

                            }
                        }
                    }
                }

                //render enemy
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

                Iterator<Enemy> iterEn = enemies.iterator();

                while (iterEn.hasNext()) {
                    Enemy enemy = iterEn.next();

                    if (enemy.isDead()) {
                        diedSound.play();
                        iterEn.remove();
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


        //untuk pause atau resume
        if(smallScreen.getState() == 1){
            smallScreen.setState(0);
            if (player.levelUp) {
                levelUp();
            } else {
                resume();
            }
        } else if (smallScreen.getState() == 2 || exit) {
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
        skin.dispose();
        map.dispose();
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
