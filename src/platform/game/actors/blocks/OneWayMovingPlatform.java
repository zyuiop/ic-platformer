package platform.game.actors.blocks;

import platform.game.Signal;
import platform.util.Box;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * A moving platform that moves from its first to its second position when it's activated, and move
 * the other way when it's disactivated
 */
public class OneWayMovingPlatform extends MovingPlatform {
	private double speed = 1D;

	public OneWayMovingPlatform(Box box, String sprite, Vector first, Vector second, Signal signal) {
		super(box, sprite, first, second, signal);
	}

	public OneWayMovingPlatform(Box box, String sprite, Vector first, Vector second, Signal signal, double speed) {
		super(box, sprite, first, second, signal);
		this.speed = speed;
	}

	@Override
	public void update(Input input) {
		super.update(input);
		if (isActive()) {
			setCurrent(getCurrent() + input.getDeltaTime() * speed);
			if (getCurrent() > 1.0) { setCurrent(1D); }
		} else {
			setCurrent(getCurrent() - input.getDeltaTime() * speed);
			if (getCurrent() < 0.0) { setCurrent(0D); }
		}
	}
}
