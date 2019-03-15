package com.forestry.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.io.Serializable;
import java.util.Random;

public class Potion extends Item implements Disposable,Serializable{
    public transient Texture texture;
    Vector2 position;
    byte kind;

    @Override
    public void dispose() {
        texture.dispose();
    }

    public Potion(byte kind, Vector2 position) {
        this.position = position;
        this.kind = kind;
        texture = new Image("Pot"+(kind)+".png");

    }
    public void render(SpriteBatch batch){
        if(texture == null){
            texture = new Image("Pot"+(kind)+".png");
        }
        batch.begin();
        batch.draw(texture,position.x,position.y,60,60);
        batch.end();

    }
    void touch(){
        if(position.x<Main.hero.x+32 && position.x>Main.hero.x){
            PotionPanel.add((byte)(kind-1));
            dispose();
            Main.potions.remove(this);
        }
    }

}
