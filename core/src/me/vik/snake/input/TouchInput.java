package me.vik.snake.input;

import com.badlogic.gdx.Gdx;

public class TouchInput {

	public static boolean isDown() {
		return Gdx.input.isTouched() || Gdx.input.isButtonPressed(0);
	}

	public static float getX() {
		return (float) Gdx.input.getX() / (float) Gdx.graphics.getWidth() * ((float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight());
	}

	public static float getY() {
		return 1f - (float) Gdx.input.getY() / (float) Gdx.graphics.getHeight();
	}
}
