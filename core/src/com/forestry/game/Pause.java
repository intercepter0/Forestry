package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Pause implements Disposable{
    SpriteBatch batch;
    static boolean click;
    static int mX,mY;
    static final Vector2 position;
    private static final Texture idle,exit,resume,info;


    @Override
    public void dispose() {
        idle.dispose();
        exit.dispose();
        resume.dispose();
        info.dispose();
    }

    static {
        idle = new Image("PauseMenu.png");
        exit = new Image("PauseMenuExit.png");
        info = new Image("PauseMenuInfo.png");
        resume = new Image("PauseMenuResume.png");
        position = new Vector2(Gdx.graphics.getWidth()/2-idle.getWidth()/2,
                Gdx.graphics.getHeight()/2-idle.getHeight()/2);
    }

    public Pause(SpriteBatch batch) {
        this.batch = batch;

    }

    public void render(){

        batch.begin();

        if(mX>position.x+102 && mX<position.x+252 && mY>position.y+168 && mY<position.x+318){  // info

            batch.draw(info,position.x,position.y);
        }

        else if(mX>position.x+296 && mX<position.x+496 && mY>position.y+142 && mY<position.x+342){ // resume
            if(click){
                 Main.hero.main.checkForPause(true);
            }
            batch.draw(resume,position.x,position.y);
        }

        else if(mX>position.x+543 && mX<position.x+667 && mY>position.y+168 && mY<position.x+318){ // exit
            if(click){
                try {
                    Data.savePlayer(Main.hero);
                }
                catch(Exception e){

                }
                Main.inMenu = true;
                Main.hero.main.check();
            }
            batch.draw(exit,position.x,position.y);
        }

        else{
            batch.draw(idle,position.x,position.y);
        }

        batch.end();
        click = false;
    }
}
