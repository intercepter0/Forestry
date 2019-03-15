package com.forestry.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
/**
 *  @version 21.04.2018
 */
public class Main extends ApplicationAdapter implements Serializable{
	static MyBatch batch;
	static boolean learning;
	static Joystick joystick;

	private static Texture img,manaIcon,healthIcon;
	static boolean print;
	static BitmapFont font;
	static String pr;
	Sprite s,loseBlack;
	boolean renderTp;
	static  Hero hero,tutorialHero;
	static boolean keyTyped1,keyTyped2,keyTyped3;
	private PotionPanel potionPanel;
	private Inventory inventory;
	private boolean drawDungeon;
	static Dungeon dungeon;
	static Laser laser;
	Music sound;
	static Teleport teleporter;
	static Pause pausePanel;
	static Hint move,combat,potionsHint,exit;
	private MainMenu mainMenu;
	static Boss boss;
	Texture p1,p2,p3,die;
	static boolean inDungeon;
	static Random random = new Random();
	float i,mana,loseAlpha = 1,rtpx;
	static int kills,difficulty = 2,spawnChance,loseTimer,mobSpawningTimer;
	static boolean teleport,check = false,lose,enemiesRender,inMenu = true,tutorial,tutorialEnemy;
	private CoinsMonitor coinsMonitor;
	private DemoHero demoHero;
	private Portal portal;
	static SphereLabel cyan,rose,yellow;
	static ArrayList<Village> villages;
	static PauseButton button;
	public static boolean pause;
	static ArrayList<Enemy> enemies;
	static ArrayList<Smoke> smokes = new ArrayList<Smoke>();
	static ArrayList<Weapon> weapons;
	static ArrayList<Potion> potions;
	static ScroolingBackground background1,background2;
	private Pixmap pixmap;
	//static SolarChield solarChield;
	private Thread autosave;

	@Override
	public void dispose () {
		if(lose){
			hero = new Hero(this);
		}
		try {
			Data.savePlayer(hero);
		}
		catch(Exception e){
		}
		if(hero!=null)hero.dispose();
		if(tutorialHero!=null)tutorialHero.dispose();
		if(potionPanel!=null)potionPanel.dispose();
		if(dungeon!=null)dungeon.teleport.dispose();
		if(laser!=null)laser.dispose();
		if(teleporter!=null)teleporter.dispose();
		if(move!=null)move.dispose();
		if(exit!=null)exit.dispose();
		if(combat!=null)move.dispose();
		if(potionsHint!=null)move.dispose();
		if(mainMenu!=null)mainMenu.dispose();
		if(boss!=null)boss.dispose();
		if(coinsMonitor!=null)coinsMonitor.dispose();
		if(portal!=null)portal.dispose();
		if(cyan!=null)cyan.dispose();
		if(yellow!=null)yellow.dispose();
		if(rose!=null)rose.dispose();
		if(villages!=null){
			for (Village v:villages
					) {
				v.dispose();
			}
		}
		Phase.texture.dispose();
		if(background1!=null)background1.dispose();
		if(background2!=null)background2.dispose();
		if(pausePanel!=null)pausePanel.dispose();
		batch.dispose();
		button.dispose();
		if(img!=null)img.dispose();
		if(manaIcon!=null)manaIcon.dispose();
		if(healthIcon!=null)healthIcon.dispose();
		if(font!=null)font.dispose();
		p1.dispose();
		p2.dispose();
		p3.dispose();
		if(die!=null)die.dispose();
		pixmap.dispose();
		if(enemies!=null) {
			for (Enemy d : enemies
					) {
				d.dispose();
			}
		}
		for (Smoke d:smokes
				) {
			d.dispose();
		}
		if(weapons!=null) {
			for (Weapon d:weapons
					) {
				d.dispose();
			}
		}
		if(potions!=null){for (Potion d:potions
				) {
			d.dispose();
		}
		}
	}

	public Main(){

	}

