package me.vik.snake.screen;

import java.util.ArrayList;

import me.vik.snake.Game;
import me.vik.snake.gameobject.EdgeObject;
import me.vik.snake.gameobject.Food;
import me.vik.snake.gameobject.GameObject;
import me.vik.snake.gameobject.Head;
import me.vik.snake.gameobject.ParticlePool;
import me.vik.snake.input.HeadInput;
import me.vik.snake.util.RenderUtil;

public class GameScreen extends RenderScreen {

	private static final int NUM_FOOD = 3;

	private HeadInput headInput;
	
	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	private ParticlePool particlePool = new ParticlePool(1000);
	private Head head;
	
	private int maxX, maxY;

	public GameScreen(Game game, HeadInput headInput) {
		super(game);
		this.headInput = headInput;
		
		maxX = (int) (getAspectRatio() / Game.GRID_SIZE) - 1;
		maxY = (int) (1f / Game.GRID_SIZE) - 1;
		
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
		if (head.hasCollidedWithSelf())
			show();
		
		for (int i = 0; i < gameObjects.size(); i++)
			gameObjects.get(i).update(dt);
		
		particlePool.update(dt);
	}

	public void renderScreen() {
		RenderUtil.renderGrid(camera, 1, 1, maxX, maxY);
		
		for (int i = 0; i < gameObjects.size(); i++)
			gameObjects.get(i).render(batch);
		
		particlePool.render(batch);
	}

	public void show() {
		particlePool.reset();
		
		if (head != null)
			gameObjects.remove(head);
		
		head = new Head(1, 1, maxX, maxY, headInput, particlePool);
		gameObjects.add(head);
		
		Food.reset(head);
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
