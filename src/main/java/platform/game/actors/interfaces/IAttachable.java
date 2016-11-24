package platform.game.actors.interfaces;

import platform.util.Vector;

/**
 * @author zyuiop
 * This interface represents an actor that can be attached (sticked) to an other actor. In general,
 * implementations of this interface will use {@link platform.game.actors.AttachLink} to manage the
 * physics of this link.
 */
public interface IAttachable extends IMovable {
	/**
	 * Attach this actor to an other actor. In a typical implementation, this method will call
	 * {@link IAttachable#detach(Vector)} then create and store an instance of
	 * {@link platform.game.actors.AttachLink} and register it into the current
	 * {@link platform.game.World}.
	 * @param attachedTo the actor to which this actor should be attached
	 * @param positionDifference the position of the attached actor, relative to the attach
	 */
	void attachTo(IPositioned attachedTo, Vector positionDifference);

	/**
	 * Detach the current actor from it's attach (if it exists), and give it a velocity.
	 * @param velocity the velocity to set to the actor when detached. In general,
	 * {@link Vector#ZERO} is used as the new velocity parameter.
	 */
	void detach(Vector velocity);

	/**
	 * Check if the actor is attached to an other actor. In general, the implementation checks if
	 * there is any stored {@link platform.game.actors.AttachLink}, and if there is one check if it
	 * is still registered.
	 * @return true if the actor is attached, false if it's not
	 */
	boolean isAttached();
}
