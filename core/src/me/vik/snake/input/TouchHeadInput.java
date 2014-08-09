package me.vik.snake.input;

import com.badlogic.gdx.Game;

public class TouchHeadInput extends HeadInput {

	private PhoneInputDetector phoneInputDetector;

	public TouchHeadInput(Game game) {
		phoneInputDetector = new PhoneInputDetector(game);
	}
	
	public void update() {
	}
	
	public void setDirectionListener(DirectionListener directionListener) {
		super.setDirectionListener(directionListener);
		phoneInputDetector.setDirectionListener(directionListener);
	}

}
