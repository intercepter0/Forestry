package com.forestry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

import java.io.Serializable;
import java.util.Random;

public class Enemy implements Disposable,Serializable,Pool.Poolable{
    int type,spriteTimer;
    boolean boss = false;
    Vector2 position;
    int direction,vanishTimer,size = 100,attackTimer,myNumber,DROPCHANCE = 4,SPEED = 3;
    float alpha = 1,blastTimer1 = 1,blastTimer2 = 1;
    transient Sprite sprite;
    String sp1,sp2,sp3,k1,k2,k3;
    transient Smoke smoke;
    Arrow arrow;
    boolean dir,die,nowDrawSprite,drop;
    transient Texture texture,blast;
    static Random random = new Random();

    @Override
    public void reset() {

    }

    @Override
    public void dispose() {
        texture.dispose();
        if(blast!=null)blast.dispose();
        arrow.dispose();
    }

    public Enemy(int type, int walkTo, int myNumber) {
        this.myNumber = myNumber;
        if(walkTo == 0) {
            if (Math.random() >= 0.5) {
                position = new Vector2(-100, 97 * 1.2f);
            }
            else {
                position = new Vector2(Gdx.graphics.getWidth() + 100, 97 * 1.2f);
            }
        }
        else if(walkTo == 1){
            position = new Vector2(-100, 97 * 1.2f);
        }
        else{
            position = new Vector2(Gdx.graphics.getWidth() + 100, 97 * 1.2f);
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
        this.type = type;
        dir = true;
        direction = 1; // 1 = right
        if(position.x>0){
            dir = false;
            direction = -1;
        }
        texture = new Image("Reaper"+type+".png");
        if(type == 4){
            position.y+=6;
        }
        else if(type == 7)blast = new Image("Blast.png");
    }

    void initWhenReaded(){
        texture = new Image("Reaper"+type+".png");
        if(type == 4){
            position.y+=6;
        }
        else if(type == 7)blast = new Image("Blast.png");
    }

    public void render(SpriteBatch batch) {
        if(!die && !Main.pause){
            move();
        }
        if(die && type == 4 && !Main.pause){
            size-=4;
            position.y+=2;
            position.x+=2;

        }



        if(!nowDrawSprite) {
            if (vanishTimer < 21) {
                batch.begin();

                batch.draw(texture, position.x, position.y, size, size, 64 - spriteTimer / 10 * 32, 0, 32, 32, !dir, false);

                batch.end();


            }
            else if(!drop){
                CoinsMonitor.addCoins((int) (Math.random() * 5));
                if(random.nextInt(DROPCHANCE) == 0) {
                    if(type==4) {
                        position.y-=40;
                    }
                        switch (random.nextInt(3)) {
                            case (0):
                                Main.addWeapon(position,false);

                                break;

                            case (1):
                                Main.addPotion(position);

                                break;
                            case (2):
                                Main.addPotion(position);

                                break;
                        }

            }
            drop = true;
            }
        }
        else if(!Main.pause){

            alpha-=0.013;
            batch.begin();
            if(alpha>0) {
                if(sprite == null){
                    if(type == 5 || type == 6) {
                        sprite = new Sprite(texture, 32, 0, 32, 32);
                        sprite.setSize(100, 100);
                        sprite.setPosition(position.x, position.y);
                        if (!dir) {
                            sprite.flip(true, false);

                        }
                    }
                }
                sprite.draw(batch, alpha);
            }
            if(!dir)position.y-=1;
            sprite.setPosition(position.x,position.y);
            sprite.rotate(-0.9f*direction);
            if(!dir){
                sprite.setPosition(sprite.getX(),sprite.getY()-0.8f);
            }
            batch.end();
            if(!drop){
                CoinsMonitor.addCoins((int) (Math.random() * 5));
                if(random.nextInt(DROPCHANCE) == 0) {
                    if(type==4) {
                        position.y-=40;
                    }
                    switch (random.nextInt(3)){
                        case (0):
                            Main.addWeapon(position,false);

                            break;

                        case (1):
                            Main.addPotion(position);

                            break;
                        case (2):
                            Main.addPotion(position);

                            break;
                    }

                }
                drop = true;
            }
        }

        if (spriteTimer == 29) {
            spriteTimer = 0;
        }
        else if(die && type == 7){
            batch.begin();
            batch.draw(blast,position.x-23,position.y-23,147*1.2f,147*1.2f,147*(int)blastTimer1,(int)blastTimer2*147,147,147,!dir,false);
            batch.end();
            blastTimer1+=0.5f;
            if(blastTimer1 == 5){
                blastTimer2++;
                blastTimer1=0;

            }
        }

        if(smoke != null) {
            smoke.animate();
            smoke.render(batch);
        }
    }

    protected void die(){
        if(type < 4) {
            smoke = new Smoke(position);
        }
        else if(type == 4){
            size--;
        }
        else if(type == 5 || type == 6){
            nowDrawSprite = true;
            sprite = new Sprite(texture,32,0,32,32);
            sprite.setSize(100,100);
            sprite.setPosition(position.x,position.y);
            if(!dir){
                sprite.flip(true,false);

            }
        }
         Main.kills++;
         die = true;
    }
    private void move() {
        if(Math.abs(Main.hero.x-position.x)<70 && !Main.pause) {
            attack();
            return;
        }
        spriteTimer++;
        if(true) {
            if (Math.max(Main.hero.x, position.x) == position.x && !Main.pause) {
                position.x -= SPEED/2;
                dir = false;
                direction = -1;
            } else if(!Main.pause){
                position.x += SPEED/2;
                dir = true;
                direction = 1;
            }
        }
        else{
            if(Math.abs(Main.hero.x-position.x)>150) {
                if (Math.max(Main.hero.x, position.x) == position.x) {
                    position.x -= SPEED;
                    dir = false;
                    direction = -1;
                } else if (Math.max(Main.hero.x, position.x) == position.x) {
                    position.x += SPEED;
                    dir = true;
                    direction = 1;
                }
            }
            else{

            }
        }

    }
    private void attack(){
        if(attackTimer == 40 && !Main.pause){
            Main.hero.damage();
            attackTimer = 0;
        }
        if(!Main.pause)attackTimer++;
    }
     void deadOrAlive(){
        if(die) {
            vanishTimer++;
        }
        if(vanishTimer >= 91){
            dispose();
            Main.enemies.remove(this);
            smoke.dispose();
            smoke = null;
        }
    }

    public Enemy() {

    }
}
