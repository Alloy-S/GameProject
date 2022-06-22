package com.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.AfterDark;

//untuk menampilkan level dan memasuki map per level
public class LevelMenu extends ScreenAdapter {
    private Stage stage;
    private Viewport viewport;
    private Table levelTable;
    private TextureAtlas atlas;
    protected Skin skin;
    private AfterDark AssetGame;
    private TextButton button;
    private Texture title;
    private TextButton level1;
    private TextButton level2;
    private TextButton level3;
    private TextButton level4;
    private TextButton level5;
    private TextButton level6;
    private TextButton level7;
    private TextButton level8;
    private TextButton level9;
    private TextButton level10;
    private Array<TextButton> level;
    public int maxLevelGame = 4;
    private final int WIDTH_LEVEL_BUTTON = 90;
    private final int HEIGHT_LEVEL_BUTTON = 90;


    public LevelMenu(AfterDark asset) {
        this.AssetGame = asset;
        title = new Texture("title.png");
    }

    @Override
    public void render(float delta) {
        AssetGame.batch.begin();
        AssetGame.batch.draw(AssetGame.bg, 0, 0);
        AssetGame.batch.draw(title, 40, 470, 500, 250);
        AssetGame.batch.end();
        stage.act();
        stage.draw();

        for (int i = 9; i > maxLevelGame - 1; i--) {
            level.get(i).setDisabled(true);
        }


    }

    @Override
    public void show() {
        atlas = new TextureAtlas("button/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("button/uiskin.json"), atlas);
        viewport = new ExtendViewport(600, 720);
        stage = new Stage(viewport);
        levelTable = new Table();
        levelTable.setFillParent(true);
        stage.addActor(levelTable);
        levelTable.setPosition(0, 20);
        level = new Array<>();


        level1 = new TextButton("1", skin, "btn-small");
        levelTable.add(level1).size(WIDTH_LEVEL_BUTTON, HEIGHT_LEVEL_BUTTON).padRight(10);

        level2 = new TextButton("2", skin, "btn-small");
        levelTable.add(level2).size(WIDTH_LEVEL_BUTTON, HEIGHT_LEVEL_BUTTON).padRight(10);

        level3 = new TextButton("3", skin, "btn-small");
        levelTable.add(level3).size(WIDTH_LEVEL_BUTTON, HEIGHT_LEVEL_BUTTON).padRight(10);

        level4 = new TextButton("4", skin, "btn-small");
        levelTable.add(level4).size(WIDTH_LEVEL_BUTTON, HEIGHT_LEVEL_BUTTON).padRight(10);

        level5 = new TextButton("5", skin, "btn-small");
        levelTable.add(level5).size(WIDTH_LEVEL_BUTTON, HEIGHT_LEVEL_BUTTON).padRight(10);
        levelTable.row();

        level6 = new TextButton("6", skin, "btn-small");
        levelTable.add(level6).size(WIDTH_LEVEL_BUTTON, HEIGHT_LEVEL_BUTTON).padRight(10);

        level7 = new TextButton("7", skin, "btn-small");
        levelTable.add(level7).size(WIDTH_LEVEL_BUTTON, HEIGHT_LEVEL_BUTTON).padRight(10);

        level8 = new TextButton("8", skin, "btn-small");
        levelTable.add(level8).size(WIDTH_LEVEL_BUTTON, HEIGHT_LEVEL_BUTTON).padRight(10);

        level9 = new TextButton("9", skin, "btn-small");
        levelTable.add(level9).size(WIDTH_LEVEL_BUTTON, HEIGHT_LEVEL_BUTTON).padRight(10);

        level10 = new TextButton("10", skin, "btn-small");
        levelTable.add(level10).size(WIDTH_LEVEL_BUTTON, HEIGHT_LEVEL_BUTTON).padRight(10);

        level.add(level1, level2, level3, level4);
        level.add(level5, level6, level7, level8);
        level.add(level9, level10);

        level1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
                AssetGame.setScreen(new GameScreen(AssetGame, "map/map_s.tmx"));
            }
        });
        level2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
                AssetGame.setScreen(new GameScreen(AssetGame, "map/map_s2.tmx"));
            }
        });
        level3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
                AssetGame.setScreen(new GameScreen(AssetGame, "map/map_s3.tmx"));
            }
        });
        level4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
                AssetGame.setScreen(new GameScreen(AssetGame, "map/map_1.tmx"));
            }
        });
        level5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
                //AssetGame.setScreen(new GameScreen(AssetGame, "map/map_2.tmx"));
            }
        });
        level6.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
                //AssetGame.setScreen(new GameScreen(AssetGame, "map/map_2.tmx"));
            }
        });

        level7.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
                //AssetGame.setScreen(new GameScreen(AssetGame, "map/map_2.tmx"));
            }
        });

        level8.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
                //AssetGame.setScreen(new GameScreen(AssetGame, "map/map_2.tmx"));
            }
        });

        level9.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
                //AssetGame.setScreen(new GameScreen(AssetGame, "map/map_2.tmx"));
            }
        });

        level10.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
                //AssetGame.setScreen(new GameScreen(AssetGame, "map/map_2.tmx"));
            }
        });

        Button homeButton = new Button(skin, "btn-home");
        homeButton.setHeight(90);
        homeButton.setWidth(90);
        homeButton.setPosition(250, 100);
        stage.addActor(homeButton);

        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetGame.soundclick.play();
                AssetGame.setScreen(new Menu(AssetGame));
            }
        });


        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private TextButton addButton(String text, float width, float height, String style, Table table, boolean vertikal) {
        button = new TextButton(text, skin, style);
        table.add(button).width(width).height(height).padRight(10);
        if (vertikal) {
            table.row();
        }

        return button;
    }
}
