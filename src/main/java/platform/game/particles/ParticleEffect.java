package platform.game.particles;

import platform.game.World;
import platform.game.actors.animations.BlowAnimation;
import platform.game.actors.animations.ParticleActor;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public interface ParticleEffect {
	ParticleEffect BLOW = (pos) -> (new BlowAnimation(pos));

	default void play(World world, Vector location) {
		world.register(build(location));
	}

	ParticleActor build(Vector location);
}
