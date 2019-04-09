package com.mygdx.tugofwar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TugOfWar extends ApplicationAdapter {
	SpriteBatch batch;
	float screenHeight, screenWidth;
	Texture background, gameOver;
	Texture[] horse, man, rope;
	BitmapFont font;

	int animationState = 0;
	int gameState = 0;
	int pause = 0;

	float velocity;
	float horsePower = 0.1f;
	float manX, horseX, ropeX, ropeY;
	int score;

	@Override
	public void create() {
		batch = new SpriteBatch();
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		background = new Texture("bg.png");
		gameOver = new Texture("gameover.jpg");

		man = new Texture[2];
		man[0] = new Texture("Weak.png");
		man[1] = new Texture("Strong.png");

		horse = new Texture[2];
		horse[0] = new Texture("horse 1.png");
		horse[1] = new Texture("horse 2.png");

		rope = new Texture[2];
		rope[0] = new Texture("rope 1.png");
		rope[1] = new Texture("rope 2.png");

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(8);
	}

	public void initialize() {
		score = 0;
		velocity = 0;

		manX = (int)screenWidth / 5 * 4;
		horseX = (int)screenWidth / 5 * 2;
		ropeX = (int)screenWidth / 5 * 3 -120;
		ropeY = (int)screenHeight / 18;
	}

	@Override
	public void render() {
		batch.begin();
		batch.draw(background, 0, 0, screenWidth, screenHeight);

		if (gameState == 1) {
			batch.draw(man[animationState], manX, 0);
			batch.draw(horse[animationState], horseX, 0);
			batch.draw(rope[animationState], ropeX, ropeY);


			if (Gdx.input.justTouched()) {
				velocity = -5;
			}

			if (pause < 8) {
				pause++;
			} else {
				pause = 0;
				score++;
				horsePower += 0.001f;
				if (animationState == 0) {
					animationState++;
				} else {
					animationState = 0;
				}
			}

			velocity += horsePower;
			manX -= velocity;
			horseX -= velocity;
			ropeX -= velocity;

			if (manX <= 0) {
				manX = 0;
				gameState = 2;
			}

		} else if (gameState == 0) {
			// Waitng to start
			if (Gdx.input.justTouched()) {
				initialize();
				gameState = 1;
			}
		} else if (gameState == 2) {
			batch.draw(gameOver, screenWidth / 2, screenHeight / 2);
			if (Gdx.input.justTouched()) {
				initialize();
				gameState = 1;
			}
		}

		//draw the score
		font.draw(batch, "Score: " + String.valueOf(score/10),100,screenHeight - 200);
		batch.end();
	}
}
