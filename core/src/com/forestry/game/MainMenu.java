package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

public class MainMenu implements Disposable{
    private Texture background;
    Main main;
    private MenuButton continueButton,exit,settings,newGame;

    @Override
    public void dispose() {
        background.dispose();
    }

    public MainMenu(Main main) {
        this.main = main;
        background = new Image("menuBg.jpg");
        continueButton = new MenuButton("continue", Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/3.5f,this);
        exit = new MenuButton("exit", Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/3.5f-300,this);
        newGame = new MenuButton("newGame", Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/3.5f-100,this);
        settings = new MenuButton("settings", Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/3.5f-200,this);
    }

    void render(SpriteBatch batch){
        batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();
        continueButton.render(batch);
        newGame.render(batch);
        settings.render(batch);
        exit.render(batch);
    }
    void click(int x,int y){
        continueButton.click(x,y);
        newGame.click(x,y);
        settings.click(x,y);
        exit.click(x,y);
        Pause.click = false;
    }
    void mouseMoved(int x,int y){
        continueButton.mouseMoved(x,y);
        newGame.mouseMoved(x,y);
        settings.mouseMoved(x,y);
        exit.mouseMoved(x,y);
    }
    void newGame(){
        Main.tutorial = false;
        FileHandle file = Gdx.files.local("save.forestry");
        if(file.exists()){
            file.delete();
        }
        Main.inMenu = false;
        Main.pause = false;
        main.check();
    }
    void continueGame(){
        Main.tutorial = false;
        Main.inMenu = false;
        Main.pause = false;
        main.check();
    }
}
