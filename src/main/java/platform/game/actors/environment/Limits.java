package platform.game.actors.environment;

import platform.game.Actor;
import platform.game.Effect;
import platform.game.actors.DataReadable;
import platform.game.actors.DataWritable;
import platform.game.data.ActorFactory;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Limits extends Actor implements DataWritable, DataReadable {
	private Box allowedBounds;

	protected Limits() {
	}

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

	@Override
	public void read(ActorFactory factory) {
		allowedBounds = factory.getDataMap().get("allowedBounds").getAsBox();
	}

	@Override
	public void write(ActorFactory factory) {
		factory.getDataMap().put("allowedBounds", allowedBounds);
	}
}
