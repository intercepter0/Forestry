package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.io.Serializable;
import java.util.Random;

public class Guard implements Disposable,Serializable {
    private transient Texture texture;
    Vector2 position;
    transient Hint hint;
    static Random random;
    boolean flip,stop,pass;

    @Override
    public void dispose() {
        texture.dispose();
    }

    public Guard(float x, boolean flip) {
       this.flip = flip;
        position = new Vector2(x,97*1.2f);
       random = new Random();

    }

    public void render(SpriteBatch batch){
        batch.begin();
        if(texture!=null)batch.draw(texture,position.x,position.y,106,106,32,0,32,32,flip,false);
        else{
            random = new Random();
            texture = new Image("Guard"+(random.nextInt(4)+1)+".png");
            batch.draw(texture,position.x,position.y,106,106,32,0,32,32,flip,false);
        }
        batch.end();
        if(stop && hint != null){
            hint.render(batch);
        }
        else if(stop && hint == null){
            hint = new Hint("Что бы пройти, ты\nдолжен заплатить!",new Vector2(position.x-40,position.y+130),24,this);
        }

        if(flip)payMoney();
    }

    public void pass(){
        pass = true;
        hint = null;
        Main.hero.stop(false);
    }
    private void payMoney(){
        if(Main.hero.x+Hero.mainTexture.getWidth()>=position.x && Main.hero.direction && !pass && !Gdx.input.isKeyPressed(Input.Keys.A)){
            Main.hero.stopper = this;
            Main.hero.stop(true);
            stop = true;
        }
        else{
            Main.hero.stop(false);
            stop = false;
        }
    }

}
