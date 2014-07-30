package me.vik.snake.screen;

import me.vik.snake.Game;
import me.vik.snake.input.TouchInput;
import me.vik.snake.util.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class MenuScreen extends RenderScreen {

	private Rectangle playBounds = new Rectangle(0.1f, 0.5f, getAspectRatio() - 2 * 0.1f, 0.2f);
	private Rectangle howToBounds = new Rectangle(playBounds.x, playBounds.y - playBounds.height - 0.05f, playBounds.width, playBounds.height);
	private Rectangle audioBounds = new Rectangle(getAspectRatio() - 0.15f - 0.05f, 0.05f, 0.15f, 0.15f);
	
	private boolean soundOn = true;
	
	public MenuScreen(Game game) {
		super(game);
	}
	
	public void updateScreen(float dt) {
		if (TouchInput.isDown() && Gdx.input.justTouched()) {
			if (playBounds.contains(TouchInput.getX(), TouchInput.getY()))
				game.switchToDifficultySelectionScreen();
			
			if (howToBounds.contains(TouchInput.getX(), TouchInput.getY()))
				game.switchToHowToScreen();
			
			if (audioBounds.contains(TouchInput.getX(), TouchInput.getY()))
				soundOn = !soundOn;
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
		batch.draw(Textures.playOff, playBounds.x, playBounds.y, playBounds.width, playBounds.height);
		batch.draw(Textures.howToOff, howToBounds.x, howToBounds.y, howToBounds.width, howToBounds.height);
		batch.draw(soundOn ? Textures.soundOn : Textures.soundOff, audioBounds.x, audioBounds.y, audioBounds.width, audioBounds.height);
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
