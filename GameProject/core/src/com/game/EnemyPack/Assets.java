package com.game.EnemyPack;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {
    public AssetManager manager;

    public void load(String fileName){
        if(manager == null){
            manager = new AssetManager();
        }
        manager.load(fileName,  TextureAtlas.class);
    }
}
