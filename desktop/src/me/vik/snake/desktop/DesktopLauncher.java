package me.vik.snake.desktop;

import me.vik.snake.Game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 200;
		config.height = 350;
		config.title = "Bubble Bite";
		config.resizable = false;
		config.foregroundFPS = 60;
		config.backgroundFPS = -1;
		
		new LwjglApplication(new Game(), config);
	}
}
