package me.vik.snake.input;

import com.badlogic.gdx.input.GestureDetector.GestureAdapter;

public class FlickListener extends GestureAdapter {

	private DirectionListener directionListener;
	
	public boolean fling(float velocityX, float velocityY, int button) {
		if (Math.abs(velocityX) > Math.abs(velocityY)) {
			if (velocityX > 0) {
				directionListener.onRight();
			} else {
				directionListener.onLeft();
			}
		} else {
			if (velocityY > 0) {
				directionListener.onDown();
			} else {
				directionListener.onUp();
			}
		}
		
		return super.fling(velocityX, velocityY, button);
	}
	
	public void setDirectionListener(DirectionListener directionListener) {
		this.directionListener = directionListener;
	}

}
