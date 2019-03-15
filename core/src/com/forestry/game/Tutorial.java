package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class Tutorial {
    private Sprite text;
    private String path;
    private Vector2 position;
    private SpriteBatch batch;

    public Tutorial(String path, SpriteBatch batch) {
        this.path = path;
        this.batch = batch;
        text = new Sprite(new Image(path));
        position = new Vector2(Gdx.graphics.getWidth()/2-text.getWidth()/2,Gdx.graphics.getHeight()/2);
        text.setPosition(position.x,position.y);
    }
    public void render(){
        batch.begin();
        text.draw(batch,(position.x+text.getWidth()/2)/300);
        batch.end();
}
    public void move(int x){
        position.x+=x;
        text.setPosition(position.x,position.y);
    }
}
