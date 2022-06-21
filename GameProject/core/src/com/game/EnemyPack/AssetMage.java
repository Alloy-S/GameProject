package com.game.EnemyPack;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetMage {
    public AssetManager manager;

    public void load(){
        if(manager == null){
            manager = new AssetManager();
        }
        manager.load("mageMovement.pack",  TextureAtlas.class);
    }
}

