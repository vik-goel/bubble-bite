package me.vik.snake.android;

import me.vik.snake.Game;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Game game;

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(game = new Game(), config);

		game.setAdHandler(new AndroidAd(this));
	}

}
