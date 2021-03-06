package janglifang;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;
import java.util.Vector;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture wallpaperTexture;
	private OrthographicCamera objOrthographicCamera; //ปรับภาพตามขนาดของจอภาพอัตโนมัติ
	private BitmapFont nameBitmapFont;
	private Texture cloudTexture;
	int xCloudAnInt,yCloundAnInt=600;
	private boolean cloudABoolean = true;
	private Rectangle pigRectangle; //ใช้เป็นตัวควบคุม
	private Texture pigTexture;
	private Vector3 objVector3; //ใช้เคลื่อนที่หมู
	private Sound pigSound; //เสียงหมู
	private Rectangle coinsRectangle;  // สร้างเหรียญ
	private Texture coinsTexture;     //  สร้างเหรียญ

	private Array<Rectangle> coinsArray, rainArray;
	private long lastDropCoins; //สุ่มจากตำแหน่งสุดท้าย ที่ไม่ตรงกับตำแหน่งเดิม
	private Iterator<Rectangle> coinsIterator; // java.util

	private Sound waterDropSound; //ไฟล์เสียงเมื่อเหรียญตกลงถึงพื้น

	private Sound coinDropSound;

	private BitmapFont scoreBitmapFont; //แสดงคะแนน
	private int scoreAnInt=0; //รวมคะแนน

	private Texture rainTexture;  //หยดนำ้
	private Rectangle rainRectangle;
	private long lastDropRain;
	private Iterator rainIterator;

	private Music rainMusic;
	private Music backgroundMusic; //เสียงที่เล่นตลอดเวลา

	private int flaseAnInt;

	private boolean finishABoolean = false;
	private int finialScoreAnInt; //คะแนนสุดท้ายที่ได้

	private BitmapFont showScoreBitmapFont;
	@Override
	public void create() {
		batch = new SpriteBatch();

		//การกำหนดขนาดจอที่ต้องการ
		objOrthographicCamera = new OrthographicCamera();
		objOrthographicCamera.setToOrtho(false, 1200, 800);  //1200*800

		//กำหนด Wallpeper
		wallpaperTexture = new Texture("hB_Lifang.png");

		//setup BitMapFont
		nameBitmapFont = new BitmapFont();
		nameBitmapFont.setColor(Color.MAGENTA);
		nameBitmapFont.setScale(4);

		//setup Cloud
		cloudTexture = new Texture("cloud.png");

		//setup pig
		pigTexture = new Texture("pig.png");

		//setup Rectangle Pig
		pigRectangle = new Rectangle();
		pigRectangle.x = 568; 	//(1200/2)=600  600-(64/2)=568
		pigRectangle.y = 100;
		pigRectangle.width = 64;   //ขนาดภาพหมู
		pigRectangle.height = 64;  //ขนาดภาพหมู

		//Setup pig Sound
		pigSound = Gdx.audio.newSound(Gdx.files.internal("pig.wav"));

		//Setup Coins
		coinsTexture = new Texture("coins.png");

		//Create coinsArray
		coinsArray = new Array<Rectangle>();
		coinsRandomDrop(); //สุ่มหาตำแหน่งจาก 1-1200 จุด ของหน้าจอ

		//Setup WaterDrop
		waterDropSound = Gdx.audio.newSound(Gdx.files.internal("water_drop.wav"));

		//Set Coins Drop
		coinDropSound = Gdx.audio.newSound(Gdx.files.internal("coins_drop.wav"));

		//Setup scoreBitMapFont
		scoreBitmapFont = new BitmapFont();
		scoreBitmapFont.setColor(Color.BLUE); //กำหนดสี
		scoreBitmapFont.setScale(4);          //กำหนดขนาดการแสดงผล

		//Setup rainTexture
		rainTexture = new Texture("droplet.png");

		//Create rainArray
		rainArray = new Array<Rectangle>();
		rainRandomDrop();  //การสุ่มที่ทำให้ฝนตก

		//Setup rainMusic  เล่นเพลง rain.mp3
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		//Setup backgroundMusic
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bggame.mp3"));

		//Setup ShowScore
		showScoreBitmapFont = new BitmapFont();
		showScoreBitmapFont.setColor(230,28,223,255);
		showScoreBitmapFont.setScale(5);

	}// create เอาไว้กำหนดค่า

		private void rainRandomDrop() {
		rainRectangle = new Rectangle();
		rainRectangle.x = MathUtils.random(0, 1136); //1200-64=1136  64 คือขนาดภาพ
		rainRectangle.y = 800;  //ขนาดจอ
		rainRectangle.width = 64;
		rainRectangle.height = 64;

		rainArray.add(rainRectangle);
		lastDropRain = TimeUtils.nanoTime();
	}

	private void coinsRandomDrop() {
		coinsRectangle = new Rectangle();  //เงาที่อยู่ใต้ภาพ
		coinsRectangle.x = MathUtils.random(0, 1136);
		//พิกัดของ x ที่นำมาให้ coinsRectangle 1200-64=1136 ทุก DataTimes ขยับจะได้ค่าที่สุ่มใหม่
		coinsRectangle.y = 800;
		//เหรียญตกจากด้านบน ทำให้กำหนด y คงที่ (ถ้าต้องการให้เหรียญมาจากด้านอื่น ก็ขยับ y
		coinsRectangle.width = 64;
		coinsRectangle.height = 64;
		coinsArray.add(coinsRectangle);
		lastDropCoins = TimeUtils.nanoTime(); //ถ้ามีการดรอบเหรียญได้ค่าเดิมจะไม่ดรอบเหรียญ คือไม่ชนตำแหน่งกัน
	}//coinsRandomDrop

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//setup screen
		objOrthographicCamera.update();
		batch.setProjectionMatrix(objOrthographicCamera.combined);

		//ใช้วาดภาพ

		batch.begin();

		//Drawable Wallpaper
		batch.draw(wallpaperTexture, 0, 0);

		//Drawble Cloud
		batch.draw(cloudTexture, xCloudAnInt, yCloundAnInt);

		//Drawable BitMapFont
		nameBitmapFont.draw(batch, "Coins PBRU", 50, 700); //ออกมา 50 จากบน 600

		//Drawable Pig
		batch.draw(pigTexture, pigRectangle.x, pigRectangle.y);

		//Drawable coins
		for (Rectangle forCoins : coinsArray) {
			batch.draw(coinsTexture,forCoins.x,forCoins.y);
		}

		//Drawable Score
		scoreBitmapFont.draw(batch, "Score = " + Integer.toString(scoreAnInt), 800, 750);
		//แปลงค่าข้อความเป็นตัวเลข toString(sccoreAnInt)
		//แสดงข้อความที่ระดับความสูงเดียวกับคำว่า Coin PBRU ที่ 750

		//Drawbable Rain
		for (Rectangle forRain : rainArray) {
			batch.draw(rainTexture,forRain.x ,forRain.y);
		}

		//เปลี่ยนฉาก
		if (finishABoolean) {
			batch.draw(wallpaperTexture,0,0);
			showScoreBitmapFont.draw(batch,"Your Score ="+ Integer.toString(finialScoreAnInt),700,750);
		}//if



		batch.end();
		movecloud();

		//Active when Touch Screen เมื่อนิ้วโดนจอภาพให้ทำงาน
		activeTouchScreen();

		//Random Drop Coins การสุ่มการหย่อนเหรียญลงมา
		randomDropCoins();

		//Random Drop coins
		randomDropRain();

		//Play rain.mp3
		rainMusic.play();

		//Play backGroundMusic
		backgroundMusic.play();

	}// render ตัวนี้คือ Loop

	private void randomDropRain() {
		if (TimeUtils.nanoTime()-lastDropRain>1E9) {
			rainRandomDrop();
		}//if
		rainIterator = rainArray.iterator();
		while (rainIterator.hasNext()) //ให้ฝนตก เท่ากับเวลาที่เหรียญตก
		{
			Rectangle myRainRectangle = (Rectangle) rainIterator.next();
			myRainRectangle.y -= 50 * Gdx.graphics.getDeltaTime();

			//When Rain drop into Floor เมื่อฝนตกลงพื้น ให้เล่นเสียงนำ้
			if (myRainRectangle.y + 64 < 0) {
				waterDropSound.play();
				rainIterator.remove();
			}//if

			//When Rain Overlap Pig
			if (myRainRectangle.overlaps(pigRectangle)) {
				scoreAnInt -= 1;  //ลดคะแนน
				rainIterator.remove();
			}//if

		}//while
	}//randomDropRain

	private void randomDropCoins() {
		if (TimeUtils.nanoTime()-lastDropCoins > 1E9) //สุ่มตัวเลขทุก 1 วินาที
		// > 1E9 คือ มีค่ามากกว่า 10 ยกกำลัง 9
		{
			coinsRandomDrop();
		}
		coinsIterator = coinsArray.iterator();
		while (coinsIterator.hasNext()) //ค่าของการหย่อนเหรียญไม่สิ้นสุด ให้หย่อนเหรีญไปเลี่อยๆ
		{
			Rectangle myCoinsRectangle = coinsIterator.next();
			myCoinsRectangle.y -= 50 * Gdx.graphics.getDeltaTime();
			// ตัวแปรที่โชว์เหรียญ y ค่า y ที่เปลี่ยน ด้วยความเร็ว -50

			//When Coins into Floor เมื่อเหรียญหย่อนลงถึงพื้นแล้วให้ล้างหน่วยความจำใหม่
			if (myCoinsRectangle.y +64 <0) {
				flaseAnInt += 1; //หยุดเสียง
				waterDropSound.play();    //เล่นไฟล์เสียง water_drop.wav
				coinsIterator.remove();
				checkFalse(); //หยุดเสียง
			}//if

			//WhenCoins OverLap Pig  เหรียญตรงกับหมู
			if (myCoinsRectangle.overlaps(pigRectangle)) {
				coinDropSound.play();
				coinsIterator.remove(); //เหรียญหายไป

				scoreAnInt += 1; //เก็บค่าคะแนน เมื่อเหรียญโดนหมู

				checkFalse(); //ตรวจว่าคะแนนถึงเกณฑ์ที่วางไว้ 20 คะแนน
			}


		}// While Loop


	}//randomDropCoins

	private void checkFalse() {
		if (flaseAnInt > 20) {
			dispose();

			if (!finishABoolean) //เปลี่ยนฉาก
			{
				finialScoreAnInt = scoreAnInt;
			}
			finishABoolean = true;

		}
	}//checkFalse

	@Override
	public void dispose() {
		super.dispose();

		backgroundMusic.dispose(); //เสียงที่เล่นตลอด
		rainMusic.dispose(); //ฝนหยุดตก
		pigSound.dispose();
		waterDropSound.dispose();
		coinDropSound.dispose();
	}

	private void activeTouchScreen() {
		//if (Gdx.input.isTouched()) {     //เมื่อมีการคลิก
		//	objVector3 = new Vector3();  //เก็บค่าที่นิ้วไปโดนจอ  x , y
		//	objVector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);

		//	if (objVector3.x<600) {        //มีค่าน้อยกว่าจุดกึ่งกลางจอ
		//		pigRectangle.x -= 10;
		//	} else {
		//		pigRectangle.x += 10;
		//	}
	//	}//if


		if (Gdx.input.isTouched()) {     //เมื่อมีการคลิก
			//Sound Effect Pig
			pigSound.play();

			objVector3 = new Vector3();  //เก็บค่าที่นิ้วไปโดนจอ  x , y
			objVector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);

			//if (objVector3.x < 600) {        //มีค่าน้อยกว่าจุดกึ่งกลางจอ  กรณ๊นี้กำหนดค่าตายตัวของกึ่งกลางจอเอง
			if (objVector3.x < Gdx.graphics.getWidth() / 2) //กรณีนี้หาค่ากึ่งกลางได้แม้นย่ำกว่า
			{
				if (objVector3.x < 0) {
					pigRectangle.x = 0;
				} else {
					pigRectangle.x -= 10;
				}
			} else {
				if (pigRectangle.x > 1136) {
					pigRectangle.x = 1136;
				} else {
					pigRectangle.x += 10;
				}
			}
		}
			}

	private void movecloud() {
		if (cloudABoolean) {
			if (xCloudAnInt < 937) {
				xCloudAnInt += 100 * Gdx.graphics.getDeltaTime();
			} else {
				cloudABoolean = !cloudABoolean;
			}
		} else {
			if (xCloudAnInt > 0) {
				xCloudAnInt -= 100 * Gdx.graphics.getDeltaTime();
			} else {
				cloudABoolean = !cloudABoolean;
			}
		}

	}


}// Main class