	@Override
	public void create () {
		batch = new MyBatch();
		portal = new Portal();
		autosave = new Autosave();
		autosave.start();

		try {
			sound = Gdx.audio.newMusic(Gdx.files.internal("sound.ogg"));
			sound.setVolume(0.3f);
			sound.play();
		}
		catch (Exception e) {}
		//sound.dispose();
		joystick = new Joystick();
		button = new PauseButton();
		for (int i = 1; i < Smoke.textures.length+1; i++) {
			Smoke.textures[i-1] = new Image("Smoke"+i+".png");
		}
        check();
	}

	@Override
	public void pause() {
		super.pause();
		if(lose){
			hero = new Hero(this);
		}
		try {
			Data.savePlayer(hero);
		}
		catch(Exception e){

		}
	}

	public void check(){
		Pause.click = false;
		Pause.mX = 0;
		Pause.mY = 0;
		if(inMenu){
			mainMenu = new MainMenu(this);
			InputProcessor mainmenuinfo = new MenuMouse(mainMenu);
		}
		else if(tutorial){
			InputProcessor mouseInfo = new MouseInfo();
			enemies = new ArrayList<Enemy>();
			weapons = new ArrayList<Weapon>();
			potions = new ArrayList<Potion>();
			hero = new Hero(this);
			tutorialHero = new Hero(this);
			hero.currentMana=120;
			hero.currentHp = 90;
			exit = new Hint("<Нажмите >Выйти в меню",new Vector2(50,Gdx.graphics.getHeight()-200),28,null);
			move = new Hint("Движение: стрелки слева.",new Vector2(Gdx.graphics.getWidth()/2-200,50),64,null);
			combat = new Hint("Для убийства противника,\nиспользуйте нужную комбинацию Сфер.",new Vector2(Gdx.graphics.getWidth()*1.3f,80),56,null);
			potionsHint = new Hint("Попробуйте зелья.\nНажмите на нужное для использования.",new Vector2(Gdx.graphics.getWidth()*3,80),56,null);
		}
		else {
			InputProcessor mouseInfo = new MouseInfo();
			if (teleport) {
				demoHero.dispose();
				try {

					//hero = Data.readPlayer(this);
					if(hero.currentHp<=0 || true){
						hero = new Hero(this);
					}

					villages = Data.villages;
					potions = Data.potions;
					enemies = Data.enemies;
					for (Enemy e:enemies
						 ) {
						e.initWhenReaded();
					}
					weapons = Data.weapons;
					System.out.println("Reading player completed successfully!");
				}
				catch(Exception e) {
					hero = new Hero(this);
					enemies = new ArrayList<Enemy>();
					villages = new ArrayList<Village>();
					villages.add(new Village());
					weapons = new ArrayList<Weapon>();
					potions = new ArrayList<Potion>();
					System.out.println("Save file not found. Creating player...");
				}
				potionPanel = new PotionPanel();
				coinsMonitor = new CoinsMonitor(Data.coins);
				mobSpawningTimer = 0;

			} else {
				img = new Image("Tavern.jpg");
				demoHero = new DemoHero(this);
				mouseInfo = new MouseInfo();
			}


		}
		initTestObjects();
		potionPanel = new PotionPanel();
		background1 = new ScroolingBackground(0, false);
		background2 = new ScroolingBackground(ScroolingBackground.texture.getWidth() * 1.2f, false);
		rose = new SphereLabel("rose", "J", Gdx.graphics.getWidth() - 380, 130);
		yellow = new SphereLabel("yellow", "K", Gdx.graphics.getWidth() - 260, 130);
		cyan = new SphereLabel("cyan", "L", Gdx.graphics.getWidth() - 140, 130);
	}

