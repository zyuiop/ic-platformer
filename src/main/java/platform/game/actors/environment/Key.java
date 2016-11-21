package platform.game.actors.environment;

import platform.game.Actor;
import platform.game.Signal;
import platform.game.actors.basic.PositionedActor;
import platform.game.actors.entities.Player;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Key extends PositionedActor implements Signal {
	private boolean taken = false;

	public Key(String color, double size, Vector position) {
		super("key." + color, size, position);
	}

	@Override
	public int getPriority() {
		return 60;
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);

		if (other instanceof Player && getBox().isColliding(other.getBox())) {
			take();
		}
	}

	private void take() {
		this.taken = true;
		getWorld().unregister(this);
	}

	@Override
	public boolean isActive() {
		return taken;
	}
}
