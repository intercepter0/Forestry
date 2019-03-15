package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.jar.JarOutputStream;

public class Hero implements Serializable,Disposable {
    transient static Texture mainTexture,mainTextureDamage;
    int spriteTimer,phaseTimer,payMoney;
    transient Main main;
    static transient Guard stopper;
    Weapon myWeapon;
    short[] potions = new short[6];
    private static transient Hint pay;
    float x,y,maxHp = 200,currentHp = maxHp,maxMana = 200,currentMana = maxMana,speedUp,speed = 4,rsUp,bonusMana = 1,myOffset;
    boolean direction = true,move,alreadyStaying;
    boolean mainTextureRage,protection,mainTextureProtection,arcaneBoost;
    int size = 100,wall,rKills,rage,protectionTimer,dir = 1,level = 1,damage,heal;
    private transient ArrayList<Phase> phase;
    private static int OFFSET;

    @Override
    public void dispose() {
        mainTexture.dispose();
        mainTextureDamage.dispose();
    }

    /**
    *  direction: true - right; false - left.
    */


    /**
     * Default constructor for Hero.
     */
    public Hero(Main main) {
        OFFSET = Gdx.graphics.getWidth()/3;
        this.main = main;
        phase = new ArrayList<Phase>();
        x=Gdx.graphics.getWidth()/2-50;
        y=97*1.2f;
        mainTextureDamage = new Image("MainHeroDamage.png");
        mainTexture = new Image("MainHero.png");
        myWeapon = new Weapon(1,new Vector2(0,0));
        myWeapon.hp = 0;
        myWeapon.mana = 0;
        myWeapon.bonusMana = 0;
        potions = new short[6];
        for (int i = 0; i < 3; i++) {
            potions[i] = 1;
        }
    }
    void initializeWhenReaded(Main main){
        OFFSET = Gdx.graphics.getWidth()/3;
        this.main = main;
        phase = new ArrayList<Phase>();
        mainTextureDamage = new Image("MainHeroDamage.png");
        mainTexture = new Image("MainHero.png");
        myWeapon.initIfReaded();
    }

    /**
     * Sets new weapon.
     */
    protected void setWeapon(){
        maxHp = 200 + myWeapon.getHp();
        maxMana = 200 + myWeapon.getMana();
        bonusMana = 1+myWeapon.getBonusHeal()/100;
        if(maxMana<currentMana)
            currentMana = maxMana;
        if(maxHp<currentHp)
            currentHp = maxHp;
    }

    protected void stop(boolean stop){
        if(stop){
            speed = 0;
            rsUp = speedUp;
            speedUp = 0;
            if (pay == null) {
                payMoney = (level*49+(int)(Math.random()*15));
                pay = new Hint("<Нажмите> Заплатить "+payMoney+" монет",
                        new Vector2(x-160,y+160),24,null);

            }
        }
        else{
            speed = 4;
        }
    }

    public short[] getPotions() {
        return potions;
    }

    public void render(MyBatch batch) {
        healing();
        protection();
        rage();
        if (speed == 0 && pay != null) {
            pay.render(batch);
            if (CoinsMonitor.getCoins() >= payMoney && Gdx.input.isKeyPressed(Input.Keys.E)) {
                CoinsMonitor.addCoins(payMoney * -1);
                stopper.pass();

            }
        }
        try {
            if (!phase.isEmpty()) {
                for (Phase p : phase
                        ) {
                    if (!Main.pause) p.render(batch);
                }
                while (speedUp == 0 && !phase.isEmpty()) {
                    phase.remove(0);
                    phase.trimToSize();
                }
            }
        }
        catch (Exception e){}


        batch.begin();
        if(move) {
            if(damage>0) {
                damage--;
                batch.draw(mainTextureDamage, x + rage / 20, y, size, size,
                        64 - spriteTimer / 10 * 32, 0, 32, 32, !direction, false);
            }
            else
                batch.draw(mainTexture, x + rage / 20, y, size, size,
                        64 - spriteTimer / 10 * 32, 0, 32, 32, !direction, false);

        }
        else{
            if(damage>0){
                batch.draw(mainTextureDamage, x+rage/20, y, size, size,
                        32, 0, 32, 32, !direction, false);
                damage--;
            }
            else
            batch.draw(mainTexture, x+rage/20, y, size, size,
                    32, 0, 32, 32, !direction, false);

        }
        batch.end();
        logic();
        //animate();
        move(false);
        if(move) {
            spriteTimer++;
            if (spriteTimer == 30) {
                spriteTimer = 0;
            }
        }
        if(currentHp <= 0){
            main.lose();
        }
    }
    void heal(){
        heal = (int)maxHp/2;
        mainTexture = new Image("MainHeroHeal.png");
    }

