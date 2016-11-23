package platform.game.actors.blocks;

import platform.game.Signal;
import platform.util.Box;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class AlwaysMovingPlatform extends MovingPlatform {
	private double time = 0;
	private double speed = 1D;
	private double sleep = 1D;

	public AlwaysMovingPlatform(Box box, String sprite, Vector first, Vector second, Signal signal) {
		super(box, sprite, first, second, signal);
	}

	public AlwaysMovingPlatform(Box box, String sprite, Vector first, Vector second, Signal signal, double speed, double sleep) {
		super(box, sprite, first, second, signal);
		this.speed = speed;
		this.sleep = sleep;
	}

	@Override
	public void update(Input input) {
		super.update(input);

		if (!isActive()) { return; }

		time += input.getDeltaTime();
		if (time * speed <= 1) {
			setCurrent(time * speed); // allez
		} else if (time * speed > 1 + sleep * speed) {
			if (getCurrent() > 0) {
				setCurrent(1 - (time * speed - 1 - speed * sleep)); // go back
				if (getCurrent() < 0) { setCurrent(0D); }
			} else {
				setCurrent(0D);
				if (time * speed > 2 + (2 * sleep * speed)) {
					time = 0;
				}
			}
		}
	}
}
