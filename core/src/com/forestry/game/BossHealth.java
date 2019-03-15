package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

class BossHealth implements Disposable{
    private Main main;
    private Boss boss;
    private float x,y;
    private Texture texture,bg;
    private BitmapFont font;
    private String bossName;

    @Override
    public void dispose() {
        font.dispose();
        texture.dispose();
        bg.dispose();
    }

    BossHealth(Main main1, Boss boss1, int type) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("10700.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        String font_chars = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        parameter.characters = font_chars;
        parameter.size = 32; // font size
        font = generator.generateFont(parameter);
        main = main1;
        boss = boss1;
        switch (type) {
            case (1):
                bossName = "Восставший";
                break;
            case (2):
                bossName = "Огненный дух";
                break;
            case (3):
                bossName = "Ожившая ведьма";
                break;
            case(4):
                bossName = "Минотавр";
                break;
        }
        texture = new Image(main.createProceduralPixmap(Gdx.graphics.getWidth()/3,30,1,0,0));
        bg = new Image(main.createProceduralPixmap(Gdx.graphics.getWidth()/3+8,38,0,0,0));
        x = Gdx.graphics.getWidth()/2-texture.getWidth()/2;
        y = Gdx.graphics.getHeight()/1.3f;
    }

    void render(SpriteBatch batch){
         batch.begin();
         batch.draw(bg,x-4,y-4);
         batch.draw(texture,x,y,boss.getHp()/5f*x,30);
         font.draw(batch,"Босс: "+bossName,x+10,y+24);
         batch.end();
    }

}
