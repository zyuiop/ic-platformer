package platform.game.actors.environment;

import platform.game.Effect;
import platform.game.actors.Actor;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * An actor that kills anything that goes out of it
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
