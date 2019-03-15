package com.forestry.game;

public class Autosave extends Thread {

    @Override
    public void run() {

        while (true){
            if(Main.teleport && !Main.tutorial && !Main.inMenu) {
                try {
                    System.out.println("Autosave...");
                    Data.savePlayer(Main.hero);
                } catch (Exception e) {
                    System.out.println("----Error autosaving----");
                    e.printStackTrace();
                }
                System.out.println("Autosave completed successfully!");
            }
            try {
                Thread.sleep(40000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
