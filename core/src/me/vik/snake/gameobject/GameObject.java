package me.vik.snake.gameobject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject {

	protected float x, y;
	protected Color color;
	
	public GameObject(float x, float y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public void update(float dt) {
	}
	
	public void render(SpriteBatch batch) {
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public Color getColor() {
		return color;
	}
}
