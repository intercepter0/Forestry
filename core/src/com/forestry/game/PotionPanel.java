package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;


public class PotionPanel implements Disposable{

    Potion[] potions = new Potion[6];
    static Texture[] textures = new Texture[6];
    static short[] count = new short[6];
    BitmapFont font,num;
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("10700.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();

    @Override
    public void dispose() {
        for (int i = 0; i < textures.length; i++) {
            textures[i].dispose();
        }
        font.dispose();
        num.dispose();
    }

    public PotionPanel() {
        for (int i = 0; i < 3; i++) {
            textures[i] = new Image("Pot"+(i+1)+"-"+checkMax(count[i])+".png");

        }
        for (int i = 3; i < 6; i++) {
            textures[i] = new Image("Pot"+(i+1)+"-"+checkMax(count[i])+".png");

        }
        if(Main.hero == null){

            count = new short[6];
            for (int i = 0; i < 3; i++) {
                count[i] = 1;
            }
        }
        else
            count = Main.hero.getPotions();
        parameter.size = 36; // font size
        parameter2.size = 24; // font size
        font = generator.generateFont(parameter);
        num = generator.generateFont(parameter2);
        generator.dispose();


    }


    public void render(SpriteBatch batch){

        batch.begin();
        for (int i = 0; i < potions.length; i++) {
            batch.draw(textures[i],i*90+Gdx.graphics.getWidth()/3.5f,Gdx.graphics.getHeight()-110,100,100);
        }
        batch.end();
    }

    public static void add(byte kind){
         count[kind]+=1;
        textures[kind] = new Image("Pot"+(kind+1)+"-"+checkMax(count[kind])+".png");
    }
    public static void tap(int x,int y){

        if((int)(x/90+0.5f)-5<count.length && (int)(x/90+0.5f)-5>=0 && y>Gdx.graphics.getHeight()-110){
            use((int)(x/90+0.5f)-5);
        }
    }
    public static void use(int p){
        if(count[p]+1-1>=5){

        }
        if(count[p]>0) {
            switch (p + 1) {    // potions effects
                case (1)://heal
                    Main.hero.currentHp += (Main.hero.maxHp * 0.3f);
                    System.out.println(p);
                    count[p] -= 1;
                    try {
                        textures[p] = new Image("Pot" + (p + 1) + "-" + count[p + 1 - 1] + ".png");
                    }
                    catch (Exception e){
                        textures[p] = new Image("Pot" + (p + 1) + ".png");
                    }
                    if (Main.hero.maxHp < Main.hero.currentHp)
                        Main.hero.currentHp = Main.hero.maxHp;
                    break;
                case (2)://mana
                    Main.hero.currentMana += Main.hero.maxMana * 0.4 * Main.hero.bonusMana;
                    count[p] -= 1;
                    try {
                        textures[p] = new Image("Pot" + (p + 1) + "-" + count[p + 1 - 1] + ".png");
                    }
                    catch (Exception e){
                        textures[p] = new Image("Pot" + (p + 1) + ".png");
                    }
                    if (Main.hero.maxMana < Main.hero.currentMana)
                        Main.hero.currentMana = Main.hero.maxMana;
                    break;
                case (3)://speedUp
                    if (Main.hero.speedUp < 1) {
                        Main.hero.speedUp = 4;

                        count[p] -= 1;
                        try {
                            textures[p] = new Image("Pot" + (p + 1) + "-" + count[p + 1 - 1] + ".png");
                        }
                        catch (Exception e){
                            textures[p] = new Image("Pot" + (p + 1) + ".png");
                        }
                    }
                    break;
                case (4):

                    if (!Main.hero.protection && Main.hero.rage == 0 && !Main.hero.arcaneBoost) {
                        Main.hero.arcaneBoost = true;
                        Main.hero.mainTexture = new Image("MainHeroArcaneBoost.png");
                        count[p] -= 1;
                        try {
                            textures[p] = new Image("Pot" + (p + 1) + "-" + count[p + 1 - 1] + ".png");
                        }
                        catch (Exception e){
                            textures[p] = new Image("Pot" + (p + 1) + ".png");
                        }
                    }
                    break;
                case (5):
                    if (!Main.hero.protection && Main.hero.rage == 0 && !Main.hero.arcaneBoost) {
                        Main.hero.rage = 600;
                        Main.hero.x -= 60 * Main.hero.dir;
                        count[p] -= 1;
                        try {
                            textures[p] = new Image("Pot" + (p + 1) + "-" + count[p + 1 - 1] + ".png");
                        }
                        catch (Exception e){
                            textures[p] = new Image("Pot" + (p + 1) + ".png");
                        }
                    }
                    break;
                case (6):
                    if (Main.hero.rage == 0 && !Main.hero.protection && !Main.hero.arcaneBoost) {
                        Main.hero.protection = true;
                        count[p] -= 1;
                        try {
                            textures[p] = new Image("Pot" + (p + 1) + "-" + count[p + 1 - 1] + ".png");
                        }
                        catch (Exception e){
                            textures[p] = new Image("Pot" + (p + 1) + ".png");
                        }
                    }
                    break;
            }
        }


    }

    private void remove(){

    }
    private static short checkMax(short n){
        if(n>4)
            return 4;
        else
            return n;
    }

}
