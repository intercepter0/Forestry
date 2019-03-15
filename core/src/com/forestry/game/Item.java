package com.forestry.game;

import com.badlogic.gdx.math.Vector2;

public class Item {
    Vector2 position;
    int move = 2,srcX,srcY;
    boolean up = true;
    public void move(){
        if(up){
            position.y+=0.2f;
            if(position.y  - srcY > 5){
                up = false;
            }
        }
        else{
            position.y-=0.2f;
            if(srcY == position.y){
                up = true;
            }
        }
    }

}
