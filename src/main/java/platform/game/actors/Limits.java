package platform.game.actors;

import platform.game.Actor;
import platform.game.Effect;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Limits extends Actor {
	private final Box allowedBounds;

	public Limits(Box allowedBounds) {
		this.allowedBounds = allowedBounds;
	}

	@Override
	public int getPriority() {
		return 100000;
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);

		if (!allowedBounds.isColliding(other.getBox())) {
			other.hurt(this, Effect.VOID, Double.POSITIVE_INFINITY, Vector.ZERO);
		}
	}
}
