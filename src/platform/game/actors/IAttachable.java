package platform.game.actors;

import platform.game.actors.technical.AttachLink;

/**
 * @author zyuiop
 *         This interface represents an actor that can be attached (sticked) to an other actor. In general,
 *         implementations of this interface will use {@link AttachLink} to manage the
 *         physics of this link.
 */
public interface IAttachable {
	/**
	 * Check if the actor is attached to an other actor. In general, the implementation checks if
	 * there is any stored {@link AttachLink}, and if there is one check if it
	 * is still registered.
	 *
	 * @return true if the actor is attached, false if it's not
	 */
	boolean isAttached();
}