    private void healing(){
        if(heal>0){
            if(Math.random()>0.5f){
                if(currentHp<maxHp){
                    currentHp++;
                }
                heal--;
            }
        }
    }

    /**
     * Potion effect "Rage".
     */
    private void rage(){
        if(rage>0 && !mainTextureRage){
            rKills = Main.kills;
            mainTexture = new Image("MainHeroRage.png");
            mainTextureRage = true;
        }
        else if(rage == 0 && mainTextureRage){
            mainTexture = new Image("MainHero.png");
            mainTextureRage = false;
        }
        if(rage>0){  //  rage
            if(rKills<Main.kills){
                currentHp+=(Main.kills-rKills)*30;
                rKills = Main.kills;
                if(currentHp>maxHp)
                    currentHp = maxHp;
            }
            rage--;
            size = 100+rage/20;
        }

    }

    /**
     * Potion effect "Protection".
     */
    private void protection(){

        if(protection && !mainTextureProtection){
            mainTexture = new Image("MainHeroSheild.png");
            mainTextureProtection = true;
        }

        else if(!protection && mainTextureProtection){
            mainTexture = new Image("MainHero.png");
            mainTextureProtection = false;
        }

        protectionTimer++;
        if(protectionTimer>=360) {
            protection = false;
            protectionTimer = 0;
        }
    }

    /**
     * Hero damage. Run if enemies are closer from hero.
     */
    protected void damage(){
        if(!Main.tutorial || currentHp>80) {
            if (protection)
                currentHp += 4;
            else {
                Gdx.input.vibrate(40);
                currentHp -= 12;
                damage = 10;
            }
        }
    }

    /**
     * Moving.
     * @param t
     */
    void move(boolean t){
        if(!t) {
            if (speedUp > 0 && !Main.pause) {
                speed = 4 + speedUp;
                speedUp -= 0.005;
                if (!phase.isEmpty() && phase.get(0).alpha <= 0.05f) {
                    phase.remove(0);
                    phase.trimToSize();
                    while (speedUp == 0) {
                        phase.remove(0);
                        phase.trimToSize();
                    }
                }
                if (phaseTimer % 4 == 0) {
                    Phase ph = new Phase(speedUp / 4, x, y, !direction);
                    phase.add(ph);
                }
                phaseTimer++;
            }
        }
        if (Joystick.directionMove == -1 && !Main.pause ){
            if(myOffset<=-OFFSET){
                move = false;
                alreadyStaying = false;
                return;
            }
            if(x>=OFFSET){
                myOffset-=speed;
                x-=speed;
                wall = 0;

            }
            else{
                Main.setBackgroundPosition(speed,(byte)1);
                myOffset-=speed;
                wall = -1;
                if(!phase.isEmpty()) {
                    for (Phase p : phase
                            ) {
                        p.sprite.setX(p.sprite.getX()+speed);
                    }
                }
            }
            direction = false;
            move = true;
            alreadyStaying = false;
            dir = -1;
        }
        else if(Joystick.directionMove == 1 && !Main.pause){
            if(x<=Gdx.graphics.getWidth()-OFFSET-110){
                x+=speed;
                myOffset+=speed;
                wall = 0;
            }
            else{
                Main.setBackgroundPosition(speed,(byte)-1);
                myOffset+=speed;
                if(!phase.isEmpty()) {
                    for (Phase p : phase
                            ) {
                       p.sprite.setX(p.sprite.getX()-speed);
                    }
                }
                wall = 1;
            }
            stop(false);
            direction = true;
            move = true;
            alreadyStaying = false;
            dir = 1;
        }
        else{
            move = false;
        }
    }

    /**
     * Restoring health and mana.
     */
    private void logic(){
        if(!Main.tutorial) {
            if (currentMana < maxMana) currentMana += 0.13f * bonusMana;
            for (Village village:Main.villages) {
                if (currentHp < maxHp && x > village.position.x &&
                        x < village.position.x + Village.texture1.getWidth() && !Main.inDungeon)
                    currentHp += 0.15f;
                else if (currentHp < maxHp) currentHp += 0.005f;
            }

        }
    }
}
