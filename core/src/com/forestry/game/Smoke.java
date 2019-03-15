package com.forestry.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Smoke extends Enemy implements Disposable {
    Vector2 position;
    private short spriteTimer,TIMERSPEED = 7;
    static Texture[] textures = new Texture[13];
    private Texture mainTexture;
    private boolean exist = true;

    @Override
    public void dispose() {
        for (int i = 0; i < textures.length; i++) {
            //mainTexture.dispose();
        }

    }

    public Smoke(Vector2 position) {
        this.position = position;

    }
    public void render(SpriteBatch batch){
            batch.begin();
            batch.draw(mainTexture, position.x-15, position.y-30, mainTexture.getWidth() * 1.5f, mainTexture.getHeight() * 1.5f);
            batch.end();
    }
    void animate(){

        switch (spriteTimer) {
            case (0):
                mainTexture = textures[spriteTimer/TIMERSPEED];
                //position.x-=15;
               // position.y-=30;
                break;
            case (7):
                mainTexture = textures[spriteTimer/TIMERSPEED];

                break;
            case (14):
                mainTexture = textures[spriteTimer/TIMERSPEED];

                break;
            case (21):
                mainTexture = textures[spriteTimer/TIMERSPEED];

                break;
            case (28):
                mainTexture = textures[spriteTimer/TIMERSPEED];

                break;
            case (35):
                mainTexture = textures[spriteTimer/TIMERSPEED];

                break;
            case (42):
                mainTexture = textures[spriteTimer/TIMERSPEED];

                break;
            case (49):
                mainTexture = textures[spriteTimer/TIMERSPEED];

                break;
            case (56):
                mainTexture = textures[spriteTimer/TIMERSPEED];

                break;
            case (63):
                mainTexture = textures[spriteTimer/TIMERSPEED];

                break;
            case (70):
                mainTexture = textures[spriteTimer/TIMERSPEED];

                break;
            case (77):
                mainTexture = textures[spriteTimer/TIMERSPEED];

                break;
            case (84):
                mainTexture = textures[spriteTimer/TIMERSPEED];

                break;
            case (91):
                dispose();
                super.smoke = null;

                break;
        }
        spriteTimer++;
    }
}
