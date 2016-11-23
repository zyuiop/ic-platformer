package platform.game.actors.animations;

import platform.game.actors.basic.PositionedActor;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public abstract class ParticleActor extends PositionedActor {
	protected ParticleActor(Vector position, double size, String spriteName) {
		super(position, size, spriteName);
	}

	protected ParticleActor(Vector position, double sizeX, double sizeY, String spriteName) {
		super(position, sizeX, sizeY, spriteName);
	}
}
