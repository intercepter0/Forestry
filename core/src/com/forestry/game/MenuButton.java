package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class MenuButton implements Disposable{
    private String name;
    private float y;
    private boolean mm;
    private Texture texture,locked,light;
    private MainMenu mainMenu;

    @Override
    public void dispose() {
        texture.dispose();
        locked.dispose();
        light.dispose();
    }

    public MenuButton(String name, float y, MainMenu mainMenu) {
        this.name = name;
        this.y = y;
        this.mainMenu = mainMenu;
        if(name.equals("continue")){
            locked = new Image("continueButtonLocked.png");
        }
        texture = new Image(name+"ButtonIdle.png");
        light = new Image(name+"ButtonLight.png");
    }
    void render(SpriteBatch batch){
        batch.begin();
        if(mm) batch.draw(light,100,y);
        else
        batch.draw(texture,100,y);

        batch.end();
    }
    void click(int x,int y){
        y = Gdx.graphics.getHeight()-y;
        if(x>120 && x<120+texture.getWidth() && y>this.y && y<this.y+texture.getHeight()){
             switch (name.charAt(0)){
                 case ('c'):
                     mainMenu.main.checkForPause(true);
                     mainMenu.continueGame();
                     break;
                 case ('n'):
                     mainMenu.main.checkForPause(true);
                     mainMenu.newGame();
                     break;

                 case('s'):
                     Main.pausePanel = null;
                     Main.pause = false;
                     Main.tutorial = true;
                     Main.inMenu = false;
                     mainMenu.main.check();
                     break;
                 case('e'):
                     Gdx.app.exit();
                     break;
             }
        }
    }
    void mouseMoved(int x,int y){
        y = Gdx.graphics.getHeight()-y;
        if(x>120 && x<120+texture.getWidth() && y>this.y && y<this.y+texture.getHeight()){
            mm = true;
        }
        else{
            mm = false;
        }
    }
}
