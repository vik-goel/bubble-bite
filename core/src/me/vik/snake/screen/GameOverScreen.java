package me.vik.snake.screen;

import me.vik.snake.Game;
import me.vik.snake.input.TouchInput;
import me.vik.snake.util.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameOverScreen extends RenderScreen {

	private BitmapFont font = new BitmapFont();
	private SpriteBatch fontBatch = new SpriteBatch();

	private Rectangle playAgain = new Rectangle(0.05f, 0.05f, 0.15f, 0.15f);
	private Rectangle menu = new Rectangle(getAspectRatio() - playAgain.x - playAgain.width, playAgain.y, playAgain.width, playAgain.height);

	public GameOverScreen(Game game) {
		super(game);
	}

	public void updateScreen(float dt) {
		if (TouchInput.isDown() && Gdx.input.justTouched()) {
			if (playAgain.contains(TouchInput.getX(), TouchInput.getY()))
				game.switchToDifficultySelectionScreen();

			if (menu.contains(TouchInput.getX(), TouchInput.getY()))
				game.switchToMenuScreen();
		}
	}

	public void renderScreen() {
		batch.begin();
		batch.enableBlending();

		batch.draw(Textures.background, 0, 0, getAspectRatio(), 1);
		renderRibbon();
		renderButtons();

		batch.end();

		renderScore();
	}

	public void renderRibbon() {
		final float scaleFactor = 0.9f;

		float aspectRatio = getAspectRatio();
		float width = aspectRatio * scaleFactor;
		float x = (aspectRatio - width) / 2;
		float height = scaleFactor * (float) Textures.gameOverRibbon.getHeight() / (float) Textures.gameOverRibbon.getWidth();
		float y = 1 - height - 0.1f;

		batch.draw(Textures.gameOverRibbon, x, y, width, height);
	}

	private void renderScore() {
		String score = "Score: 1658";
		TextBounds scoreBounds = font.getBounds(score);
		
		String highScore = "High Score: 99999";
		TextBounds highScoreBounds = font.getBounds(highScore);

		float scoreX = (Gdx.graphics.getWidth() - scoreBounds.width) / 2;
		float scoreY = (Gdx.graphics.getHeight() + scoreBounds.height * 5) / 2;
		
		float highScoreX = (Gdx.graphics.getWidth() - highScoreBounds.width) / 2;
		float highScoreY = scoreY - highScoreBounds.height * 2;
		
		fontBatch.begin();
		font.setColor(0, 0, 0, 1);
		
		font.draw(fontBatch, score, scoreX, scoreY);
		font.draw(fontBatch, highScore, highScoreX, highScoreY);
		
		fontBatch.end();
	}

	private void renderButtons() {
		batch.draw(Textures.restartButton, playAgain.x, playAgain.y, playAgain.width, playAgain.height);
		batch.draw(Textures.menuButton, menu.x, menu.y, menu.width, menu.height);
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
