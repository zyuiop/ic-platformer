package platform.game.actors.interfaces;

import platform.game.Actor;

/**
 * @author zyuiop
 *
 * Represents an actor, as an interface.
 * This interface is not supposed to be complete, it is just here to provide a base for different
 * higher level interfaces (and in the first place the {@link IAttachable} interface).
 */
public interface IActor {
	/**
	 * Check if this actor is registered into a world
	 * @see Actor#isRegistered()
	 * @return true if the actor is registered in a world
	 */
	boolean isRegistered();
}
