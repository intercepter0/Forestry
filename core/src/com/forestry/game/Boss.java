package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.util.Random;

public class Boss extends Enemy implements Disposable{
    boolean boss = true;
    private int type,spriteTimer,direction,attackTimer,hp = 5;
    private float vanishTimer;
    private boolean finalBoss,dir,die,vanishSwitch,fullyDie;
    private Arrow arrow;
    private Main main;
    private static final float SPEED = 2;
    private static Random random = new Random();
    private BossHealth bossHealth;
    static Vector2 position = new Vector2(Gdx.graphics.getWidth()*10,1.2f*97);
    private Texture sprite;
    private Sprite flash;

    @Override
    public void dispose() {
        sprite.dispose();
    }

    public boolean isDie() {
        return fullyDie;
    }

    public Boss(boolean finalBoss, Main main1) {
        main = main1;
        this.finalBoss = finalBoss;
        position = new Vector2(Gdx.graphics.getWidth()*10,1.2f*97);
        if(!finalBoss){
            type = random.nextInt(4)+1;
        }

        sprite = new Image("boss"+type+".png");
        bossHealth = new BossHealth(main,this,type);
        switch (random.nextInt(3)){
            case (0):
                sp1 = "cyan";
                k1 = "l";
                break;
            case (2):
                sp1 = "yellow";
                k1 = "k";
                break;
            case (1):
                sp1 = "rose";
                k1 = "j";
                break;
        }
        switch (random.nextInt(3)){
            case (0):
                sp2 = "cyan";
                k2 = "l";
                break;
            case (2):
                sp2 = "yellow";
                k2 = "k";
                break;
            case (1):
                sp2 = "rose";
                k2 = "j";
                break;
        }
        switch (random.nextInt(3)){
            case (0):
                sp3 = "cyan";
                k3 = "l";
                break;
            case (2):
                sp3 = "yellow";
                k3 = "k";
                break;
            case (1):
                sp3 = "rose";
                k3 = "j";
                break;
        }
        arrow = new Arrow(sp1,sp2,sp3,k1,k2,k3,position);
    }

    @Override
    public void render(SpriteBatch batch){
        if(!vanishSwitch) {
            batch.begin();
            batch.draw(sprite, position.x, position.y, 130, 130, 64 - spriteTimer / 10 * 32, 0, 32, 32, !dir, false);
            batch.end();
        }
        if(die){
            batch.begin();
            flash.draw(batch,vanishTimer);
            batch.end();
            if(!vanishSwitch){
                vanishTimer+=0.1f;
                if(vanishTimer >= 1){
                    vanishSwitch = true;
                }
            }
            else{
                vanishTimer-=0.05f;
                if(vanishTimer<=0){
                    Main.addWeapon(position,true);
                    fullyDie = true;
                    Main.boss = null;
                    Main.dungeon.teleport = new Teleport(false,Gdx.graphics.getWidth(),main);
                    main.renderTp = true;
                }
            }
        }

        else if(FindClosestEnemy.find() == this){
            arrow.render(batch);
        }

        if(!vanishSwitch)
        if(position.x>-100 && position.x<=Gdx.graphics.getWidth()+100)bossHealth.render(batch);
        if(!Main.pause)move();
    }

    public int getHp() {
        return hp;
    }

    protected static void updatePosition(float x){
        position.x+=x;
    }

    private void move() {
        if(Math.abs(Main.hero.x-position.x)<100 && !Main.pause) {
            attack();
            return;
        }
        else if(Main.hero.x<position.x){
            dir = false; // left
            direction = -1;
        }
        else if(Main.hero.x>position.x){
            dir = true; // right
            direction = 1;
        }
        if(!Main.pause)position.x+=SPEED*direction;
        spriteTimer++;
        if (spriteTimer == 30) {
            spriteTimer = 0;
        }

    }

    private void attack(){
        if(attackTimer == 40 && !Main.pause){
            Main.hero.damage();
            Main.hero.damage();
            attackTimer = 0;
        }
        if(!Main.pause)attackTimer++;
    }

    private void defeat(){
         die = true;
         vanishTimer = 0.1f;
         flash = new Sprite(new Texture(main.createProceduralPixmap(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),1,1,1)));
    }

    @Override
    protected void die(){  // not die, damage only
        hp-=1;
        if(hp<1){
            defeat();
            return;
        }

        switch (random.nextInt(3)){
            case (0):
                sp1 = "cyan";
                k1 = "l";
                break;
            case (2):
                sp1 = "yellow";
                k1 = "k";
                break;
            case (1):
                sp1 = "rose";
                k1 = "j";
                break;
        }
        switch (random.nextInt(3)){
            case (0):
                sp2 = "cyan";
                k2 = "l";
                break;
            case (2):
                sp2 = "yellow";
                k2 = "k";
                break;
            case (1):
                sp2 = "rose";
                k2 = "j";
                break;
        }
        switch (random.nextInt(3)){
            case (0):
                sp3 = "cyan";
                k3 = "l";
                break;
            case (2):
                sp3 = "yellow";
                k3 = "k";
                break;
            case (1):
                sp3 = "rose";
                k3 = "j";
                break;
        }
        arrow = new Arrow(sp1,sp2,sp3,k1,k2,k3,position);
    }
    float getX(){
        return position.x;
    }

}
