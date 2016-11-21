package platform.game.actors;

import platform.game.Actor;
import platform.game.Effect;
import platform.game.actors.basic.MovableActor;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Fireball extends MovableActor {
	private Actor sender;

	public Fireball(Vector position, Vector velocity, Actor sender) {
		this(position, velocity, sender, .4);
	}

	public Fireball(Vector position, Vector velocity, Actor sender, double size) {
		super("fireball", size, position, velocity);
		this.sender = sender;
	}

	@Override
	public int getPriority() {
		return 1000;
	}

	@Override
	public void draw(Input input, Output output) {
		if (getCurrentSprite() != null)
			output.drawSprite(getCurrentSprite(), getBox(), input.getTime());
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (other.getBox().isColliding(getBox()) && !other.equals(sender)) {
			if (other.hurt(this, Effect.FIRE, 1D, getPosition())) {
				getWorld().unregister(this);
				return; // don't change velocity or anything
			}
		}

		if (other.isSolid()) {
			Vector delta = other.getBox().getCollision(getPosition());
			if (delta != null) {
				setPosition(getPosition().add(delta));
				setVelocity(getVelocity().mirrored(delta));
			}
		}
	}
}
