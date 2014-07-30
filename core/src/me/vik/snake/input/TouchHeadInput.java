package me.vik.snake.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;

public class TouchHeadInput extends HeadInput {

	private FlickListener flickListener;
	
	public TouchHeadInput() {
		Gdx.input.setInputProcessor(new GestureDetector(new FlickListener()));
	}

	public void update() {
	}
	
	public void setDirectionListener(DirectionListener directionListener) {
		super.setDirectionListener(directionListener);
		flickListener.setDirectionListener(directionListener);
	}

}
