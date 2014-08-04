package me.vik.snake.util;

import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraShaker {

	private static final float INITIAL_MAGNITUDE = 0.01f;
	private static final float MIN_MAGNITUDE = 0.001f;
	private static final float ENERGY_LOSS = 0.65f;
	
	private static final float MIN_SHAKE_TIME = 8;
	private static final float MAX_SHAKE_TIME = 16;
	
	private static CameraShaker instance = new CameraShaker();
	
	private CameraShaker() {
	}
	
	public static CameraShaker getInstance() {
		return instance;
	}
	
	private OrthographicCamera camera;
	private Random random = new Random();
	
	private boolean isShaking = false;
	private float origX, origY;
	
	private float magnitude;
	private double direction;
	private float shakeTime;
	
	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}
	
	public void shakeCamera(boolean extremeShake) {
		if (isShaking)
			return;
		
		isShaking = true;
		origX = camera.position.x;
		origY = camera.position.y;
		
		magnitude = INITIAL_MAGNITUDE * (extremeShake ? 3 : 1);
		
		initShake();
	}
	
	public void update(float dt) {
		if (!isShaking)
			return;
		
		shakeTime -= dt;
		
		if (shakeTime < 0) {
			initShake();
			return;
		}
		
		float dx = (float) Math.cos(direction) * magnitude;
		float dy = (float) Math.sin(direction) * magnitude;
		
		camera.position.x += dx;
		camera.position.y += dy;
		
		camera.update();
	}

	private void initShake() {
		camera.position.x = origX;
		camera.position.y = origY;
		camera.update();
		
		magnitude *= ENERGY_LOSS;
		
		if (magnitude < MIN_MAGNITUDE) {
			isShaking = false;
			return;
		}
		
		direction = random.nextFloat() * Math.PI * 2;
		shakeTime = random.nextFloat() * (MAX_SHAKE_TIME - MIN_SHAKE_TIME) + MIN_SHAKE_TIME;
	}
	
}
