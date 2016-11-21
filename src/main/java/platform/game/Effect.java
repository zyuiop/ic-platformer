package platform.game;

/**
 * @author zyuiop
 *         <p>
 *         This enum represents all the kinds of damage or effect an actor can apply to an other.
 */
public enum Effect {
	FIRE,
	PHYSICAL,
	AIR(false),
	VOID,
	ACTIVATION(false),
	HEAL(false, true);

	private boolean harming = true;
	private boolean healing = false;

	Effect(boolean harming) {
		this.harming = harming;
	}

	Effect() {
	}

	Effect(boolean harming, boolean healing) {
		this.harming = harming;
		this.healing = healing;
	}

	public boolean isHarming() {
		return harming;
	}

	public boolean isHealing() {
		return healing;
	}
}
