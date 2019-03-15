package com.forestry.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.*;
import java.util.ArrayList;

public class Data{
    static int coins = 0;
    static ArrayList<Village> villages;
    static ArrayList<Potion> potions;
    static ArrayList<Weapon> weapons;
    static ArrayList<Enemy> enemies;

    /**
     * Saves player in local directory.
     * @param player serializable
     * @throws IOException
     */
    public static void savePlayer(Hero player) throws IOException {
        FileHandle file = Gdx.files.local("save.forestry");
        try {
            file.writeBytes(serialize(player), false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads player from local directory.
     * @param main for player init
     * @return player
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Hero readPlayer(Main main) throws IOException, ClassNotFoundException {
        Hero player = null;
        FileHandle file = Gdx.files.local("save.forestry");
        player = (Hero) deserialize(file.readBytes());
        player.initializeWhenReaded(main);
        return new Hero(main);
    }

    private static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(Main.villages);
        o.writeObject(Main.potions);
        o.writeObject(Main.weapons);
        o.writeObject(Main.enemies);
        o.writeInt(CoinsMonitor.getCoins());
        o.writeObject(obj);
        return b.toByteArray();
    }

    private static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        villages = (ArrayList<Village>)o.readObject();
        potions = (ArrayList<Potion>)o.readObject();
        weapons = (ArrayList<Weapon>)o.readObject();
        enemies = (ArrayList<Enemy>)o.readObject();
        coins = o.readInt();
        return o.readObject();
    }

}

