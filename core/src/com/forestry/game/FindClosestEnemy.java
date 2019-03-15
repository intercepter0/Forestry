package com.forestry.game;

import com.badlogic.gdx.Gdx;

public abstract class FindClosestEnemy {
    /**
     * @return closest enemy in in field of hero's view
     */
    static Enemy find(){
        float ce = 100000;
        Enemy closestEnemy = null;
        if(Main.inDungeon && Main.boss != null) {
            closestEnemy = Main.boss;
            ce = Math.abs(Main.hero.x-Main.boss.getX());
        }
        for (Enemy e : Main.enemies) {
            try {
                if (Math.abs(e.position.x - Main.hero.x) < ce && !Main.enemies.isEmpty() &&
                        e.position.x > 16 && e.position.x < Gdx.graphics.getWidth() - 16 && e.dir == !Main.hero.direction && !e.die) {
                    closestEnemy = e;
                    ce = (int) Math.abs(e.position.x - Main.hero.x);
                }
            }
            catch(Exception e1){}
        }
        return closestEnemy;
    }


}
