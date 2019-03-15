package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Teleport implements Disposable{
     boolean toCave,flashed,switchAlpha,end;
    Sprite flash;
    float x,alpha;
    static float cx,cy;
    static boolean tap;
    private Hint hint;
    Texture texture;
    Main main;

    @Override
    public void dispose() {
        texture.dispose();
    }

    public boolean isEnd() {
        return end;
    }

    public Teleport(boolean toCave, float x1, Main main1) {

        main = main1;
        this.toCave = toCave;
        x = x1;
        if(toCave) {
            texture = new Image("portalToCave.png");
        }
        else{
            texture = new Image("portalToForest.png");
        }
        hint = new Hint("<Нажмите> Войти",new Vector2(x1+texture.getWidth()/2-50,480),24,null);
    }

    public float getX() {
        return x;
    }

    void move(float x1){
        x+=x1;
        hint.position.x+=x1;
    }

    protected void render(SpriteBatch batch){
        if(!switchAlpha) {
            batch.begin();
            batch.draw(texture, x, 97 * 1.2f);
            batch.end();
        }

        if(Main.hero.x+55>x+200 && Main.hero.x+55<x-200+texture.getWidth() && !switchAlpha){
            hint.render(batch);
            if(tap){
                tap = false;
                if(cx>x && cx<x+texture.getWidth() && cy>97 * 1.2f && cy<97 * 1.2f+texture.getHeight()+100){
                    flashed = true;
                    flash = new Sprite(new Texture(main.createProceduralPixmap(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),1,1,1)));
                }
            }

        }
        if(flashed && !end){
            if(!switchAlpha){
                alpha+=0.01;
                if(alpha>=0.95){
                    switchAlpha = true;
                    dispose();
                    main.tp();
                }
            }
            else{
                alpha-=0.01f;
                if(alpha<=0.05)
                    end = true;
            }
        }


    }
}
