package com.forestry.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Dungeon {
    Teleport teleport;
    private ScroolingBackground background1,background2;



    public Dungeon() {
        background1 = new ScroolingBackground(0,true);
        background2 = new ScroolingBackground(1920,true);
    }

    protected void render(SpriteBatch batch){
        background1.drawBackground(batch);
        background2.drawBackground(batch);
        if(teleport!=null){
            teleport.render(batch);
            if(teleport.flashed && !teleport.isEnd()){
                batch.begin();
                teleport.flash.draw(batch,teleport.alpha);
                batch.end();
            }
        }
    }

    protected void renderForeground(SpriteBatch batch){
        if(teleport!=null&&!teleport.flashed) {
            background1.drawForeground(batch);
            background2.drawForeground(batch);
        }
        else if(teleport==null){
            background1.drawForeground(batch);
            background2.drawForeground(batch);
        }
    }

    void move(float x){
        background1.position.x+=x;
        background2.position.x+=x;
        if(teleport!=null){
            teleport.move(x);
        }
    }
}
