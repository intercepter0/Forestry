package com.forestry.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SolarShield {
    Sprite sprite = new Sprite(new Image("solarShield.png"));
    float alpha;
    int count;

    public SolarShield() {
        sprite.setPosition(Main.hero.x+Main.hero.size/2-sprite.getWidth()/2,Main.hero.y);
    }
    void render(SpriteBatch batch){
        alpha = count%2;
        batch.begin();
        sprite.draw(batch,alpha);
        batch.end();
        sprite.setPosition(Main.hero.x+Main.hero.size/2-sprite.getWidth()/2,Main.hero.y);
    }
}
