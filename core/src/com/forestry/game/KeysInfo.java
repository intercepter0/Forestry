package com.forestry.game;

import com.badlogic.gdx.Gdx;

public class KeysInfo {
    public static boolean isKeyDown(int key){
        if(Gdx.input.isKeyPressed(key)){
            return true;
        }
        else{
            return false;
        }
    }
}
