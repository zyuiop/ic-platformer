package platform.game.particles;

import platform.game.World;
import platform.game.actors.animations.BlowAnimation;
import platform.game.actors.animations.ParticleActor;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * An interface helping to build particle effects easily
 */
public interface ParticleEffect {
	/**
	 * A blow effect
	 * @see BlowAnimation
	 */
	ParticleEffect BLOW = (pos) -> (new BlowAnimation(pos));

	default void play(World world, Vector location) {
		world.register(build(location));
	}

	/**
	 * Build the particle at a given location
	 * @param location the location where the particle should be created
	 * @return a particle, not yet registered
	 */
	ParticleActor build(Vector location);
}
