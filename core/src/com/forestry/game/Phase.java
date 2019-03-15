package com.forestry.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

import java.io.Serializable;

public class Phase implements Serializable{
    float alpha,x,y;
    static Texture texture = new Image("MainHeroPhase.png");
    Sprite sprite = new Sprite(texture,64 - Main.hero.spriteTimer / 10 * 32, 0,32,32);

    public static void dispose() {
        texture.dispose();
    }

    public Phase(float alpha, float x, float y, boolean flip) {
        this.alpha = alpha;
        sprite.setSize(Main.hero.size,Main.hero.size);
        sprite.setPosition(x+(Main.hero.rage/20),y);
        if(flip)sprite.flip(true,false);
    }

    public void render(SpriteBatch batch){
         batch.begin();
         sprite.draw(batch,alpha);
         batch.end();
         if(Main.hero.speedUp>0)alpha-=0.05f;
         else alpha = 0;
    }
}

