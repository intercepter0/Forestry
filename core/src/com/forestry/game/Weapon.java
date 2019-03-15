package com.forestry.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.io.Serializable;
import java.util.Random;

public class Weapon extends Item implements Serializable,Disposable{
    int iLevel,hp,mana;
    float bonusMana;
    transient Texture texture;
    boolean renderInfo;
    Vector2 position;
    String prefix;
    transient WeaponInfo info;
    static Random random = new Random();

    @Override
    public void dispose() {
        texture.dispose();
        info.dispose();
    }

    public Weapon() {

    }

    public int getiLevel() {
        return iLevel;
    }

    public int getHp() {
        return hp;
    }

    public int getMana() {
        return mana;
    }

    public float getBonusHeal() {
        return bonusMana;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getX() {
        return position.x;
    }

    public Weapon(int level, Vector2 vector2) {
        this.position = vector2;
        iLevel = (level-3)+random.nextInt(7);
        if(iLevel < 1) iLevel = 1;
        else if(iLevel > WeaponInfo.namesRus.length) iLevel = WeaponInfo.namesRus.length;
        int modifier = random.nextInt(7)+1;
        switch (modifier){
            case (1):
                prefix = "Ржавый ";
                break;
            case (2):
                prefix = "Поношенный ";
                break;
            case (3):
                prefix = "Блестящий ";
                break;
            case (4):
                prefix = "Добротный ";
                break;
            case (5):
                prefix = "Укрепленный ";
                break;
            case (6):
                prefix = "Зачарованный ";
                break;
            case (7):
                prefix = "Сияющий ";
                break;
        }
        while(hp<=3){
            hp = ((iLevel * modifier) * 3);
        }
        mana = ((iLevel*random.nextInt(9)+1)*3);
        String s =(""+random.nextFloat());
        bonusMana = (random.nextInt(10)+Float.parseFloat(""+s.charAt(2)+""+s.charAt(3))/10);
        texture = new Image("Sword"+iLevel+".png");
        switch (iLevel){
            case (1):

                break;
        }
        if(info == null){
            info = new WeaponInfo(this);
        }
    }
    void initIfReaded(){
        if(iLevel == 0){
            iLevel = 1;
        }
        texture = new Image("Sword"+iLevel+".png");
        if(info == null){
            info = new WeaponInfo(this);
        }
    }
    public void render(SpriteBatch batch){
        if(texture == null){
            initIfReaded();
        }
        batch.begin();
        batch.draw(texture,position.x,position.y);
        batch.end();
        if(renderInfo){
            info.render(batch);
        }
        touch();
    }
    private void touch(){
        if(position.x<Main.hero.x+32 && position.x>Main.hero.x){
           //Main.weapons.remove(this);
           renderInfo = true;
           info.update(position.x);
        }
        else{
            renderInfo = false;
        }
    }
    }
