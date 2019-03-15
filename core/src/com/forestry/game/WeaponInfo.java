package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.io.Serializable;
import java.security.Key;

public class WeaponInfo implements Serializable,Disposable{
    Weapon weapon;
    static final String[] namesRus = {"клинок пехотинца","меч странника","мстительный клинок","обсидиановый палаш",
            "дакийский фалькс","большой меч Предвидения","острие погибели","покрытый серебром меч",
            "королевский меч","жезл чародея","обжигающий кинжал","клинок крови","пепельный жезл","меч маны",
            "магмовый палаш","драгоценный нож","сияющий клинок","клык мороза","ярость тьмы","лучезарный клинок"};
    Texture weaponTexture;
    static Texture sign;
    Vector2 weaponPosition;
    static Sprite sprite;
    boolean visible = true,removeWeapon;
    String name;
    static BitmapFont nameFont,hp,mana,bh;
    static final String font_chars = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";


    @Override
    public void dispose() {

    }

    void update(float xx){
        weaponPosition = new Vector2(xx-sign.getWidth()/2+weaponTexture.getWidth()/2,97*1.2f+sign.getHeight()/3);
        sprite.setPosition(weaponPosition.x,weaponPosition.y);
    }

    public WeaponInfo(Weapon weapon) {

        name = namesRus[weapon.getiLevel()-1];
        this.weapon = weapon;
        weaponTexture = weapon.getTexture();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("10700.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32; // font size
        parameter.characters = font_chars;
        parameter2.size = 24; // font size
        parameter2.characters = font_chars;
        nameFont = generator.generateFont(parameter);
        nameFont.setColor(Color.WHITE);
        hp = generator.generateFont(parameter2);
        mana = generator.generateFont(parameter2);
        bh = generator.generateFont(parameter2);
        generator.dispose();
        sign = new Image("Sign.png");
        sprite = new Sprite(sign);
        try {
            weaponPosition = new Vector2(weapon.getX() - sign.getWidth() / 2 + weaponTexture.getWidth() / 2, 97 * 1.2f + sign.getHeight() / 3);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        sprite.setPosition(weaponPosition.x,weaponPosition.y+20);
    }

    void tap(int x,int y) {
        if(weapon.renderInfo) {
            if (x > weaponPosition.x && x < weaponPosition.x + sprite.getWidth() / 2 && y > weaponPosition.y + 40 && y < weaponPosition.y + 125) {
                //экипировать
                Main.hero.myWeapon = weapon;
                Main.hero.setWeapon();
                removeWeapon = true;

            } else if (x > weaponPosition.x + sprite.getWidth() / 2 && x < weaponPosition.x + sprite.getWidth() && y > weaponPosition.y + 40 && y < weaponPosition.y + 125) {
                //подобрать
                Inventory.add(weapon);
                removeWeapon = true;
            }
        }
    }

    public void render(SpriteBatch batch){
        batch.begin();
        sprite.draw(batch);
        nameFont.draw(batch,weapon.prefix+name,weaponPosition.x+18,weaponPosition.y+sign.getHeight()/1.4f+20);
        hp.draw(batch,"Здоровье: "+weapon.getHp(),weaponPosition.x+18,weaponPosition.y+sign.getHeight()/1.66f+20);
        if(weapon.getHp()>Main.hero.myWeapon.getHp()){
            hp.setColor(Color.GREEN);
            hp.draw(batch,"(+"+(weapon.getHp()-Main.hero.myWeapon.getHp()+")"),weaponPosition.x+18+hp.getSpaceWidth()*13,weaponPosition.y+sign.getHeight()/1.66f+20);
            hp.setColor(Color.WHITE);
        }
        else if(weapon.getHp()<Main.hero.myWeapon.getHp()){
            hp.setColor(Color.RED);
            hp.draw(batch,"("+(weapon.getHp()-Main.hero.myWeapon.getHp()+")"),weaponPosition.x+18+hp.getSpaceWidth()*13,weaponPosition.y+sign.getHeight()/1.66f+20);
            hp.setColor(Color.WHITE);
        }
        else{
            hp.setColor(Color.ORANGE);
            hp.draw(batch,"(+0)",weaponPosition.x+18+hp.getSpaceWidth()*13,weaponPosition.y+sign.getHeight()/1.66f+20);
            hp.setColor(Color.WHITE);
        }
        mana.draw(batch,"Мана: "+weapon.getMana(),weaponPosition.x+20,weaponPosition.y+sign.getHeight()/1.91f+20);
        if(weapon.getMana()>Main.hero.myWeapon.getMana()){
            hp.setColor(Color.GREEN);
            hp.draw(batch,"(+"+(weapon.getMana()-Main.hero.myWeapon.getMana()+")"),weaponPosition.x+18+mana.getSpaceWidth()*13,weaponPosition.y+sign.getHeight()/1.91f+20);
            hp.setColor(Color.WHITE);
        }
        else if(weapon.getMana()<Main.hero.myWeapon.getMana()){
            hp.setColor(Color.RED);
            hp.draw(batch,"("+(weapon.getMana()-Main.hero.myWeapon.getMana()+")"),weaponPosition.x+18+mana.getSpaceWidth()*13,weaponPosition.y+sign.getHeight()/1.91f+20);
            hp.setColor(Color.WHITE);
        }
        else{
            hp.setColor(Color.ORANGE);
            hp.draw(batch,"(+0)",weaponPosition.x+18+hp.getSpaceWidth()*13,weaponPosition.y+sign.getHeight()/1.91f+20);
            hp.setColor(Color.WHITE);
        }
        bh.draw(batch,"Восст. маны + "+weapon.getBonusHeal()+"%",weaponPosition.x+19,weaponPosition.y+sign.getHeight()/2.2f+20);
        batch.draw(weaponTexture,weaponPosition.x+sprite.getWidth()-weaponTexture.getWidth()-30,weaponPosition.y+sign.getHeight()/2.9f+30,80,80);
        batch.end();
        if(removeWeapon){
            removeWeapon = true;
            weapon.dispose();
            Main.weapons.remove(weapon);
            dispose();
        }
    }

}
