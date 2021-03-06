package platform.game.actors.environment;

import platform.game.actors.Actor;
import platform.game.Effect;
import platform.game.Signal;
import platform.game.actors.DisplayableActor;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * An element that can be directly lit or unlit by the player using fireballs or blowing
 * It is also a signal that transmits its status (lit or not lit)
 */
public class Torch extends DisplayableActor implements Signal {
	private boolean lit;
	private double variation = 0D;

	public Torch(Vector position) {
		this(position, false);
	}

	public Torch(Vector position, boolean lit) {
		super(position, .8, "torch");
		this.lit = lit;
	}

	@Override
	public void update(Input input) {
		super.update(input);

		if (isLit() && !getSpriteName().contains("lit")) {
			setSpriteName("torch.lit.1");
			variation = 0D;
		} else if (!lit && getSpriteName().contains("lit")) {
			setSpriteName("torch");
		}

		if (isLit()) {
			variation += input.getDeltaTime();
			if (variation > 0.6)
				variation = 0D;

			if (variation >= 0.3 && getSpriteName().endsWith("1"))
				setSpriteName("torch.lit.2");
			else if (variation < 0.3 && getSpriteName().endsWith("2"))
				setSpriteName("torch.lit.1");
		}
	}

	public boolean isLit() {
		return lit;
	}

	public void setLit(boolean lit) {
		this.lit = lit;
	}

	@Override
	public boolean hurt(Actor damageFrom, Effect damageType, double amount, Vector location) {
		if (damageType == Effect.FIRE && !isLit()) {
			setLit(true);
			return true;
		}

		if (damageType == Effect.AIR && isLit()) {
			setLit(false);
			return true;
		}

		return super.hurt(damageFrom, damageType, amount, location);
	}

	@Override
	public int getPriority() {
		return 30;
	}

	@Override
	public boolean isActive() {
		return lit;
	}
}
