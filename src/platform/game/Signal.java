package platform.game;

/**
 * @author zyuiop
 */
public interface Signal {

	/**
	 * A signal that is always true
	 */
	Signal ENABLED = () -> true;

	/**
	 * A signal that is always false
	 */
	Signal DISABLED = () -> false;

	/**
	 * Check if this signal is currently active
	 * @return true if the signal is active, false if it's not.
	 */
	boolean isActive();
}
