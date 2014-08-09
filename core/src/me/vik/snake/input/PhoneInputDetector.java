package me.vik.snake.input;

import me.vik.snake.screen.RenderScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.input.GestureDetector;

public class PhoneInputDetector extends GestureDetector {

	private Game game;
	private static FlickListener flickListener;
	
	public PhoneInputDetector(Game game) {
		super(flickListener = new FlickListener());
		this.game = game;
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(this);
	}
	
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK) 
			((RenderScreen) game.getScreen()).onBackPressed();

		return false;
	}

	public void setDirectionListener(DirectionListener directionListener) {
		flickListener.setDirectionListener(directionListener);
	}

}
