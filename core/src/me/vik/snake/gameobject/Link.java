package me.vik.snake.gameobject;

import me.vik.snake.Game;
import me.vik.snake.util.Direction;
import me.vik.snake.util.RenderUtil;
import me.vik.snake.util.Textures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Link extends GameObject {

	private static ParticlePool particlePool;

	protected Direction direction = Direction.UP;
	protected Link parent, child;
	protected TextureRegion texRegion = Textures.link;

	public Link(float x, float y, ParticlePool particlePool) {
		super(x, y, Color.WHITE);
		Link.particlePool = particlePool;
	}

	public Link(Link parent, Color color) {
		super(parent.x, parent.y, color);

		this.direction = parent.direction;
		this.color = color;
		this.parent = parent;
		parent.child = this;

		move(true);
	}

	public void move(boolean reverse) {
		int moveAmount = reverse ? -1 : 1;

		switch (direction) {
		case RIGHT:
			x += moveAmount;
			break;
		case UP:
			y += moveAmount;
			break;
		case DOWN:
			y -= moveAmount;
			break;
		case LEFT:
			x -= moveAmount;
			break;
		}
	}

	protected void moveToParent() {
		if (parent == null)
			return;

		x = parent.x;
		y = parent.y;
		direction = parent.direction;

		parent.moveToParent();
	}

	protected Link getTail() {
		if (child == null)
			return this;

		return child.getTail();
	}

	protected void remove() {
		particlePool.createParticles(x * Game.GRID_SIZE, y * Game.GRID_SIZE, color, 10);

		if (child != null) {
			child.parent = parent;
			child.x = x;
			child.y = y;
		}
		if (parent != null)
			parent.child = child;
	}

	protected void removeColor(Color removeColor) {
		if (child != null)
			child.removeColor(removeColor);

		if (color.equals(removeColor))
			remove();
	}

	protected Color findChain() {
		if (child != null) {
			Color childRemoveColor = child.findChain();

			if (childRemoveColor != null)
				return childRemoveColor;
		}

		int numLinks = 1;

		if (child != null) {
			if (child.color.equals(color)) {
				numLinks++;

				if (child.child != null && child.child.color.equals(color))
					numLinks++;
			}
		}

		if (parent != null) {
			if (parent.color.equals(color)) {
				numLinks++;

				if (parent.parent != null && parent.parent.color.equals(color))
					numLinks++;
			}
		}

		if (numLinks >= 3)
			return color;

		return null;
	}

	public Link getChild() {
		return child;
	}

	public Link getParent() {
		return parent;
	}

	public boolean linkAt(float x, float y) {
		if (this.x == x && this.y == y)
			return true;

		if (child != null)
			return child.linkAt(x, y);

		return false;
	}

	public void render(SpriteBatch batch) {
		super.render(batch);

		if (child != null)
			child.render(batch);

		RenderUtil.renderGridObject(this, batch, texRegion, color);
	}

}