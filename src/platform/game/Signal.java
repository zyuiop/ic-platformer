package platform.game;

import platform.game.logic.And;
import platform.game.logic.Not;
import platform.game.logic.Or;

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

	/**
	 * Invert this signal
	 * @return an other signal corresponding to this one inverted
	 * @see Not
	 */
	default Signal not() {
		return new Not(this);
	}

	/**
	 * Applies logical AND on this signal and an other
	 * @param other the other signal
	 * @return a new And signaal
	 * @see And
	 */
	default Signal and(Signal other) {
		return new And(this, other);
	}

	/**
	 * Applies logical OR on this signal and an other
	 * @param other the other signal
	 * @return a new Or signal
	 * @see Or
	 */
	default Signal or(Signal other) {
		return new Or(this, other);
	}
}
