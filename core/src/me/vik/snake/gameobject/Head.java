package me.vik.snake.gameobject;

import me.vik.snake.input.DirectionListener;
import me.vik.snake.input.HeadInput;
import me.vik.snake.util.Direction;
import me.vik.snake.util.Textures;

import com.badlogic.gdx.graphics.Color;

public class Head extends Link implements DirectionListener {

	private Direction currentDirection;

	private HeadInput headInput;

	private float moveDelay = 20;
	private float moveDelayCounter = 0;

	private boolean selfCollision = false;

	private int minX, minY, maxX, maxY;

	public Head(int minX, int minY, int maxX, int maxY, HeadInput headInput, ParticlePool particlePool) {
		super(maxX / 2, maxY / 2, particlePool);

		this.headInput = headInput;
		headInput.setDirectionListener(this);
		
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;

		currentDirection = direction;
		texRegion = Textures.head[0];
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

			while ((removeColor = findChain()) != null) {
				removeColor(removeColor);
			}
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
		if (direction != Direction.DOWN) {
			currentDirection = Direction.UP;
			texRegion = Textures.head[0];
		}
	}

	public void onDown() {
		if (direction != Direction.UP) {
			currentDirection = Direction.DOWN;
			texRegion = Textures.head[2];
		}
	}

	public void onRight() {
		if (direction != Direction.LEFT) {
			currentDirection = Direction.RIGHT;
			texRegion = Textures.head[1];
		}
	}

	public void onLeft() {
		if (direction != Direction.RIGHT) {
			currentDirection = Direction.LEFT;
			texRegion = Textures.head[3];
		}
	}

}
