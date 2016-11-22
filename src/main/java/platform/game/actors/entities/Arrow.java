package platform.game.actors.entities;

import platform.game.Actor;
import platform.game.Effect;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Arrow extends AtachableProjectible {
	public Arrow(Vector position, Vector velocity, Actor sender) {
		super("arrow", position, velocity, sender, 1, 0.25);
	}

	@Override
	protected boolean damage(Actor other) {
		if (other.hurt(this, Effect.PHYSICAL, 2D, getPosition())) {
			Vector delta = other.getBox().getCollision(getPosition());
			attachTo(other, delta == null ? Vector.ZERO : delta);

			return true;
		}

		return false;
	}

	@Override
	protected void hitBlock(Actor solidActor, Vector delta) {
		Vector diff = getPosition().sub(solidActor.getPosition());
		attachTo(solidActor, diff, getVelocity().getAngle());
	}
}
