package me.vik.snake.util;

import me.vik.snake.Game;
import me.vik.snake.gameobject.GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class RenderUtil {

	private static ShapeRenderer sr = new ShapeRenderer();
	
	public static void renderGridObject(GameObject object, SpriteBatch batch, Texture texture, Color tint, float xOffset) {
		renderGridObject(object, batch, texture, tint, xOffset, Direction.UP);
	}
	
	@SuppressWarnings("incomplete-switch")
	public static void renderGridObject(GameObject object, SpriteBatch batch, Texture texture, Color tint, float xOffset, Direction direction) {
		float x = object.getX() * Game.GRID_SIZE + xOffset;
		float y = object.getY() * Game.GRID_SIZE;
		
		float rotation = 0;
		
		switch (direction) {
		case RIGHT:
			rotation = 270;
			break;
		case DOWN:
			rotation = 180;
			break;
		case LEFT:
			rotation = 90;
			break;
		}
		
		batch.begin();
		batch.enableBlending();
		batch.setColor(tint);
		
		batch.draw(texture, x, y, Game.GRID_SIZE / 2, Game.GRID_SIZE / 2, Game.GRID_SIZE, Game.GRID_SIZE, 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
		
		batch.end();
	}
	
	public static Color createColor(int r, int g, int b) {
		return new Color(r / 255f, g / 255f, b / 255f, 1f);
	}

	public static void renderGrid(OrthographicCamera camera, int minX, int minY, int maxX, int maxY, float xOffset) {
		Gdx.gl20.glLineWidth(1);
		
		final float col = 0.1f;
		
		sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeType.Line);
		sr.setColor(col, col, col, 1f);
		
		for (int x = minX + 1; x < maxX + minX - 1; x++)
			sr.line(x * Game.GRID_SIZE + xOffset, Game.GRID_SIZE, x * Game.GRID_SIZE + xOffset, maxY * Game.GRID_SIZE);
			
		
		for (int y = minY + 1; y < maxY + minY - 1; y++)
			sr.line(Game.GRID_SIZE + xOffset, y * Game.GRID_SIZE, maxX * Game.GRID_SIZE + xOffset, y * Game.GRID_SIZE);
		
		sr.end();
	}
}
