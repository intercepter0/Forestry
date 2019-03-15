package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class ScroolingBackground implements Disposable{
    static Texture texture = new Image("Background++.jpg"),grass = new Image("Grass++.png"),
            caveImg = new Image("caveBackground.jpg"),caveGrass = new Image("caveBackgroundGrass.png");
    boolean cave,first = true;
    static int scale;
    Vector2 position;

    @Override
    public void dispose() {
        texture.dispose();
        grass.dispose();
        caveImg.dispose();
        caveGrass.dispose();
    }

    ScroolingBackground(float x, boolean cave) {
        this.cave = cave;
        scale = Gdx.graphics.getHeight()/texture.getHeight();
        position = new Vector2(x,0);
    }

    public void drawBackground(SpriteBatch batch){
        if(!cave) {
            batch.begin();
            batch.draw(texture, position.x, position.y, texture.getWidth() * 1.2f, texture.getHeight() * 1.2f);
            batch.end();
            if (position.x <= texture.getWidth() * -1.2f) {
                first = false;
                position.x += texture.getWidth() * 2.4f;
            }
            else if(position.x>texture.getWidth()*1.2f){
                position.x += texture.getWidth() * -2.4f;
            }
        }
        else{
            batch.begin();
            batch.draw(caveImg, position.x, position.y);
            batch.end();
            if (position.x <= caveImg.getWidth()*-1)
                position.x += caveImg.getWidth()*2;
            else if(position.x>caveImg.getWidth()){
                position.x += caveImg.getWidth() * -2;
            }
        }
    }

    public void drawForeground(SpriteBatch batch){
        batch.begin();
        if(!cave) {
            batch.draw(grass, position.x, position.y, texture.getWidth() * 1.2f, texture.getHeight() * 1.2f);
        }
        else
            batch.draw(caveGrass, position.x, position.y);
        batch.end();
    }
}
