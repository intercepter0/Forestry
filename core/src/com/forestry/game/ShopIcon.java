package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.io.Serializable;

public class ShopIcon implements Disposable,Serializable{
    private int type,kind,num,price;
    private Vector2 position;
    private transient Texture texture,bright;
    private transient BitmapFont font;
    private static FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private static final String font_chars = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
    boolean click;
    ShopPanel shopPanel;

    @Override
    public void dispose() {
        texture.dispose();
        bright.dispose();
        font.dispose();
    }

    public ShopIcon(ShopPanel panel,int type, int kind, int pos, int price) {
        click = false;
        shopPanel = panel;
        num = pos;
        this.price = price;
        this.type = type;
        this.kind = kind;
        parameter.size = 32;
        parameter.characters = font_chars;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("10700.ttf"));
        font = generator.generateFont(parameter);
        generator.dispose();
        position = new Vector2(ShopPanel.position.x+250*pos+9,ShopPanel.position.y+8);
        texture = new Image("Shop_"+kind+".png");
        bright = new Image("Shop_"+kind+"b.png");
    }
    public void render(SpriteBatch batch){
        if(font==null){
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("10700.ttf"));
            parameter.size = 32;
            parameter.characters = font_chars;
            font = generator.generateFont(parameter);
            generator.dispose();
            position = new Vector2(ShopPanel.position.x+250*num+9,ShopPanel.position.y+8);
            texture = new Image("Shop_"+kind+".png");
            bright = new Image("Shop_"+kind+"b.png");
        }
         batch.begin();
         if(ShopPanel.mX>position.x && ShopPanel.mX<position.x+texture.getWidth() && ShopPanel.mY<position.y && ShopPanel.mY>position.y-texture.getHeight()){
             batch.draw(bright,position.x,position.y);
             if(click && CoinsMonitor.getCoins()>=price){

                 CoinsMonitor.addCoins(price*-1);
                 PotionPanel.add((byte)(kind-1));
                 shopPanel.icons[num] = null;
                 click = false;
             }
             else if(CoinsMonitor.getCoins()<price && click){
                 font.setColor(Color.RED);
             }

         }

         else
             batch.draw(texture,position.x,position.y);
        font.draw(batch, "Цена: " + price, position.x + 14, position.y + 54);
        font.setColor(Color.WHITE);
        batch.end();

    }

    public int getType() {
        return type;
    }

    public int getKind() {
        return kind;
    }

    public Vector2 getPosition() {
        return position;
    }
}
