package platform.game.actors;

import platform.game.Actor;
import platform.game.World;
import platform.game.actors.interfaces.IAttachable;
import platform.game.actors.interfaces.IPositioned;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 *         This class represents a link between two actors, one attached to the other. This class computes
 *         the physics needed to make this attach link. In general, this class is used directly from
 *         {@link IAttachable} implementations.
 */
public class AttachLink extends Actor {
	private IPositioned attachedTo;
	// TODO : use MovableActor and PositionedActor instead of interfaces ?
	private IAttachable attachedActor; // technically it could be an IMovable, but it's more logic to use IAttachable
	private Vector difference;

	/**
	 * Create an attach link between two actors with a relative position difference.
	 *
	 * @param attachedTo the actor to which we want to be attached
	 * @param attachedActor the actor to attach to the previous actor
	 * @param difference the position of the attached actor relative to the attach
	 */
	public AttachLink(IPositioned attachedTo, IAttachable attachedActor, Vector difference) {
		this.attachedTo = attachedTo;
		this.difference = difference;
		this.attachedActor = attachedActor;
	}

	/**
	 * Registher the attach to the world. An attach that is not registered is not active.
	 *
	 * @param world the world this actor is registered in
	 */
	@Override
	public void register(World world) {
		super.register(world);

		// Cancel actor velocity
		this.attachedActor.setVelocity(Vector.ZERO);
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void update(Input input) {
		super.update(input);

		if (attachedTo == null || !attachedTo.isRegistered() || attachedTo.getPosition() == null ||
				!attachedActor.isRegistered() || !attachedActor.isAttached()) {
			detach();
			return;
		}

		attachedActor.setPosition(attachedTo.getPosition().add(difference));
	}

	/**
	 * Unregister this attach. Please note that this method DOESN'T call
	 * {@link IAttachable#detach(Vector)}.
	 */
	public void detach() {
		if (isRegistered()) { getWorld().unregister(this); }
	}
}
