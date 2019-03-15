package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.io.Serializable;

public class Village implements Disposable,Serializable{
    static Texture texture1 = new Image("Village"+1+".png");
    static Texture texture2 = new Image("Village"+2+".png");
    private int roll;
    Vector2 position;
    Shop shop;
    Guard guard1,guard2;

    @Override
    public void dispose() {
        texture1.dispose();
        texture2.dispose();
    }

    public Village() {
        roll = (int)(Math.random()+0.5f);
        position = new Vector2(Gdx.graphics.getWidth()*3,20);
        guard1 = new Guard(Gdx.graphics.getWidth()*3,true);
        guard2 = new Guard(Gdx.graphics.getWidth()*3+texture1.getWidth(),false);
        shop = new Shop(new Vector2(position.x+texture1.getWidth()/4+(int)(Math.random()+0.5)*texture1.getWidth()/2,97*1.2f),false);
    }
    public void render(SpriteBatch batch){
        batch.begin();
        if(roll == 1) {
            batch.draw(texture1, position.x, position.y);
        }
        else
            batch.draw(texture2, position.x, position.y);
        batch.end();
        shop.render(batch);
        guard1.render(batch);
        guard2.render(batch);
    }
}
