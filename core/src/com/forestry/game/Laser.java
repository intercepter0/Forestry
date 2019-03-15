package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Laser implements Disposable{
    static Texture[] textures = new Texture[13];
    Texture mainTexture;
    int timer;

    @Override
    public void dispose() {
        mainTexture.dispose();
        for (int i = 0; i < textures.length; i++) {
            textures[i].dispose();
        }
    }

    public Laser() {
        if(textures[0] == null) {
            for (int i = 0; i < textures.length; i++) {
                textures[i] = new Image((i + 1) + ".png");
            }
        }
        mainTexture = textures[12];
    }
    public void render(SpriteBatch batch){
        batch.begin();
        if(Main.hero.direction) {
            batch.draw(mainTexture, Main.hero.x + Main.hero.size / 2, 0, Gdx.graphics.getWidth(), mainTexture.getHeight());
        }
        else{
            batch.draw(mainTexture, Main.hero.x + Main.hero.size / 2-Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), mainTexture.getHeight());
        }
        batch.end();
        if(timer>=textures.length*5) {
            Main.laser = null;
            timer = 0;
        }
        if(timer%5 == 0 || timer == 0){
            mainTexture = textures[timer/5];
        }
        if(timer == 45){
            for (Enemy e:Main.enemies) {
                if(Main.hero.direction && e.position.x>Main.hero.x || !Main.hero.direction && e.position.x<Main.hero.x)
                e.die();
            }
        }
        timer++;
    }
}
