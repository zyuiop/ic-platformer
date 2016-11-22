package platform.game.actors.blocks;

import platform.game.Actor;
import platform.game.Effect;
import platform.game.Signal;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * Simple solid actor that does nothing.
 */
public class DestroyableBlock extends Block implements Signal {
	private double health;

	public DestroyableBlock(Box box, String sprite, double maxHealth) {
		super(box, sprite);
		this.health = maxHealth;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public boolean isSolid() {
		return health > 0;
	}

	@Override
	public void draw(Input input, Output output) {
		if (isSolid())
			super.draw(input, output);
	}

	@Override
	public Vector getPosition() {
		if (isSolid())
			return super.getPosition();
		return null;
	}

	@Override
	public boolean hurt(Actor damageFrom, Effect damageType, double amount, Vector location) {
		if (damageType.isHarming()) {
			health -= amount;
			return true;
		}
		return super.hurt(damageFrom, damageType, amount, location);
	}

	public double getHealth() {
		return health;
	}

	@Override
	public boolean isActive() {
		// active is visible
		return isSolid();
	}
}
