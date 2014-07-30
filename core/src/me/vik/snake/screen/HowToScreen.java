package me.vik.snake.screen;

import me.vik.snake.Game;
import me.vik.snake.input.TouchInput;
import me.vik.snake.util.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class HowToScreen extends RenderScreen {

	private Rectangle backBounds = new Rectangle(0.04f, 0.04f, 0.15f, 0.15f);

	public HowToScreen(Game game) {
		super(game);
	}

	public void updateScreen(float dt) {
		if (TouchInput.isDown() && Gdx.input.justTouched()) {
			if (backBounds.contains(TouchInput.getX(), TouchInput.getY()))
				game.switchToMenuScreen();
		}
	}

	public void renderScreen() {
		batch.begin();
		batch.enableBlending();

		batch.draw(Textures.background, 0, 0, getAspectRatio(), 1);
		drawButtons();

		batch.end();
	}

	private void drawButtons() {
		batch.draw(Textures.backButton, backBounds.x, backBounds.y, backBounds.width, backBounds.height);
	}

	public void show() {

	}

	public void hide() {

	}

	public void pause() {

	}

	public void resume() {

	}

	public void dispose() {

	}

}