	/**
	 * Adds random enemy.
	 * @param walkTo
	 */
	protected void addEnemy(int walkTo){
		Enemy enemy;
		if(tutorial){
			enemy = new Enemy(random.nextInt(7) + 1, walkTo, enemies.size());
			enemies.add(enemy);
		}
		if(inDungeon && Math.random()<0.7f){
			if(boss!=null) {
				if (boss.getX() < -300 || boss.getX() > Gdx.graphics.getWidth() + 200 && boss != null) {
					enemy = new Enemy(random.nextInt(7) + 1, walkTo, enemies.size());
					enemies.add(enemy);
				}
			}
		}
		else if(Math.random()<0.7f && mobSpawningTimer>600){
			enemy = new Enemy(random.nextInt(7) + 1, walkTo, enemies.size());
			enemies.add(enemy);
		}
	}

	/**
	 * Adds random weapon.
	 * @param vector2
	 */
	protected static void addWeapon(Vector2 vector2,boolean maxLvl){
		if(!maxLvl) {
			Weapon weapon = new Weapon(random.nextInt((kills / 10) + 1), new Vector2(vector2.x, vector2.y)); //10 - как редко будет выпадать лучший меч
			weapons.add(weapon);
		}
		else{
			Weapon weapon = new Weapon((kills / 10)+3, new Vector2(vector2.x, vector2.y)); //10 - как редко будет выпадать лучший меч
			weapons.add(weapon);
		}

	}

	/**
	 * Adds random potion.
	 * @param vector2
	 */
	protected static void addPotion(Vector2 vector2){
		Potion potion = new Potion((byte)DropChance.getRandomPotionKind(),new Vector2(vector2.x,vector2.y));
		potions.add(potion);
	}
	//TODO полоска здоровья(что бы не вылазила), мобы не дожны спавниться пока герой в деревне

