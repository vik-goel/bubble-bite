package me.vik.snake.gameobject;

import java.util.ArrayList;
import java.util.Random;

import me.vik.snake.Game;
import me.vik.snake.util.Direction;
import me.vik.snake.util.RenderUtil;
import me.vik.snake.util.Textures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Link extends GameObject {

	private static Random random = new Random();
	private static ArrayList<Link> removedLinks = new ArrayList<Link>(150);
	private static ParticlePool particlePool;

	protected Direction direction = Direction.UP;
	protected Link parent, child;
	protected Texture tex = Textures.link;

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

	public void destroy() {
		if (child != null)
			child.destroy();

		remove(0);
	}

	protected void remove(int linkNum) {
		removedLinks.add(this);

		createParticles();
		spliceSelfOutOfChain();

		getHead().removeLinkScore(linkNum);
	}

	private void createParticles() {
		Color particleColor = color;

		if (this instanceof Head) {
			particleColor = Color.RED;
			((Head) this).onRemoveLink(true, 10);
		}

		particlePool.createParticles(x * Game.GRID_SIZE, y * Game.GRID_SIZE, particleColor, random.nextInt(5) + 5);
	}
	
	private void spliceSelfOutOfChain() {
		if (child != null) {
			child.parent = parent;
			child.x = x;
			child.y = y;
		}
		if (parent != null)
			parent.child = child;
	}

	private Head getHead() {
		if (parent == null)
			return (Head) this;

		return parent.getHead();
	}

	protected int removeColor(Color removeColor, int linkNum) {
		if (child != null)
			linkNum = child.removeColor(removeColor, linkNum);

		if (color.equals(removeColor))
			remove(++linkNum);

		return linkNum;
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

	public void render(SpriteBatch batch, float xOffset) {
		super.render(batch, xOffset);

		if (child != null)
			child.render(batch, xOffset);

		RenderUtil.renderGridObject(this, batch, tex, color, xOffset, direction);
	}
	
	public Direction getDirection() {
		return direction;
	}

	public static void resetRemovedLinks() {
		removedLinks.clear();
	}

}
