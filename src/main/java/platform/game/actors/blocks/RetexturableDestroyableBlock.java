package platform.game.actors.blocks;

import java.util.function.Function;
import platform.game.Actor;
import platform.game.Effect;
import platform.util.Box;
import platform.util.Vector;

/**
 * A destroyable block that can have a different texture depending on its health
 */
public class RetexturableDestroyableBlock extends DestroyableBlock {
	private Function<Double, String> adapterFunction;

	/**
	 * Create a RetexturableDestroyableBlock. This is a special kind of {@link DestroyableBlock}
	 * that can change its texture when it's being destroyed.
	 *
	 * @param box the box of this block
	 * @param sprite the default sprite of this block
	 * @param maxHealth the maximum health of the block
	 * @param adapterFunction a function returning a string (the name of the sprite) depending on
	 * a double (the remaining health of the block), not null
	 */
	public RetexturableDestroyableBlock(Box box, String sprite, double maxHealth, Function<Double, String> adapterFunction) {
		super(box, sprite, maxHealth);
		if (adapterFunction == null)
			throw new NullPointerException();
		this.adapterFunction = adapterFunction;
	}

	@Override
	public boolean hurt(Actor damageFrom, Effect damageType, double amount, Vector location) {
		if (super.hurt(damageFrom, damageType, amount, location)) {
			setSpriteName(adapterFunction.apply(getHealth()));
			return true;
		}

		return false;
	}
}
