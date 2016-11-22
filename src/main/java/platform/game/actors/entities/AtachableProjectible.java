package platform.game.actors.entities;

import platform.game.Actor;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public abstract class AtachableProjectible extends Projectile {
	private Actor attachedTo;
	private Vector difference;
	private double attachAngle = 0;

	public AtachableProjectible(String texture, Vector position, Vector velocity, Actor sender, double size) {
		super(texture, position, velocity, sender, size);
	}

	public AtachableProjectible(String texture, Vector position, Vector velocity, Actor sender, double sizeX, double sizeY) {
		super(texture, position, velocity, sender, sizeX, sizeY);
	}

	@Override
	public void update(Input input) {
		if (attachedTo != null) {
			if (!attachedTo.isRegistered() || attachedTo.getPosition() == null) {
				detach(Vector.ZERO);
			} else {
				setPosition(attachedTo.getPosition().add(difference));
			}
		} else {
			super.update(input);
		}
	}

	@Override
	public void interact(Actor other) {
		if (attachedTo == null || !attachedTo.isRegistered())
			super.interact(other);
	}

	@Override
	protected double getAngle() {
		if (attachedTo == null || !attachedTo.isRegistered()) {
			return super.getAngle();
		}

		return attachAngle;
	}

	public void attachTo(Actor attachedTo, Vector positionDifference) {
		this.attachedTo = attachedTo;
		this.difference = positionDifference;
	}


	public void attachTo(Actor attachedTo, Vector positionDifference, double attachAngle) {
		this.attachedTo = attachedTo;
		this.difference = positionDifference;
		this.attachAngle = attachAngle;
	}

	public void detach(Vector velocity) {
		this.attachedTo = null;
		this.attachAngle = 0D;
		setVelocity(velocity);
	}
}
