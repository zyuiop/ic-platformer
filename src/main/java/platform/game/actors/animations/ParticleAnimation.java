package platform.game.actors.animations;

import java.util.ArrayDeque;
import java.util.Queue;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class ParticleAnimation extends ParticleActor {
	private Queue<ParticleActor> particles = new ArrayDeque<>();
	private ParticleActor currentParticle;

	public ParticleAnimation(Vector position) {
		super((String) null, 0D, position);
	}

	@Override
	public void draw(Input input, Output output) {
		// do nothing
	}

	@Override
	public void postUpdate(Input input) {
		super.postUpdate(input);

		if (currentParticle == null || !currentParticle.isRegistered()) {
			// switch current particle
			currentParticle = particles.poll();

			if (currentParticle == null) {
				getWorld().unregister(this);
				return;
			}

			getWorld().register(currentParticle);
		}
	}

	public ParticleAnimation add(ParticleActor particle) {
		particles.add(particle);
		return this;
	}

	@Override
	public int getPriority() {
		return 10000; // higher than particle because I want to be called before them
	}
}