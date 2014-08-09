package me.vik.snake.gameobject;

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

	private Direction currentDirection;

	private HeadInput headInput;

	private float moveDelay;
	private float moveDelayCounter = 0;

	private boolean selfCollision = false;

	private int minX, minY, maxX, maxY;
	private int score = 0;

	private boolean soundOn = false;

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

			Color removeColor;

			boolean removedColor = false;
			int linkNum = 0;

			while ((removeColor = findChain()) != null) {
				linkNum = removeColor(removeColor, linkNum);
				removedColor = true;
			}

			if (removedColor)
				onRemoveLink(false, linkNum);

			warpWalls();
			checkSelfCollisions();
		}
	}

	protected void onRemoveLink(boolean deathShake, int linkNum) {
		CameraShaker.getInstance().shakeCamera(deathShake);

		if (soundOn) {
			if (linkNum > 5 || deathShake)
				explosions[1].play();
			else explosions[0].play();
		}
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
			moveDelay = 35 / 5f;
			break;
		case MEDIUM:
			moveDelay = 24 / 5f;
			break;
		case HARD:
			moveDelay = 17 / 5f;
			break;
		}
	}

	public int getScore() {
		return score;
	}

	public void consumeFoodScore() {
		score += 2;
	}

	public void removeLinkScore(int linkNum) {
		if (!selfCollision)
			score += linkNum;
	}

	public void setMusicEnabled(boolean soundOn) {
		this.soundOn = soundOn;
	}

	public static void createMusic() {
		explosions = new Sound[2];

		for (int i = 0; i < explosions.length; i++)
			explosions[i] = Gdx.audio.newSound(Gdx.files.internal("sound/explosion " + (i + 1) + ".mp3"));
	}

}
