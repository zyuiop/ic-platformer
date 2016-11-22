package platform.game.actors.animations;

import platform.game.actors.basic.PositionedActor;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public abstract class ParticleActor extends PositionedActor {

	public ParticleActor(String spriteName, double size, Vector position) {
		super(spriteName, size, position);
	}

	public ParticleActor(Sprite sprite, double size, Vector position) {
		super(sprite, size, position);
	}

	public ParticleActor(String spriteName, double sizeX, double sizeY, Vector position) {
		super(spriteName, sizeX, sizeY, position);
	}

	public ParticleActor(Sprite sprite, double sizeX, double sizeY, Vector position) {
		super(sprite, sizeX, sizeY, position);
	}
}
