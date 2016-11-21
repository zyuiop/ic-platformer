package platform.game;

import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Fireball extends Entity {
	private Actor sender;

	public Fireball(Vector position, Vector velocity, Actor sender) {
		this(position, velocity, sender, .4);
	}

	public Fireball(Vector position, Vector velocity, Actor sender, double size) {
		super(position, velocity, "fireball", size);
		this.sender = sender;
	}

	@Override
	public int getPriority() {
		return 1000;
	}

	@Override
	public void draw(Input input, Output output) {
		if (getSprite() != null)
			output.drawSprite(getSprite(), getBox(), input.getTime());
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
