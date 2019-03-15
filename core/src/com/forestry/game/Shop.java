package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.io.Serializable;
import java.util.Random;

public class Shop implements Disposable,Serializable{
    private transient Texture texture;
    Vector2 position;
    private boolean elite,panelRender;
    ShopPanel shopPanel;
    boolean tap;
    int cx,cy;
    transient Hint hint;
    private static Random random = new Random();

    @Override
    public void dispose() {
        texture.dispose();
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isElite() {
        return elite;
    }

    public Shop(Vector2 position, boolean elite) {
        this.position = position;
        this.elite = elite;
        texture = new Image("Shop"+(random.nextInt(3)+1)+".png");
    }
    public void render(SpriteBatch batch){
        batch.begin();
        if(texture!=null)batch.draw(texture,position.x,position.y,100,100);
        else{
            texture = new Image("Shop"+(random.nextInt(3)+1)+".png");
            batch.draw(texture,position.x,position.y,100,100);
        }
        batch.end();

        if(Main.hero.x+Main.hero.mainTexture.getWidth()>=position.x && Main.hero.x+Main.hero.mainTexture.getWidth()<=position.x+texture.getWidth()+68 ||
                Main.hero.x>=position.x && Main.hero.x<=position.x+texture.getWidth()+68){
            if (hint == null) {
                hint = new Hint("<Нажмите> Открыть магазин",new Vector2(position.x-10,position.y+140),24,null);
            }
            hint.render(batch);
            if(tap && cx>position.x && cx<position.x+100 && cy>position.y && cy<position.y+150){
                if (shopPanel == null) {
                    shopPanel = new ShopPanel();

                }
                panelRender = true;

            }

            if (shopPanel != null && panelRender) {
                shopPanel.render(batch);
            }
        }
        else{
            panelRender = false;
        }
        tap = false;
    }
}
