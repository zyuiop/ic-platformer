package platform.game.actors.blocks;

import platform.game.Signal;
import platform.util.Box;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * A platform that moves in two directions, as long as its signal is activated
 */
public class AlwaysMovingPlatform extends MovingPlatform {
	private double time = 0;
	private double speed = 1D;
	private double sleep = 1D;

	/**
	 * Create a platform that moves between first and second positions as long as the signal is active
	 * @param box the initial box of this platform
	 * @param sprite the sprite of the platform
	 * @param first the first position
	 * @param second the second position
	 * @param signal the signal to listen
	 * @param speed the speed of the platform
	 * @param sleep the sleep time at each extremity of the track
	 */
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
