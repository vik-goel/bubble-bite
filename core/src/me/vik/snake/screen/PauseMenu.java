package me.vik.snake.screen;

import me.vik.snake.Game;
import me.vik.snake.input.TouchInput;
import me.vik.snake.util.Textures;
import me.vik.snake.util.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class PauseMenu {

	private static final float ACCELERATION = 0.008f;

	private Game game;
	private ShapeRenderer sr = new ShapeRenderer();

	private boolean enabled = false;
	private float alpha = 0;
	private float alphaSpeed = 0;

	private float resumeX, restartX, menuX;
	private float buttonY = 0.475f;
	private float buttonRadius = 0.035f;

	public PauseMenu(Game game) {
		this.game = game;
	}

	public void update(float dt) {
		alphaSpeed += ACCELERATION;
		
		if (enabled) {
			if (alpha < 1) {
				alpha = Math.min(1, alpha + alphaSpeed);
			} else {
				Gdx.graphics.setContinuousRendering(false);
			}

			respondToInput();
		}

		else if (!enabled && alpha > 0) {
			alpha = Math.max(0, alpha - alphaSpeed);
		}
	}

	private void respondToInput() {
		if (!TouchInput.isDown() || !Gdx.input.justTouched())
			return;

		float x = TouchInput.getX();
		float y = TouchInput.getY();

		if (Util.distance(x, y, resumeX, buttonY) < buttonRadius) {
			enabled = false;
			alphaSpeed = 0;
			Gdx.graphics.setContinuousRendering(true);
		}

		else if (Util.distance(x, y, restartX, buttonY) < buttonRadius) {
			enabled = false;
			alpha = 0;
			game.switchToDifficultySelectionScreen();
		}

		else if (Util.distance(x, y, menuX, buttonY) < buttonRadius) {
			enabled = false;
			alpha = 0;
			game.switchToMenuScreen();
		}
	}

	public void render(SpriteBatch batch, float aspectRatio) {
		if (alpha <= 0)
			return;
		
		renderMask();
		renderMenu(batch, aspectRatio);
	}
	
	private void renderMask() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		sr.begin(ShapeType.Filled);
		sr.setColor(0, 0, 0, alpha * 0.8f);
		sr.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sr.end();
		
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	
	private void renderMenu(SpriteBatch batch, float aspectRatio) {
		float xOffs = 0.06f;
		float width = aspectRatio - xOffs * 2;
		float height = ((float) Textures.pauseMenu.getHeight() / (float) Textures.pauseMenu.getWidth()) * width;
		float y = (1 - height) / 2;

		batch.begin();
		batch.setColor(1, 1, 1, alpha);
		batch.draw(Textures.pauseMenu, xOffs, y, width, height);
		batch.end();

		restartX = aspectRatio / 2;

		float buttonOffs = width / 2;
		resumeX = restartX - buttonOffs + buttonRadius * 3;
		menuX = restartX + buttonOffs - buttonRadius * 3;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		alpha = enabled ? 0 : 1;
		alphaSpeed = 0;
	}

}
