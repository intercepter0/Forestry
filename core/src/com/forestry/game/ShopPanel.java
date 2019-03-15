package com.forestry.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.io.Serializable;
import java.util.Random;

public class ShopPanel implements Disposable,Serializable{
    static Texture background,backgroundBright;
    static int mX,mY;
    int [] price = new int[4];
    ShopIcon[] icons = new ShopIcon[4];
    static Vector2 position;
    static Random random = new Random();
    static boolean click;

    @Override
    public void dispose() {
        background.dispose();
        backgroundBright.dispose();
    }

    public ShopPanel() {
        if(background == null) {
            background = new Image("ShopPanel.png");
            backgroundBright = new Image("ShopPanelb.png");
        }
        position = new Vector2(Gdx.graphics.getWidth()/2-background.getWidth()/2,Gdx.graphics.getHeight()/2.5f);
        int pot;
        for (int i = 0; i < 4; i++) {
            pot = DropChance.getRandomPotionKind();
            if(pot==6)
                price[i] = random.nextInt(10)+60;
            else if(pot>3)
                price[i] = random.nextInt(10)+40;
            else
                price[i] = random.nextInt(10)+15;
            icons[i] = new ShopIcon(this,0,pot,i,price[i]);
        }
    }
    public void render(SpriteBatch batch){
        if(click){
            for (int i = 0; i < icons.length; i++) {
                if(icons[i]!=null) {
                    icons[i].click = true;
                }
            }
        }
        else{
            for (int i = 0; i < icons.length; i++) {
                if(icons[i]!=null) {
                    icons[i].click = false;
                }
            }
        }
        if(background == null) {
            background = new Image("ShopPanel.png");
            backgroundBright = new Image("ShopPanelb.png");
        }
        if(position == null){
            position = new Vector2(Gdx.graphics.getWidth()/2-background.getWidth()/2,Gdx.graphics.getHeight()/2.5f);
        }
        batch.begin();
        if(mX>=position.x+748 && mX<position.x+background.getWidth() &&
                mY>position.y+10 && mY<position.y+40) {
            batch.draw(backgroundBright,position.x,position.y);
            if (click) {
                CoinsMonitor.addCoins(Inventory.getPrice());
                Inventory.toZero();
            }
            click = false;
        }
        else
        batch.draw(background,position.x,position.y);
        batch.end();
        for (int i = 0; i < icons.length; i++) {
            if(icons[i]!=null){
                icons[i].render(batch);
            }
        }





    }
}
