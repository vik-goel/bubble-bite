package me.vik.snake.gameobject;

import java.util.ArrayList;
import java.util.Random;

import me.vik.snake.util.RenderUtil;
import me.vik.snake.util.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Food extends GameObject {

	private static final int COLOR_FOOD_SCALE = 10;

	private static Color[] colors = new Color[] { RenderUtil.createColor(109, 216, 109), // green
			RenderUtil.createColor(249, 183, 3), // orange
			RenderUtil.createColor(103, 211, 215), // cyan
			RenderUtil.createColor(184, 70, 9), // dark orange
			RenderUtil.createColor(175, 108, 214), // purple
			RenderUtil.createColor(218, 16, 168), // magenta
			RenderUtil.createColor(10, 10, 196), // blue
			RenderUtil.createColor(222, 141, 141) }; // pink

	private static Random random = new Random();
	private static ArrayList<Food> food = new ArrayList<Food>();

	private static int numFoodEaten = 0;
	private static Sound[] eat;

	private Head head;

	private int minX, minY, maxX, maxY;

	public Food(int minX, int minY, int maxX, int maxY) {
		super(0, 0, Color.BLACK);

		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;

		food.add(this);
	}

	public void update(float dt) {
		if (head.x == x && head.y == y) {
			numFoodEaten++;
			eat[random.nextInt(eat.length)].play();
			
			new Link(head.getTail(), color);
			head.consumeFoodScore();
		
			genFood();
		}
	}

	public void render(SpriteBatch batch, float xOffset) {
		super.render(batch, xOffset);
		RenderUtil.renderGridObject(this, batch, Textures.food, color, xOffset);
	}

	private void genFood() {
		genRandomPosition();
		genRandomColor();
	}

	private void genRandomPosition() {
		while (true) {
			genPos();

			if (!intersectingFood() && !nearSnake())
				break;
		}
	}

	private boolean intersectingFood() {
		for (int i = 0; i < food.size(); i++)
			if (food.get(i) != this && food.get(i).x == x && food.get(i).y == y)
				return true;

		return false;
	}
	
	private boolean nearSnake() {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				float xPos = x + i;
				float yPos = y + j;
				
				if (head.linkAt(xPos, yPos))
					return true;
			}
		}
		
		return false;
	}

	private void genPos() {
		x = random.nextInt(maxX - minX) + minX;
		y = random.nextInt(maxY - minY) + minY;
	}

	private void genRandomColor() {
		Color biasedColor = getBiasedColor();

		if (biasedColor != null) {
			color = biasedColor;
			return;
		}

		int numColors = Math.min(colors.length, numFoodEaten / COLOR_FOOD_SCALE + 2);
		color = colors[random.nextInt(numColors)];
	}

	private Color getBiasedColor() {
		Color color = null;

		if (shouldBias()) {
			color = matchOtherFoodColor();
			color = matchTailColor();
		}

		return color;
	}

	private boolean shouldBias() {
		Link tail = head.getTail();
		Link tailParent = tail.getParent();

		if (tail == head)
			return false;
		
		int numLinks = 1;
		
		if (tailParent.getColor().equals(tail.getColor()))
			numLinks++;
		
		for (int i = 0; i < food.size(); i++)
			if (food.get(i) != this && food.get(i).getColor().equals(tail.getColor()))
				numLinks++;
		
		return numLinks < 3;
	}

	private Color matchOtherFoodColor() {
		float biasChance = 0.2f;

		for (int i = 0; i < food.size(); i++)
			if (food.get(i) != this && random.nextFloat() > biasChance)
				return food.get(i).getColor();

		return null;
	}

	private Color matchTailColor() {
		Link tail = head.getTail();
		Link tailParent = tail.getParent();

		if (tail == head)
			return null;

		Color color = tail.getColor();

		float biasChance = 0.2f;

		if (tailParent.getColor().equals(color))
			biasChance *= 2;

		if (random.nextFloat() > biasChance)
			return color;

		return null;
	}

	public static void reset(Head head) {
		numFoodEaten = 0;

		for (int i = 0; i < food.size(); i++) {
			food.get(i).head = head;
			food.get(i).genFood();
		}
	}
	
	static {
		eat = new Sound[6];
		
		for (int i = 0; i < eat.length; i++)
			eat[i] = Gdx.audio.newSound(Gdx.files.internal("sound/eat " + (i + 1) + ".mp3"));
	}

}
