package me.vik.snake.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;

public class TouchHeadInput extends HeadInput {

	private FlickListener flickListener;

	public void update() {
	}
	
	public void setDirectionListener(DirectionListener directionListener) {
		super.setDirectionListener(directionListener);
		
		if (flickListener == null) 
			Gdx.input.setInputProcessor(new GestureDetector(flickListener = new FlickListener()));
		
		flickListener.setDirectionListener(directionListener);
	}

}
