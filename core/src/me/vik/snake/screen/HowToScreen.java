package me.vik.snake.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import me.vik.snake.Game;
import me.vik.snake.input.TouchInput;
import me.vik.snake.util.Textures;

public class HowToScreen extends RenderScreen {

	private Rectangle backBounds = new Rectangle(0.04f, 0.04f, 0.15f, 0.15f);
	private Rectangle forwardBounds = new Rectangle(getAspectRatio() - backBounds.width - backBounds.x, backBounds.y, backBounds.width, backBounds.height);
	private int screenNum = 1;

	public HowToScreen(Game game) {
		super(game);
	}

	public void updateScreen(float dt) {
		if (TouchInput.isDown() && Gdx.input.justTouched()) {
			if (backBounds.contains(TouchInput.getX(), TouchInput.getY())) {
				onBackPressed();
			}

			if (forwardBounds.contains(TouchInput.getX(), TouchInput.getY())) {
				if (screenNum == 1)
					screenNum++;
				else {
					screenNum = 1;
					game.switchToMenuScreen();
				}
			}
		}
	}

	public void renderScreen() {
		batch.begin();
		batch.enableBlending();

		batch.draw(Textures.background, 0, 0, getAspectRatio(), 1);
		batch.draw(screenNum == 1 ? Textures.instructions1 : Textures.instructions2, 0, 0, getAspectRatio(), 1);
		drawButtons();

		batch.end();
	}

	private void drawButtons() {
		batch.draw(Textures.backButton, backBounds.x, backBounds.y, backBounds.width, backBounds.height);
		batch.draw(screenNum == 1 ? Textures.forwardButton : Textures.menuButton, forwardBounds.x, forwardBounds.y, forwardBounds.width, forwardBounds.height);
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
		if (screenNum > 1)
			screenNum--;
		else
			game.switchToMenuScreen();
	}

}
