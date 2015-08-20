package janglifang;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

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


	}// create เอาไว้กำหนดค่า

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

		batch.end();
		movecloud();

		//Active when Touch Screen เมื่อนิ้วโดนจอภาพให้ทำงาน
		activeTouchScreen();

	}// render ตัวนี้คือ Loop

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

			if (objVector3.x < 600) {        //มีค่าน้อยกว่าจุดกึ่งกลางจอ
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
