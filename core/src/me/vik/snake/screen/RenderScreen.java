package me.vik.snake.screen;

import me.vik.snake.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class RenderScreen implements Screen {

	protected Game game;
	protected OrthographicCamera camera;
	protected SpriteBatch batch;

	public RenderScreen(Game game) {
		this.game = game;
		batch = new SpriteBatch();
		
		float aspectRatio = getAspectRatio();

		camera = new OrthographicCamera(aspectRatio, 1);
		camera.position.x = aspectRatio / 2;
		camera.position.y = 0.5f;
		camera.update();
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);

		updateScreen(Math.min(Gdx.graphics.getRawDeltaTime() * 60f, 3f));
		renderScreen();
	}

	public void resize(int width, int height) {

	}
	
	public float getAspectRatio() {
		return (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
	}
	
	public abstract void updateScreen(float dt);

	public abstract void renderScreen();

	public abstract void onBackPressed();

}
