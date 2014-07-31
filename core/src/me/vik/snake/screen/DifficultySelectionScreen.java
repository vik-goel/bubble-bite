package me.vik.snake.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import me.vik.snake.Game;
import me.vik.snake.input.TouchInput;
import me.vik.snake.util.Difficulty;
import me.vik.snake.util.Textures;

public class DifficultySelectionScreen extends RenderScreen {

	private Rectangle easyBounds = new Rectangle(0.1f, 0.7f, getAspectRatio() - 2 * 0.1f, 0.2f);
	private Rectangle mediumBounds = new Rectangle(easyBounds.x, easyBounds.y - easyBounds.height - 0.05f, easyBounds.width, easyBounds.height);
	private Rectangle hardBounds = new Rectangle(mediumBounds.x, mediumBounds.y - mediumBounds.height - 0.05f, mediumBounds.width, mediumBounds.height);
	private Rectangle backBounds = new Rectangle(0.02f, 0.02f, 0.15f, 0.15f);

	public DifficultySelectionScreen(Game game) {
		super(game);
	}

	public void updateScreen(float dt) {
		if (TouchInput.isDown() && Gdx.input.justTouched()) {
			if (easyBounds.contains(TouchInput.getX(), TouchInput.getY()))
				game.switchToGameScreen(Difficulty.EASY);

			if (mediumBounds.contains(TouchInput.getX(), TouchInput.getY()))
				game.switchToGameScreen(Difficulty.MEDIUM);

			if (hardBounds.contains(TouchInput.getX(), TouchInput.getY()))
				game.switchToGameScreen(Difficulty.HARD);

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
		batch.draw(Textures.easyDifficulty, easyBounds.x, easyBounds.y, easyBounds.width, easyBounds.height);
		batch.draw(Textures.mediumDifficulty, mediumBounds.x, mediumBounds.y, mediumBounds.width, mediumBounds.height);
		batch.draw(Textures.hardDifficulty, hardBounds.x, hardBounds.y, hardBounds.width, hardBounds.height);
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
