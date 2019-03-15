package com.forestry.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.io.Serializable;

/*


 */
public class Arrow implements Disposable,Serializable{
    private String sphere1,sphere2,sphere3,k1,k2,k3;
    private transient Texture t1,t2,t3,arrow,pressed;
    Vector2 position;

    public Arrow(String sphere1, String sphere2, String sphere3,String k1, String k2, String k3, Vector2 position) {
        this.sphere1 = sphere1;
        this.sphere2 = sphere2;
        this.sphere3 = sphere3;
        this.k1 = k1;
        this.k2 = k2;
        this.k3 = k3;
        this.position = position;
        t1 = new Image(sphere1+"Sphere.png");
        pressed = new Image("targetReached.png");
        t2 = new Image(sphere2+"Sphere.png");
        t3 = new Image(sphere3+"Sphere.png");
        arrow = new Image("arrow.png");
    }
    public void render(SpriteBatch batch){
        if(t1 == null){
            pressed = new Image("targetReached.png");
            t1 = new Image(sphere1+"Sphere.png");
            t2 = new Image(sphere2+"Sphere.png");
            t3 = new Image(sphere3+"Sphere.png");
            arrow = new Image("arrow.png");
        }
        batch.begin();
        batch.draw(arrow,position.x+38,position.y+115);
        batch.draw(t1,position.x-10,position.y+155,40,40);
        if(Main.keyTyped1) {
            batch.draw(pressed,position.x-10,position.y+155,40,40);
        }
        batch.draw(t2,position.x+50-10,position.y+155,40,40);
        if(Main.keyTyped2) {
            batch.draw(pressed,position.x+50-10,position.y+155,40,40);
        }
        batch.draw(t3,position.x+100-10,position.y+155,40,40);
        if(Main.keyTyped3) {
            batch.draw(pressed,position.x+100-10,position.y+155,40,40);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        if(t1!=null)t1.dispose();
        if(t2!=null)t2.dispose();
        if(t3!=null)t3.dispose();
        if(arrow!=null)arrow.dispose();
    }
}
