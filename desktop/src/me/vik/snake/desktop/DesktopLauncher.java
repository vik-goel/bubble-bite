package me.vik.snake.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import me.vik.snake.Game;
import me.vik.snake.input.KeyboardHeadInput;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 200;
		config.height = 350;
		config.title = "Besnaked";
		config.resizable = false;
		
		new LwjglApplication(new Game(new KeyboardHeadInput()), config);
	}
}
