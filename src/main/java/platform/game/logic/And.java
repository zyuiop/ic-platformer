package platform.game.logic;

import platform.game.Signal;

/**
 * @author zyuiop
 */
public final class And implements Signal {
	private final Signal s1;
	private final Signal s2;

	public And(Signal s1, Signal s2) {
		if (s1 == null || s2 == null)
			throw new NullPointerException();

		this.s1 = s1;
		this.s2 = s2;
	}

	@Override
	public boolean isActive() {
		return s1.isActive() && s2.isActive();
	}
}
