package platform.game.actors.entities;

import platform.game.Actor;
import platform.game.actors.AttachLink;
import platform.game.actors.basic.PositionedActor;
import platform.game.actors.interfaces.IAttachable;
import platform.game.actors.interfaces.IPositioned;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
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
		if (attachLink !=null) { super.interact(other); }
	}

	@Override
	protected double getAngle() {
		if (attachLink != null) {
			return super.getAngle();
		}

		return attachAngle;
	}

	@Override
	public void attachTo(IPositioned attachedTo, Vector positionDifference) {
		attachTo(attachedTo, positionDifference, 0D);
	}


	public void attachTo(IPositioned attachedTo, Vector positionDifference, double attachAngle) {
		if (attachLink != null)
			attachLink.detach();
		this.attachLink = new AttachLink(attachedTo, this, positionDifference);
		this.attachAngle = attachAngle;
	}

	@Override
	public void detach(Vector velocity) {
		if (attachLink != null)
			attachLink.detach();
		this.attachLink = null;
		this.attachAngle = 0D;
		setVelocity(velocity);
	}

	@Override
	public boolean isAttached() {
		return attachLink != null;
	}
}
