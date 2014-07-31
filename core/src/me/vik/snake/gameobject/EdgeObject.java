package me.vik.snake.gameobject;

import me.vik.snake.util.RenderUtil;
import me.vik.snake.util.Textures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EdgeObject extends GameObject {

	public EdgeObject(float x, float y) {
		super(x, y, Color.WHITE);
	}

	public void render(SpriteBatch batch, float xOffset) {
		super.render(batch, xOffset);
		RenderUtil.renderGridObject(this, batch, Textures.edge, color, xOffset);
	}

}
