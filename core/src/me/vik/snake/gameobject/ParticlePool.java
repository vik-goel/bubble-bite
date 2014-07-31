package me.vik.snake.gameobject;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParticlePool {

	private ArrayList<Particle> used, unused;

	public ParticlePool(int numParticles) {
		used = new ArrayList<Particle>(numParticles);
		unused = new ArrayList<Particle>(numParticles);

		for (int i = 0; i < numParticles; i++)
			unused.add(new Particle());
	}

	public void update(float dt) {
		for (int i = 0; i < used.size(); i++) {
			if (used.get(i).isDead()) {
				recycleParticle(i);
				i--;
				continue;
			}

			used.get(i).update(dt);
		}
	}

	public void render(SpriteBatch batch, float xOffset) {
		for (int i = 0; i < used.size(); i++)
			used.get(i).render(batch, xOffset);
	}

	public void createParticles(float x, float y, Color color, int numParticles) {
		for (int i = 0; i < numParticles; i++) {
			if (unused.isEmpty())
				return;

			Particle p = unused.remove(unused.size() - 1);
			p.init(x, y, color);
			used.add(p);
		}
	}

	public void reset() {
		for (int i = used.size() - 1; i >= 0; i--)
			recycleParticle(i);
	}

	public int getNumUsed() {
		return used.size();
	}
	
	private void recycleParticle(int index) {
		unused.add(used.get(index));
		used.remove(index);
	}

}
