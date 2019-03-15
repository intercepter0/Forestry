package com.forestry.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Inventory {
    InventoryIcon icon;
    static int allPrice;
    public Inventory() {
        icon = new InventoryIcon();
    }

    public static void add(Weapon weapon){
        allPrice+=weapon.iLevel;
    }

    protected static void toZero(){
        allPrice = 0;
    }

    public static int getPrice() {
        return allPrice;
    }
}
