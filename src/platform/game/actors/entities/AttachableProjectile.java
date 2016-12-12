package platform.game.actors.entities;

import platform.game.actors.Actor;
import platform.game.actors.technical.AttachLink;
import platform.game.actors.IAttachable;
import platform.game.actors.PositionedActor;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * Represents a projectile that can be attached to an other actor
 */
public abstract class AttachableProjectile extends Projectile implements IAttachable {
	private AttachLink attachLink;
	private double attachAngle = 0;

	public AttachableProjectile(String texture, Vector position, Vector velocity, Actor sender, double size) {
		super(texture, position, velocity, sender, size);
	}

	public AttachableProjectile(String texture, Vector position, Vector velocity, Actor sender, double sizeX, double sizeY) {
		super(texture, position, velocity, sender, sizeX, sizeY);
	}

	@Override
	public void update(Input input) {
		if (!isAttached()) {
			super.update(input);
		}
	}

	@Override
	public void interact(Actor other) {
		if (!isAttached()) {
			super.interact(other);
		}
	}

	@Override
	public double getRotation() {
		if (!isAttached()) {
			return super.getRotation();
		}

		return attachAngle;
	}

	public void attachTo(PositionedActor attachedTo, Vector positionDifference) {
		attachTo(attachedTo, positionDifference, 0D);
	}

	public void attachTo(PositionedActor attachedTo, Vector positionDifference, double attachAngle) {
		this.detach(Vector.ZERO); // detach & reset velocity
		this.attachLink = new AttachLink<>(attachedTo, this, positionDifference);
		this.getWorld().register(this.attachLink);
		this.attachAngle = attachAngle;
	}

	public void detach(Vector velocity) {
		if (attachLink != null)
			attachLink.detach();
		this.attachLink = null;
		this.attachAngle = 0D;
		setVelocity(velocity);
	}

	@Override
	public boolean isAttached() {
		if (attachLink != null) {
			if (attachLink.isRegistered())
				return true;

			// Not registered : attach cancelled
			detach(Vector.ZERO);
		}
		return false;
	}
}
