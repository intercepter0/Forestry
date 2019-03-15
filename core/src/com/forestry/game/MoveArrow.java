package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class MoveArrow implements Disposable{
    Texture arrow;
    boolean flip;
    float x,y;

    @Override
    public void dispose() {
        arrow.dispose();
    }

    public MoveArrow(boolean flip) {
        this.flip = flip;
        if(flip){
            x = Gdx.graphics.getWidth()/16;
            y = Gdx.graphics.getHeight()/10;
        }
        else{
            x = Gdx.graphics.getWidth()/16+200;
            y = Gdx.graphics.getHeight()/10;
        }
        arrow = new Image("moveArrow.png");
    }
    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(arrow,x,y,Gdx.graphics.getWidth()/9,Gdx.graphics.getWidth()/9,
                0,0,arrow.getWidth(),arrow.getHeight(),flip,false);
        batch.end();
    }
    void tap(int x,int y){
        if(x>this.x+arrow.getWidth()/2 && x<this.x+Gdx.graphics.getWidth()/16+arrow.getWidth()/2 && y>this.y && y<this.y+Gdx.graphics.getWidth()/16){
            if(flip){
                Joystick.directionMove = -1;
            }
            else{
                Joystick.directionMove = 1;
            }
        }
    }
}
