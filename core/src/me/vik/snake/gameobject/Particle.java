package me.vik.snake.gameobject;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Particle extends GameObject {

	private static Random random = new Random();

	private ShapeRenderer sr;
	
	private float width, height;
	private float dx, dy;
	private float speed;
	private float acceleration;
	
	public Particle(ShapeRenderer sr) {
		super(0, 0, Color.BLACK);
		this.sr = sr;
	}

	public void update(float dt) {
		super.update(dt);

		dy -= acceleration * dt;
		
		x += dx * speed;
		y += dy * speed;
	}

	public void render(SpriteBatch batch, float xOffset) {
		super.render(batch, xOffset);

		sr.setColor(color);
		sr.rect(x + xOffset, y, width, height);
	}

	public void init(float x, float y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;

		dx = random.nextFloat() - 0.5f;
		dy = random.nextFloat() * 0.25f + 0.15f;

		width = height = random.nextFloat() * 0.02f + 0.005f;
		speed = 0.02f;

		acceleration = (0.005f + random.nextFloat() * 0.015f) * 5f;
	}
	
	public boolean isDead() {
		return x < 0 || y < 0 || x > 1 || y > 1;
	}

}
