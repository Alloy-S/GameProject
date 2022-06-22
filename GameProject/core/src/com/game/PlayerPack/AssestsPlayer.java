package com.game.PlayerPack;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

class AssetsPlayer {
    public AssetManager manager;

//untuk load assets player
    public void load(){
        if(manager == null){
            manager = new AssetManager();
        }
        manager.load("Movement.pack",  TextureAtlas.class);
        manager.load("stepSound.wav",  Sound.class);
    }
}
