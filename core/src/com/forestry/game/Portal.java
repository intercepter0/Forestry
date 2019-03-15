package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Portal implements Disposable{
    Texture[] textures = new Texture[9];
    Texture mainTexture;
    int timer;

    @Override
    public void dispose() {
        for (int t = 0; t < textures.length; t++) {
            textures[t].dispose();
        }
        mainTexture.dispose();
    }

    public Portal() {
        for (int i = 0; i < textures.length; i++) {
            textures[i] = new Image("Portal" + (i + 1) + ".png");
        }
        mainTexture = textures[8];
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(mainTexture, 0, Gdx.graphics.getHeight()/3.3f, 230, 230);
        batch.end();
        timer++;
        if (timer >= 45)
            timer = -1;

        if (timer % 5 == 0 || timer == 0)
            mainTexture = textures[timer / 5];

    }
}
