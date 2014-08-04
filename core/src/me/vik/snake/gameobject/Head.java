package me.vik.snake.gameobject;

import java.util.Random;

import me.vik.snake.input.DirectionListener;
import me.vik.snake.input.HeadInput;
import me.vik.snake.util.CameraShaker;
import me.vik.snake.util.Difficulty;
import me.vik.snake.util.Direction;
import me.vik.snake.util.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;

public class Head extends Link implements DirectionListener {

	private static Sound[] explosions;
	private static Random random = new Random();
	
	private Direction currentDirection;

	private HeadInput headInput;

	private float moveDelay;
	private float moveDelayCounter = 0;

	private boolean selfCollision = false;

	private int minX, minY, maxX, maxY;
	private int score = 0;

	public Head(int minX, int minY, int maxX, int maxY, HeadInput headInput, ParticlePool particlePool) {
		super(maxX / 2, maxY / 2, particlePool);

		this.headInput = headInput;
		headInput.setDirectionListener(this);
		
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;

		currentDirection = direction;
		tex = Textures.head;
	}

	public void update(float dt) {
		super.update(dt);

		headInput.update();

		moveDelayCounter += dt;
		moveSnake();
	}

	private void moveSnake() {
		if (moveDelayCounter > moveDelay) {

			moveDelayCounter -= moveDelay;
			direction = currentDirection;

			getTail().moveToParent();
			move(false);

			warpWalls();
			checkSelfCollisions();

			Color removeColor;

			boolean removedColor = false;
			
			while ((removeColor = findChain()) != null) {
				removeColor(removeColor);
				removedColor = true;
			}
			
			if (removedColor) 
				onRemoveLink(false);
		}
	}
	
	protected void onRemoveLink(boolean deathShake) {
		CameraShaker.getInstance().shakeCamera(deathShake);
		explosions[random.nextInt(explosions.length)].play();
	}

	private void warpWalls() {
		if (x < minX)
			x = maxX - 1;
		else if (x >= maxX)
			x = minX;

		if (y < minY)
			y = maxY - 1;
		else if (y >= maxY)
			y = minY;
	}

	private void checkSelfCollisions() {
		if (child != null && child.linkAt(x, y))
			selfCollision = true;
	}

	public boolean hasCollidedWithSelf() {
		return selfCollision;
	}

	public void onUp() {
		if (direction != Direction.DOWN) 
			currentDirection = Direction.UP;
	}

	public void onDown() {
		if (direction != Direction.UP) 
			currentDirection = Direction.DOWN;
	}

	public void onRight() {
		if (direction != Direction.LEFT) 
			currentDirection = Direction.RIGHT;
	}

	public void onLeft() {
		if (direction != Direction.RIGHT) 
			currentDirection = Direction.LEFT;
	}

	public void setDifficulty(Difficulty difficulty) {
		switch (difficulty) {
		case EASY:
			moveDelay = 35;
			break;
		case MEDIUM:
			moveDelay = 24;
			break;
		case HARD:
			moveDelay = 17;
			break;
		}
	}
	
	public int getScore() {
		return score;
	}
	
	public void consumeFoodScore() {
		score += 10;
	}
	
	public void removeLinkScore() {
		if (!selfCollision)
			score += 15;
	}
	
	static {
		explosions = new Sound[5];
		
		for (int i = 0; i < explosions.length; i++)
			explosions[i] = Gdx.audio.newSound(Gdx.files.internal("sound/explosion " + (i + 1) + ".mp3"));
	}

}
