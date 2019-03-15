package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

import java.io.Serializable;

public class CoinsMonitor implements Disposable{
    transient static Texture texture = new Image("coin.png");
    transient static Sprite sprite = new Sprite(texture);
    private static int coins = 0;
    transient BitmapFont font;
    transient FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("10700.ttf"));
    transient FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    @Override
    public void dispose() {
        texture.dispose();
        font.dispose();
    }

    public CoinsMonitor(int coins) {
        CoinsMonitor.coins = coins;
        sprite.setPosition(Gdx.graphics.getWidth()-420,Gdx.graphics.getHeight()-57);
        sprite.setSize(33,33);
        parameter.size = 24; // font size
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    public void render(SpriteBatch batch){
        batch.begin();
        sprite.draw(batch);
        font.draw(batch,""+coins,Gdx.graphics.getWidth()-380,Gdx.graphics.getHeight()-35);
        batch.end();
    }

    public static int getCoins() {
        return coins;
    }

    public static void addCoins(int coins2) {
        coins += coins2;
    }
}
