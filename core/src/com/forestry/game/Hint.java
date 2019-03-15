package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.io.Serializable;

public class Hint implements Disposable{
    String label;
    Vector2 position;
    BitmapFont font;
    Guard g;
    boolean tap;
    int xtap,ytap;
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("10700.ttf"));
    static final String font_chars = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
    static FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    public Hint(String label, Vector2 position,int size,Guard guard) {
        g = guard;
        this.label = label;
        this.position = new Vector2(position.x,position.y);
        parameter.size = size;
        parameter.characters = font_chars;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void dispose() {
        try{font.dispose();}
        catch (Exception e){}
    }

    public void render(SpriteBatch batch){
        batch.begin();
        font.draw(batch,label,position.x+3,position.y+18);
        batch.end();
        if(tap && g!=null){
            if(xtap>position.x+3-210 && xtap<position.x+40 && ytap>position.y-20 && ytap<position.y+70){
                if (CoinsMonitor.getCoins() >= Main.hero.payMoney) {
                    CoinsMonitor.addCoins(Main.hero.payMoney * -1);
                    g.pass();

                }
            }
            tap = false;
        }
    }
    void tap(int x,int y){
        xtap = x;
        ytap = y;
        tap = true;
    }
}
