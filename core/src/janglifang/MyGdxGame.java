package janglifang;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture wallpaperTexture;
	private OrthographicCamera objOrthographicCamera; //ปรับภาพตามขนาดของจอภาพอัตโนมัติ
	private BitmapFont nameBitmapFont;
	private Texture cloudTexture;
	int xCloudAnInt,yCloundAnInt=600;
	private boolean cloudABoolean = true;
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

		batch.end();
		movecloud();
	}// render ตัวนี้คือ Loop

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
