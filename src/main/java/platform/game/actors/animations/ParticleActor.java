package platform.game.actors.animations;

import platform.game.actors.DisplayableActor;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * A particle. This class does absolutely nothing but is there to distinguish particles from other
 * actors.
 */
public abstract class ParticleActor extends DisplayableActor {
	protected ParticleActor(Vector position, double size, String spriteName) {
		super(position, size, spriteName);
	}

	protected ParticleActor(Vector position, double sizeX, double sizeY, String spriteName) {
		super(position, sizeX, sizeY, spriteName);
	}
}
