package platform.game.actors;

/**
 * @author zyuiop
 *         This interface represents an actor that can be attached (sticked) to an other actor. In general,
 *         implementations of this interface will use {@link platform.game.actors.AttachLink} to manage the
 *         physics of this link.
 */
public interface IAttachable {
	/**
	 * Check if the actor is attached to an other actor. In general, the implementation checks if
	 * there is any stored {@link platform.game.actors.AttachLink}, and if there is one check if it
	 * is still registered.
	 *
	 * @return true if the actor is attached, false if it's not
	 */
	boolean isAttached();
}
