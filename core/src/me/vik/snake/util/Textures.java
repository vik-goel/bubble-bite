package me.vik.snake.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Textures {

	public static TextureRegion[] head;
	public static TextureRegion link, food, particle, edge;
	
	static {
		Texture atlas = new Texture("atlas2.png");
		atlas.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		TextureRegion[][] splitAtlas = TextureRegion.split(atlas, 64, 64);
		
		link = splitAtlas[0][0];
		food = splitAtlas[0][1];
		particle = splitAtlas[0][2];
		edge = splitAtlas[0][3];
		
		head = new TextureRegion[4];
		
		for (int i = 0; i < head.length; i++)
			head[i] = splitAtlas[1][i];
	}
	
}
