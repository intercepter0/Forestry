package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

public class SphereLabel implements Disposable{
    private String color,key;
    private float x,y;
    private int press;
    boolean keyPressed;
    private Texture texture;

    @Override
    public void dispose() {
        texture.dispose();
    }

    public SphereLabel(String color, String key, float x, float y) {
        this.color = color;
        this.key = key;
        this.x = x;
        this.y = y;
        texture = new Image(color+"Sphere.png");

    }

    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(texture,x-5+10,y-5+10,100,100);
        batch.end();
    }

    protected void tap(int x,int y){
        if(x>this.x && x<this.x+120 && y>this.y && y<this.y+120){
            Main.checkForKeys(key.toLowerCase());
            Gdx.input.vibrate(25);
        }
    }

    void switchWithUltimate(){
        if(Main.hero.arcaneBoost){
            texture = new Image(color+"SphereUltimate.png");
        }
        else{
            texture = new Image(color+"Sphere.png");
        }
    }


}
