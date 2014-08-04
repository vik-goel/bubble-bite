package me.vik.snake.util;

import me.vik.snake.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ScoreManager {

	private static ScoreManager easy = new ScoreManager(Difficulty.EASY);
	private static ScoreManager medium = new ScoreManager(Difficulty.MEDIUM);
	private static ScoreManager hard = new ScoreManager(Difficulty.HARD);

	private int score = 0;
	private int bestScore;
	private boolean updatedBestScore = false;

	private Preferences prefs;
	private String scoreKey;
	
	private ScoreManager(Difficulty difficulty) {
		scoreKey = "bestScore:" + difficulty;
		
		prefs = Gdx.app.getPreferences(Game.PREFERENCES_KEY);
		bestScore = prefs.getInteger(scoreKey, 0);
	}

	public static ScoreManager getInstance(Difficulty difficulty) {
		switch (difficulty) {
		case EASY:
			return easy;
		case MEDIUM:
			return medium;
		case HARD:
			return hard;
		}
		
		return medium;
	}

	public void setCurrentScore(int score) {
		this.score = score;
		
		if (score > bestScore) {
			bestScore = score;
			updatedBestScore = true;
		}
	}

	public int getCurrentScore() {
		return score;
	}
	
	public int getBestScore() {
		return bestScore;
	}
	
	public void writeBestScore() {
		if (updatedBestScore) {
			prefs.putInteger(scoreKey, bestScore);
			prefs.flush();
		}
		
		updatedBestScore = false;
	}
	
}
