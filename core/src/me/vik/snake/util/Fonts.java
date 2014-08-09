package me.vik.snake.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Fonts {

	public static BitmapFont scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"), Gdx.files.internal("fonts/score.png"), false);
	public static BitmapFont bubbleFont = new BitmapFont(Gdx.files.internal("fonts/bubble.fnt"), Gdx.files.internal("fonts/bubble.png"), false);

	static {
		scoreFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bubbleFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
}
