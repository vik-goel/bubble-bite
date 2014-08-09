package me.vik.snake.screen;

import me.vik.snake.Game;
import me.vik.snake.input.TouchInput;
import me.vik.snake.util.Difficulty;
import me.vik.snake.util.Fonts;
import me.vik.snake.util.RenderUtil;
import me.vik.snake.util.ScoreManager;
import me.vik.snake.util.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameOverScreen extends RenderScreen {

	private SpriteBatch fontBatch = new SpriteBatch();
	private Color fontColor = RenderUtil.createColor(58, 35, 10);

	private Rectangle playAgain = new Rectangle(0.05f, 0.05f, 0.15f, 0.15f);
	private Rectangle menu = new Rectangle(getAspectRatio() - playAgain.x - playAgain.width, playAgain.y, playAgain.width, playAgain.height);
	private ScoreManager scoreManager;
	
	private Difficulty difficulty;

	public GameOverScreen(Game game) {
		super(game);
	}

	public void updateScreen(float dt) {
		if (TouchInput.isDown() && Gdx.input.justTouched()) {
			if (playAgain.contains(TouchInput.getX(), TouchInput.getY()))
				game.switchToGameScreen(difficulty);

			if (menu.contains(TouchInput.getX(), TouchInput.getY()))
				game.switchToMenuScreen();
		}
	}

	public void renderScreen() {
		batch.begin();
		batch.enableBlending();

		batch.draw(Textures.background, 0, 0, getAspectRatio(), 1);
		batch.draw(Textures.scoreBackground, getAspectRatio() * 0.05f, 0.25f, getAspectRatio() * 0.9f, 0.4f);
		
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
		final float originalFontHeight = 32;
		final float targetFontHeight = 0.055f;
		
		float fontHeight = Gdx.graphics.getHeight() * targetFontHeight;
		float scaleFactor =  fontHeight / originalFontHeight;
		
		Fonts.bubbleFont.setScale(scaleFactor);
		
		String score = "Score: " + scoreManager.getCurrentScore();
		TextBounds scoreBounds = Fonts.bubbleFont.getBounds(score);
		
		String highScore = "High Score: " + scoreManager.getBestScore();
		TextBounds highScoreBounds = Fonts.bubbleFont.getBounds(highScore);

		float scoreX = (Gdx.graphics.getWidth() - scoreBounds.width) / 2;
		float highScoreX = (Gdx.graphics.getWidth() - highScoreBounds.width) / 2;
		
		fontBatch.begin();
		Fonts.bubbleFont.setColor(fontColor);
		
		Fonts.bubbleFont.draw(fontBatch, score, scoreX, Gdx.graphics.getHeight() / 16f * 8.5f);
		Fonts.bubbleFont.draw(fontBatch, highScore, highScoreX, Gdx.graphics.getHeight() / 16f * 6.5f);
		
		fontBatch.end();
	}

	private void renderButtons() {
		batch.draw(Textures.restartButton, playAgain.x, playAgain.y, playAgain.width, playAgain.height);
		batch.draw(Textures.menuButton, menu.x, menu.y, menu.width, menu.height);
	}
	

	public void setScoreManager(Difficulty difficulty) {
		this.scoreManager = ScoreManager.getInstance(difficulty);
		this.difficulty = difficulty;
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
	
	public void onBackPressed() {
		game.switchToMenuScreen();
	}

}
