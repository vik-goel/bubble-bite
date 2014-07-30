package me.vik.snake.util;

import me.vik.snake.Game;
import me.vik.snake.gameobject.GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class RenderUtil {

	private static ShapeRenderer sr = new ShapeRenderer();
	
	public static void renderGridObject(GameObject object, SpriteBatch batch, TextureRegion region, Color tint) {
		float x = object.getX() * Game.GRID_SIZE;
		float y = object.getY() * Game.GRID_SIZE;
		
		batch.begin();
		batch.enableBlending();
		batch.setColor(tint);
		
		batch.draw(region, x, y, Game.GRID_SIZE, Game.GRID_SIZE);
		
		batch.end();
	}
	
	public static Color createColor(int r, int g, int b) {
		return new Color(r / 255f, g / 255f, b / 255f, 1f);
	}

	public static void renderGrid(OrthographicCamera camera, int minX, int minY, int maxX, int maxY) {
		Gdx.gl20.glLineWidth(1);
		
		final float col = 0.1f;
		
		sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeType.Line);
		sr.setColor(col, col, col, 1f);
		
		for (int x = minX + 1; x < maxX + minX - 1; x++)
			sr.line(Game.GRID_SIZE, x * Game.GRID_SIZE, maxX * Game.GRID_SIZE, x * Game.GRID_SIZE);
		
		for (int y = minY + 1; y < maxY + minY - 1; y++)
			sr.line(y * Game.GRID_SIZE, Game.GRID_SIZE, y * Game.GRID_SIZE, maxY * Game.GRID_SIZE);
		
		sr.end();
	}
}
