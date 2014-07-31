package me.vik.snake.input;


public abstract class HeadInput {

	protected DirectionListener directionListener;

	public abstract void update();

	public void setDirectionListener(DirectionListener directionListener) {
		this.directionListener = directionListener;
	}

}
