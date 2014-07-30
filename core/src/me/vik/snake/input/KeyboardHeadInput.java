package me.vik.snake.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyboardHeadInput extends HeadInput {

	public void update() {
		boolean up = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
		boolean left = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean down = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
		boolean right = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		
		if (up && !down)
			directionListener.onUp();
		else if (down && !up)
			directionListener.onDown();
		
		if (right && !left)
			directionListener.onRight();
		else if (left && !right)
			directionListener.onLeft();
	}

}
