package me.vik.snake.screen;

import java.util.ArrayList;

import me.vik.snake.Game;
import me.vik.snake.gameobject.EdgeObject;
import me.vik.snake.gameobject.Food;
import me.vik.snake.gameobject.GameObject;
import me.vik.snake.gameobject.Head;
import me.vik.snake.gameobject.ParticlePool;
import me.vik.snake.input.HeadInput;
import me.vik.snake.input.TouchInput;
import me.vik.snake.util.CameraShaker;
import me.vik.snake.util.Difficulty;
import me.vik.snake.util.RenderUtil;
import me.vik.snake.util.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen extends RenderScreen {

	private static final int NUM_FOOD = 3;

	private HeadInput headInput;

	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	private ParticlePool particlePool = new ParticlePool(1000);
	private Head head;

	private SpriteBatch fontBatch = new SpriteBatch();
	private BitmapFont font = new BitmapFont();

	private int maxX, maxY;
	private boolean destroyed;

	private Rectangle pauseButtonBounds = new Rectangle(getAspectRatio() - Game.GRID_SIZE - 0.01f, 1 - Game.GRID_SIZE, Game.GRID_SIZE, Game.GRID_SIZE);
	private PauseMenu pauseMenu;
	private boolean paused = false;

	public GameScreen(Game game, HeadInput headInput) {
		super(game);
		
		this.headInput = headInput;

		pauseMenu = new PauseMenu(game);
		initGameObjects();
	}

	private void initGameObjects() {
		maxX = (int) (getAspectRatio() / Game.GRID_SIZE) - 1;
		maxY = (int) (1f / Game.GRID_SIZE) - 2;

		for (int i = 0; i < NUM_FOOD; i++)
			gameObjects.add(new Food(1, 1, maxX, maxY));

		for (int i = 0; i <= maxX; i++) {
			gameObjects.add(new EdgeObject(i, 0));
			gameObjects.add(new EdgeObject(i, maxY));
		}

		for (int i = 0; i <= maxY; i++) {
			gameObjects.add(new EdgeObject(0, i));
			gameObjects.add(new EdgeObject(maxX, i));
		}
	}

	public void updateScreen(float dt) {
		updatePauseMenu(dt);

		if (paused)
			return;

		updateCameraShaker(dt);
		particlePool.update(dt);
		
		if (!destroyed) {
			if (head.hasCollidedWithSelf()) {
				destroyed = true;
				head.destroy();
			}

			for (int i = 0; i < gameObjects.size(); i++)
				gameObjects.get(i).update(dt);
		} else {
			if (particlePool.getNumUsed() <= 12)
				game.switchToGameOverScreen();
		}
	}

	private void updatePauseMenu(float dt) {
		if (!paused && !destroyed && TouchInput.isDown() && Gdx.input.justTouched() && pauseButtonBounds.contains(TouchInput.getX(), TouchInput.getY())) {
			paused = !paused;
			pauseMenu.setEnabled(paused);
		}

		pauseMenu.update(dt);

		if (!pauseMenu.isEnabled())
			paused = false;
	}
	
	private void updateCameraShaker(float dt) {
		CameraShaker.getInstance().setCamera(camera);
		CameraShaker.getInstance().update(dt);
	}

	public void renderScreen() {
		RenderUtil.renderGrid(camera, 1, 1, maxX, maxY, getXOffset());

		for (int i = 0; i < gameObjects.size(); i++)
			if (!destroyed || !(gameObjects.get(i) instanceof Head))
				gameObjects.get(i).render(batch, getXOffset());

		particlePool.render(batch, getXOffset());

		renderScore();
		renderPauseButton();

		pauseMenu.render(batch, getAspectRatio());
	}

	private void renderScore() {
		String message = "Score: " + head.getScore();

		fontBatch.begin();
		font.setColor(1, 1, 1, 1);
		font.draw(fontBatch, message, 5f, Gdx.graphics.getHeight() - font.getBounds(message).height / 2 + 3f);
		fontBatch.end();
	}

	private void renderPauseButton() {
		batch.begin();
		batch.draw(Textures.pauseButton, pauseButtonBounds.x, pauseButtonBounds.y, pauseButtonBounds.width, pauseButtonBounds.height);
		batch.end();
	}

	public void show() {
		Gdx.graphics.setContinuousRendering(true);

		particlePool.reset();

		if (head != null)
			gameObjects.remove(head);

		head = new Head(1, 1, maxX, maxY, headInput, particlePool);
		gameObjects.add(head);

		Food.reset(head);

		destroyed = false;
	}

	public void hide() {
		Gdx.graphics.setContinuousRendering(false);
	}

	public void setDifficulty(Difficulty difficulty) {
		head.setDifficulty(difficulty);
	}

	public float getXOffset() {
		return (getAspectRatio() % Game.GRID_SIZE) / 2;
	}

	public void pause() {

	}

	public void resume() {

	}

	public void dispose() {

	}

}
