package com.forestry.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Joystick {

    private MoveArrow right,left;
    static int directionMove;

    public Joystick() {
        left = new MoveArrow(true);
        right = new MoveArrow(false);
    }
    void render(SpriteBatch batch){
        left.render(batch);
        right.render(batch);
        if(directionMove == 1){
            right.render(batch);
            right.render(batch);
        }
        else if(directionMove == -1){
            left.render(batch);
            left.render(batch);
        }
    }
    void tap(int x,int y){
        left.tap(x,y);
        right.tap(x,y);
    }
}
