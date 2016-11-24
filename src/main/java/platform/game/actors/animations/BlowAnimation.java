package platform.game.actors.animations;

import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class BlowAnimation extends ParticleActor {
	private double time;

	public BlowAnimation(Vector position) {
		super(position, .8, "smoke.gray.1");
	}

	@Override
	public void update(Input input) {
		super.update(input);

		double oldTime = time;
		time = time + input.getDeltaTime();
		if (oldTime < .1 && time >= .1)
			setSpriteName("smoke.gray.2");
		else if (oldTime < .2 && time >= .2)
			setSpriteName("smoke.gray.3");
		else if (time >= .3)
			getWorld().unregister(this);
	}

	@Override
	public double getOpacity() {
		return 0.8 - time;
	}

	@Override
	public int getPriority() {
		return 50;
	}
}
