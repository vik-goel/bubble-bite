package me.vik.snake.screen;

import me.vik.snake.Game;
import me.vik.snake.input.TouchInput;
import me.vik.snake.util.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Rectangle;

public class MenuScreen extends RenderScreen {

	private Preferences prefs;
	
	private Rectangle playBounds = new Rectangle(0.1f, 0.425f, getAspectRatio() - 2 * 0.1f, 0.19f);
	private Rectangle howToBounds = new Rectangle(playBounds.x, playBounds.y - playBounds.height - 0.025f, playBounds.width, playBounds.height);
	private Rectangle audioBounds = new Rectangle(getAspectRatio() - 0.15f - 0.05f, 0.025f, 0.15f, 0.15f);
	private Rectangle creditsBounds = new Rectangle(getAspectRatio() * 0.06f, 0.035f, 0.13f * (float)Textures.creditsButton.getWidth() / (float)Textures.creditsButton.getHeight(), 0.13f);
	
	private boolean soundOn;
	
	public MenuScreen(Game game) {
		super(game);
		prefs = Gdx.app.getPreferences(Game.PREFERENCES_KEY);
		soundOn = prefs.getBoolean("soundOn", true);
	}
	
	public void updateScreen(float dt) {
		if (TouchInput.isDown() && Gdx.input.justTouched()) {
			if (playBounds.contains(TouchInput.getX(), TouchInput.getY()))
				game.switchToDifficultySelectionScreen();
			
			if (howToBounds.contains(TouchInput.getX(), TouchInput.getY()))
				game.switchToHowToScreen();
			
			if (audioBounds.contains(TouchInput.getX(), TouchInput.getY())) {
				soundOn = !soundOn;
				prefs.putBoolean("soundOn", soundOn);
				prefs.flush();
			}
			
			if (creditsBounds.contains(TouchInput.getX(), TouchInput.getY()))
				game.switchToCreditsScreen();
		}
	}

	public void renderScreen() {
		batch.begin();
		batch.enableBlending();

		batch.draw(Textures.background, 0, 0, getAspectRatio(), 1);
		batch.draw(Textures.title, 0, 0.63f, getAspectRatio(), 0.37f);
		
		drawButtons();
		
		batch.end();
	}

	private void drawButtons() {
		batch.draw(Textures.playOff, playBounds.x, playBounds.y, playBounds.width, playBounds.height);
		batch.draw(Textures.howToOff, howToBounds.x, howToBounds.y, howToBounds.width, howToBounds.height);
		batch.draw(soundOn ? Textures.soundOn : Textures.soundOff, audioBounds.x, audioBounds.y, audioBounds.width, audioBounds.height);
		batch.draw(Textures.creditsButton, creditsBounds.x, creditsBounds.y, creditsBounds.width, creditsBounds.height);
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
	
	public boolean isSoundOn() {
		return soundOn;
	}
	
	public void onBackPressed() {
	}

}
