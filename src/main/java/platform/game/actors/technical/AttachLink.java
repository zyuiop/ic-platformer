package platform.game.actors.technical;

import platform.game.World;
import platform.game.actors.Actor;
import platform.game.actors.IAttachable;
import platform.game.actors.MovableActor;
import platform.game.actors.PositionedActor;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 *         This class represents a link between two actors, one attached to the other. This class computes
 *         the physics needed to make this attach link. In general, this class is used directly from
 *         {@link IAttachable} implementations.
 *
 *         The type parameter is a workaround to allow multiple types on the same object
 */
public class AttachLink<T extends MovableActor & IAttachable> extends Actor {
	private final PositionedActor attachedTo;
	private final T attachedActor;
	private final Vector difference;

	/**
	 * Create an attach link between two actors with a relative position difference.
	 *
	 * @param attachedTo the actor to which we want to be attached
	 * @param attachedActor the actor to attach to the previous actor
	 * @param difference the position of the attached actor relative to the attach
	 */
	public AttachLink(PositionedActor attachedTo, T attachedActor, Vector difference) {
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
	 * Unregister this attach
	 */
	public void detach() {
		if (isRegistered()) { getWorld().unregister(this); }
	}
}
