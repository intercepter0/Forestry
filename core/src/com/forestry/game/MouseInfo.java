package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class MouseInfo implements InputProcessor{
    MouseInfo(){
        Gdx.input.setInputProcessor(this);
    }

    public boolean keyDown (int keycode) {
        if(keycode>37 && keycode<41) {
            Main.cyan.keyPressed = true;
            Main.rose.keyPressed = true;
            Main.yellow.keyPressed = true;
        }
        return false;
    }

    @Override
    public boolean keyUp (int keycode) {

        return false;
    }

    @Override
    public boolean keyTyped (char character) {
        try {
            if (Integer.parseInt("" + character) < 7)
                PotionPanel.use(character);
        }
        catch(Exception e){}

        return false;
    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        if(Main.teleport) {
            ShopPanel.click = true;
            ShopPanel.mX = x;
            ShopPanel.mY = Gdx.graphics.getHeight() - y - 200;
            Pause.mX = x;
            Pause.mY = y;
            Main.joystick.tap(x, Gdx.graphics.getHeight() - y);
            Main.cyan.tap(x, Gdx.graphics.getHeight() - y);
            Main.rose.tap(x, Gdx.graphics.getHeight() - y);
            Main.yellow.tap(x, Gdx.graphics.getHeight() - y);
            PotionPanel.tap(x, Gdx.graphics.getHeight() - y);
            for (Weapon w : Main.weapons) {
                w.info.tap(x, Gdx.graphics.getHeight() - y);
            }
            Main.button.tap(x, Gdx.graphics.getHeight() - y);
            for (Village v : Main.villages
                    ) {
                if (v.guard1.hint != null) {
                    v.guard1.hint.tap(x, Gdx.graphics.getHeight() - y);
                }
                if (v.guard2.hint != null)
                    v.guard2.hint.tap(x, Gdx.graphics.getHeight() - y);
            }
            Teleport.tap = true;
            Teleport.cx = x;
            Teleport.cy = Gdx.graphics.getHeight() - y;
            for (Village v : Main.villages
                    ) {
                v.shop.tap = true;
                v.shop.cx = x;
                v.shop.cy = Gdx.graphics.getHeight() - y;
            }
        }
        else{
            Pause.mX = x;
            Pause.mY = y;
            PotionPanel.tap(x, Gdx.graphics.getHeight() - y);
            Main.button.tap(x, Gdx.graphics.getHeight() - y);
            Main.joystick.tap(x, Gdx.graphics.getHeight() - y);
        }


        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        if(Main.teleport) {
            try {
                ShopPanel.click = false;
            } catch (Exception e) {
            }

        }
        Joystick.directionMove = 0;
        Pause.click = true;
        return false;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        Main.joystick.tap(x,Gdx.graphics.getHeight()-y);
        return false;
    }

    @Override
    public boolean mouseMoved (int x, int y) {


        return false;
    }

    @Override
    public boolean scrolled (int amount) {
        return false;
    }
}
