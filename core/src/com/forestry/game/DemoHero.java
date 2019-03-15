package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

class DemoHero implements Disposable{
    Texture texture;
    Vector2 myposition;
    Hint h;
    boolean move,direction,alreadyStaying = true,drawHint;
    int spriteTimer,size = 100,speed = 4;
    Main main;

    @Override
    public void dispose() {
        h.dispose();
        texture.dispose();
    }

    public DemoHero(Main main1) {
        texture = new Image("MainHero.png");
        main = main1;
        myposition = new Vector2(Gdx.graphics.getWidth()/2.021f,Gdx.graphics.getHeight()/3.2f);
    }
    public void render(SpriteBatch batch){
        batch.begin();
        if(move) {
            batch.draw(texture, myposition.x, myposition.y, size, size, 64 - spriteTimer / 10 * 32, 0, 32, 32, !direction, false);
        }
        else{
            batch.draw(texture, myposition.x, myposition.y, size, size, 32, 0, 32, 32, !direction, false);
        }
        spriteTimer++;
        if (spriteTimer == 30)
            spriteTimer = 0;
        batch.end();
        if(drawHint)h.render(batch);
        move();
    }
    void move(){
        if(myposition.x<200 && Gdx.input.justTouched()){
            Main.teleport = true;
            main.check();
        }
        if (Joystick.directionMove == -1){
            if(myposition.x>=200){
                myposition.x-=speed;
            }
            else{
                if(h == null)
                h = new Hint("<Нажмите> Начать игру!",new Vector2(180,540),24,null);
                drawHint = true;
            }
            direction = false;
            move = true;
            alreadyStaying = false;
        }
        else if(Joystick.directionMove == 1){
            if(myposition.x<=Gdx.graphics.getWidth()-300-texture.getWidth()){
                myposition.x+=speed;
                drawHint = false;
            }
            direction = true;
            move = true;
            alreadyStaying = false;
        }
        else{
            move = false;
        }
    }


}
