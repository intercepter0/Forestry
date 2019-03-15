package com.forestry.game;

import java.util.Random;

public abstract class DropChance {
    private static Random random = new Random();

    public static int getRandomPotionKind(){

        int r = random.nextInt(100)+1;

        if(r>=95)
            return 6;
        else if(r>=60)
            return random.nextInt(2)+4;
        else
            return random.nextInt(3)+1;
    }

}
