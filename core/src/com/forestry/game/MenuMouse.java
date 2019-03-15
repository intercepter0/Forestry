package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class MenuMouse implements InputProcessor{
    MainMenu mainMenu;
    public MenuMouse(MainMenu mainMenu1) {
        mainMenu = mainMenu1;
        Gdx.input.setInputProcessor(this);
    }

    public boolean keyDown (int keycode) {

        return false;
    }

    @Override
    public boolean keyUp (int keycode) {

        return false;
    }

    @Override
    public boolean keyTyped (char character) {

        return false;
    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        mainMenu.click(x,y);
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {

        return false;
    }

    @Override
    public boolean mouseMoved (int x, int y) {
        mainMenu.mouseMoved(x,y);
        return false;
    }

    @Override
    public boolean scrolled (int amount) {
        return false;
    }
}
