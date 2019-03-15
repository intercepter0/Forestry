package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class PauseButton implements Disposable{
    private Texture texture;
    float x,y;

    @Override
    public void dispose() {
        texture.dispose();
    }

    public PauseButton() {
        texture = new Image("pause.png");
        x = Gdx.graphics.getWidth()-100;
        y = Gdx.graphics.getHeight()-100;
    }

    void render(SpriteBatch batch){
        if(!Main.pause) {

            batch.begin();
            batch.draw(texture, x, y, 80, 80);
            batch.end();
        }
    }
    void tap(int x,int y){
        if(x>this.x && x<this.x+150 && y>this.y && y<this.y+80){
            Main.pause = true;
            if (Main.pausePanel == null) {
                Main.pausePanel = new Pause(Main.batch);
            } else {
                Main.pausePanel = null;
            }

            Main.pause = true;
        }
    }
}
