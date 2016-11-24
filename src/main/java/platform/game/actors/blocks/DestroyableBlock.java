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
 * A block that can be destroyed
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
	public Vector getPosition() {
		if (isSolid())
			return super.getPosition();
		return null;
	}

	@Override
	public void update(Input input) {
		super.update(input);

		if (getHealth() <= 0)
			getWorld().unregister(this);
	}

	@Override
	public boolean hurt(Actor damageFrom, Effect damageType, double amount, Vector location) {
		if (damageType.isHarming()) {
			health -= amount;
			return true;
		}
		return super.hurt(damageFrom, damageType, amount, location);
	}

	/**
	 * Get the health of this block. When the health reaches 0, the block is destroyed.
	 * @return the health of the block
	 */
	public double getHealth() {
		return health;
	}

	@Override
	public boolean isActive() {
		// active is visible
		return isSolid();
	}
}