	/**
	 * Run if hero's hp < 0.
	 */
	protected void lose(){
		if (die == null) {
            FileHandle file = Gdx.files.local("save.forestry");
            if(file.exists()){
				hero = new Hero(this);
				enemies = new ArrayList<Enemy>();
				potions = new ArrayList<Potion>();
				weapons = new ArrayList<Weapon>();
				smokes = new ArrayList<Smoke>();
            	file.delete();
			}
			die = new Image("youDied.jpg");
			loseBlack = new Sprite(new Image(createProceduralPixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 0)));
			lose = true;
		}
	}

	void checkForPause(boolean resume){
		if(resume){
			pause = !pause;
			pausePanel = null;
		}
		else {
			if (Gdx.input.isKeyJustPressed((Input.Keys.ESCAPE))) {

				if (pausePanel == null) {
					pausePanel = new Pause(batch);
				} else {
					pausePanel = null;
				}

				pause = !pause;
			}
		}
	}

	void tp(){

		enemies = new ArrayList<Enemy>();
		weapons = new ArrayList<Weapon>();
		potions = new ArrayList<Potion>();
		if(!inDungeon) {
			dungeon = new Dungeon();
			inDungeon = true;
			boss = new Boss(false, this);
			rtpx = teleporter.getX()+200;
			hero.x = Gdx.graphics.getWidth()/3;
		}

		else{
			hero.x = rtpx;
			renderTp = false;
			inDungeon = false;
			boss = null;
			dungeon = null;
		}
		teleporter = null;
	}

	private void checkForDungeon(){
		if(inDungeon) {
			dungeon.render(batch);
		}
	}


	void checkForSpawnTeleport(){
		if(!inDungeon && teleporter == null && random.nextInt(5000)==0){
			teleporter = new Teleport(true,Gdx.graphics.getWidth(),this);
		}
		else if(inDungeon && boss!=null && boss.isDie()){
			renderTp = true;
		}
		if(renderTp){
			if (teleporter == null) {
                 teleporter = new Teleport(false,Gdx.graphics.getWidth(),this);
			}
			teleporter.render(batch);
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(Gdx.input.isKeyPressed(Input.Keys.P)){
			CoinsMonitor.addCoins(1);
		}

		if(inMenu){
			mainMenu.render(batch);

		}

		else if(tutorial){
			background1.drawBackground(batch);
			background2.drawBackground(batch);

			tutorialHero.render(batch);

			if (enemies!=null && !enemies.isEmpty()) {
				for (Enemy e : enemies) {
					e.render(batch);
				}
			}
			try {
				if (!potions.isEmpty()) {
					for (Potion potion : potions) {
						potion.render(batch);
					}
				}
			} catch (Exception e) {

			}
			try {
				if (!enemies.isEmpty()) {
					for (Enemy e : enemies) {
						e.deadOrAlive();
					}
				}
			} catch (Exception e) {

			}
			try {
				if (!potions.isEmpty()) {
					for (Potion e : potions) {
						e.touch();
					}
				}
			} catch (Exception e) {

			}

			background1.drawForeground(batch);
			background2.drawForeground(batch);
			move.render(batch);
			combat.render(batch);
			potionsHint.render(batch);


			batch.begin();
			batch.draw(p2, 45, Gdx.graphics.getHeight() - 45, hero.maxHp + 4, 24);  //healthBg
			batch.draw(p1, 47, Gdx.graphics.getHeight() - 43, i, 20, 0, 0, (int) i, 30, false, false);           //current hp
			i = hero.currentHp;
			batch.draw(healthIcon, 35, Gdx.graphics.getHeight() - 50);
			batch.draw(p2, 45, Gdx.graphics.getHeight() - 75, hero.maxMana + 4, 18);
			batch.draw(p3, 47, Gdx.graphics.getHeight() - 73, mana, 14);
			mana = hero.currentMana;
			batch.draw(manaIcon, 35, Gdx.graphics.getHeight() - 80);
			batch.end();
			potionPanel.render(batch);
			if (pausePanel != null) {
				pausePanel.render();
			}
			cyan.render(batch);
			rose.render(batch);
			yellow.render(batch);
			if (FindClosestEnemy.find() != null && FindClosestEnemy.find().arrow != null) {
				FindClosestEnemy.find().arrow.render(batch);
			}
			joystick.render(batch);
			button.render(batch);
				if(false){
					hero.currentHp = 200;
					hero.currentMana = 200;
					tutorial = false;
					inMenu = true;
					check();

			}



		}
		else {
			mobSpawningTimer++;
			if (!teleport) {
				batch.begin();
				batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				batch.end();
				demoHero.render(batch);
				portal.render(batch);
				joystick.render(batch);
			} else {

				checkForPause(false);
				checkForSpawnTeleport();
				checkForDungeon();
				if (teleport && !inDungeon) {
					background1.drawBackground(batch);
					background2.drawBackground(batch);
					//background3.drawBackground(batch);

					for (Village v:villages
						 ) {
						if (v != null) {
							if(v.position.x>-2500)v.render(batch);
						}
					}
				}
				if(villages.get(villages.size()-1).position.x<=-3000){
				    villages.add(new Village());
                }
				if (teleporter != null && !inDungeon || teleporter != null && !teleporter.isEnd()) {
					teleporter.render(batch);
				}
				if (laser != null) laser.render(batch);
				if (inDungeon && dungeon.teleport != null && !dungeon.teleport.flashed) {
					hero.render(batch);
				} else if (inDungeon && dungeon.teleport == null) {
					hero.render(batch);
				} else if (!inDungeon) {
					hero.render(batch);
				}
				if (inDungeon && dungeon.teleport != null && !dungeon.teleport.flashed || !inDungeon || inDungeon &&
						dungeon.teleport == null) {
					if (!enemies.isEmpty()) {
						for (Enemy e : enemies) {
							e.render(batch);
						}
					}
					if (inDungeon && boss != null) {
						boss.render(batch);
					} else if (inDungeon) {

					}
					try {
						if (!weapons.isEmpty()) {
							for (Weapon weapon : weapons) {
								try {
									weapon.render(batch);
								}
								catch (Exception e){}
							}

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						if (!potions.isEmpty()) {
							for (Potion potion : potions) {
								potion.render(batch);
							}
						}
					} catch (Exception e) {

					}

					//enemiesRender = false;

					try {
						if (!enemies.isEmpty()) {
							for (Enemy e : enemies) {
								e.deadOrAlive();
							}
						}
					} catch (Exception e) {

					}
					try {
						if (!potions.isEmpty()) {
							for (Potion e : potions) {
								e.touch();
							}
						}
					} catch (Exception e) {
					}
					if (inDungeon) {
						dungeon.renderForeground(batch);
					}
					if (!inDungeon) {

						background1.drawForeground(batch);
						background2.drawForeground(batch);
					}
					//background3.drawForeground(batch);
					if(print){
						batch.begin();
						font.draw(batch,pr,10,50);
						batch.end();
					}
					joystick.render(batch);
					batch.begin();

					// Draw interface


					batch.draw(p2, 45, Gdx.graphics.getHeight() - 45, hero.maxHp + 4, 24);  //healthBg
					batch.draw(p1, 47, Gdx.graphics.getHeight() - 43, i, 20, 0, 0, (int) i, 30, false, false);           //current hp
					i = hero.currentHp;
					batch.draw(healthIcon, 35, Gdx.graphics.getHeight() - 50);
					batch.draw(p2, 45, Gdx.graphics.getHeight() - 75, hero.maxMana + 4, 18);
					batch.draw(p3, 47, Gdx.graphics.getHeight() - 73, mana, 14);
					mana = hero.currentMana;
					batch.draw(manaIcon, 35, Gdx.graphics.getHeight() - 80);
					batch.end();
					button.render(batch);
					coinsMonitor.render(batch);
					potionPanel.render(batch);
					if (pausePanel != null) {
						pausePanel.render();
					}
					cyan.render(batch);
					rose.render(batch);
					yellow.render(batch);
					if (FindClosestEnemy.find() != null && FindClosestEnemy.find().arrow != null) {
						FindClosestEnemy.find().arrow.render(batch);
					}
					for (Village village:villages
						 ) {

						if ((Math.random() * (400 - kills / 8) < difficulty) && hero.x < village.position.x && !pause ||
								hero.x > village.position.x + village.texture1.getWidth() &&
										(Math.random() * (400 - kills / 2) < difficulty) && spawnChance <= 0 && !pause) {
							if (village.position.x > 0 && village.position.x < Gdx.graphics.getWidth() && !pause && !inDungeon) {
								spawnChance = 80;
								addEnemy(1);
							} else if (village.position.x + village.texture1.getWidth() > 0 &&
									village.position.x + village.texture1.getWidth() < Gdx.graphics.getWidth() && !inDungeon ||
									!pause && inDungeon && boss != null && boss.getX() > Gdx.graphics.getWidth()) {
								addEnemy(-1);
								spawnChance = 80;
							} else {
								addEnemy(0);
								spawnChance = 80;
							}


						} else {
							spawnChance--;
						}
					}


					if (lose) {
						batch.begin();
						font.draw(batch,pr,10,50);
						batch.end();
						batch.begin();
						batch.draw(die, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
						loseBlack.draw(batch, loseAlpha);
						loseTimer++;
						if(loseTimer>=200){
							if(font!=null)font.draw(batch,"Нажмите для выхода в меню",100,100);
							else{
								FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("10700.ttf"));
								FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
								parameter.color = Color.GRAY;
								parameter.characters = Hint.font_chars;
								parameter.size = 28;
								font = generator.generateFont(parameter);
								generator.dispose();
							}
							if(Gdx.input.isTouched()){
								teleport = false;
								hero = new Hero(this);
								potionPanel = new PotionPanel();
								lose = false;
								inMenu = true;loseTimer = 0;
								loseAlpha = 1;
								check();
							}
						}
						batch.end();
						if (loseAlpha > 0.01) loseAlpha -= 0.005f;

					}


					if (check) {
						check();
						check = false;
					}
				}
					if (teleporter != null && teleporter.flashed && !teleporter.isEnd()) {
						batch.begin();
						teleporter.flash.draw(batch, teleporter.alpha);
						batch.end();
					}

			}

		}
	}
	


	/**
	 * Move background.
	 * @param x
	 * @param direction
	 */
	public static void setBackgroundPosition(float x,byte direction){
		if (dungeon != null) {
			dungeon.move(hero.speed*direction);
			Boss.updatePosition(hero.speed*direction);
		}
		if (teleporter != null && !inDungeon) {
             teleporter.move(hero.speed*direction);
		}
		if(!inDungeon && !tutorial) {
			for (Village village:villages
				 ) {


				village.position.x += hero.speed * direction;
				village.guard1.position.x += hero.speed * direction;
				village.guard2.position.x += hero.speed * direction;
				village.shop.position.x += hero.speed * direction;
				if (village.shop.hint != null)
					village.shop.hint.position.x += hero.speed * direction;
			}
			background1.position.x += hero.speed * direction;
			background2.position.x += hero.speed * direction;
		}
		else if(tutorial){
			background1.position.x += hero.speed * direction;
			background2.position.x += hero.speed * direction;
			move.position.x += hero.speed * direction;
			combat.position.x += hero.speed * direction;
			potionsHint.position.x += hero.speed * direction;
			if(combat.position.x<=-100){
				if(!tutorialEnemy){
					tutorialEnemy = true;
					hero.main.addEnemy(-1);
				}
			}
		}
		for (Enemy e:enemies
				 ) {
				e.position.x += hero.speed * direction;


			}
			for (Weapon e:weapons
					) {
				e.position.x+=hero.speed*direction;
			}
			for (Potion e:potions
					) {
				e.position.x+=hero.speed*direction;
			}
		}

		static void checkForKeys(String key) {
			if (hero.arcaneBoost) {
				keyTyped1 = false;
				keyTyped2 = false;
				keyTyped3 = false;
				switch (key.charAt(0)){
					case ('j'):
						laser = new Laser();
						break;
					case ('k'):
						//solarChield = new SolarChield();
						break;
					case ('l'):
						hero.heal();
						break;
				}

				hero.arcaneBoost = false;
				hero.mainTexture = new Image("MainHero.png");
			}
			if(hero.currentMana>=8) {
			if (!tutorial) hero.currentMana -= 8;
			try {

				if (keyTyped1) {
					if (keyTyped2) {
						if (key.equals(FindClosestEnemy.find().k3 + "")) {
							FindClosestEnemy.find().die();
							keyTyped1 = false;
							keyTyped2 = false;
							keyTyped3 = false;
						} else {
							keyTyped1 = false;
							keyTyped2 = false;
							keyTyped3 = false;
						}
					} else {
						if (key.equals(FindClosestEnemy.find().k2 + "")) {
							keyTyped2 = true;
						} else {
							keyTyped1 = false;
							keyTyped2 = false;
						}
					}
				} else {

					if ((key).equals(FindClosestEnemy.find().k1 + "")) {
						keyTyped1 = true;
					}
				}
			} catch (Exception e) {
			}
		}
		else{
				Gdx.input.vibrate(50);
			}
		}



	private void initTestObjects() {

		int width =1 ;
		int height = 1;
		Pixmap pixmap2 = createProceduralPixmap(width, height,0,0,0);
        healthIcon = new Image("healthIcon.png");
        manaIcon = new Image("manaIcon.png");
		p1 = new Image("hBar.jpg");
		p2 = new Image(pixmap2);
		p3 = new Image("mBar.jpg");



	}

	/**
	 * Creates a new Pixmap.
	 * @param width
	 * @param height
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	Pixmap createProceduralPixmap (int width, int height, int r, int g, int b) {
		pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

		pixmap.setColor(r, g, b, 1);
		pixmap.fill();

		return this.pixmap;
	}
	static void print(String message){
		if (print == false) {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("10700.ttf"));
			FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
			parameter.color = Color.WHITE;
			parameter.size = 28;
			font = generator.generateFont(parameter);
			generator.dispose();
			print = true;
		}

		pr = message;
	}
}
