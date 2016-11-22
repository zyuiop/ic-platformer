package platform.game.actors.blocks;

import platform.game.Actor;
import platform.game.Effect;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * Simple solid actor that does nothing.
 */
public abstract class RetexturableDestroyableBlock extends DestroyableBlock {
	public RetexturableDestroyableBlock(Box box, String sprite, double maxHealth) {
		super(box, sprite, maxHealth);
	}

	@Override
	public boolean hurt(Actor damageFrom, Effect damageType, double amount, Vector location) {
		if (super.hurt(damageFrom, damageType, amount, location)) {
			setSpriteName(getTexture(getHealth()));
			return true;
		}

		return false;
	}

	protected abstract String getTexture(double health);
}
